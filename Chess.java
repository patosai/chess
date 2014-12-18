import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

// import the chess pieces
import Pieces.*;

public class Chess extends JPanel{
	private ChessBoard board;

	//Sockets
	private String hostName;
	private int portNumber;
	private boolean isConnected;
	private boolean isWhite;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private InputStream inputStream;
	private String inputLine, outputLine;
		//server only
	private ServerSocket serverSocket;

	//everything - mouse listener
	private AllEncompassingListener listener = new AllEncompassingListener();

	private boolean checkmaaaaate = false;

	private JFrame frame;
	private JTextArea chatBox;
	private JTextField chatField;
	private String playerName = "default";
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
		menu.setMnemonic('G');
		menuItem = new JMenuItem("New");
		menu.add(menuItem);
		menuItem.setActionCommand("new_game");
		menuItem.setMnemonic('N');
		menuItem.addActionListener(new AllEncompassingListener());

		menuItem = new JMenuItem("Open");
		menu.add(menuItem);
		menuItem.setMnemonic('O');
		menuItem.setActionCommand("open");
		menuItem.addActionListener(new AllEncompassingListener());

		menuItem = new JMenuItem("Save");
		menu.add(menuItem);
		menuItem.setMnemonic('S');
		menuItem.setActionCommand("save");
		menuItem.addActionListener(new AllEncompassingListener());

		menuItem = new JMenuItem("Quit");
		menu.add(menuItem);
		menuItem.setMnemonic('Q');
		menuItem.addActionListener(new AllEncompassingListener());
		menuItem.setActionCommand("quit");
		menu.addSeparator();

		menuItem = new JMenuItem("Undo move");
		menuItem.setActionCommand("undo_move");
		menuItem.setMnemonic('U');
		menuItem.addActionListener(new AllEncompassingListener());
		menu.add(menuItem);

		menuItem = new JMenuItem("Redo move");
		menuItem.setMnemonic('R');
		menuItem.setActionCommand("redo_move");
		menu.add(menuItem);
		menuItem.addActionListener(new AllEncompassingListener());

		menuItem = new JMenuItem("Resign");
		menuItem.setMnemonic('E');
		menu.add(menuItem);
		menuItem.setActionCommand("resign");
		menuItem.addActionListener(new AllEncompassingListener());

		menuBar.add(menu);

		///// Connect Menu
		menu = new JMenu("Connect");
		menu.setMnemonic('C');
		menuItem = new JMenuItem("Connect to IP/Port");
		menuItem.setActionCommand("connect");
		menuItem.setMnemonic('C');
		menuItem.addActionListener(new AllEncompassingListener());
		menu.add(menuItem);
		menuItem = new JMenuItem("Set up a connection client");
		menuItem.setActionCommand("server");
		menuItem.setMnemonic('S');
		menuItem.addActionListener(new AllEncompassingListener());
		menu.add(menuItem);
		menuBar.add(menu);

		///// Options Menu
		menu = new JMenu("Options");
		menu.setMnemonic('O');
		menuItem = new JMenuItem("Change name");
		menuItem.setMnemonic('C');
		menu.add(menuItem);
		menuItem.setActionCommand("change_name");
		menuItem.addActionListener(new AllEncompassingListener());
		menuBar.add(menu);

		///// About Menu
		menu = new JMenu("About");
		menu.setMnemonic('A');
		menuItem = new JMenuItem("Help");
		menuItem.setMnemonic('H');
		menu.add(menuItem);
		menuItem = new JMenuItem("About");
		menuItem.setMnemonic('A');
		menuItem.setActionCommand("about");
		menuItem.addActionListener(new AllEncompassingListener());
		menu.add(menuItem);
		menuBar.add(menu);

		return menuBar;

	}

	public final JToolBar initializeToolbar() {
		toolBar = new JToolBar("sidebar", JToolBar.VERTICAL);
		toolBar.setPreferredSize(new Dimension(125, F_HEIGHT));
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
		board.checkNotice.setWrapStyleWord(true);
		//board.checkNotice.setMaximumSize(board.checkNotice.getPreferredSize());
		board.checkNotice.setPreferredSize(new Dimension(125, 50));
		toolBar.add(board.checkNotice);

		board.showMoves = new JTextArea(String.format("%-10s", "White") + "Black\n", 10, 1);
		board.showMoves.setBackground(new Color(179, 179, 179));
		board.showMoves.setLineWrap(true);
		board.showMoves.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(board.showMoves);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(125, F_HEIGHT - 200));
		toolBar.add(scrollPane, BorderLayout.CENTER);

		chatBox = new JTextArea();
		chatBox.setEditable(false);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		scrollPane = new JScrollPane(chatBox);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(125, F_HEIGHT - 200));
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

	public final void showAbout() {
		String about = ("Created by Patrick Tsai, 2014");

		JOptionPane.showMessageDialog(this, about);
	}

	public void socketListen() {
		System.out.println("now listening");
		try {
			String inputLine = in.readLine();
			if (inputLine != null && inputLine.length() >= 1) {
				/*
				if (in.readLine().isEmpty()) {
					System.out.println("empty line");
					return;
				}
				*/
				System.out.println("Received: " + inputLine);

				if (inputLine.charAt(0) == 'm') {
					board.isMoveValid(Integer.parseInt(inputLine.substring(1, 2)), Integer.parseInt(inputLine.substring(2, 3)), Integer.parseInt(inputLine.substring(3, 4)), Integer.parseInt(inputLine.substring(4, 5)));
					board.movePiece(Integer.parseInt(inputLine.substring(1, 2)), Integer.parseInt(inputLine.substring(2, 3)), Integer.parseInt(inputLine.substring(3, 4)), Integer.parseInt(inputLine.substring(4, 5)));
				}
				// Fix chat later
				if (inputLine.charAt(0) == 'e') {
					chatBox.append(inputLine.substring(1) + "\n");
				}
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame, "Unknown host: " + hostName);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Exception when listening on " + hostName + ":" + portNumber);
		}
		return;
	}

	public static void main(String[] args) {
		Chess c = new Chess();

		c.frame = new JFrame("Chess");
		c.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.frame.getContentPane().add(c);
		c.frame.setJMenuBar(c.initializeMenubar());
		c.frame.getContentPane().add(c.initializeToolbar(), BorderLayout.EAST);
		c.frame.addWindowListener(c.listener);
		c.frame.pack();
		c.frame.setLocationRelativeTo(null);
		c.frame.setVisible(true);

		while (true) {

			c.repaint();
			if ((c.isConnected && c.isWhite && !c.board.whiteToMove) ||
				(c.isConnected && !c.isWhite && c.board.whiteToMove)) {
					c.socketListen();
			}
			//if ((c.isConnected && c.isWhite && !c.board.whiteToMove) ||
				//(c.isConnected && !c.isWhite && c.board.whiteToMove)) return;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Thread sleep was interrupted.");
			}
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
			if (checkmaaaaate) {
				return;
			}

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
					//if (board.getPiece(newSelectedRow, newSelectedCol) != null)
					board.getPiece(newSelectedRow, newSelectedCol).moved();
					if (isConnected) socketSend(selectedRow.intValue(), selectedCol.intValue(), newSelectedRow, newSelectedCol);
					if (board.whiteInCheck || board.blackInCheck) {
						if (board.isCheckmate()) {
							checkmaaaaate = true;
							String moveText = board.showMoves.getText();
							for (int i = moveText.length() - 1; i > moveText.length() - 10; i--) {
								if (moveText.charAt(i) == '+') {
									moveText = moveText.substring(0, i) + '#' + moveText.substring(i + 1, moveText.length() - 1);
									board.showMoves.setText(moveText);
									break;
								}
							}
							if (board.whiteToMove) {
								int reply = JOptionPane.showConfirmDialog(frame, "White has been checkmated!\nNew game?", "Checkmate!", JOptionPane.YES_NO_OPTION);
								if (reply == JOptionPane.YES_OPTION) {
									board.resetBoard();
									board.setupDefault();
									checkmaaaaate = false;
									return;
								}
							}
							else {
								int reply = JOptionPane.showConfirmDialog(frame, "Black has been checkmated!\nNew game?", "Checkmate!", JOptionPane.YES_NO_OPTION);
								if (reply == JOptionPane.YES_OPTION) {
									board.resetBoard();
									board.setupDefault();
									checkmaaaaate = false;
									return;
								}
							}
						}
					}
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

		public void socketSend(int iR, int iC, int fR, int fC) {
			out.println("m" + iR + "" + iC + "" + fR + "" + fC + "\n");//m for move
			System.out.println("Sent: m" + iR + "" + iC + "" + fR + "" + fC + "\n");
		}
		public void socketSend(String message) {
			out.println("e" + message + "\n"); //e for message
		}
	}
	//~~~~~~~~~~~~ End of Mouse Listener ~~~~~~~~~~~~~//

	///////////// now for all the action listener ///////////////
	class AllEncompassingListener extends WindowAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
				case "new_game":
					board.resetBoard();
					board.setupDefault();
					checkmaaaaate = false;
					break;
				case "open":
					checkmaaaaate = false;
					final JFileChooser fcopen = new JFileChooser();
					fcopen.setAcceptAllFileFilterUsed(false);
					fcopen.setMultiSelectionEnabled(false);
					fcopen.setFileFilter(new FileNameExtensionFilter(".dat (Chess save file)", "dat"));
					int openOption = fcopen.showOpenDialog(frame);
					ChessPiece[][] newOpenBoard;
					if (openOption == JFileChooser.APPROVE_OPTION) {
						File openFile = fcopen.getSelectedFile();
						newOpenBoard = fileParser(openFile);
						board.changeBoard(newOpenBoard);
					}
					break;
				case "save":
					final JFileChooser fcsave = new JFileChooser();
					fcsave.setAcceptAllFileFilterUsed(false);
					fcsave.setMultiSelectionEnabled(false);
					fcsave.setFileFilter(new FileNameExtensionFilter(".dat (Chess save file)", "dat"));
					int saveOption = fcsave.showSaveDialog(frame);
					if (saveOption == JFileChooser.APPROVE_OPTION) {
						File saveFile = fcsave.getSelectedFile();
						String filePath = saveFile.getAbsolutePath();
						if (!filePath.endsWith(".dat")) saveFile = new File(filePath + ".dat");
						fileSaver(saveFile);
					}
					break;
				case "quit":
					System.exit(0);
				case "connect":
					JTextField host = new JTextField(20);
					JTextField port = new JTextField(5);
					JPanel connectPanel = new JPanel();
					connectPanel.add(new JLabel("Hostname:"));
					connectPanel.add(host);
					connectPanel.add(Box.createHorizontalStrut(15)); //spacer
					connectPanel.add(new JLabel("Port:"));
					connectPanel.add(port);
					int result = JOptionPane.showConfirmDialog(null, connectPanel, "Enter connection information", JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						hostName = host.getText();
						portNumber = Integer.parseInt(port.getText());
						clientSetup();
					}
					break;
				case "server":
					JTextField serverport = new JTextField(5);
					JPanel serverPanel = new JPanel();
					String pubIP = "";
					String intIP = "";
					try {
						URL whatismyip = new URL("http://checkip.amazonaws.com");
						BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
						pubIP = in.readLine();
						intIP = InetAddress.getLocalHost().getHostAddress();
					} catch (Exception ex) {}
					serverPanel.add(new JLabel("<html>Public IP: " + pubIP + "<br>" + "LAN IP: " + intIP));
					serverPanel.add(Box.createHorizontalStrut(10)); //spacer
					serverPanel.add(new JLabel("Port:"));
					serverPanel.add(serverport);
					int serverresult = JOptionPane.showConfirmDialog(null, serverPanel, "Enter a port number", JOptionPane.OK_CANCEL_OPTION);
					if (serverresult == JOptionPane.OK_OPTION) {
						portNumber = Integer.parseInt(serverport.getText());
						serverSetup();
					}
				case "undo_move":
					if (board.undoArray.size() > 0) {
						checkmaaaaate = false;
						board.undo();
					}
					break;
				case "redo_move":
					board.redo();
					break;
				case "resign":
					chatBox.append(playerName + " has resigned!\n");
					break;
				case "change_name":
					String newname = JOptionPane.showInputDialog("Enter a new name here:");
					playerName = newname;
					break;
				case "chatbox_message":
					String message = chatField.getText();
					chatField.setText("");
					chatBox.append(playerName + ": " + message + "\n");
					if (isConnected) {
						System.out.println("Chat attempting to send");
						socketSend(playerName + ": " + message + "\n");
					}
					break;
				case "about":
					showAbout();
					break;
				default:
					break;
			}
		}

		public void windowClosing(WindowEvent e) {
			if (isConnected) closeServer();
		}

		private ChessPiece[][] fileParser(File file) {
			//piece type, row, col, hasmoved
			try {Scanner input = new Scanner(file);
			ChessPiece[][] openBoard = new ChessPiece[8][8];
			String firstLine = input.nextLine();
			if (firstLine.charAt(0) == 'w') {
				if (!board.getWhitesMove()) board.flipWhiteToMove();
			}
			if (firstLine.charAt(0) == 'b') {
				if (board.getWhitesMove()) board.flipWhiteToMove();
			}
			//set movecount
			board.setMoveCount(Integer.parseInt(firstLine.substring(1)));
			while (input.hasNextLine()) {
				String nextLine = input.nextLine();
				if (nextLine.equals("==")) {
					String lastLines = input.nextLine() + "\n";
					while (input.hasNextLine()) lastLines += input.nextLine() + "\n";
					board.setMoveText(lastLines);
					break;
				}
				if (nextLine.length() <= 2) continue;
				int row = Integer.parseInt(nextLine.substring(3, 4));
				int col = Integer.parseInt(nextLine.substring(5, 6));
				ChessPiece piece = getPiece(nextLine.substring(0,2), row, col);
				if (nextLine.substring(7, 8).equals("m")) piece.moved();
				openBoard[row][col] = piece;
			}
			return openBoard;
			} catch (FileNotFoundException e) {return null;}
		}

		private void fileSaver(File file) {
			try {
				BufferedWriter o = new BufferedWriter(new FileWriter(file));
				ChessPiece[][] loadedBoard = board.getBoard();
				if (board.getWhitesMove()) o.write("w");
				else o.write("b");
				//input move counter
				o.write(board.getMoveCount() + "\n");
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (loadedBoard[i][j] == null)
							continue;
						String retStr = "";
						retStr += getPieceString(loadedBoard[i][j]) + " " + i + " " + j + " ";
						if (loadedBoard[i][j].haveIMoved())
							retStr += "m";
						else retStr += " ";
						o.write(retStr + "\n");
					}
				}
				o.write("==\n");
				String moves = board.getMoves().getText();
				o.write(moves);
				o.close();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "The save has failed. Please try again.", "Save Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		private ChessPiece getPiece(String piece, int row, int col) {
			switch (piece) {
				case "wp":
					return new whitePawn(row, col);
				case "wn":
					return new whiteKnight(row, col);
				case "wb":
					return new whiteBishop(row, col);
				case "wr":
					return new whiteRook(row, col);
				case "wq":
					return new whiteQueen(row, col);
				case "wk":
					return new whiteKing(row, col);
				case "bp":
					return new blackPawn(row, col);
				case "bn":
					return new blackKnight(row, col);
				case "bb":
					return new blackBishop(row, col);
				case "br":
					return new blackRook(row, col);
				case "bq":
					return new blackQueen(row, col);
				case "bk":
					return new blackKing(row, col);
				default:
					return null;
			}
		}

		private String getPieceString(ChessPiece piece) {
			if (piece instanceof whitePawn) return "wp";
			else if (piece instanceof whiteKnight) return "wn";
			else if (piece instanceof whiteBishop) return "wb";
			else if (piece instanceof whiteRook) return "wr";
			else if (piece instanceof whiteQueen) return "wq";
			else if (piece instanceof whiteKing) return "wk";
			else if (piece instanceof blackPawn) return "bp";
			else if (piece instanceof blackKnight) return "bn";
			else if (piece instanceof blackBishop) return "bb";
			else if (piece instanceof blackRook) return "br";
			else if (piece instanceof blackQueen) return "bq";
			else if (piece instanceof blackKing) return "bk";
			return "";
		}

		public void serverSetup() {
			try {
				serverSocket = new ServerSocket(portNumber);
				//JOptionPane.showMessageDialog(frame, "Waiting for client...");
				clientSocket = serverSocket.accept();
				chatBox.append("A player has connected.\n");
				isConnected = true;
				isWhite = true;
				if (playerName.equals("default")){
					if (isWhite) playerName = "White";
					else playerName = "Black";
				}
				if (!isWhite) reverseDrawing = true;
				board.resetBoard();
				board.setupDefault();
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				inputStream = clientSocket.getInputStream();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Exception when listening on " + hostName + ":" + portNumber);
			}
		}

		public void clientSetup() {
			try {
				clientSocket = new Socket(hostName, portNumber);
				chatBox.append("You have connected.\n");
				isConnected = true;
				isWhite = false;
				if (!isWhite) reverseDrawing = true;
				if (playerName.equals("default")){
					if (isWhite) playerName = "White";
					else playerName = "Black";
				}
				board.resetBoard();
				board.setupDefault();
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				inputStream = clientSocket.getInputStream();
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(frame, "Unknown host: " + hostName);
				return;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Exception when listening on " + hostName + ":" + portNumber);
			}
		}

		public void socketSend(int iR, int iC, int fR, int fC) {
			out.println("m" + iR + "" + iC + "" + fR + "" + fC);//m for move
		}
		public void socketSend(String message) {
			out.println("e" + message); //e for message
		}

		public void closeServer() {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
				if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
				if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
			} catch (IOException e) {JOptionPane.showMessageDialog(frame, "Exception when closing IO streams!");}

		}


	}
	////////////////////////////////////////////////////////////////////////////
}
