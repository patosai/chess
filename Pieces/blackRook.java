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
		System.out.println(row + " " + col + "     " + finalRow + " " + finalCol);
		if (row == finalRow) {
			if (col > finalCol) 
				for (int c = col; c <= finalCol; c++) {
				if (c == finalCol && board[row][c].getClass().getName().charAt(7) != 'w') return false;
				if (board[row][c] != null) return false;
			}
			
			for (int c = col; c == finalCol; c += ((finalCol - col)/Math.abs(finalCol - col))) {
				if (c == finalCol && board[row][c].getClass().getName().charAt(7) != 'w') return false;
				if (board[row][c] != null) return false;
			}
		}
		else if (col == finalCol) {
			for (int r = row; r == finalRow; r += ((finalRow - row)/Math.abs(finalRow - row))) {
				if (r == finalRow && board[r][col].getClass().getName().charAt(7) != 'w') return false;
				if (board[r][col] != null) return false;
			}
		}
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}