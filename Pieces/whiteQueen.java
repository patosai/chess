package Pieces;

public class whiteQueen extends ChessPiece {
	public whiteQueen(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'Q';
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		int dx = finalCol - col;
		int dy = finalRow - row;
		
		if (! ((dx == 0 && dy != 0) ||
		       (dx != 0 && dy == 0) ||
			   (Math.abs(dx) == Math.abs(dy)))  ) {
				System.out.println("error!");
				return false;
			  }
		
		//check if there is white piece at end position
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'w') {
			return false;
		}
		
		//check horiz
		if (dx != 0 && dy == 0) {
			for (int c = col + (dx/Math.abs(dx)); c != finalCol; c += dx/(Math.abs(dx))) {
				if (board[row][c] != null) return false;
			}
		}
		
		//check vert
		if (dx == 0 && dy != 0) {
			for (int r = row + (dy/Math.abs(dy)); r != finalRow; r += dy/(Math.abs(dy))) {
				if (board[r][col] != null) return false;
			}
		}
		
		//check diags
		if (Math.abs(dx) == Math.abs(dy) && dx != 0 && dy != 0) {
			for (int i = 1; i != Math.abs(dx); i++) {
				if (board[row + (i * (dy / Math.abs(dy)))][col + (i * (dy/Math.abs(dy)))] != null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}