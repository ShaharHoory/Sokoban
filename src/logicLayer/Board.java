package logicLayer;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JPanel {

	private Cell[][] _cells;
	private int _numOfTargets;
	private Cell _playerLoc;
	private JLabel[][] sprites;

	public Board(Cell[][] cells) {
		_cells = cells;
		GridLayout gl = new GridLayout(cells.length, cells[0].length); // TODO: check if cells[0].length is ok
		sprites = new JLabel[cells.length][cells[0].length];
		spritesInitiaion();
		gl.setHgap(-265);
		gl.setVgap(-5);
		this.setLayout(gl);
		_numOfTargets = 0;
		countTargets();
		repaintBoard();
	}
	
	public static Cell[][] cloneBoard(Board other) {
		Cell[][] clone = new Cell[other.getCells().length][other.getCells()[0].length];
		for (int i = 0; i<other.getCells().length; i++) {
			for (int j = 0; j<other.getCells()[i].length; j++) {
				clone[i][j] = other.getCellAt(i, j).clone();
			}
		}
		return clone;
	}
	
	public Cell getPlayerLoc() {
		return _playerLoc;
	}
	
	public int getNumOfTargets() {
		return _numOfTargets;
	}
	
	public Cell[][] getCells() {
		return _cells;
	}
	
	public void setPlayerLoc(Cell playerLoc) {
		_playerLoc = playerLoc;
	}
	
	//increases/decreases number of empty tatgets by 1
	public void decreaseNumOfTargets() {
		_numOfTargets--;
	}
	public void increaseNumOfTargets() {
		_numOfTargets++;
	}
	
	private void spritesInitiaion() { //initialize a label in every cell
		for (int i = 0; i < sprites.length; i++) {
			for (int j = 0; j < sprites[i].length; j++) {
				sprites[i][j] = new JLabel();
				sprites[i][j].setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
	}

	//returns the number of targets in the board and updates _numOfTargets
	private void countTargets() {
		for (int i = 0; i < _cells.length; i++)
			for (int j=0; j < _cells[i].length; j++)
				if(_cells[i][j].isStorage() && !(_cells[i][j].hasBox())) { //target
					_numOfTargets++;
			}
	}
	
	public boolean hasWon() {
		return _numOfTargets == 0;
	}

	
	//repaints the board
	public void repaintBoard() { 
		ImageIcon icon;
		for (int i = 0; i < _cells.length; i++)
			for (int j=0; j < _cells[0].length; j++) {
				 if (_cells[i][j].isEmptyFloor() && !(_cells[i][j].isStorage())) { //emptyFloor
					sprites[i][j].setIcon(new ImageIcon("sprites/emptyFloor2.png"));
					this.add(sprites[i][j]);
				}
				else if (_cells[i][j].hasPlayer()) { //player
					_playerLoc = _cells[i][j];
					if (_cells[i][j].isStorage()) {
						sprites[i][j].setIcon(new ImageIcon("sprites/cleanerOnToilet.png"));
						this.add(sprites[i][j]);
					}
					else {
					sprites[i][j].setIcon(new ImageIcon("sprites/cleaner.png"));
					this.add(sprites[i][j]);
					}
				}
				else if(_cells[i][j].isStorage() && !(_cells[i][j].hasBox())) { //target
					sprites[i][j].setIcon(new ImageIcon("sprites/ToiletTargetReSized.png"));
					this.add(sprites[i][j]);
				}
				else if (!(_cells[i][j].isStorage()) && _cells[i][j].hasBox()) { //box
					sprites[i][j].setIcon(new ImageIcon("sprites/poopTransparent.png"));
					this.add(sprites[i][j]);
				}
				else if (_cells[i][j].isStorage() && _cells[i][j].hasBox()) { //box in target
					sprites[i][j].setIcon(new ImageIcon("sprites/closedToiletsWithPoop2.png"));
					this.add(sprites[i][j]);
				}
				else { //wall
					sprites[i][j].setIcon(new ImageIcon("sprites/wallRound.png"));
					this.add(sprites[i][j]);
				}
			}
	}

	// returns true if (x,y) is in the game board
	public boolean isInBoard(int x, int y) {
		return (x >= 0 && x < _cells.length) && (y >= 0 && y < _cells[x].length);
	}

	// returns the cell at (x,y) if exists, null otherwise
	public Cell getCellAt(int x, int y) {
		if (!isInBoard(x, y))
			return null;
		return _cells[x][y];
	}

}
