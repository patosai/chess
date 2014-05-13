package Pieces;

public class blackKing extends ChessPiece {
	public blackKing(int row, int col) {
		this.row = row;
		this.col = col;
		letter = 'K';
		hasMoved = false;
	}
	
	public boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol) {
		int dx = finalCol - col;
		int dy = finalRow - row;
		//check if castling
		  //0-0
		if (dy == 0 && dx == 2) {
			if (!hasMoved && board[7][7] != null && !board[7][7].hasMoved) {
				for (int c = col + 1; c <= finalCol; c++) {
					if (board[row][c] != null) return false;
				}
				//am I in check?
				
				//do speshul stuff
				board[7][7] = board[7][5];
				board[7][5] = null;
				setRow(7);
				setCol(7);
				board[7][6] = board[7][7];
				board[7][7] = null;
				board[7][6].setRow(7);
				board[7][6].setCol(7);
				moved();
				board[7][6].moved();
				return false;
			}
		}
		else if (dy == 0 && dx == (-2)) {
			if (!hasMoved && board[7][0] != null && !board[7][0].hasMoved) {
				for (int c = finalCol; c < col; c++) {
					if (board[row][c] != null) return false;
				}
				//am I in check?
				
				//do more speshul stuff
				board[7][3] = board[7][5];
				board[7][5] = null;
				setRow(7);
				setCol(3);
				board[7][4] = board[7][0];
				board[7][0] = null;
				board[7][4].setRow(7);
				board[7][4].setCol(4);
				moved();
				board[7][4].moved();
				return false;
			}
		}
		else if ((Math.abs(dx) > 1) || (Math.abs(dy) > 1)) return false;
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'w') return false;
		return true;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}