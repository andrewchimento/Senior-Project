package seniorproject.utilities;

/**
 * The base GameStats model used for inheritance
 * 
 * {@link seniorproject.solitaire.SolitaireStats}
 * {@link seniorproject.minesweeper.MinesweeperStats}
 * @author Andrew
 */
public class GameStats {

	protected int time;
	
	/**
	 * The default constructor for a GameStats object
	 * 
	 * @return a GameStats instance
	 */
	public GameStats(){
		
		time = 0;
	}
	
	public int getTime(){
		
		return time;
	}
	
	public void setTime(int _time){
		
		time = _time;
	}
}
