package GUI;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

	private JPanel _mainPanel;
	public GameWindow() {
		super("Sokoban");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		_mainPanel = new JPanel();
		GameMenu _gameMenu = new GameMenu();
		this.add(_gameMenu, BorderLayout.WEST);
		this.setSize(800, 600);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
	}
}
