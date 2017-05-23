package GUI;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;

import logicLayer.LevelLoader;

public class GameMenu extends JPanel implements ActionListener{

	private HashMap<String, JButton> _buttons;
	private JComboBox<Integer> _levelSelect;
	private LevelLoader _levelLoader;

	
	public GameMenu(GameWindow window) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_levelLoader = new LevelLoader();
		try {
			_levelLoader.load("levels.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeMenu(window);
		initializeLevelSelect();
		
	}
	
	public LevelLoader getLevelLoader() {
		return _levelLoader;
	}
	
	public JComboBox<Integer> getLevelSelect() {
		return _levelSelect;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private void addButton(JButton button) {
		_buttons.put(button.getText(), button);
		button.setHorizontalAlignment((int) JButton.LEFT_ALIGNMENT);
		button.setBounds(button.getBounds().x, button.getBounds().y, 50, 200);
		this.add(button);
	}
	
	private void initializeMenu(GameWindow window) {
		_buttons = new HashMap<String, JButton>();
		addButton(new JButton("Test Me"));
		addButton(new JButton("Exit"));
		_buttons.get("Exit").addActionListener(window);
	}

	private void initializeLevelSelect() {
		_levelSelect = new JComboBox<Integer>();
		_levelSelect.setAlignmentX(JComboBox.LEFT_ALIGNMENT);
		_levelSelect.setMaximumSize(getPreferredSize());
		int levels = _levelLoader.getLevelsCount();
		for (int i = 0; i<levels; i++)
			_levelSelect.addItem(new Integer(i));
		this.add(_levelSelect);
	}
	
	private void allignAllLeft() {
		
	}
}
