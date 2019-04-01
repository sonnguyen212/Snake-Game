package snake.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameMultiplayer;
import snake.model.Player;


public class BoardMultiplayer extends BoardAbstract  implements Observer {
	
	private static final Font SCORE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	
	private GameMultiplayer game;
	private Color snakeColorPlayerOne;
	private Color snakeColorPlayerTwo;
	
	public BoardMultiplayer(GameMultiplayer game) {
		super(game);
		this.game = game;
		this.snakeColorPlayerOne = CustomColors.GREEN;
		this.snakeColorPlayerTwo = CustomColors.RED;
		game.addObserver(this);
	}
	
	public void setSnakeColor(Player player, Color color) {
		if (player == Player.ONE) {
			snakeColorPlayerOne = color;
		}
		else {
			snakeColorPlayerTwo = color;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		
		Board board = game.getBoard();
		drawBoard(context2D, board);
		drawFood(context2D, game.getFood(), board);
		drawSnake(context2D, game.getSnake(Player.ONE), snakeColorPlayerOne, board);
		drawSnake(context2D, game.getSnake(Player.TWO), snakeColorPlayerTwo, board);
		
		if (game.isEnded()){
			drawOverlayGameEnded(context2D, board);
		}
		else if (game.isPaused()) {
			drawOverlayPaused(context2D, board);
		}
	}
	
	@Override
	protected void drawOverlayGameEnded(Graphics2D context, Board board) {
		super.drawOverlayGameEnded(context, board);
		drawWinnerTextAndImage(context, board);
	}
	
	private void drawWinnerTextAndImage(Graphics2D context, Board board) {

		String text = null;
		Image image = null;
		Player winner = game.getWinner();
		if (winner == Player.ONE) {
			image = CustomImages.TITLE_PLAYER_ONE;
			text = "Final score for player 1: " + game.getScore(winner);
		} 
		else if (winner == Player.TWO) {
			image = CustomImages.TITLE_PLAYER_TWO;
			text = "Final score for player 2: " + game.getScore(winner);
		}
		else {
			image = CustomImages.TITLE_GAME_OVER;
			text = "It is a tie.";
		}
		
		Rectangle boardRect = getRectangleForBoard(board);
		Rectangle popupRectangle = getRectangleForPopUp(board);
		int titleX = boardRect.x + boardRect.width/2 - CustomImages.TITLE_PLAYER_ONE.getWidth()/2;
		int titleY = popupRectangle.y;
		context.drawImage(image, titleX, titleY, null);
		
		int scoreX = boardRect.x + boardRect.width/2 - 5*text.length();
		int scoreY = boardRect.y + boardRect.height/2 + 5;
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setColor(CustomColors.PANEL);
		context.setFont(SCORE_FONT);
		context.drawString(text, scoreX, scoreY);
	}
	
}
