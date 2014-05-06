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
		board[0][0] = new whiteRook(0, 0);
		board[0][1] = new whiteKnight(0, 1);
		board[0][2] = new whiteBishop(0, 2);
		board[0][3] = new whiteKing(0, 3);
		board[0][4] = new whiteQueen(0, 4);
		board[0][5] = new whiteBishop(0, 5);
		board[0][6] = new whiteKnight(0, 6);
		board[0][7] = new whiteRook(0, 7);
		board[7][0] = new blackRook(7, 0);
		board[7][1] = new blackKnight(7, 1);
		board[7][2] = new blackBishop(7, 2);
		board[7][3] = new blackKing(7, 3);
		board[7][4] = new blackQueen(7, 4);
		board[7][5] = new blackBishop(7, 5);
		board[7][6] = new blackKnight(7, 6);
		board[7][7] = new blackRook(7, 7);
	}
}