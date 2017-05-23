package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{

	private LevelLoader _levelLoader;
	private Game _game;
	private GameMenu _gameMenu;
	
	public GameWindow() {
		super("Sokoban");
		_levelLoader = new LevelLoader();
		try {
			_levelLoader.load("levels.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		_gameMenu = new GameMenu(this);
		this.add(_gameMenu, BorderLayout.WEST);
		changeLevel(0);
		this.setResizable(true); //TODO: make it proportional to every window's size
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

	public LevelLoader get_levelLoader() {
		return _levelLoader;
	}

	public Game get_game() {
		return _game;
	}
	
	public void changeLevel(int level) {
		System.out.println(level);
		if (_game!=null)
			this.getContentPane().remove(_game);
		//this.validate();
		_game = new Game((_levelLoader.get_levels().elementAt(level))); //just a trying	
		this.getContentPane().add(_game, BorderLayout.CENTER);
		this.setVisible(true);
	}
}
