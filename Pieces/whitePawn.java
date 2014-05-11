package Pieces;

public class whitePawn extends ChessPiece {
	
	public whitePawn(int row, int col) {
		this.row = row;
		this.col = col;
		hasMoved = false;
	}

	public String toString() {
		return colToLetter() + (row + 1);
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
			//check if can move two squares forward
		if ((finalRow - row) == 2 && board[finalRow][finalCol] == null && board[finalRow - 1][finalCol] == null) {
			return true;
		}
		return false;
	}
	
}