import java.util.ArrayList;
import javax.swing.*;
import Pieces.*;

public class ChessBoard {
	private ChessPiece[][] board;

	protected boolean whiteToMove;
	protected boolean whiteInCheck;
	protected boolean blackInCheck;

	protected int moveCounter = 1;

	//for undo
	ArrayList<UndoMoveObject> undoArray = new ArrayList<UndoMoveObject>();
	//for redo
	ArrayList<RedoMoveObject> redoArray = new ArrayList<RedoMoveObject>();

	//JTextArea stuff for toolbar
	protected JTextArea checkNotice;
	protected JTextArea showMoves;

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

	public ChessPiece getPiece(int row, int col) {return board[row][col];}
	public ChessPiece[][] getBoard() {return board;}
	public void changeBoard(ChessPiece[][] board) {this.board = board;}
	public boolean getWhitesMove() {return whiteToMove;}
	public JTextArea getMoves() {return showMoves;}
	public void setMoveText(String text) {showMoves.setText(text);}
	public int getMoveCount() {return moveCounter;}
	public void setMoveCount(int count) {moveCounter = count;}

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

	//no jtextarea update
	public boolean miniIsMoveValid(int initialRow, int initialCol, int finalRow, int finalCol) {
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
		ChessPiece whiteKing = null;
		ChessPiece blackKing = null;
		ChessPiece temp = board[finalRow][finalCol];
				//get king pointers
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] == null) continue;
				String name = board[r][c].getClass().getName().substring(7);
				if (name.equals("whiteKing")) whiteKing = board[r][c];
				if (name.equals("blackKing")) blackKing = board[r][c];
			}
		}
				//is white king in check before/after?
		if (whiteKing.amIInCheck(board)) {
			movePiece(initialRow, initialCol, finalRow, finalCol);
			if (whiteKing.amIInCheck(board)) {
				movePiece(finalRow, finalCol, initialRow, initialCol);
				board[finalRow][finalCol] = temp;
				return false;
			}
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
		}
				//is black king in check before/after?
		if (blackKing.amIInCheck(board)) {
			movePiece(initialRow, initialCol, finalRow, finalCol);
			if (blackKing.amIInCheck(board)) {
				movePiece(finalRow, finalCol, initialRow, initialCol);
				board[finalRow][finalCol] = temp;
				return false;
			}
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
		}

		//is king in check after the move?
		movePiece(initialRow, initialCol, finalRow, finalCol);
		if (whiteToMove && whiteKing.amIInCheck(board)) {
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
			return false;
		}
		if (!whiteToMove && blackKing.amIInCheck(board)) {
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
			return false;
		}
		movePiece(finalRow, finalCol, initialRow, initialCol);
		board[finalRow][finalCol] = temp;
		return true;
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
		ChessPiece whiteKing = null;
		ChessPiece blackKing = null;
		ChessPiece temp = board[finalRow][finalCol];
				//get king pointers
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] == null) continue;
				String name = board[r][c].getClass().getName().substring(7);
				if (name.equals("whiteKing")) whiteKing = board[r][c];
				if (name.equals("blackKing")) blackKing = board[r][c];
			}
		}
				//is white king in check before/after?
		if (whiteKing.amIInCheck(board)) {
			movePiece(initialRow, initialCol, finalRow, finalCol);
			if (whiteKing.amIInCheck(board)) {
				movePiece(finalRow, finalCol, initialRow, initialCol);
				board[finalRow][finalCol] = temp;
				return false;
			}
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
		}
				//is black king in check before/after?
		if (blackKing.amIInCheck(board)) {
			movePiece(initialRow, initialCol, finalRow, finalCol);
			if (blackKing.amIInCheck(board)) {
				movePiece(finalRow, finalCol, initialRow, initialCol);
				board[finalRow][finalCol] = temp;
				return false;
			}
			movePiece(finalRow, finalCol, initialRow, initialCol);
			board[finalRow][finalCol] = temp;
		}

		//is king in check after the move?
		movePiece(initialRow, initialCol, finalRow, finalCol);
		if (whiteToMove && whiteKing.amIInCheck(board)) {
			movePiece(finalRow, finalCol, initialRow, initialCol);
			return false;
		}
		if (!whiteToMove && blackKing.amIInCheck(board)) {
			movePiece(finalRow, finalCol, initialRow, initialCol);
			return false;
		}
			//update check notice
		if (!whiteToMove && whiteKing.amIInCheck(board)) {
			checkNotice.setText("");
			checkNotice.append("White is in check!");
		}
		else if (whiteToMove && blackKing.amIInCheck(board)) {
			checkNotice.setText("");
			checkNotice.append("Black is in check!");
		}
		else checkNotice.setText("");
		//movePiece(finalRow, finalCol, initialRow, initialCol);
		//board[finalRow][finalCol] = temp;

		//update if white or black is in check
		//movePiece(initialRow, initialCol, finalRow, finalCol);
		if (whiteKing.amIInCheck(board)) {
			whiteInCheck = true;
		}
		else whiteInCheck = false;
		if (blackKing.amIInCheck(board)) {
			blackInCheck = true;
		}
		else blackInCheck = false;

		//for JTextArea
		String pieceMove = board[finalRow][finalCol].toString();

		movePiece(finalRow, finalCol, initialRow, initialCol);
		board[finalRow][finalCol] = temp;
		if (board[finalRow][finalCol] != null) {
			pieceMove = pieceMove.substring(0, pieceMove.length() - 2) + "x" + pieceMove.substring(pieceMove.length() - 2, pieceMove.length());
		}
		if (whiteInCheck || blackInCheck) pieceMove = pieceMove + "+";

		//is it a castle?
		if (board[initialRow][initialCol] instanceof whiteKing || board[initialRow][initialCol] instanceof blackKing) {
			if (finalCol - initialCol == 2) pieceMove = "0-0";
			if (initialCol - finalCol == 2) pieceMove = "0-0-0";
		}
		//update move JTextArea
		if (whiteToMove) {
			showMoves.append(String.format("%-10s", moveCounter + ". " + pieceMove));
		}
		else {
			showMoves.append(pieceMove + "\n");
			moveCounter++;
		}

		//add to undo move array
		UndoMoveObject newUndo = new UndoMoveObject(board[initialRow][initialCol], initialRow, initialCol, board[finalRow][finalCol]);
		if (!(board[initialRow][initialCol].haveIMoved())) newUndo.setNotMoved();
		if (board[initialRow][initialCol] instanceof whiteKing || board[initialRow][initialCol] instanceof blackKing) {
			if (Math.abs(initialCol - finalCol) == 2) {
				newUndo.isCastle();
			}
		}
		undoArray.add(newUndo);

		flipWhiteToMove();
		return true;
	}

	public void undo() {
		UndoMoveObject undo = undoArray.get(undoArray.size() - 1);
		int row = undo.getUndoPiece().getRow();
		int col = undo.getUndoPiece().getCol();
		movePiece(row, col, undo.getLastRow(), undo.getLastCol());
		board[row][col] = undo.getTakenPiece();
		if (undo.hadNotMoved()) undo.getUndoPiece().undoHasMoved();
		if (undo.checkIfCastle()) {
			if (col - undo.getLastCol() == 2) {
				movePiece(row, col - 1, row, col + 1);
				board[row][col + 1].undoHasMoved();
			}
			if (undo.getLastCol() - col == 2) {
				movePiece(row, col + 1, row, col - 2);
				board[row][col - 2].undoHasMoved();
			}
		}

		//make redo object
		RedoMoveObject newRedo = new RedoMoveObject(board[undo.getLastRow()][undo.getLastCol()], row, col);
		if (undo.checkIfCastle()) newRedo.isCastle();
		redoArray.add(newRedo);

		undoArray.remove(undoArray.size() - 1);
		// truncate move JTextArea
			//undo black's move
		String moveText = showMoves.getText();
		int truncateLocation = 0;
		if (whiteToMove) {
			for (truncateLocation = moveText.length() - 1; truncateLocation > moveText.length() - 20; truncateLocation--) {
				if (moveText.charAt(truncateLocation) == ' ') break;
			}
			moveText = moveText.substring(0, truncateLocation + 1);
			moveCounter--;
		}
			//undo white's move
		if (!whiteToMove) {
			for (truncateLocation = moveText.length() - 1; truncateLocation > moveText.length() - 20; truncateLocation--) {
				if (moveText.charAt(truncateLocation) == '.') break;
			}
			moveText = moveText.substring(0, truncateLocation - 1);
		}
		showMoves.setText(moveText);
		flipWhiteToMove();


	}

	public void redo() {
		if (redoArray.size() == 0) return;
		RedoMoveObject redoObject = redoArray.get(redoArray.size() - 1);
		if (whiteToMove && redoObject.getRedoPiece().getClass().getName().charAt(7) == 'b') return;
		if (!whiteToMove && redoObject.getRedoPiece().getClass().getName().charAt(7) == 'w') return;
		ChessPiece redoPiece = redoObject.getRedoPiece();

		UndoMoveObject undoObject = new UndoMoveObject(redoPiece, redoPiece.getRow(), redoPiece.getCol(), board[redoObject.getFutureRow()][redoObject.getFutureCol()]);
		if (redoObject.checkIfCastle()) undoObject.isCastle();
		undoArray.add(undoObject);

		//for JTextArea
		boolean pieceWasTaken = (board[redoObject.getFutureRow()][redoObject.getFutureCol()] != null);
		if (redoObject.checkIfCastle()) {
			if (redoPiece.getCol() - redoObject.getFutureCol() == 2) {
				movePiece(redoPiece.getRow(), redoPiece.getCol() - 4, redoPiece.getRow(), redoPiece.getCol() - 1);
			}
			if (redoObject.getFutureCol() - redoPiece.getCol() == 2) {
				movePiece(redoPiece.getRow(), redoPiece.getCol() + 3, redoPiece.getRow(), redoPiece.getCol() + 1);
			}
		}
		int initialCol = redoPiece.getCol();
		movePiece(redoPiece.getRow(), redoPiece.getCol(), redoObject.getFutureRow(), redoObject.getFutureCol());

		String pieceMove = redoObject.getRedoPiece().toString();

		if (pieceWasTaken) {
			pieceMove = pieceMove.substring(0, pieceMove.length() - 2) + "x" + pieceMove.substring(pieceMove.length() - 2, pieceMove.length());
		}
		if (whiteInCheck || blackInCheck) pieceMove = pieceMove + "+";
		//is it a castle?
		if (redoPiece instanceof whiteKing || redoPiece instanceof blackKing) {
			if (redoObject.getFutureCol() - initialCol == 2) pieceMove = "0-0";
			if (initialCol - redoObject.getFutureCol() == 2) pieceMove = "0-0-0";
		}
		//update move JTextArea
		if (whiteToMove) {
			showMoves.append(String.format("%-15s", moveCounter + ". " + pieceMove));
		}
		else {
			showMoves.append(pieceMove + "\n");
			moveCounter++;
		}
		flipWhiteToMove();
		redoArray.remove(redoArray.size() - 1);
	}

	public void resetBoard() {
		moveCounter = 1;
		showMoves.setText(String.format("%-10s", "White") + "Black");
		checkNotice.setText("");
		if (!whiteToMove) flipWhiteToMove();
	}

	public ArrayList<Integer> getPossibleMoves(ChessPiece piece) {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (miniIsMoveValid(piece.getRow(), piece.getCol(), r, c)) {
					Integer a = (10 * r) + c;
					possibleMoves.add(a);
				}
			}
		}
		return possibleMoves;
	}

	public boolean isCheckmate() {
		ChessPiece wKing = null;
		ChessPiece bKing = null;
		//get pointers for kings
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] == null) continue;
				if (board[r][c] instanceof blackKing) bKing = board[r][c];
				if (board[r][c] instanceof whiteKing) wKing = board[r][c];
			}
		}
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] == null) continue;
				if (whiteToMove && board[r][c].getClass().getName().charAt(7) == 'b') continue;
				if (!whiteToMove && board[r][c].getClass().getName().charAt(7) == 'w') continue;
				ArrayList<Integer> possMoves = getPossibleMoves(board[r][c]);
				if (possMoves.size() > 0) {
					return false;
				}
			}
		}
		return true;
	}

	// default setup
	public void setupDefault() {
		//preliminary clearing of board
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				board[r][c] = null;
			}
		}
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
