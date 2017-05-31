package GUI;
import logicLayer.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class WelcomeMenu extends JPanel {

	protected JButton _playButton;
	protected JButton _backButton;
	protected JButton _exitButton;
	protected JComboBox<Integer> _levelSelect;
	protected JButton _startButton;
	protected JLabel _chooseLevel;
	
	public WelcomeMenu(LevelLoader lvlLoader) {
		super();
		this.setLayout(null);
		initializeLevelSelect(lvlLoader);	
		initializeButtons();
		this.focusDisable();
		this.setVisible(true);
		//background image:
		ImageIcon icon = new ImageIcon("sprites/welcomeMenu2.png");
		JLabel background = new JLabel(icon);
		background.setBounds(0, 0, 400, 290);
		this.add(background);
	}
	
	
	private void focusDisable() {
		_levelSelect.setFocusable(false);
		_levelSelect.setRequestFocusEnabled(false);
	}
	
	//initializes all the buttons
	private void initializeButtons() {
		_playButton = addButton("Play", 157, 100);
		_exitButton = addButton("Exit", 157, 140);
		_startButton = addButton("Start", 157, 140);
		_backButton = addButton("Back", 157, 180);
		_startButton.setVisible(false);
		_backButton.setVisible(false);
	}
	
	//creates a button
	 private JButton addButton(String text, int x, int y) {
			JButton button = new JButton(text);
			button.setFont(new Font("Tahoma", Font.BOLD, 14));
			button.setForeground(new Color(88, 44, 0));
			button.setBackground(Color.WHITE);
			button.setFocusable(false);
			button.setRequestFocusEnabled(false);
			button.setMinimumSize(new Dimension(100, 50));
			button.setVisible(true);
			button.setAlignmentX(CENTER_ALIGNMENT);
			button.setAlignmentY(CENTER_ALIGNMENT);
			button.setBounds(x, y, 70, 35);
			this.add(button);
			this.revalidate();
			return button;
		}
	
	 //initializes the combo-box of level select
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
		
		_chooseLevel.setBounds(157, 49, 150, 40);
		
		this.add(_chooseLevel);
		_chooseLevel.setVisible(false);
		_levelSelect.setAlignmentX(CENTER_ALIGNMENT);
		_levelSelect.setAlignmentY(CENTER_ALIGNMENT);
		
		_levelSelect.setBounds(157, 77, 70, 30);
		
		this.add(_levelSelect);
		_levelSelect.setVisible(false);
		focusDisable();
	}
	
	//exits the program according to the user's choise
	public void exit() {
		String ObjButtons[] = { "Yes", "No" };
		int PromptResult = JOptionPane.showOptionDialog(this, "Are you sure you want to exit?",
				"Online Examination System", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
				ObjButtons, ObjButtons[1]);
		if (PromptResult == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
