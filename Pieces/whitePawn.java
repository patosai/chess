package Pieces;

public class whitePawn extends ChessPiece {
	protected boolean enPassant;

	public whitePawn(int row, int col) {
		this.row = row;
		this.col = col;
		hasMoved = false;
		enPassant = false;
	}
	
	public String toString() {
		return colToLetter() + (row + 1);
	}
	
	public boolean possibleEnPassant() {
		return enPassant;
	}
	
	public void switchEnPassant() {
		enPassant = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
			//check if can move two squares forward
		if (!(hasMoved) && row == 1 && finalRow == 3 && col == finalCol){
			if (board[finalRow][finalCol] == null && board[finalRow - 1][finalCol] == null) {
				enPassant = true;
				return true;
			}
		}
			//check if can move one square forward
		if (board[finalRow][finalCol] == null && finalRow - row == 1 && col == finalCol) {
			return true;
		}
			//check if can take piece on left
		if (finalRow == (row + 1) && finalCol == (col - 1) && board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) == 'b') {
			return true;
		}
			//check if can take piece on right
		if (finalRow == (row + 1) && finalCol == (col + 1) && board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) == 'b') {
			return true;
		}
			//check if can enpassant left
		if ((finalRow - row == 1) && (col - finalCol) == 1 && board[finalRow][finalCol] == null && board[finalRow - 1][finalCol] != null && 
			board[finalRow - 1][finalCol].getClass().getName().substring(7).equals("blackPawn") &&
			board[finalRow - 1][finalCol].possibleEnPassant()) {
			board[finalRow - 1][finalCol] = null;
			return true;
		}
			//check if can enpassant right
		if ((finalRow - row == 1) && (finalCol - col) == 1 && board[finalRow][finalCol] == null && board[finalRow - 1][finalCol] != null && 
			board[finalRow - 1][finalCol].getClass().getName().substring(7).equals("blackPawn") &&
			board[finalRow - 1][finalCol].possibleEnPassant()) {
			board[finalRow - 1][finalCol] = null;
			return true;
		}
		
		return false;
	}
	
	public boolean amIInCheck(ChessPiece[][] board) {return false;}
	
}