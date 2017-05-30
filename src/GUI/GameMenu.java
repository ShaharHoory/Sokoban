package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

import logicLayer.LevelLoader;

public class GameMenu extends JPanel {

	protected JComboBox<Integer> _levelSelect;
	protected JButton _exitButton;
	protected JButton _resetButton;
	protected JButton _undoButton;
	protected JButton _redoButton;
	protected JButton _pauseButton;
	
	public GameMenu(JComboBox<Integer> levelSelect) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		LevelLoader lvlLoader = new LevelLoader();
		initializeLevelSelect(lvlLoader);	
		_levelSelect.setSelectedIndex(levelSelect.getSelectedIndex());
		initializeMenu();		
		this.focusDisable();
	}
	
	public JComboBox<Integer> getLevelSelect() {
		return _levelSelect;
	}
	
	/* @Override
	public void actionPerformed(ActionEvent e) {
	} */
	
	private void focusDisable() {
		_levelSelect.setFocusable(false);
		_levelSelect.setRequestFocusEnabled(false);
		
	}
	
	 //initializes the buttons in the menu
	 private void initializeMenu() {
		 this.setMaximumSize(new Dimension(800,GameMenu.HEIGHT));
		 this.setSize(getMaximumSize());
		 _pauseButton = addButton("Pause");
		 _resetButton = addButton("Reset");
		 _undoButton = addButton("Undo");
		 _redoButton = addButton("Redo");
		 _exitButton = addButton("Exit");
	} 
	 
	 //adds a button and customizes it
	 private JButton addButton(String text) {
			JButton button = new JButton(text);
			button.setFont(new Font("Tahoma", Font.BOLD, 14));
			button.setForeground(Color.blue);
			button.setBackground(Color.WHITE);
			button.setMaximumSize(new Dimension(400, 50));
			button.setMinimumSize(getMaximumSize());
			button.setFocusable(false);
			button.setRequestFocusEnabled(false);
			button.setHorizontalAlignment((int) JButton.LEFT_ALIGNMENT);
			//button.setBounds(button.getBounds().x, button.getBounds().y, 50, 200);
			button.setVisible(true);
			this.add(button);
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

		JLabel chooseLevel = new JLabel("Choose a level: ");
		_levelSelect.setMaximumSize(new Dimension(100, 50));
		_levelSelect.setBounds(50, 50, getWidth(), getHeight());
		this.add(chooseLevel);
		this.add(_levelSelect);
		this.add(Box.createVerticalStrut(100));
	}
	
	public JButton getButton(String text) {
		for (int i = 0; i<getComponentCount(); i++) {
			if (getComponent(i) instanceof JButton) {
				JButton b = (JButton) getComponent(i);
				if (b.getText().toLowerCase().equals(text.toLowerCase())) {
					return b;
				}
			}
		}
		return null;
	}
	
}
