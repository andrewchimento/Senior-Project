package seniorproject.solitaire;

import seniorproject.utilities.GameStats;

/**
 * Child class of {@link seniorproject.utilities.GameStats GameStats}
 * 
 * Holds statistical information for a game of Solitaire
 * 
 * @author Andrew
 */
public class SolitaireStats extends GameStats {

	private int numMoves;
	
	/**
	 * Default constructor for SolitaireStats
	 * 
	 * @return a default SolitaireStats object
	 */
	public SolitaireStats(){
		
		super();
		numMoves = 0;
	}
	
	public int getNumMoves(){
		
		return numMoves;
	}
	
	public void setNumMoves(int numMoves){
		
		this.numMoves = numMoves;
	}
}
