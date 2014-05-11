package Pieces;

public class blackPawn extends ChessPiece {

	public blackPawn(int row, int col) {
		this.row = row;
		this.col = col;
		hasMoved = false;
	}
	
	public String toString() {
		return colToLetter() + (row + 1);
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
			//check if can move two squares forward
		if (board[finalRow][finalCol] == null && board[finalRow + 1][finalCol] == null && row - finalRow == 2 && col == finalCol && !(hasMoved)) {
			return true;
		}
		if (board[finalRow][finalCol] == null && row - finalRow == 1 && col == finalCol) {
			return true;
		}
		return false;
	}
	
}