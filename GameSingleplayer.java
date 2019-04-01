package snake.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;


public class GameSingleplayer extends Game implements ActionListener {
	
	private static final int DEFAULT_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	private static final int TIMER_UPDATE_INTERVAL = 15;
	private static final int TIMER_INITIAL_DELAY = 500;
	
	private State state;
	private Board board;
	private Snake snake;
	private int score;
	private Food food;
	private boolean isWon = false;
	private boolean isLost = false;
	
	// Variables for implementing continuous snake movement.
	private Timer timer;
	boolean timerEnabled = true;
	private int timerSpeedIncrease = 0;
	private int timerUpdateInterval = 200;
	private long timerLastUpdateTime = 0;
	
	public GameSingleplayer() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public GameSingleplayer(int width, int height) {
		super();
		this.board = new Board(width, height);
		this.snake = new Snake();
		
		// Create a timer object that send an ActionEvent to this class, in a periodic interval.
		this.timer = new Timer(TIMER_UPDATE_INTERVAL, this);
		this.timer.setInitialDelay(TIMER_INITIAL_DELAY);
		reset();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Food getFood() {
		return food;
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public boolean isStarted() {
		return state != State.STARTUP;
	}

	@Override
	public boolean isRunning() {
		return state == State.RUNNING;
	}

	@Override
	public boolean isPaused() {
		return state == State.PAUSED;
	}

	@Override
	public boolean isEnded() {
		return state == State.ENDED;
	}
	
	public boolean isWon() {
		return isWon;
	}
	
	public boolean isLost() {
		return isLost;
	}
	
	public boolean isTimedMovementEnabled() {
		return timerEnabled;
	}

	@Override
	public void start() {
		if (state == State.STARTUP) {
			state = State.RUNNING;
			timer.start();
			setChanged();
			notifyObservers(new Event(Event.Type.START));
		}
	}

	@Override
	public void pause() {
		if (state == State.RUNNING) {
			state = State.PAUSED;
			timer.stop();
			setChanged();
			notifyObservers(new Event(Event.Type.PAUSE));
		}
	}

	@Override
	public void resume() {
		if (state == State.PAUSED) {
			state = State.RUNNING;
			timer.start();
			setChanged();
			notifyObservers(new Event(Event.Type.RESUME));
		}
	}
	
	@Override
	public void togglePause() {
		if (isPaused()) {
			resume();
		}
		else {
			pause();
		}
	}

	@Override
	public void reset() {
		// Set the game variables.
		isLost = false;
		isWon = false;
		snake.setup(getInitialSnake());
		state = State.STARTUP;
		score = 0;
		food = Food.generateRandomFood(snake, board);
		timerSpeedIncrease = 0;
		timer.stop();
	}
	
	public void enableTimedMovement() {
		timerEnabled = true;
	}
	
	public void disableTimedMovement() {
		timerEnabled = false;
	}
	
	public void setTimedMovementSpeed(int speed) {
		if (speed <= 0) {
			throw new IllegalArgumentException("speed " + speed + " is not allowed");
		}
		this.timerUpdateInterval = speed;
		this.timerSpeedIncrease = 0;
	}
	
	public void setBoardSize(Dimension size){
		board = new Board(size.width, size.height);
		reset();
	}
	
	public void move(Direction moveDirection) {
		if (state != State.RUNNING) {
			return;
		}
		
		if (snake.isNeckDirection(moveDirection)) {
			return;
		}
	
		Field newHeadPosition = snake.getNextHeadPosition(moveDirection, board);
		boolean snakeEatsFood = newHeadPosition.equals(food.getPosition());		
		boolean snakeEatsItSelf = snake.move(moveDirection, snakeEatsFood, board);
		if (isBoardFull()) {
			state = State.ENDED;
			isWon = true;
		} 
		else if (snakeEatsItSelf) {
			state = State.ENDED;
			isLost = true;
		}
		else if (snakeEatsFood) {
			food = Food.generateRandomFood(snake, board);
		}
		
		// Increment score.
		if (snakeEatsFood) {
			score++;
			if (score % 5 == 0) {
				timerSpeedIncrease += 5;
			}
		}
		
		// Notify the observing classes that the game changed. Send an argument with 
		// the type of event that happened.
		Event event;
		if (isWon) {
			event = new Event(Event.Type.WIN);
		}
		else if (isLost) {
			event = new Event(Event.Type.LOSE);
		}
		else if (snakeEatsFood) {
			event = new Event(Event.Type.EAT);
		}
		else {
			event = new Event(Event.Type.MOVE);
		}
		setChanged();
		notifyObservers(event);
		timerLastUpdateTime = System.currentTimeMillis();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - timerLastUpdateTime;
		if (state == State.RUNNING && timerEnabled && elapsedTime > timerUpdateInterval - timerSpeedIncrease) {
			move(snake.getHeadDirection());
			timerLastUpdateTime = currentTime;
		}
	}
	
	private boolean isBoardFull() {
		return snake.getSize() == board.getSize();
	}
	
	private ArrayList<Field> getInitialSnake() {
		// Find initial snake position.
		Field center = board.getCenter();
		Field centerRight = new Field(center.getRow(), center.getColumn() + 1);
		ArrayList<Field> snakePositions = new ArrayList<Field>();
		snakePositions.add(center);
		snakePositions.add(centerRight);
		return snakePositions;
	}

}
