package Pieces;

public class UndoMoveObject {
	private ChessPiece pieceTakenOffBoard;
	private ChessPiece pieceLastMoved;
	
	private int lastCol;
	private int lastRow;
	
	public UndoMoveObject(ChessPiece piece, int lastRow, int lastCol, ChessPiece gonePiece) {
		pieceLastMoved = piece;
		pieceTakenOffBoard = gonePiece;
		this.lastRow = lastRow;
		this.lastCol = lastCol;
	}
	
	public int getLastRow() {
		return lastRow;
	}
	
	public int getLastCol() {
		return lastCol;
	}
	
	public ChessPiece getTakenPiece() {
		return pieceTakenOffBoard;
	}
	
	public ChessPiece getUndoPiece() {
		return pieceLastMoved;
	}
}