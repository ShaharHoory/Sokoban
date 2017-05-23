package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

	private Game _game;
	
	public GameWindow() {
		super("Sokoban");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		GameMenu gameMenu = new GameMenu(this);
		_game = new Game((gameMenu.getLevelLoader().get_levels().elementAt(0))); //just a trying
		this.add(gameMenu, BorderLayout.WEST);
		this.add(_game, BorderLayout.CENTER);
		this.setResizable(false); //TODO: make it proportional to every window's size
		this.setSize(800, 600);
		this.setVisible(true);
		this.addKeyListener(_game);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		this.dispose();
	}

	public static void main(String[] args) {
		new GameWindow();
	}
}
