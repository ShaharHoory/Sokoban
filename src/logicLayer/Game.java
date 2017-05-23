package logicLayer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Stack;
import GUI.*;

import javax.swing.JPanel;

public class Game extends JPanel {
	
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
	

}
