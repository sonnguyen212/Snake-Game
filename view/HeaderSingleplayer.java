package snake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import snake.model.GameSingleplayer;


public class HeaderSingleplayer extends Header implements Observer {
	
	private static final Font SCORE_FONT = new Font("Sans_Serif", Font.BOLD, 20);
	private GameSingleplayer game;
	
	public HeaderSingleplayer(Audio audio, GameSingleplayer game) {
		super(audio);
		if (game == null) {
			throw new NullPointerException();
		}
		this.game = game;
		game.addObserver(this);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		if (game.isStarted()) {
			drawScoreText((Graphics2D)context);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		repaint();
	}
	
	private void drawScoreText(Graphics2D context) {
		Dimension size = getSize();
		int fontSize = SCORE_FONT.getSize();
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(SCORE_FONT);
		context.setColor(Color.WHITE);
		context.drawString("Score: " + game.getScore(), fontSize, size.height/2 - fontSize/2);
	}
	
}
