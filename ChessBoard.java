import Pieces.*;

public class ChessBoard {
	private ChessPiece[][] board;
	
	public ChessBoard() {
		board = new ChessPiece[8][8];
	}
	
	public ChessBoard(int row, int col) {
		board = new ChessPiece[row][col];
	}
	
	public ChessPiece getPiece(int row, int col) {
		return board[row][col];
	}
	
	public void setPiece(ChessPiece piece, int row, int col) {
		board[row][col] = piece;
	}
}