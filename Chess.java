import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

// import the chess pieces
import Pieces.*;

public class Chess extends JFrame{
	private ChessBoard board;
	
	// is a tile selected and highlighted?
		// true if a piece is on tile and tile has been clicked on
	private boolean tileSelected;
	
	// drawing variables
	private boolean reverseDrawing;
	private final int gridSize = 50;
	private final int initialX = 25; // default 25
	private final int initialY = 50; // default 50
	// mouse variables
	private int rawX;
	private int rawY;
	private Integer selectedRow;
	private Integer selectedCol;
		// establish chess piece image sizes; default = 50
	private final int pieceX = 50;
	private final int pieceY = 50;
	
	//  chess piece Image files  //
	Image whiteKing;
	Image whiteQueen;
	Image whiteRook;
	Image whiteBishop;
	Image whiteKnight;
	Image whitePawn;
	Image blackKing;
	Image blackQueen;
	Image blackRook;
	Image blackBishop;
	Image blackKnight;
	Image blackPawn;
	//  //  //  //   //  //  //  //
	
	
	JPanel drawing;
	
	public Chess() {
		initializeGUI();						// do initial GUI fancy stuff
		//initializeToolbar();					// create the toolbar
		loadSprites();							// get sprites
		board = new ChessBoard();				// initialize an empty board
		add(new Painting());					// for painting class
		addMouseListener(new MouseListener());	// for MouseListener
		reverseDrawing = false;					// white/black side change. false = white
		tileSelected = false;
	}
	
	public final void initializeGUI() {
		setTitle("Chess");
		setSize(575, 500); //width, height
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setResizable(false);
		requestFocusInWindow();
	}
	
	public final void initializeToolbar() {
		// make the toolbar
		//JMenuBar menuBar;
		//JMenu menu, submenu,
		//JMenuItem menuItem;
		
		// create the menu bar
		JMenuBar menuBar = new JMenuBar();
		
		// create the file menu
		JMenu menu = new JMenu("File");
		menu.getAccessibleContext().setAccessibleDescription(
        "Core program operations");
		menuBar.add(menu);
			
			// create the New button
		JMenuItem menuItem = new JMenuItem("New");
		menu.add(menuItem);
		setJMenuBar(menuBar);
	}
	
	public final void loadSprites() {
		Toolkit tkit = Toolkit.getDefaultToolkit();
		whiteKing   = tkit.getImage(Chess.class.getResource("Sprites/whiteKing.png"));
		whiteQueen  = tkit.getImage(Chess.class.getResource("Sprites/whiteQueen.png"));
		whiteRook   = tkit.getImage(Chess.class.getResource("Sprites/whiteRook.png"));
		whiteBishop = tkit.getImage(Chess.class.getResource("Sprites/whiteBishop.png"));
		whiteKnight = tkit.getImage(Chess.class.getResource("Sprites/whiteKnight.png"));
		whitePawn   = tkit.getImage(Chess.class.getResource("Sprites/whitePawn.png"));
		blackKing   = tkit.getImage(Chess.class.getResource("Sprites/blackKing.png"));
		blackQueen  = tkit.getImage(Chess.class.getResource("Sprites/blackQueen.png"));
		blackRook   = tkit.getImage(Chess.class.getResource("Sprites/blackRook.png"));
		blackBishop = tkit.getImage(Chess.class.getResource("Sprites/blackBishop.png"));
		blackKnight = tkit.getImage(Chess.class.getResource("Sprites/blackKnight.png"));
		blackPawn   = tkit.getImage(Chess.class.getResource("Sprites/blackPawn.png"));
	}
	
	public static void main(String[] args) {
		Chess c = new Chess();
		while (true) {
			c.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {}
		}
	}
	
	//~~~~~~~~~~~~~~~~ PAINTING CLASS ~~~~~~~~~~~~~~~~//
	public class Painting extends JPanel {
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			setBackground(Color.BLACK);
			
			// initial checker board setup
			int tempX = initialX;
			int tempY = initialY;
			for (int j = 0; j < 8; j++) {
				tempX = initialX;
				for (int i = 0; i < 8; i++) {
					if ((i + j) % 2 == 1) {
						g2.setPaint(new Color(179, 86, 5));
					}
					else g2.setPaint(Color.WHITE);
					g2.fill(new Rectangle2D.Double(tempX, tempY, gridSize, gridSize));
					tempX += gridSize;
				}
				tempY += gridSize;
			}
			
			// paint row/col labels
			tempX = initialX - 15;
			tempY = initialY + 28;
				// draw rows
			for (int i = 0; i < 8; i++) {
				if (reverseDrawing) {
					g2.drawString((char)(65 + i) + "", tempX, tempY);
				}
				else {
					g2.drawString((char)(65 + (7 - i)) + "", tempX, tempY);
				}
				tempY += gridSize;
			}
				// draw cols
			tempX = initialX + 22;
			tempY = initialY + 415;
			for (int i = 0; i < 8; i++) {
				if (reverseDrawing) {
					g2.drawString(8 - i + "", tempX, tempY);
				}
				else {
					g2.drawString(i + "", tempX, tempY);
				}
				tempX += gridSize;
			}
			
			// selected tile highlighting 
			if (selectedRow != null && rawX < (400 + initialX) && rawY > (25 + initialY)
				&& rawX > initialX && rawY < (425 + initialY) && board.getPiece(selectedRow, selectedCol) != null) {
				if ((board.whiteToMove && board.getPiece(selectedRow, selectedCol).getClass().getName().charAt(7) == 'w') ||
					(!board.whiteToMove && board.getPiece(selectedRow, selectedCol).getClass().getName().charAt(7) == 'b')) {
					tileSelected = true;
					if ((selectedRow + selectedCol) % 2 == 0) {
						g2.setPaint(new Color(250, 167, 77));
					}
					else g2.setPaint(new Color(199, 189, 179));
					g2.fill(new Rectangle2D.Double( selectedCol * 50 + initialX, (7 - selectedRow) * 50 + initialY, 50, 50));
				}
				else tileSelected = false;
			}
			else tileSelected = false;
			
			// chess piece position painting
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (board.getPiece(r, c) == null) continue;
					
					String pieceName;
					// substring(7) removes the "Pieces." header in the getName() function
					if (reverseDrawing) {
						pieceName = board.getPiece(7 - r, 7 - c).getClass().getName().substring(7);
					}
					else pieceName = board.getPiece(r, c).getClass().getName().substring(7);

					int x = (c * 50) + initialX;
					int y = 350 - (r * 50) + initialY;
					switch (pieceName) {
						case "whitePawn":
							g2.drawImage(whitePawn, x, y, pieceX, pieceY, this);
							break;
						case "whiteKnight":
							g2.drawImage(whiteKnight, x, y, pieceX, pieceY, this);
							break;
						case "whiteBishop":
							g2.drawImage(whiteBishop, x, y, pieceX, pieceY, this);
							break;
						case "whiteRook":
							g2.drawImage(whiteRook, x, y, pieceX, pieceY, this);
							break;
						case "whiteQueen":
							g2.drawImage(whiteQueen, x, y, pieceX, pieceY, this);
							break;
						case "whiteKing":
							g2.drawImage(whiteKing, x, y, pieceX, pieceY, this);
							break;
						case "blackPawn":
							g2.drawImage(blackPawn, x, y, pieceX, pieceY, this);
							break;
						case "blackKnight":
							g2.drawImage(blackKnight, x, y, pieceX, pieceY, this);
							break;
						case "blackBishop":
							g2.drawImage(blackBishop, x, y, pieceX, pieceY, this);
							break;
						case "blackRook":
							g2.drawImage(blackRook, x, y, pieceX, pieceY, this);
							break;
						case "blackQueen":
							g2.drawImage(blackQueen, x, y, pieceX, pieceY, this);
							break;
						case "blackKing":
							g2.drawImage(blackKing, x, y, pieceX, pieceY, this);
							break;
						default:
							break;
					}
				}
			} // end of chess piece position painting
			
		}
	}
	//~~~~~~~~~~~~ END OF PAINTING CLASS ~~~~~~~~~~~~~//
	
	//~~~~~~~~~~~~~~~~ Mouse Listener ~~~~~~~~~~~~~~~~//
	public class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			rawX = e.getX();
			rawY = e.getY();
			
			int newSelectedCol = (rawX - initialX) / 50;
			int newSelectedRow = 7 - ((rawY - 25 - initialY) / 50);
			
			// check if new X/Y is in board bounds
			if (tileSelected && rawX < (400 + initialX) && rawY > (25 + initialY)
				&& rawX > initialX && rawY < (425 + initialY)) {
				if (board.isMoveValid(selectedRow, selectedCol, newSelectedRow, newSelectedCol)) {
					board.movePiece(selectedRow, selectedCol, newSelectedRow, newSelectedCol);
					if (board.getPiece(newSelectedRow, newSelectedCol) != null)
						board.getPiece(newSelectedRow, newSelectedCol).moved();
					tileSelected = false;
					selectedRow = null;
					selectedCol = null;
					//remove en Passant moves
					for (int r = 0; r < 8; r++) {
						for (int c = 0; c < 8; c++) {
							if (board.whiteToMove && board.getPiece(r, c) != null) {
								if (board.getPiece(r, c).getClass().getName().substring(7).equals("blackPawn")) {
									if (board.getPiece(r, c).possibleEnPassant()) {
										board.getPiece(r, c).switchEnPassant();
									}
								}
							}
							else if (!board.whiteToMove && board.getPiece(r, c) != null) {
								if (board.getPiece(r, c) != null && board.getPiece(r, c).getClass().getName().substring(7).equals("whitePawn")) {
									if (board.getPiece(r, c).possibleEnPassant()) {
										board.getPiece(r, c).switchEnPassant();
									}
								}
							}
						}
					} // end en Passant possibility switch
					return;
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {};
			}
			else {
				selectedRow = null;
			}
			selectedCol = newSelectedCol;
			selectedRow = newSelectedRow;
		}
	}
	//~~~~~~~~~~~~ End of Mouse Listener ~~~~~~~~~~~~~//
}