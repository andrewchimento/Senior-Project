package seniorproject.solitaire;

import java.util.ArrayList;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * Pile class controls the logic for a pile, which is considered a type of CardStack
 * 
 * The pile knows its own number, which helps for control purposes
 * This class inherits from {@link CardStack CardStack}
 * 
 * @author Andrew
 */
public class Pile extends CardStack {
	
	private int pileNum;
	
	/**
	 * A constructor for a Pile instance
	 * 
	 * @param pileNum	the number of the pile
	 */
	public Pile(int pileNum){
		
		super();
		
		this.pileNum = pileNum;
	}
	
	public void addInitialCards(Card card){
		
		cards.add(card);
	}
	
	public int getPileNum(){
		
		return pileNum;
	}
	
	public Card firstElement(){
		
		return cards.firstElement();
	}
	
	public Card lastElement(){
		
		return cards.lastElement();
	}
	
	public int getNumFaceUpCards(){
		
		int numFaceUpCards = 0;
		
		for(Card card : cards){
			
			if(card.isFaceUp()){
				
				numFaceUpCards++;
			}
		}
		
		return numFaceUpCards;
	}
	
	/**
	 * Determines whether or not the Pile has cards that are face down
	 * 
	 * A face down card is not in play, and cannot be moved
	 * 
	 * @return	true if the Pile has face-down cards, false if not
	 */
	public boolean hasDownCards(){
		
		for(Card card : cards){
			
			if(!card.isFaceUp()){
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines whether or not the Pile has cards that are face up
	 * 
	 * A face up card is considered in play, and able to be moved
	 * 
	 * @return	true if the Pile has face-up cards, false if not
	 */
	public boolean hasUpCards(){
		
		for(Card card : cards){
			
			if(card.isFaceUp()){
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes a given amount of cards from the Pile
	 * 
	 * Removes cards based on a number, rather than on a specific group of cards
	 * 
	 * @param numCards	the number of cards to remove
	 */
	public void removeCards(int numCards){
		
		for(int i = 0; i < numCards; i++){
			
			cards.pop();
		}
	}

	/**
	 * Adds multiple cards to the Pile
	 * 
	 * @param cardsToAdd	the cards to add to the Pile
	 */
	public void addCards(ArrayList<Card> cardsToAdd){
		
		for(Card card : cardsToAdd){
			
			cards.push(card);
		}
	}
	
	/**
	 * @see seniorproject.solitaire.CardStack#checkEmptyStack(seniorproject.utilities.Card)
	 */
	@Override
	protected boolean checkEmptyStack(Card card){
		
		return card.getValue() == CardValue.KING;
	}
	
	/**
	 * @see seniorproject.solitaire.CardStack#checkSuit(seniorproject.utilities.Suit)
	 */
	@Override
	protected boolean checkSuit(Suit suitToAdd){
		
		Suit topSuit = cards.lastElement().getSuit();
		
		// top suit is black
		if(topSuit == Suit.CLUBS || topSuit == Suit.SPADES){
			
			// adding red card
			if(suitToAdd == Suit.DIAMONDS || suitToAdd == Suit.HEARTS){
				
				return true;
			}
		}
		
		// top suit is red
		if(topSuit == Suit.DIAMONDS || topSuit == Suit.HEARTS){
			
			// adding black card
			if(suitToAdd == Suit.CLUBS || suitToAdd == Suit.SPADES){
				
				return true;
			}
		}
	
		return false;
	}
	
	/**
	 * @see seniorproject.solitaire.CardStack#checkValue(int)
	 */
	@Override
	protected boolean checkValue(int newCardNum) {

		return newCardNum == (cards.lastElement().getValue().ordinal() - 1);
	}
}
