package snake.view;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import snake.model.Board;
import snake.model.GameSingleplayer;


public class BoardSingleplayer extends BoardAbstract  implements Observer {
	
	private static final Color DEFAULT_SNAKE_COLOR = new Color(84, 216, 81);
	private static final Font SCORE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	
	private GameSingleplayer game;
	private Color snakeColor;
	
	public BoardSingleplayer(GameSingleplayer game) {
		super(game);
		this.game = game;
		this.snakeColor = DEFAULT_SNAKE_COLOR;
		game.addObserver(this);
	}
	
	public void setSnakeColor(Color color) {
		snakeColor = color;
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
		drawSnake(context2D, game.getSnake(), snakeColor, board);
		
		if (game.isLost()) {
			drawOverlayGameLost(context2D, board);
		} 
		else if (game.isWon()){
			drawOverlayGameWon(context2D, board);
		}
		else if (game.isPaused()) {
			drawOverlayPaused(context2D, board);
		}
	}
	
	@Override
	protected void drawOverlayGameEnded(Graphics2D context, Board board) {
		super.drawOverlayGameEnded(context, board);
		drawScoreText(context, board);
	}
	
	private void drawScoreText(Graphics2D context, Board board) {
		String text = "Final Score: " + game.getScore();
		Rectangle boardRect = getRectangleForBoard(board);
		int x = boardRect.x + boardRect.width/2 - 5*text.length();
		int y = boardRect.y + boardRect.height/2 + 5;
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setColor(CustomColors.PANEL);
		context.setFont(SCORE_FONT);
		context.drawString(text, x, y);
	}
	
}
