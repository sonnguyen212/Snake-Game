package snake.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


public class Snake {
	
	private ArrayList<Field> positions;
	private Direction headDirection;
	
	public Snake() {
		this.positions = new ArrayList<Field>();
		this.headDirection = null;
	}
	
	public Field getHead() {
		return positions.get(0);
	}
	
	public Field getNeck() {
		return positions.get(1);
	}
	
	public Field getTail() {
		return positions.get(positions.size() - 1);
	}
	
	public Direction getHeadDirection() {
		return headDirection;
	}
	
	public List<Field> getPositions() {
		// Return a list that can't be changed. This prevents outside classes
		// from changing the snake list.
		return Collections.unmodifiableList(positions);
	}
	
	public int getSize() {
		return positions.size();
	}
	
	public boolean contains(Field position) {
		return positions.contains(position);
	}
	
	boolean isNeckDirection(Direction direction) {
		if (direction == Direction.getOppositeOf(headDirection)) {
			return true;
		}
		return false;
	}
	
	boolean move(Direction direction, boolean eatFood, Board board) {
		if (isNeckDirection(direction)) {
			return false;
		}
		
		// Test if the snake eats its body.
		Field newHeadPosition = getNextHeadPosition(direction, board);
		if (positions.contains(newHeadPosition)) {
			int headIndex = positions.indexOf(newHeadPosition);
			int tailIndex = positions.size() - 1;
			if (eatFood || headIndex != tailIndex) {
				return true;
			}
		}

		// Remove tail if snake does not eat anything.
		if (!eatFood) {
			positions.remove(positions.size() - 1);
		}
		
		positions.add(0, newHeadPosition);
		headDirection = direction;
		return false;
	}
	
	Field getNextHeadPosition(Direction moveDirection, Board board) {
		// Find the row and column of the new head position.
		Field currentHead = getHead();
		Field direction = directionToField(moveDirection);
		int newHeadRow = currentHead.getRow() + direction.getRow();
		int newHeadColumn = currentHead.getColumn() + direction.getColumn();
		
		// Make sure that the row and column is within the board.
		return board.wrap(newHeadRow, newHeadColumn);
	}
	
	void setup(ArrayList<Field> snake) {
		positions = snake;
		headDirection = findHeadDirection(snake);
	}
	
	// Find head direction from an array of fields.
	private Direction findHeadDirection(ArrayList<Field> snake) {
		Field head = snake.get(0);
		Field neck = snake.get(1);
		int rowDiff = head.getRow() - neck.getRow();
		int columnDiff = head.getColumn() - neck.getColumn();
		if (rowDiff == 0 && columnDiff == -1) {
			return Direction.LEFT;
		}
		else if (rowDiff == 0 && columnDiff == 1) {
			return Direction.RIGHT;
		}
		else if (rowDiff == -1 && columnDiff == 0) {
			return Direction.UP;
		}
		else if (rowDiff == 1 && columnDiff == 0) {
			return Direction.DOWN;
		}
		throw new IllegalArgumentException("snake array is malformed");
	}
	
	private static Field directionToField(Direction direction) {
		switch (direction) {
			case UP:
				return new Field(-1,0);
			case DOWN:
				return new Field(1,0);
			case LEFT:
				return new Field(0,-1);
			case RIGHT:
				return new Field(0,1);
			default:
				throw new IllegalArgumentException();
		}
	}

}
