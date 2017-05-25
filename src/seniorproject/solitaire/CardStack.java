package seniorproject.solitaire;

import java.util.Stack;

import seniorproject.utilities.Card;
import seniorproject.utilities.Suit;

/**
 * The base class for every CardStack child model (includes piles and foundations)
 * 
 * This base class is extended by the {@link seniorproject.solitaire.Pile Pile} and {@link seniorproject.solitaire.Foundation Foundation} classes
 * 
 * @author Andrew
 */
public abstract class CardStack {

	public static final int WRONG_SUIT = -1;
	public static final int CANT_PUT_ON_TOP = -2;
	public static final int CARD_ADDED = 1;
	
	protected Stack<Card> cards;
	
	/**
	 * Default constructor for CardStack
	 * 
	 * @return default CardStack object
	 */
	public CardStack(){
		
		cards = new Stack<Card>();
	}
	
	public Card getTopCard(){
		
		return cards.lastElement();
	}
	
	public int getNumCards(){
		
		return cards.size();
	}
	
	/**
	 * Finds the index of the given card in the CardStack
	 * 
	 * @param card	the card to find in the CardStack
	 * @return		the index of the card, -1 if not in CardStack
	 */
	public int findCard(Card card){
		
		return cards.indexOf(card);
	}
	
	/**
	 * Adds a single card to the CardStack
	 * 
	 * Runs through logic for adding the card, including what to do for an empty stack and whether the card value and suit are correct
	 * This includes separation in logic from a pile to a foundation (uses polymorphism to control this)
	 * 
	 * @param card	the card to be added to the CardStack
	 * @return		true if the card was added, false if not
	 */
	public boolean addCard(Card card) {

		// if the stack is empty, we need to check to see if the correct card can be placed on top
		// for a pile, this is a king; for a foundation, this is an ace
		if(cards.isEmpty()){
			
			// if the stack has nothing on it, and the card they are trying to put on is good, then add the card
			if(checkEmptyStack(card)){
				
				cards.push(card);
				return true;
			}
			else{
				
				return false;
			}
		}
		
		// check to see if the card can be added
		// if its not the same suit as the foundation, it can't be added
		if(!checkSuit(card.getSuit())){
					
			return false;
		}
				
		// can only add the next higher card (i.e.: can only put a 3 on a 2)
		if(!checkValue(card.getValue().ordinal())){
			
			return false;
		}
	
		cards.push(card);
		
		return true;
	}
	
	/**
	 * Determines whether a card can be added to an empty CardStack (ie: one with no cards in it)
	 * 
	 * @param card	the card to check
	 * @return		true if the card can be added, false if not
	 */
	abstract protected boolean checkEmptyStack(Card card);
	
	/**
	 * Determines whether a card can be added to the CardStack, based on its suit
	 * 
	 * @param suit	the suit of the card to check
	 * @return		true if the card can be added, false if not
	 */
	abstract protected boolean checkSuit(Suit suit);
	
	/**
	 * Determines whether a card can be added to the CardStack, based on its value
	 * 
	 * @param newCardValue	the value of the new card to be added
	 * @return				true if the card can be added, false if not
	 */
	abstract protected boolean checkValue(int newCardValue);
}
