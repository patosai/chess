package Pieces;

public class whitePawn extends ChessPiece {

	public whitePawn(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public String toString() {
		return colToLetter() + (row + 1);
	}
}