
package snake.control;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import snake.model.*;
import snake.view.*;


public class WindowControl {
	
	private enum State {
		STARTUP,
		IN_GAME_SINGLEPLAYER,
		IN_GAME_MULTIPLAYER,
		IN_GAME_INTERNET
	}
	
	private State state;
	
	// The model classes.
	private GameSingleplayer gameSingleplayer;
	private GameMultiplayer gameMultiplayer;
	
	// The view classes.
	private JFrame window;
	private Audio audio;
	private Menu menu;
	private Controls controls;
	private Header header;
	private HeaderSingleplayer headerSingleplayer;
	private HeaderMultiplayer headerMultiplayer;
	private OptionsSingleplayer optionsSingleplayer;
	private OptionsMultiplayer optionsMultiplayer;
	private BoardSingleplayer boardSingleplayer;
	private BoardMultiplayer boardMultiplayer;
	
	// The control classes.
	private MenuListener menuListener;
	private ControlsListener controlsListener;
	private HeaderListener headerListener;
	private OptionsSingleplayerListener optionsSingleplayerListener;
	private OptionsMultiplayerListener optionsMultiplayerListener;
	private BoardSingleplayerListener boardSingleplayerListener;
	private BoardMultiplayerListener boardMultiplayerListener;
	private BoardNetworkListener boardInternetListener;
	
	public WindowControl() {
		this.state = State.STARTUP;
		
		// Model classes.
		this.gameSingleplayer = new GameSingleplayer();
		this.gameMultiplayer = new GameMultiplayer();
		
		// View classes.
		this.window = new JFrame();
		this.audio = new Audio();
		this.menu = new Menu();
		this.controls = new Controls();
		this.header = new Header(this.audio);
		this.headerSingleplayer = new HeaderSingleplayer(this.audio, this.gameSingleplayer);
		this.headerMultiplayer = new HeaderMultiplayer(this.audio, this.gameMultiplayer);
		this.optionsSingleplayer = new OptionsSingleplayer(gameSingleplayer);
		this.optionsMultiplayer = new OptionsMultiplayer(gameMultiplayer);
		this.boardSingleplayer = new BoardSingleplayer(gameSingleplayer);
		this.boardMultiplayer = new BoardMultiplayer(gameMultiplayer);
		
		// Control classes.
		this.menuListener = new MenuListener(this, this.audio);
		this.controlsListener = new ControlsListener(this, this.audio);
		this.headerListener = new HeaderListener(this.audio);
		this.optionsSingleplayerListener = new OptionsSingleplayerListener(this, this.audio, boardSingleplayer, 
				optionsSingleplayer, gameSingleplayer);
		this.optionsMultiplayerListener = new OptionsMultiplayerListener(this, this.audio, boardMultiplayer, 
				optionsMultiplayer, gameMultiplayer);
		this.boardSingleplayerListener = new BoardSingleplayerListener(this, this.audio, gameSingleplayer);
		this.boardMultiplayerListener = new BoardMultiplayerListener(this, this.audio, gameMultiplayer);
		this.boardInternetListener = new BoardNetworkListener(this, this.window, this.audio, gameMultiplayer);
		
		// Bind the view and control classes together.
		this.menu.addButtonListener(menuListener);
		this.menu.addKeyListener(menuListener);
		this.controls.addButtonListener(controlsListener);
		this.controls.addKeyListener(controlsListener);
		this.header.addButtonListener(headerListener);
		this.headerSingleplayer.addButtonListener(headerListener);
		this.headerMultiplayer.addButtonListener(headerListener);
		this.optionsSingleplayer.addButtonListener(optionsSingleplayerListener);
		this.optionsSingleplayer.addKeyListener(optionsSingleplayerListener);
		this.optionsMultiplayer.addButtonListener(optionsMultiplayerListener);
		this.optionsMultiplayer.addKeyListener(optionsMultiplayerListener);
		this.boardSingleplayer.addButtonListener(boardSingleplayerListener);
		this.boardSingleplayer.addKeyListener(boardSingleplayerListener);
		this.boardMultiplayer.addButtonListener(boardMultiplayerListener);
		this.boardMultiplayer.addKeyListener(boardMultiplayerListener);
		
		// Setup the initial frame.
		window.setTitle("Snake");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setIconImage(CustomImages.APPLE.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		window.setMinimumSize(new Dimension(230, 300));
		window.setPreferredSize(new Dimension(800, 800));
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
	public void close() {
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}
	
	public void startSingleplayerGame() {
		gameSingleplayer.reset();
		showOptionsSingleplayer();
	}
	
	public void startMultiplayerGame() {
		gameMultiplayer.reset();
		showOptionsMultiplayer();
	}
	
	public void startInternetGame() {
		gameMultiplayer.reset();
		showGameInternet();
	}
	
	public void showMenu() {
		setFrameComponents(header, menu);
	}
	
	public void showControls() {
		setFrameComponents(header, controls);
	}
	
	public void showOptionsSingleplayer() {
		setFrameComponents(headerSingleplayer, optionsSingleplayer);
		audio.registerGame(gameSingleplayer);
	}
	
	public void showOptionsMultiplayer() {
		setFrameComponents(headerMultiplayer, optionsMultiplayer);
		audio.registerGame(gameMultiplayer);
	}
	
	public void showGameSingleplayer() {
		closeInternetGame();
		setFrameComponents(headerSingleplayer, boardSingleplayer);
		audio.registerGame(gameSingleplayer);
		state = State.IN_GAME_SINGLEPLAYER;
	}
	
	public void showGameMultiplayer() {
		closeInternetGame();
		setFrameComponents(headerMultiplayer, boardMultiplayer);
		audio.registerGame(gameMultiplayer);
		state = State.IN_GAME_MULTIPLAYER;
	}
	
	public void showGameInternet() {
		boolean connectionSucces = establishInternetGame();
		if (connectionSucces) {
			setFrameComponents(headerMultiplayer, boardMultiplayer);
			audio.registerGame(gameMultiplayer);
			state = State.IN_GAME_INTERNET;
		}
	}
	
	public void switchBackToGame() {
		switch (state) {
			case IN_GAME_SINGLEPLAYER:
				showGameSingleplayer();
				gameSingleplayer.resume();
				break;
			case IN_GAME_MULTIPLAYER:
				showGameMultiplayer();
				gameMultiplayer.resume();
				break;
			case IN_GAME_INTERNET:
				showGameInternet();
				break;
			default:
				break;
		}
	}
	
	private boolean establishInternetGame() {
		if (state == State.IN_GAME_INTERNET) {
			return true;
		}
		boolean connectionSucces = boardInternetListener.establishConnection();
		if (connectionSucces) {
			boardMultiplayer.removeKeyListener(boardMultiplayerListener);
			boardMultiplayer.removeButtonListener(boardMultiplayerListener);
			boardMultiplayer.addKeyListener(boardInternetListener);
			boardMultiplayer.addButtonListener(boardInternetListener);
			gameMultiplayer.addObserver(boardInternetListener);
			gameMultiplayer.start();
		}
		return connectionSucces;
	}
	
	private void closeInternetGame() {
		if (state == State.IN_GAME_INTERNET) {
			boardInternetListener.closeConnection();
			boardMultiplayer.removeKeyListener(boardInternetListener);
			boardMultiplayer.removeButtonListener(boardInternetListener);
			gameMultiplayer.deleteObserver(boardInternetListener);
			boardMultiplayer.addKeyListener(boardMultiplayerListener);
			boardMultiplayer.addButtonListener(boardMultiplayerListener);
		}
	}
	
	private void setFrameComponents(Component headerPanel, Component centerPanel) {
		// Remove current content pane components and add the new ones.
		window.getContentPane().removeAll();
		window.getContentPane().add(headerPanel, BorderLayout.NORTH);
		window.getContentPane().add(centerPanel, BorderLayout.CENTER);
		window.getContentPane().revalidate();
		window.getContentPane().repaint();
		
		// Let the center panel be the target of keyboard events.
		centerPanel.requestFocus();
	}
	
}








