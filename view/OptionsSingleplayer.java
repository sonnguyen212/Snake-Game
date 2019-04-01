package snake.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import snake.model.GameSingleplayer;


public class OptionsSingleplayer extends OptionsAbstract {	
	
	private JButton buttonGreen;
	private JButton buttonBlue;
	private JButton buttonRed;
	private JButton buttonYellow;
	
	public OptionsSingleplayer(GameSingleplayer game) {
		super();
		if (game == null) {
			throw new NullPointerException();
		}
		
		this.buttonGreen = createColorButton(CustomImages.BUTTON_GREEN, "green");
		this.buttonBlue = createColorButton(CustomImages.BUTTON_BLUE, "blue");
		this.buttonRed = createColorButton(CustomImages.BUTTON_RED, "red");
		this.buttonYellow = createColorButton(CustomImages.BUTTON_YELLOW, "yellow");
		addButtonListener(this);
		
		super.gameOptions.add(this.buttonGreen);
		super.gameOptions.add(this.buttonBlue);
		super.gameOptions.add(this.buttonRed);
		super.gameOptions.add(this.buttonYellow);
		
		super.setBoardSizeInput(game.getBoard());
	}
	
	@Override
	public void addButtonListener(ActionListener listener) {
		super.addButtonListener(listener);
		buttonGreen.addActionListener(listener);
		buttonBlue.addActionListener(listener);
		buttonRed.addActionListener(listener);
		buttonYellow.addActionListener(listener);
	}
	
	@Override
	public void removeButtonListener(ActionListener listener) {
		super.removeButtonListener(listener);
		buttonGreen.removeActionListener(listener);
		buttonBlue.removeActionListener(listener);
		buttonRed.removeActionListener(listener);
		buttonYellow.removeActionListener(listener);
	}
	
	@Override 
	public void actionPerformed(ActionEvent event) {
		super.actionPerformed(event);
		
		// This method is used to implement group button toggling behaviour.
		String command = event.getActionCommand();
		if (command == "green") {
			setActiveButton(buttonGreen, buttonBlue, buttonRed, buttonYellow);
		}
		else if (command == "blue") {
			setActiveButton(buttonBlue, buttonGreen, buttonRed, buttonYellow);
		}
		else if (command == "red") {
			setActiveButton(buttonRed, buttonBlue, buttonGreen, buttonYellow);
		}
		else if (command == "yellow") {
			setActiveButton(buttonYellow, buttonBlue, buttonRed, buttonGreen);
		}
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		drawColorButtons((Graphics2D)context);
	}
	
	public void drawColorButtons(Graphics2D context){
		int gap = 10;
		int start = super.gameOptions.getWidth()/2 - buttonGreen.getWidth() - gap/2;
		int y = 180;
		buttonGreen.setLocation(start - gap - buttonGreen.getWidth(), y);
		buttonBlue.setLocation(start, y);
		buttonRed.setLocation(start + gap + buttonRed.getWidth(), y);
		buttonYellow.setLocation(start + 2*gap + 2*buttonYellow.getWidth(), y);
	}
	
}