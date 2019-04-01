package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import snake.view.Audio;


public class MenuListener extends KeyAdapter implements ActionListener {

	private WindowControl control;
	private Audio audio;
	
	public MenuListener(WindowControl control, Audio audio) {
		if (control == null || audio == null) {
			throw new NullPointerException();
		}
		this.control = control;
		this.audio = audio;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "singleplayer") {
			control.startSingleplayerGame();
		} 
		else if (event.getActionCommand() == "multiplayer") {
			control.startMultiplayerGame();
		}
		else if (event.getActionCommand() == "internet") {
			control.startInternetGame();
		}
		else if (event.getActionCommand() == "controls") {
			control.showControls();
		} 
		else if (event.getActionCommand() == "quit") {
			control.close();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if (keyCode == KeyEvent.VK_M) {
			audio.toggleMute();
		}
		else if (keyCode == KeyEvent.VK_ESCAPE) {
			control.switchBackToGame();
		}
	}

}

