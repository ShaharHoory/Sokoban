package logicLayer;

public class Board {
	
	private Cell[][] _cells;
	
	
	public Board(Cell[][] cells) {
		_cells = cells;
	}

	//returns true if (x,y) is in the game board
	private boolean isInBoard(int x, int y) {
		return (x>=0 && x<_cells.length) && (y>=0 && y<_cells[x].length);
	}

	//returns the cell at (x,y) if exists, null otherwise
	private Cell getCellAt(int x, int y) {
		if (!isInBoard(x,y))
			return null;
		return _cells[x][y];
	}

}
