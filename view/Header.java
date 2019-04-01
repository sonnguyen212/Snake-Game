package snake.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Header extends JPanel implements Observer {
	
	private static final int LOGO_WIDTH = 300;
	private static final int LOGO_HEIGHT = 80;
	private final int BUTTON_WIDTH = CustomImages.SOUND_ON.getWidth();
	private final int BUTTON_HEIGHT = CustomImages.SOUND_ON.getHeight();
	
	private Audio audio;
	private Image logo;
	private ImageIcon imageSoundOn;
	private ImageIcon imageSoundOff;
	private JButton buttonMuted;

	public Header(Audio audio) {
		if (audio == null) {
			throw new NullPointerException();
		}
		
		this.audio = audio;
		this.logo = CustomImages.LOGO.getScaledInstance(LOGO_WIDTH, LOGO_HEIGHT, Image.SCALE_SMOOTH);
		this.imageSoundOn = new ImageIcon(CustomImages.SOUND_ON);
		this.imageSoundOff = new ImageIcon(CustomImages.SOUND_OFF);
		this.buttonMuted = createMuteButton();
		
		add(this.buttonMuted);
		setBackground(CustomColors.PANEL);
		setFocusable(false);
		
		// Listen for changes in the Audio object (muting, etc..).
		this.audio.addObserver(this);
	}
	
	public void addButtonListener(ActionListener listener) {
		buttonMuted.addActionListener(listener);
	}
	
	public void removeButtonListener(ActionListener listener) {
		buttonMuted.removeActionListener(listener);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 90);
	}
	
	@Override
	protected void paintComponent(Graphics context) {
		super.paintComponent(context);
		Graphics2D context2D = (Graphics2D)context;
		
		// Only show logo if board is wide enough to contain it.
		Dimension size = getSize();
		int logoWidth = logo.getWidth(null);
		if (size.width > logoWidth + 230) {
			context2D.drawImage(logo, size.width/2 - logoWidth/2, 0, null);
		}
		
		// Mute button.
		int xSound = size.width - BUTTON_WIDTH - 10;
		int ySound = 8;
		buttonMuted.setLocation(xSound, ySound);
		
		// Key info
		context2D.drawImage(CustomImages.INFO_KEYS, size.width-CustomImages.INFO_KEYS.getWidth() - 10, 35, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Audio) {
			setMuteIcon(buttonMuted);
			repaint();
		}
	}
	
	private JButton createMuteButton() {
		JButton button = new JButton();
		button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		button.setActionCommand("mute");
		setMuteIcon(button);
		return button;
	}
	
	private void setMuteIcon(JButton button) {
		if (audio.isMuted()) {
			button.setIcon(imageSoundOff);
		} 
		else {
			button.setIcon(imageSoundOn);
		}
	}
	
}
