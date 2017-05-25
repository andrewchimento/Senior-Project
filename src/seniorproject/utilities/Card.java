package seniorproject.utilities;

/**
 * Card instances are used in the Solitaire and Hearts game modules. They are the underlying models and are controlled by their respective CardView instances.
 * 
 * @author Andrew
 */
public class Card {

	private CardValue value;
	private Suit suit;
	private boolean isFaceUp;
	
	/**
	 * Creates a Card instance from given suit and value
	 * 
	 * @param suit	the suit of the card to be created (Diamonds, Clubs, Hearts, Spades)
	 * @param value	the value of the card to be created (Ace, One, Two, ...)
	 * @return a Card object
	 */
	public Card(Suit suit, CardValue value){
		
		this.suit = suit;
		this.value = value;
		this.isFaceUp = false;
	}
	
	public CardValue getValue(){
		
		return value;
	}

	public void setValue(CardValue value){
		
		this.value = value;
	}
	
	public Suit getSuit(){
		
		return suit;
	}

	public void setSuit(Suit suit){
		
		this.suit = suit;
	}
	
	public void setIsFaceUp(boolean isFaceUp){
		
		this.isFaceUp = isFaceUp;
	}
	
	public boolean isFaceUp(){
		
		return isFaceUp;
	}
	
	/**
	 * Converts the object to string form
	 * Used primarily in debugging
	 * 
	 * @return the String object which represents the Card object
	 */
	@Override
    public String toString(){
    	
    	return value.toString() + " of " + suit.toString();
    }
	
	/**
	 * Comparison overload for a Card object
	 * 
	 * Compares two Card objects (this and o) based on their suit and value
	 * 
	 * @param o	the other object we are comparing this instance to
	 */
	@Override
	public boolean equals(Object o){
		
		// self comparison
		if(o == this){
			
			return true;
		}
		
		// not the same type of object comparison
		if(!(o instanceof Card)){
			
			return false;
		}
		
		Card card = (Card) o;
		
		// want to compare both 
		return this.suit == card.suit && this.value == card.value;
	}
}
