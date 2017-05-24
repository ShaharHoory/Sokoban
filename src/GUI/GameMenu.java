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

	protected HashMap<String, JButton> _buttons;
	protected JComboBox<Integer> _levelSelect;
	
	public GameMenu() { //להעיף את ההאשמאפ, ולעשות עם הכפתורים כמו עידו ודן!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		LevelLoader lvlLoader = new LevelLoader();
		initializeLevelSelect(lvlLoader);
		
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
		String[] buttonTexts = {"Undo", "Redo", "Reset", "Exit"};
		_buttons = new HashMap<String, JButton>();
		for (int i = 0; i<buttonTexts.length; i++)
			addButton(buttonTexts[i]);
		
		/* _buttons.get("Exit").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				
			}
		});
		_buttons.get("Reset").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.changeLevel(_levelSelect.getSelectedIndex());		
			}
		}); */
	} 
	 
	 //adds a button and customizes it
	 private void addButton(String text) {
			JButton button = new JButton(text);
			button.setFont(new Font("Tahoma", Font.BOLD, 20));
			button.setForeground(Color.blue);
			button.setBackground(Color.WHITE);
			button.setSize(500,200);
			button.setFocusable(false);
			button.setRequestFocusEnabled(false);
			_buttons.put(button.getText(), button);
			button.setHorizontalAlignment((int) JButton.LEFT_ALIGNMENT);
			button.setBounds(button.getBounds().x, button.getBounds().y, 50, 200);
			this.add(button);
		}
	
	private void initializeLevelSelect(LevelLoader lvlLoader) {
		_levelSelect = new JComboBox<Integer>();
		try {
			lvlLoader.load("levels.txt");
			lvlLoader.deepCopyLevels();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i<lvlLoader.getLevelsCount(); i++)
			_levelSelect.addItem(i);

		JLabel chooseLevel = new JLabel("Choose a level: ");
		_levelSelect.setMaximumSize(new Dimension(100, 50));
		
		this.add(chooseLevel);
		this.add(_levelSelect);
		this.add(Box.createVerticalStrut(100));
	}
	
}
