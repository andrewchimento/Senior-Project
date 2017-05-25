package seniorproject.hearts;

import seniorproject.utilities.Card;

/**
 * Controls all information about a Turn, namely the card played and the direction of the player
 * 
 * @author Andrew
 */
public class Turn {

	private HeartsDirections direction;
	private Card card;
	
	/**
	 * Default constructor for a Turn instance
	 * 
	 * For initialization of arrays
	 * 
	 * @return	a default Turn instance
	 */
	public Turn(){
		
		this.direction = null;
		this.card = null;
	}
	
	/**
	 * A constructor for a Turn instance
	 * 
	 * @param direction	the direction of the player
	 * @param card		the card the player played
	 * @return			a Turn instance
	 */
	public Turn(HeartsDirections direction, Card card){
		
		this.direction = direction;
		this.card = card;
	}
	
	public HeartsDirections getDirection() {
		
		return direction;
	}
	
	public void setDirection(HeartsDirections direction) {
		
		this.direction = direction;
	}
	
	public Card getCard() {
		
		return card;
	}
	
	public void setCard(Card card) {
		
		this.card = card;
	}
	
	@Override
	public String toString(){
		
		return direction.toString() + " places " + card.toString();
	}
}
