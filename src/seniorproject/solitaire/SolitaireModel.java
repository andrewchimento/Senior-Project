package seniorproject.solitaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * The underlying model class which controls the logic for a game of Solitaire
 * 
 * @author Andrew
 */
public class SolitaireModel {
	
	private Foundation foundationOne;
	private Foundation foundationTwo;
	private Foundation foundationThree;
	private Foundation foundationFour;
	private Pile pileOne;
	private Pile pileTwo;
	private Pile pileThree;
	private Pile pileFour;
	private Pile pileFive;
	private Pile pileSix;
	private Pile pileSeven;
	private Stack<Card> deck;
	private Stack<Card> drawSpot;
	
	/**
	 * Default constructor for a SolitiareModel instance
	 * 
	 * Initializes all objects and creates the deck
	 * Shuffles the deck and adds the proper amount of cards to each pile
	 * 
	 * @return	a default SolitaireModel instance
	 */
	public SolitaireModel(){
		
		foundationOne = new Foundation();
		foundationTwo = new Foundation();
		foundationThree = new Foundation();
		foundationFour = new Foundation();
		pileOne = new Pile(1);
		pileTwo = new Pile(2);
		pileThree = new Pile(3);
		pileFour = new Pile(4);
		pileFive = new Pile(5);
		pileSix = new Pile(6);
		pileSeven = new Pile(7);
		deck = new Stack<Card>();
		drawSpot = new Stack<Card>();
	
		addCardsToDeck();
		
		// shuffle the deck
		Collections.shuffle(deck);
		
		initPiles();
	}
	
	/**
	 * Adds all of the cards from a standard deck
	 * 
	 * A standard deck consists of 52 cards, 4 suits, 13 cards in each suit from Ace to King
	 */
	private void addCardsToDeck(){
		
		// put one of each card in a standard deck of 52 into the deck
		for(Suit suit : Suit.values()){
			
			for(CardValue card : CardValue.values()){
				
				deck.push(new Card(suit, card));
			}
		}
	}
	
	/**
	 * Initializes all of the piles for when the game begins
	 * 
	 * Puts the same number of cards in a pile as the pile number (ie: 1 card in pile 1, 2 cards in pile 2, ...)
	 */
	private void initPiles(){
		
		// get all of the piles in one iteratable list to make this easier
		ArrayList<Pile> allPiles = new ArrayList<Pile>();
		allPiles.add(pileOne);
		allPiles.add(pileTwo);
		allPiles.add(pileThree);
		allPiles.add(pileFour);
		allPiles.add(pileFive);
		allPiles.add(pileSix);
		allPiles.add(pileSeven);
		
		// put a 1-7 amount of cards in each pile, taking off from the top of the deck each time
		for(int i = 1; i <= allPiles.size(); i++){
			
			for(int j = 0; j < i; j++){
				
				allPiles.get(i - 1).addInitialCards(deck.pop());
			}
		}
	}
	
	public int getNumCardsInDeck(){
		
		return deck.size();
	}
	
	public int getNumCardsInDrawSpot(){
		
		return drawSpot.size();
	}
	
	public Pile getPile(int pileNumber){
		
		Pile pile = pileOne;
		
		switch(pileNumber){
		case 2:
			pile = pileTwo;
			break;
		case 3:
			pile = pileThree;
			break;
		case 4:
			pile = pileFour;
			break;
		case 5:
			pile = pileFive;
			break;
		case 6:
			pile = pileSix;
			break;
		case 7:
			pile = pileSeven;
			break;
		}
		
		return pile;
	}
	
	public Foundation getFoundation(int foundationNumber){
		
		Foundation foundation = foundationOne;
		
		switch(foundationNumber){
		case 2:
			foundation = foundationTwo;
			break;
		case 3:
			foundation = foundationThree;
			break;
		case 4:
			foundation = foundationFour;
			break;
		}
		
		return foundation;
	}
	
	public Card getTopCardFromDeck(){
		
		return deck.lastElement();
	}
	
	public Card getTopCardFromFoundation(int foundationNumber){
		
		return getFoundation(foundationNumber).getTopCard();
	}

	public Card getTopCardFromPile(int pileNumber){
	
		return getPile(pileNumber).getTopCard();
	}
	
	/**
	 * Determines whether or not the end of the game has been reached
	 * 
	 * This is true when all cards are in their respective foundations (52/4 = 13 cards in each foundation)
	 * 
	 * @return	true if game is over, false if not
	 */
	public boolean endOfGame(){
		
		return foundationOne.isFull() && foundationTwo.isFull() && foundationThree.isFull() && foundationFour.isFull();
	}
	
	/**
	 * Draws a card from the deck
	 * 
	 * After the card has been drawn from the deck, it is moved to the draw spot
	 * 
	 * @return	the card that was removed from the deck
	 */
	public Card drawFromDeck(){
		
		// pull a card from the deck
		Card returnCard = deck.pop();
		// add it to the draw spot
		drawSpot.push(returnCard);
		
		return returnCard;
	}
	
	/**
	 * Called when a card has been moved from the draw spot, and put into the playing field
	 */
	public void movedFromDrawSpot(){
		
		drawSpot.pop();
	}
	
	/**
	 * Moves all cards from the draw spot back into the deck
	 * 
	 * This is done when the player clicks on the empty deck (empty because all cards have been taken into play or moved to the draw spot)
	 */
	public void recycleDrawnCards(){
		
		// put the cards from the draw spot into the deck
		// this must be done in reverse order
		while(!drawSpot.empty()){
			
			deck.push(drawSpot.pop());
		}
	}
}
