package snake.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import snake.model.*;


public class OptionsMultiplayer extends OptionsAbstract {
	
	private static final Font COLOR_TEXT_FONT = new Font("Sans_Serif", Font.BOLD, 12);
	
	private JButton buttonGreen1;
	private JButton buttonBlue1;
	private JButton buttonRed1;
	private JButton buttonYellow1;
	private JButton buttonGreen2;
	private JButton buttonBlue2;
	private JButton buttonRed2;
	private JButton buttonYellow2;
	
	public OptionsMultiplayer(GameMultiplayer game) {
		super();
		if (game == null) {
			throw new NullPointerException();
		}
		
		// Color buttons
		this.buttonGreen1 = createColorButton(CustomImages.BUTTON_GREEN, "green1");
		this.buttonBlue1 = createColorButton(CustomImages.BUTTON_BLUE, "blue1");
		this.buttonRed1 = createColorButton(CustomImages.BUTTON_RED, "red1");
		this.buttonYellow1 = createColorButton(CustomImages.BUTTON_YELLOW, "yellow1");
		this.buttonGreen2 = createColorButton(CustomImages.BUTTON_GREEN, "green2");
		this.buttonBlue2 = createColorButton(CustomImages.BUTTON_BLUE, "blue2");
		this.buttonRed2 = createColorButton(CustomImages.BUTTON_RED, "red2");
		this.buttonYellow2 = createColorButton(CustomImages.BUTTON_YELLOW, "yellow2");
		addButtonListener(this);
		
		super.gameOptions.add(buttonGreen1);
		super.gameOptions.add(buttonBlue1);
		super.gameOptions.add(buttonRed1);
		super.gameOptions.add(buttonYellow1);
		super.gameOptions.add(buttonGreen2);
		super.gameOptions.add(buttonBlue2);
		super.gameOptions.add(buttonRed2);
		super.gameOptions.add(buttonYellow2);
		
		super.setBoardSizeInput(game.getBoard());
	}
	
	@Override
	public void addButtonListener(ActionListener listener) {
		super.addButtonListener(listener);
		buttonGreen1.addActionListener(listener);
		buttonBlue1.addActionListener(listener);
		buttonRed1.addActionListener(listener);
		buttonYellow1.addActionListener(listener);
		buttonGreen2.addActionListener(listener);
		buttonBlue2.addActionListener(listener);
		buttonRed2.addActionListener(listener);
		buttonYellow2.addActionListener(listener);
	}
	
	@Override
	public void removeButtonListener(ActionListener listener) {
		super.removeButtonListener(listener);
		buttonGreen1.removeActionListener(listener);
		buttonBlue1.removeActionListener(listener);
		buttonRed1.removeActionListener(listener);
		buttonYellow1.removeActionListener(listener);
		buttonGreen2.removeActionListener(listener);
		buttonBlue2.removeActionListener(listener);
		buttonRed2.removeActionListener(listener);
		buttonYellow2.removeActionListener(listener);
	}
	
	@Override 
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		
		// This method is used to implement group button toggling behaviour.
		String command = event.getActionCommand();
		if (command == "green1") {
			setActiveButton(buttonGreen1, buttonBlue1, buttonRed1, buttonYellow1);
		}
		else if (command == "blue1") {
			setActiveButton(buttonBlue1, buttonGreen1, buttonRed1, buttonYellow1);
		}
		else if (command == "red1") {
			setActiveButton(buttonRed1, buttonBlue1, buttonGreen1, buttonYellow1);
		}
		else if (command == "yellow1") {
			setActiveButton(buttonYellow1, buttonBlue1, buttonRed1, buttonGreen1);
		}
		else if (command == "green2") {
			setActiveButton(buttonGreen2, buttonBlue2, buttonRed2, buttonYellow2);
		}
		else if (command == "blue2") {
			setActiveButton(buttonBlue2, buttonGreen2, buttonRed2, buttonYellow2);
		}
		else if (command == "red2") {
			setActiveButton(buttonRed2, buttonBlue2, buttonGreen2, buttonYellow2);
		}
		else if (command == "yellow2") {
			setActiveButton(buttonYellow2, buttonBlue2, buttonRed2, buttonGreen2);
		}
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		drawColorButtons((Graphics2D)context);
	}
	
	public void drawColorButtons(Graphics2D context){
		context.setColor(CustomColors.PANEL);
		context.setFont(COLOR_TEXT_FONT);
		context.drawString("Player 1", getWidth()/2 - 175, gameOptions.getY() + 180);
		context.drawString("Player 2", getWidth()/2 + 25, gameOptions.getY() + 180);
		
		int gap = 10;
		int shift = 100;
		int width = buttonGreen1.getWidth();
		int start = super.gameOptions.getWidth()/2 - width - gap/2;
		int y = 190;
		buttonGreen1.setLocation(start - gap - width - shift, y);
		buttonBlue1.setLocation(start - shift, y);
		buttonRed1.setLocation(start + gap + width - shift, y);
		buttonYellow1.setLocation(start + 2*gap + 2*width - shift, y);
		buttonGreen2.setLocation(start - gap - width + shift, y);
		buttonBlue2.setLocation(start + shift, y);
		buttonRed2.setLocation(start + gap + width + shift, y);
		buttonYellow2.setLocation(start + 2*gap + 2*width + shift, y);
	}
	
}
