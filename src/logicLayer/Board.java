package logicLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JPanel {

	private Cell[][] _cells;
	private int _numOfTargets;
	private Cell _playerLoc;
	private JLabel[][] sprites;
	private GridBagConstraints _gbc;

	public Board(Cell[][] cells) {
		
		super(new GridBagLayout());
		_gbc = new GridBagConstraints();
		_gbc.insets = new Insets(0, 0, 0, 0);
		_gbc.weightx = 0;
		_gbc.weighty = 0;
		_gbc.fill = GridBagConstraints.BOTH;
		
		_cells = cells;
		sprites = new JLabel[cells.length][cells[0].length];
		spritesInitiaion();
		_numOfTargets = 0;
		countTargets();
		repaintBoard();
	}
	
	public Cell[][] cloneCells () {
		Cell [][] cells = new Cell[this.getCells().length][this.getCells()[0].length];
		for (int i = 0; i<this.getCells().length; i++) {
			for (int j = 0; j<this.getCells()[i].length; j++) {
				cells [i][j] = this.getCellAt(i, j).clone();
			}
		}
		return cells;
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
	
	//increases/decreases number of empty targets by 1
	public void decreaseNumOfTargets() {
		_numOfTargets--;
	}
	public void increaseNumOfTargets() {
		_numOfTargets++;
	}
	
	//initialize a label in every cell
	private void spritesInitiaion() {
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
	
	/* //repaints the board
	public void repaintBoard() { 
		ImageIcon icon;
		JLabel lbl;
		for (int i = 0; i < _cells.length; i++)
			for (int j=0; j < _cells[0].length; j++) {
				 if (_cells[i][j].isEmptyFloor() && !(_cells[i][j].isStorage())) { //emptyFloor
					 //sprites[i][j].setIcon(new ImageIcon("sprites/emptyFloor2.png"));
					 //this.add(sprites[i][j]);
					 lbl = new JLabel(new ImageIcon("sprites/emptyFloor2.png"));
					 sprites[i][j] = lbl;
					 add(lbl, _gbc);
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
	}*/
	
	//repaints the board
		public void repaintBoard() { 
			//JLabel lbl;
			for (int i = 0; i < _cells.length; i++) {
				_gbc.gridy = i;
				for (int j=0; j < _cells[0].length; j++) {
					_gbc.gridx = j;
					 if (_cells[i][j].isEmptyFloor() && !(_cells[i][j].isStorage())) { //emptyFloor
						 /* lbl = new JLabel(new ImageIcon("sprites/emptyFloor2.png"));
						 sprites[i][j] = lbl;
						 add(sprites[i][j], _gbc); */
						 sprites[i][j].setIcon(new ImageIcon("sprites/emptyFloor2.png"));
						 this.add(sprites[i][j], _gbc);
					}
					else if (_cells[i][j].hasPlayer()) { //player
						_playerLoc = _cells[i][j];
						if (_cells[i][j].isStorage()) {
							/* lbl = new JLabel(new ImageIcon("sprites/cleanerOnToilet.png"));
							sprites[i][j] = lbl;
							add(sprites[i][j], _gbc); */
							sprites[i][j].setIcon(new ImageIcon("sprites/cleanerOnToilet.png"));
							 this.add(sprites[i][j], _gbc);
						}
						else {
							/* lbl = new JLabel(new ImageIcon("sprites/cleaner.png"));
							sprites[i][j] = lbl;
							add(sprites[i][j], _gbc); */
							sprites[i][j].setIcon(new ImageIcon("sprites/cleaner.png"));
							 this.add(sprites[i][j], _gbc);
						}
					}
					else if(_cells[i][j].isStorage() && !(_cells[i][j].hasBox())) { //target
						/* lbl = new JLabel(new ImageIcon("sprites/ToiletTargetReSized.png"));
						sprites[i][j] = lbl;
						add(sprites[i][j], _gbc); */
						sprites[i][j].setIcon(new ImageIcon("sprites/ToiletTargetReSized.png"));
						 this.add(sprites[i][j], _gbc);
					}
					else if (!(_cells[i][j].isStorage()) && _cells[i][j].hasBox()) { //box
						/*lbl = new JLabel(new ImageIcon("sprites/poopTransparent.png"));
						sprites[i][j] = lbl;
						add(sprites[i][j], _gbc);*/
						sprites[i][j].setIcon(new ImageIcon("sprites/poopTransparent.png"));
						 this.add(sprites[i][j], _gbc);
					}
					else if (_cells[i][j].isStorage() && _cells[i][j].hasBox()) { //box in target
						/*lbl = new JLabel(new ImageIcon("sprites/closedToiletsWithPoop2.png"));
						sprites[i][j] = lbl;
						add(sprites[i][j], _gbc);*/
						sprites[i][j].setIcon(new ImageIcon("sprites/closedToiletsWithPoop2.png"));
						 this.add(sprites[i][j], _gbc);
					}
					else { //wall
						/* lbl = new JLabel(new ImageIcon("sprites/wallRound.png"));
						sprites[i][j] = lbl;
						add(sprites[i][j], _gbc); */
						sprites[i][j].setIcon(new ImageIcon("sprites/wallRound.png"));
						 this.add(sprites[i][j], _gbc);
					}
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

	public Cell[][] get_cells() {
		return _cells;
	}

	public int get_numOfTargets() {
		return _numOfTargets;
	}

	public Cell get_playerLoc() {
		return _playerLoc;
	}

	public JLabel[][] getSprites() {
		return sprites;
	}

}
