package logicLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class LevelLoader {
	
    private static final char CHAR_WALL              = '#';
    private static final char CHAR_FLOOR             = ' ';
    private static final char CHAR_PLAYER            = '@';
    private static final char CHAR_STORAGE           = '.';
    private static final char CHAR_BOX               = '$';
    private static final char CHAR_BOX_IN_STORAGE    = '*';
    private static final char CHAR_PLAYER_AT_STORAGE = '+';

    /** contains the initial board state of all the levels  */
    private Vector<Cell[][]> _levels;
    
    public LevelLoader() {
    	_levels = new Vector<>();
	}

    /**
     * Loads all the levels to the internal levels buffer
     * @param levelsFile the name of the file contains the levels
     * @return true if success
     * @throws IOException if there is any error with the file
     */
    public boolean load(String levelsFile) throws IOException {
    	_levels.clear();

    	BufferedReader br = new BufferedReader(new FileReader(levelsFile));
    	String line = null;
    	Cell level[][] = null;
    	int w=0;
    	int h=0;
    	int row = 0;
    	while ((line = br.readLine()) != null) {

    		// end of level
    		if (line.trim().isEmpty()) {
    			if (null != level){
    				_levels.add(level);
    				level = null;
    			}
    			continue;
    		}
    		
    		// board size
    		if (line.trim().startsWith("w")){
    			w = Integer.valueOf(line.trim().substring(1));
    			continue;
    		}    		
    		if (line.trim().startsWith("h")){
    			h = Integer.valueOf(line.trim().substring(1));
    			continue;
    		}

    		// comment
    		if (line.startsWith(";")) {
    			continue;
    		}

    		// start of level definition
    		if (null == level && h>0 && w>0){
    			level = new Cell[w][h];
    			row = 0;
    		}

    		// regular board line
    		for (int col=0 ; col< line.length(); col++){
    			Cell cell = parseCell(col, row, line.charAt(col));
    			if (null != cell){
    				level[col][row] = cell;
    			} else {
    				br.close();
    				return false;
    			}
    		}
    		row++;
    	}
		if (null != level){
			_levels.add(level);
			level = null;
		}
    	br.close();
    	return true;
    }
    
    /**
     * @return the number of levels available
     */
    public int getLevelsCount() {
		return _levels.size();
	}
    
    /**
     * @param index - the level number
     * @return the initial state of level number {@code index}
     *  
     * TODO - is recommended to create a deep copy of the array.
     */
    public Cell[][] get(int index) {
    	return _levels.get(index);
    }

    /**
     * create {@code Cell} instance from {@code char} representation
     * 
     * @return the {@code Cell} object 
     */
    private Cell parseCell(int col, int row, char cell) {
		switch (cell) {

		case CHAR_WALL:
			return new Cell(col, row);
		case CHAR_FLOOR:
			return new Cell(col, row, false, false, false);
		case CHAR_PLAYER:
			return new Cell(col, row, false, true, false);
		case CHAR_STORAGE:
			return new Cell(col, row, true, false, false);
		case CHAR_BOX:
			return new Cell(col, row, false, false, true);
		case CHAR_BOX_IN_STORAGE:
			return new Cell(col, row, true, false, true);
		case CHAR_PLAYER_AT_STORAGE:
			return new Cell(col, row, true, true, false);
		default:
			return null;
		}
	}

	public Vector<Cell[][]> get_levels() {
		return _levels;
	}
}
