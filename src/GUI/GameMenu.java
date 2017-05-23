package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

import logicLayer.*;

public class GameMenu extends JPanel implements ActionListener{

	private HashMap<String, JButton> _buttons;
	private JComboBox<Integer> _levelSelect;
	
	public GameMenu(GameWindow window) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializeMenu(window);
		initializeLevelSelect(window);
		
	}
	
	public JComboBox<Integer> getLevelSelect() {
		return _levelSelect;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private void addButton(String text) {
		JButton button = new JButton(text);
		button.setFont(new Font("Tahoma", Font.BOLD, 20));
		button.setForeground(Color.blue);
		button.setBackground(Color.WHITE);
		button.setSize(500,200);
		_buttons.put(button.getText(), button);
		button.setHorizontalAlignment((int) JButton.LEFT_ALIGNMENT);
		button.setBounds(button.getBounds().x, button.getBounds().y, 50, 200);
		this.add(button);
	}
	
	private void initializeMenu(GameWindow window) {
		String[] buttonTexts = {"Test Me", "Exit", "Undo", "Redo"};
		_buttons = new HashMap<String, JButton>();
		for (int i = 0; i<buttonTexts.length; i++)
			addButton(buttonTexts[i]);
		_buttons.get("Exit").addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				
			}
		});
	}

	private void initializeLevelSelect(GameWindow window) {
		_levelSelect = new JComboBox<Integer>();
		_levelSelect.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
		_levelSelect.setMaximumSize(getPreferredSize());
		int levels = window.get_levelLoader().getLevelsCount();
		for (int i = 0; i<levels; i++) {
			_levelSelect.addItem(new Integer(i));
		}
		this.add(_levelSelect);
		_levelSelect.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int level = _levelSelect.getSelectedIndex();
				if (level>=0 && level<window.get_levelLoader().getLevelsCount()){
					window.changeLevel(level);
				}
				
			}
		});
	}
}
