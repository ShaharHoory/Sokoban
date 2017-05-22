package logicLayer;

import java.util.Stack;

public class Game {
	
	private Board _board;
	private Stack<Board> _prevSteps;
	private int _score;
	
	
	public Game(Board _board) {
		_board = _board;
		_prevSteps = new Stack<Board>();
		_score = 0;
	}
	

}
