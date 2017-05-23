package logicLayer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Stack;

import javax.swing.JPanel;

public class Game extends JPanel {
	
	private Board _board;
	private Stack<Board> _prevSteps;
	private int _score;
	
	
	public Game(Cell[][] cells) {
		super(new BorderLayout());
		_board = new Board(cells);
		this.add(_board);
		_prevSteps = new Stack<Board>();
		_score = 0;
	}
	

}
