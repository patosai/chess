import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import pieces.*;

public class Chess extends JFrame{
	ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();
	
	public Chess() {
	
		// get sprites
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
		Toolkit tkit = Toolkit.getDefaultToolkit();
		whiteKing   = tkit.getImage(Chess.class.getResource("Pieces/whiteKing.png"));
		whiteQueen  = tkit.getImage(Chess.class.getResource("Pieces/whiteQueen.png"));
		whiteRook   = tkit.getImage(Chess.class.getResource("Pieces/whiteRook.png"));
		whiteBishop = tkit.getImage(Chess.class.getResource("Pieces/whiteBishop.png"));
		whiteKnight = tkit.getImage(Chess.class.getResource("Pieces/whiteKnight.png"));
		whitePawn   = tkit.getImage(Chess.class.getResource("Pieces/whitePawn.png"));
		blackKing   = tkit.getImage(Chess.class.getResource("Pieces/blackKing.png"));
		blackQueen  = tkit.getImage(Chess.class.getResource("Pieces/blackQueen.png"));
		blackRook   = tkit.getImage(Chess.class.getResource("Pieces/blackRook.png"));
		blackBishop = tkit.getImage(Chess.class.getResource("Pieces/blackBishop.png"));
		blackKnight = tkit.getImage(Chess.class.getResource("Pieces/blackKnight.png"));
		blackPawn   = tkit.getImage(Chess.class.getResource("Pieces/blackPawn.png"));
		
		// initialize an empty board
		for (int i = 0; i < 8; i++) {
			ArrayList<Object> column = new ArrayList<Object>();
			board.add(column);
		}
		
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
	}
	
	//~~~~~~~~~~~~~~~~ PAINTING CLASS ~~~~~~~~~~~~~~~~//
	public class Painting extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
		}
	}
	//~~~~~~~~~~~~ END OF PAINTING CLASS ~~~~~~~~~~~~~//
}