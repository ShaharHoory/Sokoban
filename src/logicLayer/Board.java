package logicLayer;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class Board extends JPanel {

	private Cell[][] _cells;

	public Board(Cell[][] cells) {
		super(new GridLayout(cells.length, cells[0].length)); // check if
																// cells[0].length
																// is ok
		_cells = cells;
		// TODO: generate a for loop that draws every cell from cells param
		// (puts an image according to the cell's type
	}

	// returns true if (x,y) is in the game board
	private boolean isInBoard(int x, int y) {
		return (x >= 0 && x < _cells.length) && (y >= 0 && y < _cells[x].length);
	}

	// returns the cell at (x,y) if exists, null otherwise
	private Cell getCellAt(int x, int y) {
		if (!isInBoard(x, y))
			return null;
		return _cells[x][y];
	}

}
