package Pieces;

public class ChessPiece {
	private int row;
	private int col;
	
	public String toString() {
		return col + Integer.toString(row);
	}
}