package snake.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;


public class Menu extends JPanel {
	
	private static final long serialVersionUID = 4427452065585644066L;
	public static final int MENU_SIDE_LENGTH = 500;
	public static final int MENU_MARGIN = 20;
	public static final int BUTTON_WIDTH = 140;
	public static final int BUTTON_HEIGHT = 50;
	public static final int BUTTON_GAP = 20;
	
	private JButton buttonSingleplayer;
	private JButton buttonMultiplayer;
	private JButton buttonInternet;
	private JButton buttonControls;
	private JButton buttonQuit;
	
	public Menu() {
		super();
		
		// Menu Buttons
		buttonSingleplayer = createMenuButton(CustomImages.BUTTON_SINGLEPLAYER, "singleplayer");
		buttonMultiplayer = createMenuButton(CustomImages.BUTTON_MULTIPLAYER, "multiplayer");
		buttonInternet = createMenuButton(CustomImages.BUTTON_INTERNET, "internet");
		buttonControls = createMenuButton(CustomImages.BUTTON_CONTROLS, "controls");
		buttonQuit = createMenuButton(CustomImages.BUTTON_QUIT, "quit");

		add(buttonSingleplayer);
		add(buttonMultiplayer);
		add(buttonInternet);
		add(buttonControls);
		add(buttonQuit);
	}
	
	public void addButtonListener(ActionListener listener) {
		buttonSingleplayer.addActionListener(listener);
		buttonMultiplayer.addActionListener(listener);
		buttonInternet.addActionListener(listener);
		buttonControls.addActionListener(listener);
		buttonQuit.addActionListener(listener);
	}
	
	public void removeButtonListener(ActionListener listener) {
		buttonSingleplayer.removeActionListener(listener);
		buttonMultiplayer.removeActionListener(listener);
		buttonInternet.removeActionListener(listener);
		buttonControls.removeActionListener(listener);
		buttonQuit.removeActionListener(listener);
	}

	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		drawBackground(context2D, getVisibleRect());
		drawMenuBackground(context2D, computeMenuRectangle(getSize()));
		drawMenu(context2D);
	}
	
	private void drawMenu(Graphics2D context) {
		// Title Image
		Rectangle menuRect = computeMenuRectangle(getSize());
		int titleWidth = CustomImages.TITLE_MENU.getWidth();
		int titleHeight = CustomImages.TITLE_MENU.getHeight();
		int titleX = menuRect.x + menuRect.width/2 - titleWidth/2;
		int titleY = menuRect.y + MENU_MARGIN;
		context.drawImage(CustomImages.TITLE_MENU, titleX, titleY, null);
		
		// Set button positions.
		int initialX = menuRect.x + menuRect.width/2 - BUTTON_WIDTH/2;
		int initialY = titleY + titleHeight + 50;
		buttonSingleplayer.setLocation(initialX, initialY);
		buttonMultiplayer.setLocation(initialX, initialY + (BUTTON_HEIGHT + BUTTON_GAP));
		buttonInternet.setLocation(initialX, initialY + 2 * (BUTTON_HEIGHT + BUTTON_GAP));
		buttonControls.setLocation(initialX, initialY + 3 * (BUTTON_HEIGHT + BUTTON_GAP));
		buttonQuit.setLocation(initialX, initialY + 4 * (BUTTON_HEIGHT + BUTTON_GAP) + MENU_MARGIN);
	}
	
	public static void drawMenuBackground(Graphics2D context, Rectangle rectangle) {
		context.setColor(CustomColors.POPUP);
		context.fill(rectangle);
	}
	
	public static void drawBackground(Graphics2D context, Rectangle rectangle) {
		for (int x = 0; x < rectangle.x + rectangle.width; x += CustomImages.BACKGROUND.getWidth()) {
			for (int y = 0; y < rectangle.y + rectangle.height; y += CustomImages.BACKGROUND.getHeight()) {
				context.drawImage(CustomImages.BACKGROUND, rectangle.x + x, rectangle.y + y, null);
			}
		}
	}
	
	public static JButton createMenuButton(BufferedImage image, String actionCommand) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setFocusable(false);
		button.setActionCommand(actionCommand);
		return button;
	}
	
	public static Rectangle computeMenuRectangle(Dimension windowSize) {
		int offsetWidth = (windowSize.width - MENU_SIDE_LENGTH) / 2;
		int offsetHeight = (windowSize.height - MENU_SIDE_LENGTH) / 2;
		return new Rectangle(offsetWidth, offsetHeight, MENU_SIDE_LENGTH, MENU_SIDE_LENGTH);
	}
	
}
