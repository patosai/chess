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
	
	public void movePiece(int initialRow, int initialCol, int finalRow, int finalCol) {
		ChessPiece aPiece = board[initialRow][initialCol];
		board[initialRow][initialCol] = null;
		board[finalRow][finalCol] = aPiece;
	}
	
	// default setup
	public void setupDefault() {
		for (int i = 0; i < 8; i++) {
			board[1][i] = new whitePawn(1, i);
			board[6][i] = new blackPawn(6, i);
		}
	}
}