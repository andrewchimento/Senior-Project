package seniorproject.minesweeper;

import seniorproject.utilities.GameStats;

/**
 * Child class of {@link seniorproject.utilities.GameStats GameStats}
 * 
 * Holds statistical information for a game of Minesweeper
 * 
 * @author Andrew
 */
public class MinesweeperStats extends GameStats {

	private int numClicks;
	private int numMinesLeft;
	
	/**
	 * Default constructor for MinesweeperStats
	 * 
	 * @return a default MinesweeperStats object
	 */
	public MinesweeperStats(){
		
		super();
		numClicks = 0;
	}
	
	public int getNumClicks(){
		
		return numClicks;
	}
	
	public void setNumClicks(int _numClicks){
		
		numClicks = _numClicks;
	}
	
	public int getNumMinesLeft(){
		
		return numMinesLeft;
	}
	
	public void setNumMinesLeft(int _numMinesLeft){
		
		numMinesLeft = _numMinesLeft;
	}
}
