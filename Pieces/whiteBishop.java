package Pieces;

public class whiteBishop extends ChessPiece {
	public whiteBishop(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'B';
		hasMoved = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		int dx = finalCol - col;
		int dy = finalRow - row;
		if (Math.abs(dx) != Math.abs(dy)) return false;
		
		dx /= Math.abs(finalCol - col);
		dy /= Math.abs(finalRow - row);
		for (int i = 1; i < finalCol - col; i++ ) {
			if (board[row + (i * dy)][col + (i * dx)] != null) return false;
		}
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'b')
			return false;
		
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
	public boolean amIInCheck(ChessPiece[][] board) {return false;}
}