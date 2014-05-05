import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

// import the chess pieces
import Pieces.*;

public class Chess extends JFrame{
	ChessBoard board;
	
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
		// get sprites
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
		
		// initialize an empty board
		board = new ChessBoard();
		
		// do initial GUI fancy stuff
		setTitle("Chess");
		setSize(700, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setResizable(false);
		requestFocusInWindow();
		
		// for painting class
		drawing = new Painting();
		add(drawing);
		
		// for MouseListener
		addMouseListener(new MouseListener());
	}
	
	//~~~~~~~~~~~~~~~~ PAINTING CLASS ~~~~~~~~~~~~~~~~//
	public class Painting extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}
	//~~~~~~~~~~~~ END OF PAINTING CLASS ~~~~~~~~~~~~~//
	
	public class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			
		}
	}
}