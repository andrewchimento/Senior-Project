package seniorproject.hearts;

import java.util.ArrayList;
import java.util.Comparator;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * The base class for each of the players in a game of Hearts
 * 
 * This base class is extended by the {@link Computer Computer} and {@link Human Human} classes
 * 
 * @author Andrew
 */
public abstract class Player {

	protected ArrayList<Card> hand;
	protected ArrayList<Card> passingCards;
	protected ArrayList<Card> wonCards;
	private HeartsDirections direction;
	private ArrayList<Integer> points;
	private int score;
	
	/**
	 * Constructor for a Player class
	 * 
	 * @param direction	the position of the player, given by the direction
	 * @return			a Player instance
	 */
	public Player(HeartsDirections direction){
		
		hand = new ArrayList<Card>();
		passingCards = new ArrayList<Card>();
		wonCards = new ArrayList<Card>();
		this.direction = direction;
		points = new ArrayList<Integer>();
		score = 0;
	}
	
	public void addToHand(Card card){
		
		hand.add(card);
	}
	
	public void addToHand(ArrayList<Card> cards){
		
		for(Card card : cards){
			
			hand.add(card);
		}
	}
	
	public Card getCard(int index){
		
		return hand.get(index);
	}
	
	public int getNumCardsInHand(){
		
		return hand.size();
	}
	
	public ArrayList<Card> getHand(){

		return new ArrayList<Card>(hand);
	}
	
	public ArrayList<Card> getWonCards(){
		
		return wonCards;
	}
	
	/**
	 * Adds the cards the player has won in the trick to their stack of won cards
	 * 
	 * @param wonCards	the cards the player has won
	 */
	public void addWonCards(ArrayList<Card> wonCards){
		
		this.wonCards.addAll(wonCards);
	}
	
	public HeartsDirections getDirection(){
		
		return direction;
	}
	
	/**
	 * Adds the amount of points the player has won to their score
	 * 
	 * Uses the cards the player has won in the round to calculate the points
	 */
	public void addPoints(){
		
		int roundPoints = calculatePoints(wonCards);
		points.add(roundPoints);
		score += roundPoints;
	}
	
	public int getRoundPoints(int round){
		
		return points.get(round - 1); 
	}
	
	public int getScore(){
		
		return score;
	}
	
	/**
	 * Resets the passing cards for the player
	 * 
	 * This is done after the round has ended, in preparation for a new round
	 */
	public void resetPassingCards(){
		
		passingCards.clear();
	}
	
	/**
	 * Determines whether or not the player has cards of a given suit
	 * 
	 * This is used to determine if a player can play a card of a suit other than the trick suit
	 * 
	 * @param suit	the suit to search for in the player's deck
	 * @return		true if the player has cards of the given suit, false if not
	 */
	public boolean hasSuit(Suit suit){
		
		for(Card card : hand){
			
			if(card.getSuit() == suit){
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Sorts the player's deck
	 * 
	 * This function creates a Comparator object to do the sorting
	 * A Comparator object is passed to the ArrayList.sort() function to apply a custom sort
	 * A custom sort is necessary due to the fact that cards must be sorted by their suit and their values within the suit
	 */
	public void sortHand(){
		
		hand.sort(new Comparator<Card>(){
			
			// need to write a special comparator function to sort the cards correctly
			@Override
			public int compare(Card card1, Card card2){
				
				int suitCompare = card1.getSuit().compareTo(card2.getSuit());
				
				// same suit
				if(suitCompare == 0){
					
					// in hearts, the Ace is the highest value, but the enum sets it up so the ace is the lowest value (like solitaire)
					// because we are in the same suit only one Ace exists
					if(card1.getValue() == CardValue.ACE){
						
						return 1;
					}
					else if(card2.getValue() == CardValue.ACE){
						
						return -1;
					}
					else{
						
						return card1.getValue().compareTo(card2.getValue());	
					}
				}
				
				return suitCompare;
			}
		});
	}
	
	/**
	 * Calculates the amount of points the player has scored during the round
	 * 
	 * Each Hearts card is worth one point and the Queen of Spades is worth 13 points
	 * 
	 * @param cards	the cards the player has won during the round
	 * @return		the amount of points the player has scored
	 */
	public int calculatePoints(ArrayList<Card> cards){
		
		int points = 0;
		
		for(Card card : cards){
			
			// indicates a card has not been placed yet
			if(card == null){
				
				continue;
			}
			
			if(card.getSuit() == Suit.HEARTS){
				
				points++;
			}
			
			if(card.equals(new Card(Suit.SPADES, CardValue.QUEEN))){
				
				points += HeartsModel.QUEEN_POINTS;
			}
		}
		
		return points;
	}
	
	/**
	 * Returns the cards that the player has chosen to pass
	 * 
	 * This is a different process for the Human and the Computer: the Human chooses their cards by interaction, while the Computer chooses their cards with an algorithm
	 * 
	 * @return	the list of cards the player has chosen to pass
	 */
	abstract public ArrayList<Card> getPassingCards();
}
