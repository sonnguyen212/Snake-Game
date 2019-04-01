package snake.control;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.Timer;

import snake.model.*;
import snake.view.*;


public class BoardNetworkListener extends KeyAdapter implements ActionListener , Observer {
	
	private static final int SERVER_TIME_OUT = 60*1000;
	private static final int CLIENT_TIME_OUT = 30*1000;
	private static final int PORT = 4444;

	private WindowControl control;
	private JFrame window;
	private Audio audio;
	
	private GameMultiplayer game;
	private Player player;
	private Player opponent;
	
	private ServerSocket server;
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
	private Timer timer;
	private int timerUpdateInterval = 16;
	
	public BoardNetworkListener(WindowControl control, JFrame window, Audio audio, GameMultiplayer game) {
		if (control == null || window == null || audio == null || game == null) {
			throw new NullPointerException();
		}
		this.control = control;
		this.window = window;
		this.audio = audio;
		this.game = game;
		this.player = Player.NONE;
		this.opponent = Player.NONE;
		this.server = null;
		this.client = null;
		this.out = null;
		this.in = null;
		this.timer = new Timer(timerUpdateInterval, this);
	}
	
	public boolean establishConnection() {
		player = OptionsNetwork.showPlayerSelectionDialog(window, PORT);
		if (player == Player.NONE) {
			return false;
		}
		opponent = (player == Player.ONE) ? Player.TWO : Player.ONE;

		// Player 1 is the server and player 2 connects as a client.
		boolean establishSucces = false;
		if (player == Player.ONE) {
			establishSucces = establishServerAndWaitForClient();
		}
		else {
			String hostname = OptionsNetwork.showServerLocationInputDialog(window); 
			if (!hostname.isEmpty()) {
				establishSucces = establishClient(hostname);
			}
		}
		
		if (establishSucces) {
			game.disableTimedMovement();
			timer.start();
		}
		
		return establishSucces;
	}
	
	public void closeConnection() {
		if (client != null) {
			try {
				client.close();
			}
			catch (IOException error) {
				System.out.println("unable to close client connection: " + error.getMessage());
			}
			client = null;
		}
		if (server != null) {
			try {
				server.close();
			}
			catch (IOException error) {
				System.out.println("unable to close server connection: " + error.getMessage());
			}
			server = null;
		}
		out = null;
		in = null;
		player = Player.NONE;
		timer.stop();
	}
	
	private boolean establishServerAndWaitForClient() {
		try {
			server = new ServerSocket();
			server.setReuseAddress(true);
			server.bind(new InetSocketAddress(PORT));
			server.setSoTimeout(SERVER_TIME_OUT);
			client = server.accept();
			out = new PrintWriter(client.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			return true;
		}
		catch (IOException error) {
			System.out.println("server error: " + error.getMessage());
			closeConnection();
			return false;
		}
	}
	
	private boolean establishClient(String hostname) {
		client = new Socket();
		int elapsedTime = 0;
		while (elapsedTime < CLIENT_TIME_OUT) {
			try {
				client.connect(new InetSocketAddress(hostname, PORT));
				out = new PrintWriter(client.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				return true;
			}
			catch (IOException error) {
				int sleepTime = 1000;
				try {
					Thread.sleep(sleepTime);
				} 
				catch (InterruptedException interrupt) {
					break;
				}
				elapsedTime += sleepTime;
			}
		}
		System.out.println("client error: timeout");
		closeConnection();
		return false;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				sendAndExecute("MOVEUP");
				break;
			case KeyEvent.VK_DOWN:
				sendAndExecute("MOVEDOWN");
				break;
			case KeyEvent.VK_LEFT:
				sendAndExecute("MOVELEFT");
				break;
			case KeyEvent.VK_RIGHT:
				sendAndExecute("MOVERIGHT");
				break;
			case KeyEvent.VK_W:
				sendAndExecute("MOVEUP");
				break;
			case KeyEvent.VK_S:
				sendAndExecute("MOVEDOWN");
				break;
			case KeyEvent.VK_A:
				sendAndExecute("MOVELEFT");
				break;
			case KeyEvent.VK_D:
				sendAndExecute("MOVERIGHT");
				break;
			case KeyEvent.VK_P:
				if (game.isPaused()) {
					sendAndExecute("RESUME");
				}
				else {
					sendAndExecute("PAUSE");
				}
				break;
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:
				if (game.isEnded()){
					sendAndExecute("RESTART");
				}
				break;
			case KeyEvent.VK_ESCAPE:
				control.showMenu();
				break;
			case KeyEvent.VK_M:
				audio.toggleMute();
				break;
			default:
				break;
		}
	}
	
	private void sendAndExecute(String command) {
		out.println(command);
		execute(player, command);
	}
	
	private void execute(Player player, String command) {
		Scanner commandParse = new Scanner(command);
		String firstArgument = "";
		if (commandParse.hasNext()) {
			firstArgument = commandParse.next();
		}
		switch (firstArgument) {
			case "FOOD":
				if (!commandParse.hasNextInt()) {
					break;
				}
				int x = commandParse.nextInt();
				if (!commandParse.hasNextInt()) {
					break;
				}
				int y = commandParse.nextInt();
				game.setFood(new Food(new Field(x, y)));
				break;
			case "MOVELEFT":
				game.move(player, Direction.LEFT);
				break;
			case "MOVERIGHT":
				game.move(player, Direction.RIGHT);
				break;
			case "MOVEUP":
				game.move(player, Direction.UP);
				break;
			case "MOVEDOWN":
				game.move(player, Direction.DOWN);
				break;
			case "PAUSE":
				game.pause();
				break;
			case "RESUME":
				game.resume();
				break;
			case "RESTART":
				game.reset();
				game.start();
				break;
			default:
				break;
		}
		commandParse.close();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		// Button presses.
		if (event.getActionCommand() == "restart") {
			sendAndExecute("RESTART");
			return;
		} 
		else if (event.getActionCommand() == "menu") {
			control.showMenu();
			return;
		}
		
		// Timer update. Check if the other computer has sent any commands.
		try {
			while (in.ready()) {
				String command = in.readLine();
				execute(opponent, command);
			}
		}
		catch (IOException error) {
			System.out.println("connection failed");
			control.showMenu();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		
		// Player 1 (the server) handles the placement of food. If we generate new 
		// food the location is sent to player 2.
		if (server == null) {
			return;
		}
		
		Event event = (Event)arg;
		Event.Type type = event.getType();
		if (type == Event.Type.EAT || type == Event.Type.START) {
			Field foodPos = game.getFood().getPosition();
			out.println("FOOD " + foodPos.getRow() + " " + foodPos.getColumn());
		}
	}
	
}




