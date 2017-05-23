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
	private Timer _timer;
	private Stack<Board> _prevSteps;
	
	public Stats() {
		super();
		_prevSteps = new Stack<Board>();
		this._score = initializeScore();
		this.add(_score);
		_score.setHorizontalAlignment(FlowLayout.LEFT);
		this._timer = new Timer();
	}

	public String get_score() {
		return _score.getText();
	}

	public Timer get_timer() {
		return _timer;
	}
	
	private JLabel initializeScore() {
		
		JLabel score = new JLabel("Score : 0");
		Font labelFont = score.getFont();
		String labelText = score.getText();

		int stringWidth = score.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = score.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = score.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		score.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
		return score;
	}
}
