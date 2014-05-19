import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

// import the chess pieces
import Pieces.*;

public class Chess extends JPanel{
	private ChessBoard board;
	
	private String playerName = "default";
	private JTextArea chatBox;
	private JTextField chatField;
	
	// is a tile selected and highlighted?
		// true if a piece is on tile and tile has been clicked on
	private boolean tileSelected;
	
	private final int F_WIDTH = 475;
	private final int F_HEIGHT = 475;
	
	// drawing variables
	private boolean reverseDrawing;
	private final int gridSize = 50;
	private final int initialX = 25; // default 25
	private final int initialY = 30; // default 50
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
	
	/////////// MENUBAR STUFF ////////////
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem;
	JRadioButtonMenuItem radioButton;
	JButton button;
	//////////////////////////////////////
	/////////// TOOLBAR STUFF ////////////
	JToolBar toolBar;
	//////////////////////////////////////
	
	public Chess() {
		loadSprites();							// get sprites
		board = new ChessBoard();				// initialize an empty board
		addMouseListener(new MouseListener());	// for MouseListener
		reverseDrawing = false;					// white/black side change. false = white
		tileSelected = false;
	}

	public final JMenuBar initializeMenubar() {
		// make the toolbar
		setLayout(new BorderLayout());
		
		menuBar = new JMenuBar();
		
		///// Game Menu
		menu = new JMenu("Game");
		menuItem = new JMenuItem("New");
		menu.add(menuItem);
		//menuItem.addActionListener(new Listener_NewGame());
		menuItem.setActionCommand("new_game");
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuItem = new JMenuItem("Open");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuItem = new JMenuItem("Save");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuItem = new JMenuItem("Quit");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuItem.setActionCommand("quit");
		menuItem.addActionListener(new AllEncompassingListener());
		menu.addSeparator();
		
		menuItem = new JMenuItem("Undo move");
		menuItem.setActionCommand("undo_move");
		//menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.addActionListener(new AllEncompassingListener());
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Redo move");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuItem = new JMenuItem("Resign");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());
		
		menuBar.add(menu);
		
		///// Connect Menu
		menu = new JMenu("Connect");
		menuItem = new JMenuItem("Connect to IP/Port");
		menu.add(menuItem);
		menuBar.add(menu);
		
		///// Options Menu
		menu = new JMenu("Options");
		menuItem = new JMenuItem("Change name");
		menu.add(menuItem);
		menuItem.setActionCommand("change_name");
		menuItem.addActionListener(new AllEncompassingListener());
		menuBar.add(menu);
		
		///// About Menu
		menu = new JMenu("About");
		menuItem = new JMenuItem("Help");
		menu.add(menuItem);
		menuItem = new JMenuItem("About");
		menu.add(menuItem);
		menuBar.add(menu);
		
		return menuBar;
		
	}
	
	public final JToolBar initializeToolbar() {
		toolBar = new JToolBar("sidebar", JToolBar.VERTICAL);
		toolBar.setPreferredSize(new Dimension(100, F_HEIGHT));
		toolBar.setFloatable(false);
		
		//toolBar.setLayout(new GridBagLayout());
		//GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		
		board.checkNotice = new JTextArea(2, 1);
		board.checkNotice.setFont(new Font("Verdana", Font.BOLD, 16));
		board.checkNotice.setForeground(Color.RED);
		board.checkNotice.setBackground(Color.BLACK);
		board.checkNotice.setEditable(false);
		board.checkNotice.setLineWrap(true);
		board.checkNotice.setMaximumSize(board.checkNotice.getPreferredSize());
		toolBar.add(board.checkNotice);
		
		board.showMoves = new JTextArea(String.format("%-8s", "  White") + "| Black\n", 10, 1);
		board.showMoves.setBackground(new Color(179, 179, 179));
		board.showMoves.setLineWrap(true);
		board.showMoves.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(board.showMoves);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(100, F_HEIGHT - 200));
		toolBar.add(scrollPane, BorderLayout.CENTER);
		
		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		scrollPane = new JScrollPane(chatBox);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(100, F_HEIGHT - 200));
		toolBar.add(scrollPane, BorderLayout.CENTER);
		
		chatField = new JTextField();
		chatField.setActionCommand("chatbox_message");
		chatField.addActionListener(new AllEncompassingListener());
		toolBar.add(chatField);
		
		return toolBar;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(F_WIDTH, F_HEIGHT);
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
		
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(c);
		frame.setJMenuBar(c.initializeMenubar());
		frame.getContentPane().add(c.initializeToolbar(), BorderLayout.EAST);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		while (true) {
			c.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
	
	//~~~~~~~~~~~~~~~~ PAINTING METHOD ~~~~~~~~~~~~~~~~//
		
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
					g2.drawString((i + 1) + "", tempX, tempY);
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
					if (!reverseDrawing)
						g2.fill(new Rectangle2D.Double( selectedCol * 50 + initialX, (7 - selectedRow) * 50 + initialY, 50, 50));
					else
						g2.fill(new Rectangle2D.Double( (7 - selectedCol) * 50 + initialX, selectedRow * 50 + initialY, 50, 50));
				}
				else tileSelected = false;
			}
			else tileSelected = false;
			
			// chess piece position painting
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					if (reverseDrawing) {
						if (board.getPiece(7 - r, 7 - c) == null) continue;
					}
					else {
						if (board.getPiece(r, c) == null) continue;
					}
					
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
	//~~~~~~~~~~~~ END OF PAINTING METHOD ~~~~~~~~~~~~~//
	
	//~~~~~~~~~~~~~~~~ Mouse Listener ~~~~~~~~~~~~~~~~//
	public class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			rawX = e.getX();
			rawY = e.getY() + 25; // 25 offset for menubar
			
			int newSelectedCol = (rawX - initialX) / 50;
			int newSelectedRow = 7 - ((rawY - 25 - initialY) / 50);
			
			if (reverseDrawing) {
				newSelectedCol = 7 - newSelectedCol;
				newSelectedRow = 7 - newSelectedRow;
			}
			
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
	
	///////////// now for all the action listener ///////////////
	class AllEncompassingListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "new_game":
					board.setupDefault();
					if (!board.whiteToMove) board.flipWhiteToMove();
					break;
				case "quit":
					System.exit(0);
				case "undo_move":
					if (board.undoArray.size() > 0) {
						board.undo();
					}
					break;
				case "change_name":
					String newname = JOptionPane.showInputDialog("Enter a new name here:");
					playerName = newname;
					break;
				case "chatbox_message":
					String message = chatField.getText();
					chatField.setText("");
					chatBox.append(playerName + ": " + message + "\n");
					break;
				default:
					break;
			}
		}
	}
	////////////////////////////////////////////////////////////////////////////
}