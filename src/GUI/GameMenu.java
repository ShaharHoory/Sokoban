package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class GameMenu extends JPanel implements ActionListener{

	private HashMap<String, JButton> _buttons;
	
	public GameMenu() {
		_buttons = new HashMap<>();
		addButton(new JButton("Test Me"));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void addButton(JButton button) {
		_buttons.put(button.getText(), button);
		this.add(button);
	}

}
