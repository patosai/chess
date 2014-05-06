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
			board[1][i] = new whitePawn();
			board[6][i] = new blackPawn();
		}
		board[0][0] = new whiteRook();
		board[0][1] = new whiteKnight();
		board[0][2] = new whiteBishop();
		board[0][3] = new whiteKing();
		board[0][4] = new whiteQueen();
		board[0][5] = new whiteBishop();
		board[0][6] = new whiteKnight();
		board[0][7] = new whiteRook();
		board[7][0] = new blackRook();
		board[7][1] = new blackKnight();
		board[7][2] = new blackBishop();
		board[7][3] = new blackKing();
		board[7][4] = new blackQueen();
		board[7][5] = new blackBishop();
		board[7][6] = new blackKnight();
		board[7][7] = new blackRook();
	}
}