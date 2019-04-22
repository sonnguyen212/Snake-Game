
package snake.model;


/**
 * Class representing a position on a game board.
 */
public class Field {
	
	private final int row;
	private final int column;
	
	public Field(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Field) {
			Field field = (Field)other;
			return row == field.row && column == field.column;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Field(" + row + "," + column + ")";
	}
	
}