package logicLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import GUI.*;

public class Game extends JPanel implements KeyListener {

	private Board _board;
	private Stats _stats;
	private Stack<Board> _actionsUndo;
	private Stack<Board> _actionsRedo;
	private boolean _isPaused;
	private boolean _hasWon;
	private JLabel _pauseMsg;

	public Game(Cell[][] cells) {
		super(new BorderLayout());
		_board = new Board(cells);		
		this.add(_board, BorderLayout.SOUTH);
		_stats = new Stats();
		this.add(_stats, BorderLayout.NORTH);
		_pauseMsg = new JLabel("Pause");
		_pauseMsg.setFont(new Font("Tahoma", Font.BOLD, 24));
		revalidate();
		_pauseMsg.setVisible(false);
		_actionsUndo = new Stack<Board>();
		_actionsRedo = new Stack<Board>();
		_isPaused = false;
		_hasWon = false;
		this.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (_hasWon)
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

	private boolean isLegalMove(int currX, int currY, int toAddX, int toAddY) { // returns true if the new movement is legal
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
		if (!_actionsUndo.isEmpty()) {
			_actionsRedo.push(_board);
			_board = _actionsUndo.pop();
			add(_board, BorderLayout.SOUTH);
			revalidate();
			_board.repaintBoard();
			_board.setVisible(true);
			_stats.removePoint();
		}
	}

	//updates the game according to redo command
	public void redo() {
		if (!_actionsRedo.isEmpty()) {
			_actionsUndo.push(_board);
			_board = _actionsRedo.pop();
			add(_board, BorderLayout.SOUTH);
			revalidate();
			_board.repaintBoard();
			_board.setVisible(true);
			_stats.addPoint();
		}
	}

	//pauses the game
	public void pause() {
		_pauseMsg.setVisible(true);
		revalidate();
		_isPaused = true;
	}

	//unpauses the game
	public void unpause() {
		_pauseMsg.setVisible(false);
		revalidate();
		_isPaused = false;
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
