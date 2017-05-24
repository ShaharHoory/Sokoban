package logicLayer;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import GUI.*;

public class Game extends JPanel implements KeyListener{
	
		private Board _board;
		private Stats _stats;
		
		
		public Game(Cell[][] cells) {
			super(new BorderLayout());
			_board = new Board(cells);
			this.add(_board, BorderLayout.SOUTH);
			_stats = new Stats();
			this.add(_stats, BorderLayout.WEST);
			this.setVisible(true);
		}


		public Board get_board() {
			return _board;
		}


		public Stats get_stats() {
			return _stats;
		}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 39) //right
			movePlayer(0, 1);
		else if (e.getKeyCode() == 37) //left
			movePlayer(0, -1);
		else if (e.getKeyCode() == 38) //up
			movePlayer(-1, 0);
		else if (e.getKeyCode() == 40) //down
			movePlayer(1, 0);
		
		 revalidate();
		_board.repaintBoard();
		_board.setVisible(true);
		System.out.println("empty targets: " + _board.getNumOfTargets());
		System.out.println("steps: " + _stats.get_score());
	}
	
	private void movePlayer(int toAddX, int toAddY) {
		Cell playerLoc = _board.getPlayerLoc();
		int currX = playerLoc._x;
		int currY = playerLoc._y;
		Cell nextLoc = _board.getCellAt(currX + toAddX, currY + toAddY);
		int nextLocX = nextLoc._x;
		int nextLocY = nextLoc._y;
		
		if (!isLegalMove(currX, currY, toAddX, toAddY))
			return;
		
		_stats.addPoint();
		_board.setPlayerLoc(nextLoc); //updates the playerLoc field
		_board.getCellAt(currX, currY).set_hasPlayer(false); //do that the current cell won't contain a player
		_board.getCellAt(nextLocX, nextLocY).set_hasPlayer(true); //move the player to the next cell
		
			if (nextLoc.hasBox()) {
				_board.getCellAt(nextLocX, nextLocY).set_hasBox(false);
				_board.getCellAt(nextLocX + toAddX, nextLocY + toAddY).set_hasBox(true); //push the box
				if (_board.getCellAt(nextLocX, nextLocY).isStorage()) //if a box is pushed out of a storage
					_board.increaseNumOfTargets();
				if (_board.getCellAt(nextLocX + toAddX, nextLocY + toAddY).isStorage()) //if the box was pushed on a storage
					_board.decreaseNumOfTargets();
			}
			
			if (_board.hasWon()) {
				//TODO: Do something when winning the game
				System.out.println("You Won!");
				try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("win.wav"));
				    Clip clip = AudioSystem.getClip();
				    clip.open(audioInputStream);
				    clip.start();
				}
				catch (Exception e) {}
			}
	}
	
	private boolean isLegalMove(int currX, int currY, int toAddX, int toAddY) { //returns true if the new movement is legal
		if (!(_board.isInBoard(currX + toAddX, currY + toAddY)))
			return false;
		if(_board.getCells()[currX+toAddX][currY+toAddY].isWall()) //wall
			return false;
		if (_board.getCells()[currX+toAddX][currY+toAddY].hasBox()) { //box
				if (_board.getCells()[currX + 2*toAddX][currY + 2*toAddY].hasBox() || _board.getCells()[currX + 2*toAddX][currY + 2*toAddY].isWall()) //two boxes or box and wall
					return false;
		}
		return true;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
