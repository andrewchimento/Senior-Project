package seniorproject.hearts;

import java.util.ArrayList;

import seniorproject.utilities.Card;

/**
 * Controls logic for the Human player
 * 
 * There is not much functionality to this class, because most logic is carried out by the user 
 * 
 * @author Andrew
 */
public class Human extends Player{
	
	/**
	 * Default 
	 */
	public Human(){
		
		super(HeartsDirections.HUMAN);
	}
	
	public boolean hasPassingCard(Card card){
		
		return passingCards.contains(card);
	}
	
	public void addPassingCard(Card card){
		
		if(passingCards.size() == 3){
			
			return;
		}
		
		passingCards.add(card);
	}
	
	public void removePassingCard(Card card){
		
		passingCards.remove(card);
	}

	public int getNumPassingCards() {

		return passingCards.size();
	}

	/**
	 * @see seniorproject.hearts.Player#getPassingCards()
	 */
	@Override
	public ArrayList<Card> getPassingCards() {

		// remove the cards from the deck before handing them over
		for(Card card : passingCards){
			
			hand.remove(card);
		}
		
		return new ArrayList<Card>(passingCards);
	}
	
	public void removeCard(Card card){
		
		hand.remove(card);
	}
}
