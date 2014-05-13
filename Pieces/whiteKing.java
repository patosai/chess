package Pieces;

public class whiteKing extends ChessPiece {
	public whiteKing(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'K';
		hasMoved = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		int dx = finalCol - col;
		int dy = finalRow - row;
		if ((Math.abs(dx) > 1) || (Math.abs(dy) > 1)) return false;
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'b') return false;
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}