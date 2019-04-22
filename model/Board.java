package snake.model;


public class Board {
	
	public static final int MIN_WIDTH = 5;
	public static final int MAX_WIDTH = 100;
	public static final int MIN_HEIGHT = 5;
	public static final int MAX_HEIGHT = 100;
	
	private final int width;
	private final int height;
	
	public Board(int width, int height) {
		if (width < MIN_WIDTH || width > MAX_WIDTH) {
			throw new IllegalArgumentException("invalid width " + width);
		}
		if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
			throw new IllegalArgumentException("invalid height " + height);
		}
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public int getSize() {
		return width*height;
	}
	
	public Field getCenter() {
		int row = (height - 1) / 2;
		int column = (width - 1) / 2;
		return new Field(row, column);
	}
	
	public Field wrap(int row, int column) {
		
		if (row < 0) {
			row = height - 1;
		}
		else if (row >= height) {
			row = 0;
		}
		
		if (column < 0) {
			column = width - 1;
		}
		else if (column >= width) {
			column = 0;
		}
		
		return new Field(row, column);
	}
	
	public Field wrap(Field field) {
		return wrap(field.getRow(), field.getColumn());
	}
	
}
