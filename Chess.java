import java.util.Arrays;
import java.util.ArrayList;

public class Chess {
	ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();
	
	public Chess() {
		for (int i = 0; i < 8; i++) {
			ArrayList<Object> column = new ArrayList<Object>();
			board.add(column);
		}
	}
}