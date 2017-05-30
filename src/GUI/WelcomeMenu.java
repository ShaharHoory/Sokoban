package GUI;
import logicLayer.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;
import javax.tools.FileObject;

public class WelcomeMenu extends JPanel implements KeyListener {

	protected JButton _playButton;
	protected JButton _backButton;
	protected JButton _exitButton;
	protected JComboBox<Integer> _levelSelect;
	protected JButton _startButton;
	protected JLabel _chooseLevel;
	
	public WelcomeMenu(LevelLoader lvlLoader) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel headline = new JLabel("Push The Poop!");
		headline.setAlignmentX(CENTER_ALIGNMENT);
		headline.setHorizontalAlignment(SwingConstants.CENTER);
		headline.setFont(new Font("Tahoma", Font.BOLD, 26));
		this.add(headline);
		initializeLevelSelect(lvlLoader);	
		initializeButtons();
		this.focusDisable();
		this.setVisible(true);
		//this.revalidate();
	}
	
	private void focusDisable() {
		_levelSelect.setFocusable(false);
		_levelSelect.setRequestFocusEnabled(false);
	}
	
	private void initializeButtons() {
		_playButton = addButton("Play");
		_exitButton = addButton("Exit");
		_startButton = addButton("Start");
		_backButton = addButton("Back");
		_startButton.setVisible(false);
		_backButton.setVisible(false);
	}
	
	 private JButton addButton(String text) {
			JButton button = new JButton(text);
			button.setFont(new Font("Tahoma", Font.BOLD, 14));
			button.setForeground(Color.blue);
			button.setBackground(Color.WHITE);
			button.setFocusable(false);
			button.setRequestFocusEnabled(false);
			button.setBounds(button.getBounds().x, button.getBounds().y, 50, 200);
			button.setVisible(true);
			button.setAlignmentX(CENTER_ALIGNMENT);
			button.setAlignmentY(CENTER_ALIGNMENT);
			this.add(button, BorderLayout.CENTER);
			this.revalidate();
			return button;
		}
	
	private void initializeLevelSelect(LevelLoader lvlLoader) {
		_levelSelect = new JComboBox<Integer>();
		try {
			lvlLoader.load("levels.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i<lvlLoader.getLevelsCount(); i++)
			_levelSelect.addItem(i);

		_levelSelect.setMaximumSize(new Dimension(75, 25));
		_chooseLevel = new JLabel("Choose a level: ");
		_chooseLevel.setAlignmentX(CENTER_ALIGNMENT);
		_chooseLevel.setAlignmentY(CENTER_ALIGNMENT);
		this.add(_chooseLevel);
		_chooseLevel.setVisible(false);
		_levelSelect.setAlignmentX(CENTER_ALIGNMENT);
		_levelSelect.setAlignmentY(CENTER_ALIGNMENT);
		this.add(_levelSelect);
		_levelSelect.setVisible(false);
		focusDisable();
	}
	
	public void exit() {
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?",
				"Online Examination System", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			exit();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
