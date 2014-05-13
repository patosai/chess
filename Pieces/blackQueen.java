package Pieces;

public class blackQueen extends ChessPiece {

	public blackQueen(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'Q';
		hasMoved = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		int dx = finalCol - col;
		int dy = finalRow - row;
		
		//check if queen can move toward selected tile
		if ( ! (
		  (dx == 0 && dy != 0) ||
		  (dx != 0 && dy == 0) ||
		  (Math.abs(dx) == Math.abs(dy))  )  ) {
			return false;
		}
		//check end tile
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'w') {
			return false;
		}
		//check tiles in between
		if (dx == 0 && dy != 0) {
			for (int r = row + (dy / (Math.abs(dy))); r != finalRow; r += dy / (Math.abs(dy))) {
				if (board[r][col] != null) return false;
			}
		}
		if (dx != 0 && dy == 0) {
			for (int c = col + (dx / (Math.abs(dx))); c != finalCol; c += dx / (Math.abs(dx))) {
				if (board[row][c] != null) return false;
			}
		}
		if (dx != 0 && dy != 0) {
			int ddx = dx / Math.abs(dx);
			int ddy = dy / Math.abs(dy);
			for (int i = 1; i < Math.abs(dx); i++) {
				if (board[row + (i * ddy)][col + (i * ddx)] != null) return false;
			}
		}
		
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
	public boolean amIInCheck(ChessPiece[][] board) {return false;}
	
}