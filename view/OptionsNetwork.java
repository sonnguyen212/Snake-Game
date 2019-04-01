package snake.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import snake.model.Player;

public class OptionsNetwork {

	public static Player showPlayerSelectionDialog(JFrame parent, int port) {
		Object[] options = {
			"Player 1",
			"Player 2",
			"Cancel"
		};
		
		int selection = JOptionPane.showOptionDialog(
			parent,
			"Do you want to be player 1 or player 2?\n" + 
			"It is very important that you choose differently that the one you play against.\n" + 
			"Player 1 becomes the server. Thus he/she must open port " + port + " in their\n" + 
			"router firewall. Otherwise you can not play.",
			"Player Selection",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[2]
		);
		
		if (selection == 0) {
			return Player.ONE;
		}
		else if (selection == 1) {
			return Player.TWO;
		}
		else {
			return Player.NONE;
		}
	}
	
	public static String showServerLocationInputDialog(JFrame parent) {
		String message =
			"Please input the other computers IP.\n" + 
			"They can find their IP by doing a google search for 'what is my ip'.\n";
		String hostname = JOptionPane.showInputDialog(parent, message);
		if (hostname == null || hostname.isEmpty()) {
			return "";
		}
		return hostname;
	}
	
}
