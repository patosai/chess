package Pieces;

public class blackKing extends ChessPiece {
	public blackKing(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'K';
		hasMoved = false;
	}
}