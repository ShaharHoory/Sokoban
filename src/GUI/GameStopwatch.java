package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStopwatch implements ActionListener {

	private int _mins;
	private int _secs;
	private boolean _isPaused;

	public GameStopwatch() {
		_mins = 0;
		_secs = 0;
		_isPaused = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!_isPaused) {
			if (_secs + 1 == 60) {
				_secs = 0;
				_mins++;
			} else
				_secs++;
		}
	}

	public boolean isPaused() {
		return _isPaused;
	}

	public void set_isPaused(boolean _isPaused) {
		this._isPaused = _isPaused;
	}

	public int get_mins() {
		return _mins;
	}

	public int get_secs() {
		return _secs;
	}
}