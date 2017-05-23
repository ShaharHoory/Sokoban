package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

	private JPanel _mainPanel;
	//private Game _game;
	
	public GameWindow() {
		super("Sokoban");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		_mainPanel = new JPanel();
		GameMenu gameMenu = new GameMenu(this);
		Game game = new Game((gameMenu.getLevelLoader().get_levels().elementAt(0))); //just a trying
		this.add(gameMenu, BorderLayout.WEST);
		this.getContentPane().add(game, BorderLayout.CENTER);
		this.setResizable(false); //TODO: make it proportional to every window's size
		this.setSize(800, 600);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		this.dispose();
	}

	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
	}
}
