package Pieces;

public class RedoMoveObject {
	private ChessPiece pieceToMove;
	
	private int futureCol;
	private int futureRow;
	
	public RedoMoveObject(ChessPiece piece, int futureRow, int futureCol) {
		pieceToMove = piece;
		this.futureRow = futureRow;
		this.futureCol = futureCol;
	}
	
	public int getFutureRow() {
		return futureRow;
	}
	
	public int getFutureCol() {
		return futureCol;
	}
	
	public ChessPiece getRedoPiece() {
		return pieceToMove;
	}
}