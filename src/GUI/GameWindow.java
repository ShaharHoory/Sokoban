package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener {

	private Game _game;
	private WelcomeMenu _welcomeMenu;
	private GameMenu _gameMenu;
	private LevelLoader _levelLoader;

	public GameWindow() {
		super("Push The Poop!");
		_levelLoader = new LevelLoader();
		try {
			_levelLoader.load("levels.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setResizable(false); // TODO: make it proportional to every window's size
		this.setVisible(true);
		this.getContentPane().setLayout(new BorderLayout());
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(300,200));
		_welcomeMenu = new WelcomeMenu(_levelLoader);
		this.add(_welcomeMenu, BorderLayout.CENTER);
		this.pack();
		_welcomeMenu.setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (_game == null)
					_welcomeMenu.exit();
				else
					_game.exit();
			}
		});

		this.addWelcomeMenuActionListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (_welcomeMenu!=null && _game==null && _gameMenu == null) {
			if (e.getSource() == _welcomeMenu._exitButton) {
				_welcomeMenu.exit();
			}
			if (e.getSource() == _welcomeMenu._startButton)
				playGame();
			if (e.getSource() == _welcomeMenu._playButton) {
				_welcomeMenu._playButton.setVisible(false);
				_welcomeMenu._exitButton.setVisible(false);
				_welcomeMenu._startButton.setVisible(true);
				_welcomeMenu._backButton.setVisible(true);
				_welcomeMenu._levelSelect.setVisible(true);
				_welcomeMenu._chooseLevel.setVisible(true);
			}
			if (e.getSource() == _welcomeMenu._backButton) {
				_welcomeMenu._chooseLevel.setVisible(false);
				_welcomeMenu._levelSelect.setVisible(false);
				_welcomeMenu._backButton.setVisible(false);
				_welcomeMenu._startButton.setVisible(false);
				_welcomeMenu._playButton.setVisible(true);
				_welcomeMenu._exitButton.setVisible(true);
			}
		}
		else {
			if (e.getSource() == _gameMenu._exitButton)
				_game.exit();
			if (e.getSource() == _gameMenu._levelSelect)
				changeLevel();
			if (e.getSource() == _gameMenu._resetButton)
				_game.reset(this);	
			if (e.getSource() == _gameMenu._undoButton)
				_game.undo();
			if (e.getSource() == _gameMenu._redoButton)
				_game.redo();
			if (e.getSource() == _gameMenu._pauseButton) {
				if (_game.getHasWon()) {
					JOptionPane.showMessageDialog(this, "You've Won");
					return;
				}
				else
					_game.pause();		
			}
		}
	}

	public void playGame() {
		this.setSize(800, 650);
		this.getContentPane().remove(_welcomeMenu);
		_gameMenu = new GameMenu(_welcomeMenu._levelSelect); 
		_game = new Game((_levelLoader.get(_gameMenu._levelSelect.getSelectedIndex())));
		this.add(_game, BorderLayout.CENTER);
		this.add(_gameMenu, BorderLayout.WEST);
		this.addKeyListener(_game);
		this.addGameActionListeners();
	}
	
	// adds action listener to the buttons in the welcome menu
	public void addWelcomeMenuActionListeners() {
		_welcomeMenu._startButton.addActionListener(this);
		_welcomeMenu._exitButton.addActionListener(this);
		_welcomeMenu._playButton.addActionListener(this);
		_welcomeMenu._backButton.addActionListener(this);
	}
	
	// adds action listener to the buttons
	private void addGameActionListeners() {
		_gameMenu._levelSelect.addActionListener(this);
		_gameMenu._exitButton.addActionListener(this);
		_gameMenu._resetButton.addActionListener(this);
		_gameMenu._undoButton.addActionListener(this);
		_gameMenu._redoButton.addActionListener(this);
		_gameMenu._pauseButton.addActionListener(this);
	}

	public void changeLevel() {
		this.getContentPane().remove(_game);
		try {
			_levelLoader.load("levels.txt");
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
		_gameMenu._pauseButton.setText("Pause");
		this.requestFocus();
	}

	public LevelLoader get_levelLoader() {
		return _levelLoader;
	}

	public Game get_game() {
		return _game;
	}

	public static void main(String[] args) {
		new GameWindow();
	}

}
