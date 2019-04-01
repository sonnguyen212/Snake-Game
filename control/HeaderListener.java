package snake.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import snake.view.Audio;


public class HeaderListener implements ActionListener {

	private Audio audio;
	
	public HeaderListener(Audio audio) {
		if (audio == null) {
			throw new NullPointerException();
		}
		this.audio = audio;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command == "mute"){
			audio.toggleMute();
		}
	}
	
}
