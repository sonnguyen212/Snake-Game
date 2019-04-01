package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.model.Direction;
import snake.model.GameSingleplayer;
import snake.view.Audio;


public class BoardSingleplayerListener extends KeyAdapter implements ActionListener {

	private GameSingleplayer game;
	private WindowControl control;
	private Audio audio;
	
	public BoardSingleplayerListener(WindowControl control, Audio audio, GameSingleplayer game) {
		if (control == null || audio == null || game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.control = control;
		this.audio = audio;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "restart") {
			game.reset();
			game.start();
		} 
		else if (event.getActionCommand() == "menu") {
			control.showMenu();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.move(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				game.move(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				game.move(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				game.move(Direction.RIGHT);
				break;
			case KeyEvent.VK_W:
				game.move(Direction.UP);
				break;
			case KeyEvent.VK_S:
				game.move(Direction.DOWN);
				break;
			case KeyEvent.VK_A:
				game.move(Direction.LEFT);
				break;
			case KeyEvent.VK_D:
				game.move(Direction.RIGHT);
				break;
			case KeyEvent.VK_P:
				game.togglePause();
				break;
			case KeyEvent.VK_ENTER: 
			case KeyEvent.VK_SPACE:
				if (game.isEnded()){
					game.reset();
					game.start();
				}
				break;
			case KeyEvent.VK_M:
				audio.toggleMute();
				break;
			case KeyEvent.VK_ESCAPE:
				game.pause();
				control.showMenu();
				break;
			default:
				break;
		}
	}

}
