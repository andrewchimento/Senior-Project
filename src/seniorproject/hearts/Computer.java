package seniorproject.hearts;

import java.util.ArrayList;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * A Computer player in a game of Hearts
 * 
 * Controls the logic for a Computer, namely which cards to pass and which card to play for an individual turn
 * Extends {@link Player Player} class
 * 
 * @author Andrew
 */
public class Computer extends Player{

	// this is a list of cards, in order, that would be good to pass
	// this is used to determine which cards the computer will pass
	private final Card[] goodPassingCards = {new Card(Suit.SPADES, CardValue.ACE), new Card(Suit.SPADES, CardValue.KING), new Card(Suit.DIAMONDS, CardValue.ACE), new Card(Suit.CLUBS, CardValue.ACE), new Card(Suit.DIAMONDS, CardValue.KING), new Card(Suit.CLUBS, CardValue.KING), new Card(Suit.DIAMONDS, CardValue.QUEEN), new Card(Suit.CLUBS, CardValue.QUEEN), new Card(Suit.DIAMONDS, CardValue.JACK), new Card(Suit.CLUBS, CardValue.JACK), new Card(Suit.HEARTS, CardValue.ACE), new Card(Suit.HEARTS, CardValue.KING), new Card(Suit.HEARTS, CardValue.QUEEN), new Card(Suit.HEARTS, CardValue.JACK)};
	
	/**
	 * A constructor for a Computer instance
	 * 
	 * @param direction the direction of the computer, from the point of view of the Human player
	 */
	public Computer(HeartsDirections direction){
		
		super(direction);
	}
	
	/**
	 * @see seniorproject.hearts.Player#getPassingCards()
	 */
	@Override
	public ArrayList<Card> getPassingCards() {

		// simple strategy for computer passing cards:
		// first pass either ace or king of spades, if they have them
		//   this helps avoid getting the queen of spades
		// next look for diamond and club face cards
		// next look for hearts face cards
		// these cards all all kept in the goodPassingCards array and we scan through them to find if we can pass these cards
		for(int i = 0; i < goodPassingCards.length; i++){
			
			// if the computer has this card, add it to the list
			if(hasCard(goodPassingCards[i].getSuit(), goodPassingCards[i].getValue())){
				
				passingCards.add(goodPassingCards[i]);
				hand.remove(goodPassingCards[i]);
			}
			
			if(passingCards.size() == 3){
				
				return new ArrayList<Card>(passingCards);
			}
		}
		
		// next try to void diamonds or clubs, so you're not restricted by the first card played
		// find which suit has fewer cards
		int numDiamonds = 0;
		int numClubs = 0;
		for(Card card : hand){
			
			if(card.getSuit() == Suit.DIAMONDS){
				
				numDiamonds++;
				continue;
			}
			
			if(card.getSuit() == Suit.CLUBS){
				
				numClubs++;
				continue;
			}
		}
		Suit fewerCardsSuit = (numDiamonds >= numClubs) ? Suit.DIAMONDS : Suit.CLUBS;

		// find all of the cards of that suit in the deck
		ArrayList<Card> cardsOfFewerSuit = new ArrayList<Card>();
		for(Card card : hand){
			
			if(card.getSuit() == fewerCardsSuit){
				
				cardsOfFewerSuit.add(card);
			}
		}
		
		// fill the rest of the cards with the suit that has fewer cards left
		while(passingCards.size() < 3){
			
			// take from the highest cards first
			// the cards are already sorted in ascending order, so take from the back
			Card cardToAdd = cardsOfFewerSuit.remove(cardsOfFewerSuit.size() - 1);
			hand.remove(cardToAdd);
			passingCards.add(cardToAdd);
		}
		
		return new ArrayList<Card>(passingCards);
	}
	
	/**
	 * Determines whether or not the computer has a given card in their deck
	 * 
	 * Used during the process of determining which cards the computer will pass
	 * 
	 * @param suit		the suit of the card we are looking for
	 * @param cardValue	the value of the card we are looking for
	 * @return			true if the computer has the card, false if not
	 */
	public boolean hasCard(Suit suit, CardValue cardValue){
		
		for(Card card : hand){
			
			if(card.getSuit() == suit && card.getValue() == cardValue){
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Chooses a card from the Computer's deck to play on a turn
	 * 
	 * Logic:
	 * 	if first turn of first trick: play two of clubs (this is mandatory)
	 * 	else if it is the first turn of the non-first trick: play the highest card of the suit with the fewest cards
	 * 	else calculate the number of points for each individual move, and play the highest card from that bunch
	 * 
	 * @param heartsModel	the model for the game of Hearts
	 * @return				the card the Computer has chosen to play
	 */
	public Card chooseCard(HeartsModel heartsModel){

		// if it is the first turn of the first trick, must play 2 of clubs
		//   wouldn't have gotten here if this computer didn't have the two of clubs
		if(heartsModel.isFirstTurn() && heartsModel.isFirstTrick()){
			
			return hand.remove(hand.indexOf(new Card(Suit.CLUBS, CardValue.TWO)));
		}
		
		// if the computer has the first turn, play the highest card of the suit with the fewest cards
		//   this can help to void a suit as early as possible
		if(heartsModel.isFirstTurn()){
			
			return hand.remove(hand.indexOf(firstTurn(heartsModel)));
		}
	
		// run through every card and see which card will give the fewest points
		Card cardToPlay = hand.get(0);
		// set points to an unreasonably high number to simulate infinity (because we want the lowest amount of points possible)
		int points = 10000;
		ArrayList<Card> cardsWithSamePoints = new ArrayList<Card>();
		ArrayList<Card> playableHeartsCards = new ArrayList<Card>();
		for(Card card : hand){
			
			// if we can't add it, don't bother checking how many points it's worth
			if(!heartsModel.canAdd(card, this)){
				
				continue;
			}
			
			// if the card is a queen of spades, and an ace or a king of spades is already placed, this is a good time to get rid of the queen of spades
			if(card.equals(new Card(Suit.SPADES, CardValue.QUEEN))){
				
				if(hasKingOrAceOfSpades(heartsModel)){
					
					return hand.remove(hand.indexOf(card));
				}
			}
			
			// if the card is a heart and the suit it a non-heart, we'll want to play the highest heart we have
			if(card.getSuit() == Suit.HEARTS && heartsModel.getTrickSuit() != Suit.HEARTS){
				
				playableHeartsCards.add(card);
			}
			
			// count how many points the trick would give you if you won it
			ArrayList<Card> trickCards = heartsModel.getTrickCards();
			trickCards.add(card);
			int pointsForTrick = calculatePoints(trickCards);
			
			// generally speaking, we want to play the highest card value that would give us the least amount of points, so save all the cards that are the same amount of points
			if(pointsForTrick == points){
				
				cardsWithSamePoints.add(card);
			}
			// if the points for the simulated trick is lower than the move we were going to do, update the move to the better one
			else if(pointsForTrick < points){
				
				cardToPlay = card;
				points = pointsForTrick;
				cardsWithSamePoints.clear();
			}
		}
		
		// first scan through the hearts cards that we've noticed we can play, and get the highest one
		if(!playableHeartsCards.isEmpty()){
			
			Card highestHeartsCard = playableHeartsCards.get(0);
			for(Card card : playableHeartsCards){
				
				if(highestHeartsCard.getValue().isGreaterThan(card.getValue(), false)){
					
					highestHeartsCard = card;
				}
			}
			
			return hand.remove(hand.indexOf(highestHeartsCard));
		}
		
		// then scan through all of the card worth the lowest amount of points to find the highest one
		// the cardToPlay should be worth the same amount of points as all of the cards
		if(!cardsWithSamePoints.isEmpty()){
			
			for(Card card : cardsWithSamePoints){
				
				if(cardToPlay.getValue().isLessThan(cardToPlay.getValue(), false)){
					
					cardToPlay = card;
				}
			}
		}
		
		return hand.remove(hand.indexOf(cardToPlay));
	}
	
	/**
	 * Determines which card to play if it is the first turn of a non-first trick
	 * 
	 * @see #chooseCard(HeartsModel) for logic
	 * 
	 * @param heartsModel	the model for the game of Hearts
	 * @return				the card the Computer has chosen to play
	 */
	private Card firstTurn(HeartsModel heartsModel){
		
		// calculate the total number of cards of each suit
		int numHearts = 0;
		int numSpades = 0;
		int numDiamonds = 0;
		int numClubs = 0;
		for(Card card : hand){
			
			switch(card.getSuit()){
			case HEARTS:
				numHearts++;
				break;
			case SPADES:
				numSpades++;
				break;
			case DIAMONDS:
				numDiamonds++;
				break;
			case CLUBS:
				numClubs++;
				break;
			}
		}
		
		// determine the index of the highest card of the suit with the fewest card
		// first, find which suit has the fewest number of cards
		// the index math is based off of the fact that the cards should be sorted in the order: hearts, spades, diamonds, clubs
		int fewestCardsNum = 10000;
		int[] suits = {numHearts, numSpades, numDiamonds, numClubs};
		for(int numCards : suits){
			
			if(numCards < fewestCardsNum && numCards != 0){
				
				fewestCardsNum = numCards;
			}
		}

		// make sure that hearts is broken, or we can't play a hearts
		if(numHearts == fewestCardsNum && heartsModel.isHeartsBroken()){
			
			return hand.get(numHearts - 1);
		}
		else if(numSpades == fewestCardsNum){
			
			return hand.get(numHearts + numSpades - 1);
		}
		else if(numDiamonds == fewestCardsNum){
			
			return hand.get(numHearts + numSpades + numDiamonds - 1);
		}
		else{
			
			return hand.get(hand.size() - 1);
		}
	}
	
	/**
	 * Determines whether or not the Computer has the Ace of Spades or the King of Spades cards
	 * 
	 * This is for determining when the Computer should get rid of the Queen of Spades
	 * 
	 * @param heartsModel	the model for the game of Hearts
	 * @return				true if the Computer has the Ace of Spades or the King of Spades
	 */
	private boolean hasKingOrAceOfSpades(HeartsModel heartsModel){
		
		for(Card card : heartsModel.getTrickCards()){
			
			if(card == null){
				
				continue;
			}
			
			if(card.equals(new Card(Suit.SPADES, CardValue.ACE)) || card.equals(new Card(Suit.SPADES, CardValue.KING))){
				
				return true;
			}
		}
		
		return false;
	}
}