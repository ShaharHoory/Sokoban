package logicLayer;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import GUI.*;

public class Game extends JPanel implements KeyListener {

	private Board _board;
	private Stats _stats;
	private Stack<Board> _actionsUndo;
	private Stack<Board> _actionsRedo;
	private boolean _isPaused;
	private boolean _hasWon;
	
	public Game(Cell[][] cells) {
		super(new BorderLayout());
		_board = new Board(cells);		
		this.add(_board, BorderLayout.CENTER);
		_stats = new Stats();
		this.add(_stats, BorderLayout.NORTH);
		revalidate();
		_actionsUndo = new Stack<Board>();
		_actionsRedo = new Stack<Board>();
		_isPaused = false;
		_hasWon = false;
		this.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (_hasWon || _isPaused)
			return;
		else {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN: // down arrow
				movePlayer(1, 0);
				break;
			case KeyEvent.VK_RIGHT: // right arrow
				movePlayer(0, 1);
				break;
			case KeyEvent.VK_UP: // up arrow
				movePlayer(-1, 0);
				break;
			case KeyEvent.VK_LEFT: // left arrow
				movePlayer(0, -1);
				break;
			case KeyEvent.VK_Z:
				undo();
				break;
			case KeyEvent.VK_X:
				redo();
				break;
			case KeyEvent.VK_P:
				pause();
				break;
			default:
				break;
			}
		}
		revalidate();
		_board.repaintBoard();
		_board.setVisible(true);
		if (_board.hasWon()) {
			_hasWon = true;
			try {
				AudioInputStream audioInputStream = AudioSystem
						.getAudioInputStream(this.getClass().getResource("win.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (Exception ex) {
			}
			_stats.get_stopwatch().set_isPaused(true);
			JOptionPane.showMessageDialog(this, "You Won!" + '\n'+ "press RESET to start over");
			_isPaused = true;
		}
	}

	public boolean getHasWon() {
		return _hasWon;
	}

	public void setHasWon(boolean hasWon) {
		_hasWon = hasWon;
	}

	//moves the player
	private void movePlayer(int toAddX, int toAddY) {
		Cell playerLoc = _board.getPlayerLoc();
		int currX = playerLoc._x;
		int currY = playerLoc._y;
		Cell nextLoc = _board.getCellAt(currX + toAddX, currY + toAddY);
		int nextLocX = nextLoc._x;
		int nextLocY = nextLoc._y;

		if (!isLegalMove(currX, currY, toAddX, toAddY))
			return;
		_actionsUndo.push(new Board(_board.cloneCells()));
		_stats.addPoint();
		_board.setPlayerLoc(nextLoc); // updates the playerLoc field
		_board.getCellAt(currX, currY).set_hasPlayer(false); // do that the current cell won't contain a player
		_board.getCellAt(nextLocX, nextLocY).set_hasPlayer(true); // move the player to the next cell
		if (nextLoc.hasBox()) {
			_board.getCellAt(nextLocX, nextLocY).set_hasBox(false);
			_board.getCellAt(nextLocX + toAddX, nextLocY + toAddY).set_hasBox(true); // push the box
			if (_board.getCellAt(nextLocX, nextLocY).isStorage()) // if a box is pushed out of a storage
				_board.increaseNumOfTargets();
			if (_board.getCellAt(nextLocX + toAddX, nextLocY + toAddY).isStorage()) { // if the box was pushed on a storage
				_board.decreaseNumOfTargets();
				//adds sound
				try {
					AudioInputStream audioInputStream = AudioSystem
							.getAudioInputStream(this.getClass().getResource("bath-flush1.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
				} catch (Exception e) {
				} 
			}
		}

		if (!(_actionsUndo.isEmpty())) {
			_actionsRedo = new Stack<Board>();
		}
	}

	// returns true if the new movement is legal
	private boolean isLegalMove(int currX, int currY, int toAddX, int toAddY) {
		if (!(_board.isInBoard(currX + toAddX, currY + toAddY)))
			return false;
		if (_board.getCells()[currX + toAddX][currY + toAddY].isWall()) // wall
			return false;
		if (_board.getCells()[currX + toAddX][currY + toAddY].hasBox()) { // box
			if (_board.getCells()[currX + 2 * toAddX][currY + 2 * toAddY].hasBox()
					|| _board.getCells()[currX + 2 * toAddX][currY + 2 * toAddY].isWall()) // two boxes or box and wall
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

	//updates the game according to the last command
	public void undo() {
		if (_hasWon) {
			JOptionPane.showMessageDialog(this, "You've Won, why to undo it? ;)");
			return;
		}
		if (_isPaused) {
			JOptionPane.showMessageDialog(this, "The game is paused");
			return;
		}
		if (!_actionsUndo.isEmpty()) {
			_actionsRedo.push(_board);
			_board = _actionsUndo.pop();
			add(_board, BorderLayout.CENTER);
			revalidate();
			_board.repaintBoard();
			_board.setVisible(true);
			_stats.removePoint();
		}
	}

	//updates the game according to redo command
	public void redo() {
		if (_hasWon) {
			JOptionPane.showMessageDialog(this, "You've Won");
			return;
		}
		if (_isPaused) {
			JOptionPane.showMessageDialog(this, "The game is paused");
			return;
		}
		if (!_actionsRedo.isEmpty()) {
			_actionsUndo.push(_board);
			_board = _actionsRedo.pop();
			add(_board, BorderLayout.CENTER);
			revalidate();
			_board.repaintBoard();
			_board.setVisible(true);
			_stats.addPoint();
		}
	}
	
	//resets the current level
	public void reset(GameWindow wind) {
		if (_isPaused && !_hasWon) {
			JOptionPane.showMessageDialog(this, "The game is paused");
			return;
		}
		wind.changeLevel();
	}

	//pauses the game
	public void pause() {
		revalidate();
		_isPaused = true;
		_stats.get_stopwatch().set_isPaused(true);
		String ObjButtons[] = { "Unpause" };
		int PromptResult = JOptionPane.showOptionDialog(this, "Press unpause to continue",
				"The game is now paused", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				ObjButtons, ObjButtons[0]);
		if (PromptResult == JOptionPane.DEFAULT_OPTION || PromptResult == JOptionPane.OK_OPTION) 
			unpause();			
	}

	//unpauses the game
	private void unpause() {
		revalidate();
		_isPaused = false;
		_stats.get_stopwatch().set_isPaused(false);
	}
	
	//exits the program in order to the user's choice
	public void exit() {
		_isPaused = true;
		_stats.get_stopwatch().set_isPaused(true);
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?",
				"Exit", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		else {
			_isPaused = false;
			_stats.get_stopwatch().set_isPaused(false);
		}
	}
	
	public Stack<Board> get_actionsUndo() {
		return _actionsUndo;
	}

	public Stack<Board> get_actionsRedo() {
		return _actionsRedo;
	}

	public Board get_board() {
		return _board;
	}

	public Stats get_stats() {
		return _stats;
	}

	public boolean isPaused() {
		return _isPaused;
	}
}
