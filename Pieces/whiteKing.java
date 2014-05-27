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
		//check if castling
		  //0-0
		if (dy == 0 && dx == 2) {
			if (!hasMoved && board[7][7] != null && !board[7][7].hasMoved) {
				for (int c = col + 1; c <= finalCol; c++) {
					if (board[row][c] != null) return false;
				}
				//am I in check?
				col++;
				if (amIInCheck(board)) {
					col--;
					return false;
				}
				col++;
				if (amIInCheck(board)) {
					col-= 2;
				}
				col-= 2;
				
				//do speshul stuff
				ChessPiece rook = board[0][7];
				board[0][5] = rook;
				board[0][7] = null;
				rook.setCol(5);
				rook.moved();
				return true;
			}
			else return false;
		}
		else if (dy == 0 && dx == (-2)) {
			if (!hasMoved && board[7][0] != null && !board[7][0].hasMoved) {
				for (int c = finalCol; c < col; c++) {
					if (board[row][c] != null) return false;
				}
				
				//am I in check?
				col--;
				if (amIInCheck(board)) {
					col++;
					return false;
				}
				col--;
				if (amIInCheck(board)) {
					col += 2;
					return false;
				}
				col+= 2;
				
				//do more speshul stuff
				ChessPiece rook = board[0][0];
				board[0][3] = rook;
				board[0][0] = null;
				rook.setCol(3);
				rook.moved();
				return true;
			}
			else return false;
		}
		else if ((Math.abs(dx) > 1) || (Math.abs(dy) > 1)) return false;
		if (board[finalRow][finalCol] != null && board[finalRow][finalCol].getClass().getName().charAt(7) != 'b') return false;
		
		return true;
	}
	
	public boolean amIInCheck(ChessPiece[][] board) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null && board[r][c].getClass().getName().charAt(7) == 'b') {
					if (board[r][c].canMoveToLocation(board, row, col)) return true;
				}
			}
		}
		return false;
	}
	
	public boolean possibleEnPassant() {return false;}
	public void switchEnPassant() {}
}