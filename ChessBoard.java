import Pieces.*;

public class ChessBoard {
	private ChessPiece[][] board;
	
	protected boolean whiteToMove;
	
	protected boolean whiteInCheck;
	protected boolean blackInCheck;
	
	public ChessBoard() {
		board = new ChessPiece[8][8];
		whiteToMove = true;
		setupDefault();
	}
	
	public ChessBoard(int row, int col) {
		board = new ChessPiece[row][col];
		whiteToMove = true;
		setupDefault();
	}
	
	public ChessPiece getPiece(int row, int col) {
		return board[row][col];
	}
	
	public boolean isSameTeam(int initialRow, int initialCol, int finalRow, int finalCol) {
	return (
		board[initialRow][initialCol] != null && 
		board[finalRow][finalCol] != null && 
			(board[initialRow][initialCol].getClass().getName().charAt(7) ==
			board[finalRow][finalCol].getClass().getName().charAt(7)) 
			);
	}
	
	public void flipWhiteToMove() {
		whiteToMove = !whiteToMove;
	}
	
	public void setPiece(ChessPiece piece, int row, int col) {
		board[row][col] = piece;
		piece.setRow(row);
		piece.setCol(col);
	}
	
	public void movePiece(int initialRow, int initialCol, int finalRow, int finalCol) {
		ChessPiece aPiece = board[initialRow][initialCol];
		aPiece.setRow(finalRow);
		aPiece.setCol(finalCol);
		board[finalRow][finalCol] = aPiece;
		board[initialRow][initialCol] = null;
	}
	
	public boolean isMoveValid(int initialRow, int initialCol, int finalRow, int finalCol) {
		// here we go
			// same-color test
		if (board[finalRow][finalCol] != null && 
			(board[initialRow][initialCol].getClass().getName().charAt(7) ==
			board[finalRow][finalCol].getClass().getName().charAt(7)) 
			) {
			return false;
		}
		
			// can-the-piece-even-move-there test
		if (!board[initialRow][initialCol].canMoveToLocation(board, finalRow, finalCol)) return false;
		
			// is the king in check before/after the move?!
		boolean beforeInCheck = false;
		boolean afterInCheck = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] == null) continue;
				String piece = board[r][c].getClass().getName().substring(7);
				if (piece.equals("whiteKing") || piece.equals("blackKing")) {
					if (board[r][c].amIInCheck(board)) {
						beforeInCheck = true;
						board[finalRow][finalCol] = board[initialRow][initialCol];
						board[initialRow][initialCol] = null;
						if (board[r][c].amIInCheck(board)) afterInCheck = true;
						if (beforeInCheck == true && afterInCheck == true) {
							board[initialRow][initialCol] = board[finalRow][finalCol];
							board[finalRow][finalCol] = null;
							return false;
						}
					}
				}
			}
		}
		return true;
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
		board[0][3] = new whiteQueen(0, 3);
		board[0][4] = new whiteKing(0, 4);
		board[0][5] = new whiteBishop(0, 5);
		board[0][6] = new whiteKnight(0, 6);
		board[0][7] = new whiteRook(0, 7);
		board[7][0] = new blackRook(7, 0);
		board[7][1] = new blackKnight(7, 1);
		board[7][2] = new blackBishop(7, 2);
		board[7][3] = new blackQueen(7, 3);
		board[7][4] = new blackKing(7, 4);
		board[7][5] = new blackBishop(7, 5);
		board[7][6] = new blackKnight(7, 6);
		board[7][7] = new blackRook(7, 7);
	}
}