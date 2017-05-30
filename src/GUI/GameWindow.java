package GUI;

import logicLayer.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener {
	
	private Game _game;
	private GameMenu _gameMenu;
	private LevelLoader _levelLoader;
	private JLabel _pause;
	
	public GameWindow() {
		super("Push The Poop!");
		_levelLoader = new LevelLoader();
		try {
			_levelLoader.load("levels.txt");
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
		_pause = new JLabel("Paused");
		_pause.setFont(new Font("Tahoma", Font.BOLD, 24));
		_pause.setVisible(false);
		this.setResizable(false); //TODO: make it proportional to every window's size
		this.setSize(800, 600);
		this.setVisible(true);
		this.addKeyListener(_game);
		//adds action listener to the buttons
		this.addActionListeners();
		//this.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		if (e.getSource() == _gameMenu._exitButton)
			// this.dispose();	
			_game.exit(this);
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
			if (_game.isPaused()) {
				_game.unpause();
				_pause.setVisible(false);
				_gameMenu._pauseButton.setText("Pause");
			}
			else {
				_game.pause();
				_pause.setVisible(true);
				_gameMenu._pauseButton.setText("Unpause");
			}
		}
	}

	private void addActionListeners() {
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
