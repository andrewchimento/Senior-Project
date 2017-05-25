package seniorproject.utilities;

/**
 * An enum to simplify the use of Card objects and their values
 * 
 * @author Andrew
 */
public enum CardValue {
	/**
	 * The values of the cards
	 */
	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING;
	
	/**
	 * Determines if the card value (this) is greater than the otherValue
	 * 
	 * @param otherValue	the other card value that we are comparing to
	 * @param isSolitaire	whether or not we are playing Solitaire; in Solitaire, the King is the highest card, but in Hearts, and Ace is
	 * @return				true if this card is greater than the other, false if this card is less
	 */
	public boolean isGreaterThan(CardValue otherValue, boolean isSolitaire){
		
		// in solitaire the ace is the lowest card
		if(isSolitaire){
			
			return this.ordinal() > otherValue.ordinal();
		}
		// in hearts, the ace is the highest card
		else{
			
			if(this == ACE){
				
				return true;
			}
			else if(otherValue == ACE){
				
				return false;
			}
			else{
				
				return this.ordinal() > otherValue.ordinal();
			}
		}
	}
	
	/**
	 * Determines if the card value (this) is less than the otherValue
	 * 
	 * @param otherValue	the other card value that we are comparing to
	 * @param isSolitaire	whether or not we are playing Solitaire; in Solitaire, the King is the highest card, but in Hearts, and Ace is
	 * @return				true if this card is less than the other, false if this card is greater
	 */
	public boolean isLessThan(CardValue otherValue, boolean isSolitaire){
		
		// in solitaire the ace is the lowest card
		if(isSolitaire){
			
			return this.ordinal() < otherValue.ordinal();
		}
		// in hearts, the ace is the highest card
		else{
			
			if(this == ACE){
				
				return false;
			}
			else if(otherValue == ACE){
				
				return true;
			}
			else{
				
				return this.ordinal() < otherValue.ordinal();
			}
		}
	}
}