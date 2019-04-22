package snake.model;

import java.util.Observable;


public abstract class Game extends Observable {
	
	public Game() {
		super();
	}
	
	public abstract boolean isStarted();
	public abstract boolean isRunning();
	public abstract boolean isPaused();
	public abstract boolean isEnded();

	public abstract void start();
	public abstract void pause();
	public abstract void resume();
	public abstract void togglePause();
	public abstract void reset();
	
}
