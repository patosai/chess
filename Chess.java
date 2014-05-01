import java.util.Arrays;
import java.util.ArrayList;

public class Chess extends JFrame{
	ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();
	
	public Chess() {
		for (int i = 0; i < 8; i++) {
			ArrayList<Object> column = new ArrayList<Object>();
			board.add(column);
		}
		
		setTitle("Chess");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setFocusTraversalKeysEnabled(false);
		setFocusable(true);
		setResizable(false);
		requestFocusInWindow();
	}
}