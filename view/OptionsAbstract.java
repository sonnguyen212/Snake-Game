package snake.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import snake.model.Board;


public abstract class OptionsAbstract extends JPanel implements ActionListener {
	
	private static final Font FONT_LARGE = new Font("Sans_Serif", Font.PLAIN, 20);
	private static final Font FONT_MEDIUM = new Font("Sans_Serif", Font.PLAIN, 15);
	private static final Font FONT_SMALL = new Font("Sans_Serif", Font.PLAIN, 11);
	private static final Border THICK_BORDER = new LineBorder(CustomColors.PANEL, 3);
	
	protected JPanel gameOptions;
	private JButton buttonPlay;
	private JButton buttonKindergarten;
	private JButton buttonEasy;
	private JButton buttonIntermediate;
	private JButton buttonHard;
	private JButton buttonBack;
	
	private JFormattedTextField boardInputWidth;
	private JFormattedTextField boardInputHeight;
	private JLabel boardInputError;
	
	public OptionsAbstract() {
		loadBoardInputFields();
		loadButtons();
		loadGameOptions();
		add(gameOptions);
	}
	
	private void loadBoardInputFields() {
		// Limit text field input to three digits.
		try {
			boardInputWidth = new JFormattedTextField(new MaskFormatter("###"));
			boardInputHeight = new JFormattedTextField(new MaskFormatter("###"));
		} 
		catch (java.text.ParseException error) {
			throw new RuntimeException(error.getMessage());
		}

		setTextInTextField(boardInputWidth, "20");
		setTextInTextField(boardInputHeight, "20");
		boardInputError = new JLabel();
		boardInputError.setForeground(Color.RED);
	}
	
	private void loadButtons() {
		buttonPlay = Menu.createMenuButton(CustomImages.BUTTON_PLAY, "play");
		buttonBack = Menu.createMenuButton(CustomImages.BUTTON_BACK, "back");
		buttonKindergarten = createDifficultyButton(CustomImages.DIFFICULTY_KINDERGARTEN, "kindergarten");
		buttonEasy = createDifficultyButton(CustomImages.DIFFICULTY_EASY, "easy");
		buttonIntermediate = createDifficultyButton(CustomImages.DIFFICULTY_INTERMEDIATE, "intermediate");
		buttonHard = createDifficultyButton(CustomImages.DIFFICULTY_HARD, "hard");
	}
	
	private void loadGameOptions() {
		gameOptions = new JPanel();
		gameOptions.add(boardInputWidth);
		gameOptions.add(boardInputHeight);
		gameOptions.add(boardInputError);
		gameOptions.add(buttonKindergarten);
		gameOptions.add(buttonEasy);
		gameOptions.add(buttonIntermediate);
		gameOptions.add(buttonHard);
		gameOptions.add(buttonBack);
		gameOptions.add(buttonPlay);
		gameOptions.setOpaque(false);
		gameOptions.setFocusable(false);
	}
	
	public void addButtonListener(ActionListener listener) {
		buttonPlay.addActionListener(listener);
		buttonBack.addActionListener(listener);
		buttonKindergarten.addActionListener(listener);
		buttonEasy.addActionListener(listener);
		buttonIntermediate.addActionListener(listener);
		buttonHard.addActionListener(listener);
	}
	
	public void removeButtonListener(ActionListener listener) {
		buttonPlay.removeActionListener(listener);
		buttonBack.removeActionListener(listener);
		buttonKindergarten.removeActionListener(listener);
		buttonEasy.removeActionListener(listener);
		buttonIntermediate.removeActionListener(listener);
		buttonHard.removeActionListener(listener);
	}
	
	@Override 
	public void actionPerformed(ActionEvent event) {
		// This method is used to implement group button toggling behaviour.
		String command = event.getActionCommand();
		if (command == "kindergarten") {
			setActiveButton(buttonKindergarten, buttonEasy, buttonIntermediate, buttonHard);
		}
		else if (command == "easy") {
			setActiveButton(buttonEasy, buttonKindergarten, buttonIntermediate, buttonHard);
		}
		else if (command == "intermediate") {
			setActiveButton(buttonIntermediate, buttonEasy, buttonKindergarten, buttonHard);
		}
		else if (command == "hard") {
			setActiveButton(buttonHard, buttonEasy, buttonIntermediate, buttonKindergarten);
		}
	}
	
	public void setBoardSizeInput(Board board) {
		setTextInTextField(boardInputWidth, Integer.toString(board.getWidth()));
		setTextInTextField(boardInputHeight, Integer.toString(board.getHeight()));
	}
	
	public boolean hasBoardSizeInput() {		
		try {
			String widthString = boardInputWidth.getText().replace(" ", "");
			String heightString = boardInputHeight.getText().replace(" ", "");
			int width = Integer.parseInt(widthString);
			int height = Integer.parseInt(heightString);
			
			boolean validDimensions = width >= Board.MIN_WIDTH && width <= Board.MAX_WIDTH &&
				height >= Board.MIN_HEIGHT && height <= Board.MAX_HEIGHT;
				
			if (!validDimensions) {
				return false;
			}
		}
		catch (NumberFormatException error) {
			return false;
		}
		return true;
	}
	
	public Dimension getBoardSizeInput() {
		if (!hasBoardSizeInput()) {
			throw new RuntimeException("board size inputs are not valid.");
		}
		
		try {
			String widthString = boardInputWidth.getText().replace(" ", "");
			String heightString = boardInputHeight.getText().replace(" ", "");
			int width = Integer.parseInt(widthString);
			int height = Integer.parseInt(heightString);
			return new Dimension(width, height);
		}
		catch (NumberFormatException error) {
			throw new RuntimeException("board size inputs are not valid.");
		}
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		
		// Background
		Rectangle menuRect = Menu.computeMenuRectangle(getSize());
		Menu.drawBackground(context2D, getVisibleRect());
		Menu.drawMenuBackground(context2D, menuRect);
		
		// Set transparent panel in the board area.
		gameOptions.setBounds(menuRect);
		
		drawText(context2D);
		drawDifficultyButtons();
		drawPlayAndBackButtons();
		drawErrorMessage();
		
		// Text fields.
		int xWidth = gameOptions.getWidth()/2 - boardInputWidth.getWidth() - 50;
		int xHeight = gameOptions.getWidth()/2 + 50;
		int y = 50;
		boardInputWidth.setLocation(xWidth, y);
		boardInputHeight.setLocation(xHeight, y);
	}
	
	private void drawText(Graphics2D context){
		
		int panelX = gameOptions.getX();
		int panelY = gameOptions.getY();
		int panelWidth = gameOptions.getWidth();
		
		context.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		context.setFont(FONT_LARGE);
		context.setColor(CustomColors.PANEL);
		context.drawString("GAME DIMENSIONS", panelX + 20, panelY + 30);
		context.drawString("SNAKE COLOR", panelX + 20, panelY + 150);
		context.drawString("DIFFICULTY", panelX + 20, panelY + 270);
		
		context.setFont(FONT_SMALL);
		String message = "(Type in dimensions between " + Board.MIN_WIDTH + " and " + Board.MAX_WIDTH + ")";
		context.drawString(message, panelX + 230, panelY + 26);
		
		context.setFont(FONT_MEDIUM);
		context.drawString("width", getWidth()/2 - 150, panelY + 70);
		context.drawString("height", getWidth()/2 - 8, panelY + 70);
		boardInputWidth.setBounds(150, panelY + 40, 50, 30);
		boardInputHeight.setBounds(panelWidth - 200, panelY + 40, 50, 30);
	}
	
	private void drawErrorMessage() {
		if (!hasBoardSizeInput()){
			int x = gameOptions.getWidth()/2 - 80;
			int y = 90;
			boardInputError.setLocation(x,  y);
			boardInputError.setText("Invalid width and height");
		} 
		else {
			boardInputError.setText("");
		}
	}
	
	private void drawPlayAndBackButtons() {
		int width = gameOptions.getWidth();
		int height = gameOptions.getHeight();
		int xBack = width/2 - buttonBack.getWidth() - 10;
		int xPlay = width/2 + 10;
		int y = height - buttonPlay.getHeight() - 20;
		buttonBack.setLocation(xBack, y);
		buttonPlay.setLocation(xPlay, y);
	}
	
	private void drawDifficultyButtons() {
		int spaceBetweenButtons = (gameOptions.getWidth() - 4*buttonEasy.getWidth())/5;
		int y = 300;
		buttonKindergarten.setLocation(spaceBetweenButtons, y);
		buttonEasy.setLocation(2*spaceBetweenButtons + buttonEasy.getWidth(), y);
		buttonIntermediate.setLocation(3*spaceBetweenButtons + 2*buttonIntermediate.getWidth(), y);
		buttonHard.setLocation(4*spaceBetweenButtons + 3*buttonHard.getWidth(), y);
	}
	
	protected void setActiveButton(JButton buttonPressed, JButton button1, JButton button2, JButton button3) {
		buttonPressed.setBorderPainted(true);
		button1.setBorderPainted(false);
		button2.setBorderPainted(false);
		button3.setBorderPainted(false);
	}
	
	protected static JButton createDifficultyButton(BufferedImage image, String actionCommand) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(THICK_BORDER);
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.setActionCommand(actionCommand);
		return button;
	}
	
	protected static JButton createColorButton(BufferedImage image, String actionCommand) {
		JButton button = new JButton(new ImageIcon(image));
		button.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorder(THICK_BORDER);
		button.setBorderPainted(false);
		button.setFocusable(false);
		button.setActionCommand(actionCommand);
		return button;
	}
	
	private void setTextInTextField(JFormattedTextField textField, String text) {
		textField.setFont(FONT_LARGE); 
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
		textField.setValue(text);
	}
	
}
