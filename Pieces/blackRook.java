package Pieces;

public class blackRook extends ChessPiece {
	public blackRook(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'R';
		hasMoved = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		if (row != finalRow && col != finalCol) {return false;}
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'w') return false;
		
		if (row == finalRow) {
			for (int c = col + ((finalCol - col) / Math.abs(finalCol - col)); c != finalCol; c += ((finalCol - col) / Math.abs(finalCol - col))) {
				if (board[row][c] != null) return false;
			}
		}
		else if (col == finalCol) {
			for (int r = row + ((finalRow - row) / Math.abs(finalRow - row)); r != finalRow; r += ((finalRow - row) / Math.abs(finalRow - row))) {
				if (board[r][col] != null) return false;
			}
		}
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
	public boolean amIInCheck(ChessPiece[][] board) {return false;}
}