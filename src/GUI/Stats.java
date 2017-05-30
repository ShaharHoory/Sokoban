package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import logicLayer.*;

public class Stats extends JPanel implements ActionListener  {

	private JLabel _score;
	private JLabel _time;
	private int _points;
	private GameStopwatch _stopwatch;
	private Stack<Board> _prevSteps;
	
	public Stats() {
		super();
		_points = 0;
		_prevSteps = new Stack<Board>();
		this.setLayout(new FlowLayout());
		_score = initializeScore();
		//_score.setHorizontalAlignment(FlowLayout.LEFT);
		this.add(_score, FlowLayout.LEFT);
		_stopwatch = new GameStopwatch();
		_time = initializeStopwatch();
		this.add(_time);
		//_time.setHorizontalAlignment(FlowLayout.RIGHT);
		Timer t = new Timer(1000, this);
		t.addActionListener(_stopwatch);
		t.start();
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
	
	public GameStopwatch get_stopwatch() {
		return _stopwatch;
	}
	
	private JLabel initializeScore() {
		_points = 0;
		JLabel score = new JLabel("Score : " + _points);
		score.setFont(new Font("Times New Roman", Font.BOLD, 18));	
		return score;
	}
	
	private JLabel initializeStopwatch() {
		JLabel time = new JLabel("Time : " + _stopwatch.get_mins() + " Minutes , " + _stopwatch.get_secs() + " Seconds");
		time.setFont(new Font("Times New Roman", Font.BOLD, 16));
		return time;
	}

	public int get_points() {
		return _points;
	}

	public Stack<Board> get_prevSteps() {
		return _prevSteps;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		_time.setText("Time : " + _stopwatch.get_mins() + " Minutes , " + _stopwatch.get_secs() + " Seconds");
	}
}
