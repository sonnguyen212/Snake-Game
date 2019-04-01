package snake.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class Controls extends JPanel {

	private static final long serialVersionUID = 6121636944519601998L;

	private JButton buttonBack;
	
	public Controls() {
		this.buttonBack = Menu.createMenuButton(CustomImages.BUTTON_BACK, "back");
		add(buttonBack);
	}
	
	public void addButtonListener(ActionListener listener) {
		buttonBack.addActionListener(listener);
	}
	
	public void removeButtonListener(ActionListener listener) {
		buttonBack.removeActionListener(listener);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D) context;
		
		// Background.
		Rectangle menuRect = Menu.computeMenuRectangle(getSize());
		Menu.drawBackground(context2D, getVisibleRect());
		Menu.drawMenuBackground(context2D, menuRect);
		
		// Title image.
		int xTitle = menuRect.x + menuRect.width/2 - CustomImages.TITLE_CONTROLS.getWidth()/2;
		int yTitle = menuRect.y + Menu.MENU_MARGIN;
		context.drawImage(CustomImages.TITLE_CONTROLS, xTitle, yTitle, null);
		
		// Control image.
		int xImage = menuRect.x + menuRect.width/2 - CustomImages.CONTROLS.getWidth()/2;
		int yImage = yTitle + CustomImages.TITLE_CONTROLS.getHeight()/2 + 50;
		context.drawImage(CustomImages.CONTROLS, xImage, yImage, null);
		
		// Back button.
		int xBack = menuRect.x + menuRect.width/2 - Menu.BUTTON_WIDTH/2;
		int yBack = menuRect.y + menuRect.height - Menu.BUTTON_HEIGHT - 10;
		buttonBack.setLocation(xBack, yBack);
	}
	
}
