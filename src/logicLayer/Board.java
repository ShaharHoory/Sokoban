package logicLayer;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private Cell[][] _cells;
	private int _numOfTargets;

	public Board(Cell[][] cells) {
		super(new GridLayout(cells.length, cells[0].length)); // check if cells[0].length is ok
		_cells = cells;
		countTargets();
		repaintBoard();
	}

	//returns the number of targets in the board and updates _numOfTargets
	private void countTargets() {
		for (int i = 0; i < _cells.length; i++)
			for (int j=0; j < _cells[i].length; j++)
				if(_cells[i][j].isStorage() && !(_cells[i][j].hasBox())) { //target
					ImageIcon icon = new ImageIcon("/sprites/storage.png");
					JLabel label = new JLabel(icon);
					this.add(label);
					_numOfTargets++;
			}
	}

	
	//TODO: maybe write a func that repaints only the changed cells in _cells[][]
	
	//repaints the board
	private void repaintBoard() {
		ImageIcon icon;
		for (int i = 0; i < _cells.length; i++)
			for (int j=0; j < _cells[i].length; j++) {
				if (_cells[i][j].isEmptyFloor()) { //emptyFloor
					icon = new ImageIcon("/sprites/emptyFloor.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
				else if(_cells[i][j].isStorage() && !(_cells[i][j].hasBox())) { //target
					icon = new ImageIcon("/sprites/storage.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
				else if (_cells[i][j].hasPlayer()) { //player
					icon = new ImageIcon("/sprites/character.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
				else if (!(_cells[i][j].isStorage()) && _cells[i][j].hasBox()) { //box
					icon = new ImageIcon("/sprites/box.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
				else if (_cells[i][j].isStorage() && _cells[i][j].hasBox()) { //box in target
					icon = new ImageIcon("/sprites/boxInTarget.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
				else { //wall
					icon = new ImageIcon("/sprites/wall.png");
					JLabel label = new JLabel(icon);
					this.add(label);
				}
			}
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
