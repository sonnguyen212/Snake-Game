package snake.view;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.*;

import snake.model.*;


public class Audio extends Observable implements Observer {
	
	private static final String SOUND_PATH = "/sounds/";
	
	private Clip soundEat;
	private Clip soundPause;
	private Clip soundResume;
	private Clip soundLose;
	private Clip soundWin;
	private Clip soundTie;
	private Clip soundStart;
	private Clip soundTrack;
	private boolean isMuted;
	private Game registeredGame;
	
	public Audio() {
		super();
		this.isMuted = false;
		this.registeredGame = null;
		
		// Sounds
		this.soundEat = loadSound("Blop.wav");
		this.soundPause = loadSound("BananaSlap.wav");
		this.soundResume = loadSound("BananaSlap.wav");
		this.soundLose = loadSound("DunDunDun.wav");
		this.soundWin = loadSound("TaDa.wav");
		this.soundTie = loadSound("FakeApplause.wav");
		this.soundStart = loadSound("BananaSlap.wav");
		this.soundTrack = loadSound("WaterLily.wav");
		
		// Lower the win and lose sounds.
		FloatControl gainWin = (FloatControl) soundWin.getControl(FloatControl.Type.MASTER_GAIN);
		gainWin.setValue(-15.0f);
		FloatControl gainLose = (FloatControl) soundLose.getControl(FloatControl.Type.MASTER_GAIN);
		gainLose.setValue(-5.0f);
		
		soundTrack.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public boolean isMuted() {
		return isMuted;
	}
	
	public void toggleMute() {
		isMuted = !isMuted;
		if (isMuted) {
			soundTrack.stop();
		}
		else {
			soundTrack.start();
		}
		setChanged();
		notifyObservers();
	}
	
	public void registerGame(Game game) {
		if (game == null) {
			throw new NullPointerException();
		}
		if (registeredGame != null) {
			registeredGame.deleteObserver(this);
		}
		game.addObserver(this);
		registeredGame = game;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (isMuted) {
			return;
		}

		Event event = (Event)arg;
		if (event.getType() == Event.Type.EAT) {
			playEatSound();
		}
		else if (event.getType() == Event.Type.LOSE) {
			playLoseSound();
		}
		else if (event.getType() == Event.Type.WIN) {
			playWinSound();
		}
		else if (event.getType() == Event.Type.TIE) {
			playTieSound();
		}
		else if (event.getType() == Event.Type.START) {
			playStartSound();
		}
		else if (event.getType() == Event.Type.PAUSE) {
			playPauseSound();
		}
		else if (event.getType() == Event.Type.RESUME) {
			playResumeSound();
		}

	}
	
	private void playEatSound() {
		soundEat.setFramePosition(0);
		soundEat.start();
	}

	private void playLoseSound() {
		soundLose.setFramePosition(0);
		soundLose.start();
	}
	
	private void playWinSound() {
		soundWin.setFramePosition(0);
		soundWin.start();
	}
	
	private void playTieSound() {
		soundTie.setFramePosition(0);
		soundTie.start();
	}
	
	private void playPauseSound() {
		soundPause.setFramePosition(0);
		soundPause.start();
	}
	
	private void playResumeSound() {
		soundResume.setFramePosition(0);
		soundResume.start();
	}
	
	private void playStartSound() {
		soundStart.setFramePosition(0);
		soundStart.start();
	}
	
	private Clip loadSound(String filename) {
		try {
			// We load external resources by an URL. With this we can use JAR files.
			URL location = getClass().getResource(SOUND_PATH + filename);
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(location));
			return clip;
		}
		catch (Exception error) {
			throw new RuntimeException("unable to load sound clip " + filename + ": " + error.getMessage());
		}
	}
	
}
