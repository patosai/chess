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
			//System.out.println(row + " " + col + "     " + finalRow + " " + finalCol);
			for (int c = col; c != finalCol; c += ((finalCol - col) / Math.abs(finalCol - col))) {
				if (board[row][c] != null) return false;
			}
		}
		if (col == finalCol) {
			System.out.println(row + " " + col + "     " + finalRow + " " + finalCol);
			for (int r = row; r != finalRow; r += ((finalRow - row) / Math.abs(finalRow - row))) {
				if (board[r][col] != null) return false;
			}
		}
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}