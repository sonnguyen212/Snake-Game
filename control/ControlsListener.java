package snake.control;

import java.awt.event.*;

import snake.view.Audio;


public class ControlsListener extends KeyAdapter implements ActionListener {
	
	private WindowControl control;
	private Audio audio;
	
	public ControlsListener(WindowControl control, Audio audio) {
		if (control == null || audio == null) {
			throw new NullPointerException();
		}
		this.control = control;
		this.audio = audio;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "back") {
			control.showMenu();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		switch (event.getKeyCode()) {
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
	
}
