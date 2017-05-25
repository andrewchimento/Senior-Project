package seniorproject.solitaire;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * Foundation class controls the logic for a foundation, which is considered a type of CardStack
 * 
 * This class inherits from {@link CardStack CardStack}
 * 
 * @author Andrew
 */
public class Foundation extends CardStack {
	
	private Suit suit;
	
	/**
	 * Default constructor for a Foundation instance
	 * 
	 * @return a default Foundation instance
	 */
	public Foundation(){
		
		super();
	}
	
	public Suit getSuit(){
		
		return suit;
	}
	
	public void setSuit(Suit suit){
		
		this.suit = suit;
	}
	
	public boolean isFull(){
		
		return getNumCards() == 13;
	}
	
	/**
	 * @see seniorproject.solitaire.CardStack#checkEmptyStack(seniorproject.utilities.Card)
	 */
	@Override
	protected boolean checkEmptyStack(Card card) {

		// must set the suit to that of the ace that the person is adding
		if(card.getValue() == CardValue.ACE){
			
			suit = card.getSuit();
			return true;
		}
		
		return false;
	}
	
	/**
	 * @see seniorproject.solitaire.CardStack#checkSuit(seniorproject.utilities.Suit)
	 */
	@Override
	protected boolean checkSuit(Suit suit){

		return suit.equals(this.suit);
	}

	/**
	 * @see seniorproject.solitaire.CardStack#checkValue(int)
	 */
	@Override
	protected boolean checkValue(int newCardNum) {

		return newCardNum == (cards.lastElement().getValue().ordinal() + 1); 
	}
}
