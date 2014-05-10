package Pieces;

public class blackPawn extends ChessPiece {

	public blackPawn(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public String toString() {
		return colToLetter() + (row + 1);
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		return true;
	}
	
}