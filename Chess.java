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
		loadSprites();							// get sprites
		board = new ChessBoard();				// initialize an empty board
		add(new Painting());					// for painting class
		addMouseListener(new MouseListener());	// for MouseListener
	}
	
	public final void initializeGUI() {
		setTitle("Chess");
		setSize(550, 475); //width, height
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setResizable(false);
		requestFocusInWindow();
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
			int gridSize = 50;
			int initialX = 0;
			int initialY = 50;
			
			// initial checker board setup
			for (int j = 0; j < 8; j++) {
				initialX = 0;
				for (int i = 0; i < 8; i++) {
					if ((i + j) % 2 == 0) {
						g2.setPaint(new Color(179, 86, 5));
					}
					else {
						g2.setPaint(Color.WHITE);
					}
					g2.fill(new Rectangle2D.Double(initialX, initialY, gridSize, gridSize));
					initialX += gridSize;
				}
				initialY += gridSize;
			}
			
			// selected tile highlighting
			if (selectedRow != null && rawX < 400 && rawY > 75) {
				if ((selectedRow + selectedCol) % 2 == 0) {
					g2.setPaint(new Color(250, 167, 77));
				}
				else g2.setPaint(new Color(199, 189, 179));
				g2.fill(new Rectangle2D.Double( selectedCol * 50, selectedRow * 50 + 50, 50, 50));
			}
			
			// chess piece position painting
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (board.getPiece(r, c) == null) continue;
					String pieceName = board.getPiece(r, c).getClass().getName();
					int x = 350 - (c * 50);
					int y = 400 - (r * 50);
					switch (pieceName) {
						case "Pieces.whitePawn":
							g.drawImage(whitePawn, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.whiteKnight":
							g.drawImage(whiteKnight, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.whiteBishop":
							g.drawImage(whiteBishop, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.whiteRook":
							g.drawImage(whiteRook, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.whiteQueen":
							g.drawImage(whiteQueen, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.whiteKing":
							g.drawImage(whiteKing, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackPawn":
							g.drawImage(blackPawn, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackKnight":
							g.drawImage(blackKnight, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackBishop":
							g.drawImage(blackBishop, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackRook":
							g.drawImage(blackRook, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackQueen":
							g.drawImage(blackQueen, x, y, pieceX, pieceY, this);
							break;
						case "Pieces.blackKing":
							g.drawImage(blackKing, x, y, pieceX, pieceY, this);
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
			selectedCol = e.getX() - 2;
			selectedRow = e.getY() - 75;
			selectedRow /= 50;
			selectedCol /= 50;
			rawX = e.getX();
			rawY = e.getY();
		}
	}
	//~~~~~~~~~~~~ End of Mouse Listener ~~~~~~~~~~~~~//
}