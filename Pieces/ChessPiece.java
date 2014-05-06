package Pieces;

public class ChessPiece {
	protected int row;
	protected int col;
	protected char letter;
	
	/*
	public ChessPiece(int row, int col) {
		this.row = row;
		this.col = col;
	}
	*/
	
	public int getRow() {return row;}
	public int getCol() {return col;}
	
	public String colToLetter() {
		return (char)(65 + col) + "";
	}
	
	public String toString() {
		return colToLetter() + row;
	}
}