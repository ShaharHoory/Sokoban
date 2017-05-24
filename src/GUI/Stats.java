package GUI;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import logicLayer.*;

public class Stats extends JPanel {

	private JLabel _score;
	private int _points;
	private Timer _timer;
	private Stack<Board> _prevSteps;
	
	public Stats() {
		super();
		_points = 0;
		_prevSteps = new Stack<Board>();
		this._score = initializeScore();
		this.add(_score);
		_score.setHorizontalAlignment(FlowLayout.LEFT);
		this._timer = new Timer();
	}

	public int get_score() {
		return _points;
	}
	
	public void addPoint() {
		_points++;
		_score.setText("Score : " + _points);
	}

	public void removePoint() {
		_points--;
		_score.setText("Score : " + _points);
	}
	
	public Timer get_timer() {
		return _timer;
	}
	
	private JLabel initializeScore() {
		_points = 0;
		JLabel score = new JLabel("Score : " + _points);
		score.setFont(new Font("Times New Roman", Font.BOLD, 18));	
		return score;
	}

	public int get_points() {
		return _points;
	}

	public Stack<Board> get_prevSteps() {
		return _prevSteps;
	}
}
