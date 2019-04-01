package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.model.*;
import snake.view.*;


public class OptionsSingleplayerListener extends KeyAdapter implements ActionListener {
	
	private GameSingleplayer game;
	private WindowControl control;
	private Audio audio;
	private BoardSingleplayer board;
	private OptionsSingleplayer options;
	private Difficulty difficulty;
	
	public OptionsSingleplayerListener(WindowControl control, Audio audio, BoardSingleplayer board, 
			OptionsSingleplayer options, GameSingleplayer game) {
		if (control == null || audio == null || board == null || options == null || game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		this.control = control;
		this.audio = audio;
		this.board = board;
		this.options = options;
		this.difficulty = Difficulty.EASY;
		board.setSnakeColor(CustomColors.GREEN);
		options.actionPerformed(new ActionEvent(this, 0, "easy"));
		options.actionPerformed(new ActionEvent(this, 0, "green"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		switch (command) {
			case "play":
				attemptGameStart();
				break;
			case "back":
				control.showMenu();
				break;
			case "kindergarten":
				difficulty = Difficulty.KINDERGARTEN;
				break;
			case "easy":
				difficulty = Difficulty.EASY;
				break;
			case "intermediate":
				difficulty = Difficulty.INTERMEDIATE;
				break;
			case "hard":
				difficulty = Difficulty.HARD;
				break;
			case "green":
				board.setSnakeColor(CustomColors.GREEN);
				break;
			case "blue":
				board.setSnakeColor(CustomColors.BLUE);
				break;
			case "red":
				board.setSnakeColor(CustomColors.RED);
				break;
			case "yellow":
				board.setSnakeColor(CustomColors.YELLOW);
				break;
			default:
				break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				attemptGameStart();
				break;
			case KeyEvent.VK_M:
				audio.toggleMute();
				break;
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_BACK_SPACE:
				control.showMenu();
				break;
			default:
				break;
		}
	}
	
	private void attemptGameStart() {
		if (!options.hasBoardSizeInput()) {
			return;
		}
		game.setBoardSize(options.getBoardSizeInput());
		
		if (difficulty == Difficulty.KINDERGARTEN){
			game.disableTimedMovement(); 
		} 
		else if (difficulty == Difficulty.EASY){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(300);
		} 
		else if (difficulty == Difficulty.INTERMEDIATE){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(150);
		} 
		else if (difficulty == Difficulty.HARD){
			game.enableTimedMovement();
			game.setTimedMovementSpeed(70);
		}
		
		control.showGameSingleplayer();
		game.start();
	}

}
