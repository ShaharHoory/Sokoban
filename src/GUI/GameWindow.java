package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener{
	
	private Game _game;
	private GameMenu _gameMenu;
	private LevelLoader _levelLoader;
	
	public GameWindow() {
		super("Push The Poop!");
		_levelLoader = new LevelLoader();
		try {
			_levelLoader.load("levels.txt");
			_levelLoader.deepCopyLevels();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		_gameMenu = new GameMenu();
		_game = new Game((_levelLoader.get(_gameMenu._levelSelect.getSelectedIndex())));
		this.add(_game, BorderLayout.CENTER);
		this.add(_gameMenu, BorderLayout.WEST);
		this.pack();
		this.setResizable(false); //TODO: make it proportional to every window's size
		this.setSize(800, 600);
		this.setVisible(true);
		this.addKeyListener(_game);
		_gameMenu._levelSelect.addActionListener(this);
		//adds action listener to the buttons
		for (int i=0; i<_gameMenu._buttons.size(); i++) {
			_gameMenu._buttons.get(i).addActionListener(this);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		//להעיף את ההאשמאפ, ולעשות עם הכפתורים כמו עידו ודן!!!!!!!!!!!!!
		/* if (e.getSource() == _gameMenu._buttons.va)
			System.exit(0); */
			
		if (e.getSource() == _gameMenu._levelSelect)
			changeLevel();
	}

	public static void main(String[] args) {
		new GameWindow();
	}

	public LevelLoader get_levelLoader() {
		return _levelLoader;
	}

	public Game get_game() {
		return _game;
	}
	
	/* public void changeLevel(int level) {
		System.out.println(level);
		if (_game!=null)
			this.getContentPane().remove(_game);
		//this.validate();
		this.removeKeyListener(_game);
		_game = new Game((_levelLoader.get_initialLevels().elementAt(level))); //just a trying	
		_game.get_board().repaintBoard();
		this.add(_game, BorderLayout.CENTER);
		this.addKeyListener(_game);
		this.revalidate();
		//this.setVisible(true);
		_game.setVisible(true);
		this.requestFocus();
		
	} */
	
	public void changeLevel() {
		this.getContentPane().remove(_game);
		try {
			_levelLoader.load("levels.txt");
			_levelLoader.deepCopyLevels();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.removeKeyListener(_game);
		_game = new Game(_levelLoader.get(_gameMenu._levelSelect.getSelectedIndex()));
		_game.get_board().repaintBoard();
		this.add(_game, BorderLayout.CENTER);
		this.addKeyListener(_game);
		this.revalidate();
		_game.setVisible(true);
		this.requestFocus();
		
	}
}
