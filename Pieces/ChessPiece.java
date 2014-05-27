package Pieces;

public abstract class ChessPiece {
	protected int row;
	protected int col;
	protected char letter;
	
	protected boolean hasMoved;
	
	protected int prevRow;
	protected int prevCol;
	
	public int getRow() {return row;}
	public int getCol() {return col;}
	public void setRow(int row) {this.row = row;}
	public void setCol(int col) {this.col = col;}
	public String colToLetter() {return (char)(97 + col) + "";}
	public String toString() {return letter + colToLetter() + (row + 1);}
	public String getPosition() {return colToLetter() + row;}
	public void moved() {if (!hasMoved) hasMoved = true;}
	public boolean haveIMoved() {return hasMoved;}
	public void undoHasMoved() {hasMoved = false;}
	
	public abstract boolean canMoveToLocation(ChessPiece[][] board, int finalRow, int finalCol);
	public abstract boolean possibleEnPassant();
	public abstract void switchEnPassant();
	public abstract boolean amIInCheck(ChessPiece[][] board);
}