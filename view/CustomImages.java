package snake.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class CustomImages {
	
	public static final BufferedImage LOGO;
	public static final BufferedImage BACKGROUND;
	public static final BufferedImage APPLE;
	public static final BufferedImage SNAKE_HEAD_UP;
	public static final BufferedImage SNAKE_HEAD_DOWN;
	public static final BufferedImage SNAKE_HEAD_LEFT;
	public static final BufferedImage SNAKE_HEAD_RIGHT;
	public static final BufferedImage SNAKE_CORNER_BL;
	public static final BufferedImage SNAKE_CORNER_BR;
	public static final BufferedImage SNAKE_CORNER_TL;
	public static final BufferedImage SNAKE_CORNER_TR;
	public static final BufferedImage SNAKE_TAIL_UP;
	public static final BufferedImage SNAKE_TAIL_DOWN;
	public static final BufferedImage SNAKE_TAIL_LEFT;
	public static final BufferedImage SNAKE_TAIL_RIGHT;
	public static final BufferedImage SNAKE_HORIZONTAL;
	public static final BufferedImage SNAKE_VERTICAL;
	public static final BufferedImage CONTROLS;
	public static final BufferedImage INFO_KEYS;
	public static final BufferedImage SOUND_ON;
	public static final BufferedImage SOUND_OFF;
	public static final BufferedImage TITLE_GAME_OVER;
	public static final BufferedImage TITLE_GAME_WON;
	public static final BufferedImage TITLE_PAUSED;
	public static final BufferedImage TITLE_MENU;
	public static final BufferedImage TITLE_CONTROLS;
	public static final BufferedImage TITLE_PLAYER_ONE;
	public static final BufferedImage TITLE_PLAYER_TWO;
	public static final BufferedImage BUTTON_SINGLEPLAYER;
	public static final BufferedImage BUTTON_MULTIPLAYER;
	public static final BufferedImage BUTTON_INTERNET;
	public static final BufferedImage BUTTON_CONTROLS;
	public static final BufferedImage BUTTON_HIGHSCORE;
	public static final BufferedImage BUTTON_QUIT;
	public static final BufferedImage BUTTON_PLAY;
	public static final BufferedImage BUTTON_PLAY_AGAIN; 
	public static final BufferedImage BUTTON_MENU;
	public static final BufferedImage BUTTON_BACK;
	public static final BufferedImage DIFFICULTY_KINDERGARTEN;
	public static final BufferedImage DIFFICULTY_EASY;
	public static final BufferedImage DIFFICULTY_INTERMEDIATE;
	public static final BufferedImage DIFFICULTY_HARD;
	public static final BufferedImage BUTTON_GREEN;
	public static final BufferedImage BUTTON_BLUE;
	public static final BufferedImage BUTTON_RED;
	public static final BufferedImage BUTTON_YELLOW;
	
	private static final String IMAGE_PATH = "/images/";

	static {
		LOGO = loadImage("SnakeLogo.png");
		BACKGROUND = loadImage("TileBackground.png");

		APPLE = loadImage("Apple.png");
		SNAKE_HEAD_UP = loadImage("SnakeHeadU.png");
		SNAKE_HEAD_DOWN = loadImage("SnakeHeadD.png");
		SNAKE_HEAD_LEFT = loadImage("SnakeHeadL.png");
		SNAKE_HEAD_RIGHT = loadImage("SnakeHeadR.png");
		SNAKE_CORNER_BR = loadImage("SnakeCornerBL.png");
		SNAKE_CORNER_BL = loadImage("SnakeCornerBR.png");
		SNAKE_CORNER_TR = loadImage("SnakeCornerTL.png");
		SNAKE_CORNER_TL = loadImage("SnakeCornerTR.png");
		SNAKE_TAIL_UP = loadImage("SnakeTailU.png");
		SNAKE_TAIL_DOWN = loadImage("SnakeTailD.png");
		SNAKE_TAIL_LEFT = loadImage("SnakeTailL.png");
		SNAKE_TAIL_RIGHT = loadImage("SnakeTailR.png");
		SNAKE_HORIZONTAL = loadImage("SnakeHorizontal.png");
		SNAKE_VERTICAL = loadImage("SnakeVertical.png");

		CONTROLS = loadImage("ControlsImage.png");
		INFO_KEYS = loadImage("InfoKeys.png");
		SOUND_ON = loadImage("SoundOn.png");
		SOUND_OFF = loadImage("SoundOff.png");
		TITLE_GAME_OVER = loadImage("TitleGameOver.png");
		TITLE_GAME_WON = loadImage("TitleGameWon.png");
		TITLE_PAUSED = loadImage("TitlePaused.png");
		TITLE_MENU = loadImage("TitleMenu.png");
		TITLE_CONTROLS = loadImage("TitleControls.png");
		TITLE_PLAYER_ONE = loadImage("TitleWinnerOne.png");
		TITLE_PLAYER_TWO = loadImage("TitleWinnerTwo.png");

		BUTTON_SINGLEPLAYER = loadImage("ButtonSingleplayer.png");
		BUTTON_MULTIPLAYER = loadImage("ButtonMultiplayer.png");
		BUTTON_INTERNET = loadImage("ButtonMultiplayerInternet.png");
		BUTTON_CONTROLS = loadImage("ButtonControls.png");
		BUTTON_HIGHSCORE = loadImage("ButtonHighScores.png");
		BUTTON_QUIT = loadImage("ButtonQuit.png");
		BUTTON_PLAY = loadImage("ButtonPlay.png");
		BUTTON_PLAY_AGAIN = loadImage("ButtonPlayAgain.png");
		BUTTON_MENU = loadImage("ButtonMenu.png");
		BUTTON_BACK = loadImage("ButtonBack.png");
		BUTTON_GREEN = loadImage("ColourGreen.png");
		BUTTON_BLUE = loadImage("ColourBlue.png");
		BUTTON_RED = loadImage("ColourRed.png");
		BUTTON_YELLOW = loadImage("ColourYellow.png");
		
		DIFFICULTY_KINDERGARTEN = loadImage("DifficultyKindergarten.png");
		DIFFICULTY_EASY = loadImage("DifficultyEasy.png");
		DIFFICULTY_INTERMEDIATE = loadImage("DifficultyIntermediate.png");
		DIFFICULTY_HARD = loadImage("DifficultyHard.png");
	}

	private static BufferedImage loadImage(String filename) {
		try {
			// We load external resources by an URL. With this we can use JAR files.
			URL location = CustomImages.class.getResource(IMAGE_PATH + filename);
			return ImageIO.read(location);
		}
		catch (IOException error) {
			throw new RuntimeException("Unable to load image " + filename + ": " + error.getMessage());
		}
	}

}
