package seniorproject.hearts;

import java.util.ArrayList;
import java.util.Collections;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardValue;
import seniorproject.utilities.Suit;

/**
 * The underlying Hearts class which controls the logic of a game of Hearts
 * 
 * @author Andrew
 */
public class HeartsModel {

	public static final int QUEEN_POINTS = 13;
	public static final int TOTAL_SCORE = 100;
	
	// direction constants
	// converted to ints for array indexing
	private final int HUMAN = HeartsDirections.HUMAN.ordinal();
	private final int LEFT = HeartsDirections.LEFT.ordinal();
	private final int ACROSS = HeartsDirections.ACROSS.ordinal();
	private final int RIGHT = HeartsDirections.RIGHT.ordinal();
	
	// player objects
	private Human humanPlayer;
	private Computer leftPlayer;
	private Computer acrossPlayer;
	private Computer rightPlayer;
	
	// for turn tracking
	private Player[] players; 
	private int currentTurn;
	private boolean[] madeTurn;
	private int handNumber;
	private boolean isFirstTurn;
	private int firstPlayer;
	private boolean isFirstTrick;
	private boolean isHeartsBroken;
	private int winner;
	
	// passing variables
	private boolean isPassing;
	private HeartsDirections passingDirection;
	
	// trick cards
	private Suit trickSuit;
	private ArrayList<Card> trickCards;
	
	/**
	 * Default constructor for a HeartsModel instance
	 * 
	 * @return	a default HeartsModel instance
	 */
	public HeartsModel(){
		
		// setup defaults
		humanPlayer = new Human();
		acrossPlayer = new Computer(HeartsDirections.ACROSS);
		leftPlayer = new Computer(HeartsDirections.LEFT);
		rightPlayer = new Computer(HeartsDirections.RIGHT);
		players = new Player[4];
		players[HUMAN] = humanPlayer;
		players[LEFT] = leftPlayer;
		players[ACROSS] = acrossPlayer;
		players[RIGHT] = rightPlayer;
		handNumber = 0;
		passingDirection = HeartsDirections.LEFT;
		
		setUpRound();
	}
	
	/**
	 * Sets up default variables which will be referred to during first turns
	 * 
	 * In its own function so it can be reused outside of the constructor
	 */
	private void setUpRound(){
		
		handNumber++;
		// set to defaults for first turn
		madeTurn = new boolean[4];
		for(int i = 0; i < 4; i++){
			
			madeTurn[i] = false;
		}
		isPassing = true;
		isFirstTurn = true;
		isFirstTrick = true;
		trickCards = new ArrayList<Card>();
		for(int i = 0; i < 4; i++){
			
			trickCards.add(null);
		}
		
		// deal out cards
		ArrayList<Card> cards = initCards();
		dealCards(cards);
		
		// sort all decks
		humanPlayer.sortHand();
		acrossPlayer.sortHand();
		leftPlayer.sortHand();
		rightPlayer.sortHand();
	}
	
	/**
	 * Adds all of the cards from a standard deck
	 * 
	 * A standard deck consists of 52 cards, 4 suits, 13 cards in each suit from Ace to King
	 * 
	 * @return	the standard deck of cards, already shuffled
	 */
	private ArrayList<Card> initCards(){
		
		ArrayList<Card> cards = new ArrayList<Card>();
		
		// put one of each card in a standard deck of 52 into the deck
		for(Suit suit : Suit.values()){
					
			for(CardValue card : CardValue.values()){
						
				cards.add(new Card(suit, card));
			}
		}
		
		Collections.shuffle(cards);
		
		return cards;
	}
	
	/**
	 * Deals cards to all 4 players
	 * 
	 * Each player gets 52/4 = 13 cards
	 * 
	 * @param cards	the shuffled deck of cards
	 */
	private void dealCards(ArrayList<Card> cards){
		
		while(!cards.isEmpty()){
			
			humanPlayer.addToHand(cards.remove(0));
			acrossPlayer.addToHand(cards.remove(0));
			leftPlayer.addToHand(cards.remove(0));
			rightPlayer.addToHand(cards.remove(0));
		}
	}
	
	/**
	 * Determines the first player for a round of Hearts
	 * 
	 * The first player is the one who has the 2 of Clubs
	 */
	public void determineFirstPlayer(){
		
		// search for which player has the 2 of Clubs
		Card twoOfClubs = new Card(Suit.CLUBS, CardValue.TWO);
		int i = 0;
		for(Player player : players){
			
			if(player.getHand().contains(twoOfClubs)){
				
				currentTurn = i;
				return;
			}
			
			i++;
		}
	}
	
	public Human getHumanPlayer(){
		
		return humanPlayer;
	}
	
	public Computer getAcrossPlayer(){
		
		return acrossPlayer;
	}
	
	public Computer getLeftPlayer(){
		
		return leftPlayer;
	}
	
	public Computer getRightPlayer(){
		
		return rightPlayer;
	}
	
	public Player getCurrentTurnPlayer(){
		
		return players[currentTurn];
	}
	
	public boolean isHumanTurn(){
		
		return currentTurn == HUMAN;
	}
	
	public boolean isPassing(){
		
		return isPassing;
	}
	
	public Suit getTrickSuit(){
		
		return trickSuit;
	}
	
	public void setTrickSuit(Suit trickSuit){
		
		this.trickSuit = trickSuit;
	}
	
	public ArrayList<Card> getTrickCards(){
		
		return new ArrayList<Card>(trickCards);
	}
	
	/**
	 * Adds a single trick card to the batch of trick cards for a single round
	 * 
	 * @param card		the card to be added
	 * @param direction	the direction of the player it is coming from
	 */
	public void addTrickCard(Card card, HeartsDirections direction){

		trickCards.set(direction.ordinal(), card);
	}
	
	public boolean isFirstTurn(){
		
		return isFirstTurn;
	}
	
	public void setIsFirstTurn(boolean isFirstTurn){
		
		this.isFirstTurn = isFirstTurn;
	}
	
	public boolean isFirstTrick(){
			
		return isFirstTrick;
	}
	
	public boolean isHeartsBroken(){
		
		return isHeartsBroken;
	}
	
	public void setIsHeartsBroken(boolean isHeartsBroken){

		this.isHeartsBroken = isHeartsBroken;
	}
	
	public void setFirstPlayer(HeartsDirections direction) {

		firstPlayer = direction.ordinal();
	}
	
	/**
	 * Sets the turn to the next person
	 * 
	 * Assumes clockwise order
	 */
	public void nextTurn(){
		
		madeTurn[currentTurn] = true;
		currentTurn++;
		currentTurn %= 4;
	}
	
	public int getRoundNumber(){
		
		return handNumber;
	}
	
	public boolean isHumanWin(){
		
		return winner == HUMAN;
	}
	
	public Player getWinner(){
		
		return players[winner];
	}
	
	/**
	 * Carries out the actual passing of the cards
	 * 
	 * Before a game of Hearts can start, each player must pass 3 cards in a given direction
	 * These cards care chosen by the Computer via a set algorithm or chosen by the Human player using the GUI
	 */
	public void passCards() {

		isPassing = false;
		
		// get the cards that are going to be passed
		ArrayList<Card> acrossPassingCards = acrossPlayer.getPassingCards();
		ArrayList<Card> leftPassingCards = leftPlayer.getPassingCards();
		ArrayList<Card> rightPassingCards = rightPlayer.getPassingCards();
		ArrayList<Card> humanPassingCards = humanPlayer.getPassingCards();
		
		// add the cards to the correct deck, based on the passing direction
		switch(passingDirection){
		case LEFT:
			leftPlayer.addToHand(humanPassingCards);
			acrossPlayer.addToHand(leftPassingCards);
			rightPlayer.addToHand(acrossPassingCards);
			humanPlayer.addToHand(rightPassingCards);
			break;
		case RIGHT:
			rightPlayer.addToHand(humanPassingCards);
			acrossPlayer.addToHand(rightPassingCards);
			leftPlayer.addToHand(acrossPassingCards);
			humanPlayer.addToHand(leftPassingCards);
			break;
		case ACROSS:
			acrossPlayer.addToHand(humanPassingCards);
			humanPlayer.addToHand(acrossPassingCards);
			leftPlayer.addToHand(rightPassingCards);
			rightPlayer.addToHand(leftPassingCards);
			break;
		default:
			break;		
		}
		
		// resort the deck, now with the new passed cards
		humanPlayer.sortHand();
		acrossPlayer.sortHand();
		leftPlayer.sortHand();
		rightPlayer.sortHand();
	}
	
	/**
	 * Determines whether a player can add a given card to the trick
	 * 
	 * Logic:
	 * 	can only add 2 of Spades on first turn of first trick
	 * 	can't add a Heart or Queen of Spades on first trick
	 * 	can't play Hearts on first turn until Hearts is broken (ie: Hearts is played on another trick)
	 * 	can't play a card of a different suit if the player has cards of the trick suit
	 * 
	 * @param card		the card the player wants to add
	 * @param player	the player who wants to add the card
	 * @return			true if the player can add the card, false if not
	 */
	public boolean canAdd(Card card, Player player){

		// if it is the first turn of a round, only the two of clubs can be played
		if((isFirstTurn && isFirstTrick) && !card.equals(new Card(Suit.CLUBS, CardValue.TWO))){
			
			return false;
		}
		
		// can't play hearts or queen of spades on first trick 
		if(isFirstTrick){
			
			if(card.getSuit() == Suit.HEARTS || card.equals(new Card(Suit.SPADES, CardValue.QUEEN))){
				
				return false;
			}
		}
		
		if(isFirstTurn){
			
			// can't play a hearts on the first turn until hearts has been broken
			if(card.getSuit() == Suit.HEARTS){
				
				// check if hearts is broken
				if(isHeartsBroken){
					
					return true;
				}
				else{
					
					return false;
				}
			}
			// otherwise, the player can play any card they want
			else{
				
				return true;
			}
		}
		
		// if the card is not of the trick suit, and the player has cards of the trick suit, the player cannot play that card
		if(card.getSuit() != trickSuit && player.hasSuit(trickSuit)){
			
			return false;
		}
		
		// got through all checks
		return true;
	}

	/**
	 * Determines if it is the end of the trick
	 * 
	 * Trick is over when all player have placed a card (ie: each player has made a turn)
	 * 
	 * @return	true if the trick is over, false if not
	 */
	public boolean isEndOfTrick() {
		
		// check everyone's turn status
		for(int i = 0; i < 4; i++){
			
			if(madeTurn[i] == false){
				
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Finishes a single trick
	 * 
	 * Determines the winner of the trick
	 * Gives the trick cards to the winner
	 * Determines the first player of the next trick, based on who won
	 * Clears cards in trick and resets some values
	 * 
	 * @return	the player who won the trick
	 */
	public Player endTrick() {

		// determine who won the trick
		Player player = getTrickWinner();
		
		// give them the cards they won
		player.addWonCards(trickCards);
		
		// determine the next first player
		if(player.getDirection() == HeartsDirections.HUMAN){
			
			currentTurn = HUMAN;
			firstPlayer = HUMAN;
		}
		else if(player.getDirection() == HeartsDirections.LEFT){
			
			currentTurn = LEFT;
			firstPlayer = LEFT;
		}
		else if(player.getDirection() == HeartsDirections.ACROSS){
			
			currentTurn = ACROSS;
			firstPlayer = ACROSS;
		}
		else{
			
			currentTurn = RIGHT;
			firstPlayer = RIGHT;
		}
		
		// no longer first trick
		if(isFirstTrick){
			
			isFirstTrick = false;
		}
		
		// after a trick ends, it goes back to being first turn of the round
		isFirstTurn = true;
		
		// clear the cards in the trick
		trickCards.clear();
		for(int i = 0; i < 4; i++){
			
			trickCards.add(null);
		}
		
		// reset everyone's turn status
		for(int i = 0; i < 4; i++){
			
			madeTurn[i] = false;
		}
		
		return player;
	}

	/**
	 * Finds who won the trick
	 * 
	 * The winner of the trick is the player who played the highest card of the trick suit
	 * 
	 * @return	the player who won the trick
	 */
	private Player getTrickWinner() {

		// the winner of the trick is the player who played the highest card of the trick suit
		Card highCard = trickCards.get(firstPlayer);
		for(Card card : trickCards){
			
			if(card.getSuit() == trickSuit && card.getValue().isGreaterThan(highCard.getValue(), false)){
				
				highCard = card;
			}
		}
		
		return players[trickCards.indexOf(highCard)];
	}
	
	/**
	 * Determines if the round is over
	 * 
	 * The round is over when all players are out of cards
	 * 
	 * @return	true if the round is over, false if not
	 */
	public boolean isRoundOver(){
		
		if(humanPlayer.getNumCardsInHand() == 0 && acrossPlayer.getNumCardsInHand() == 0 && leftPlayer.getNumCardsInHand() == 0 && rightPlayer.getNumCardsInHand() == 0){
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Finishes a single round of Hearts
	 * 
	 * Adds the points each player received to their total score
	 * Resets some values
	 * Sets up next round
	 */
	public void endRound(){
		
		// add points
		for(Player player : players){
			
			player.addPoints();
		}
		
		humanPlayer.resetPassingCards();
		leftPlayer.resetPassingCards();
		acrossPlayer.resetPassingCards();
		rightPlayer.resetPassingCards();
		
		switch(passingDirection){
		case LEFT:
			passingDirection = HeartsDirections.ACROSS;
			break;
		case ACROSS:
			passingDirection = HeartsDirections.RIGHT;
			break;
		case RIGHT:
			passingDirection = HeartsDirections.LEFT;
			break;
		default:
			break;
		}
		
		// set up next round
		setUpRound();
	}
	
	/**
	 * Determines if the game of Hearts has ended
	 * 
	 * A game of Hearts is ended when at least one player has surpassed a given score threshold
	 * 
	 * @return	true if the game has ended, false if not
	 */
	public boolean gameOver(){
			
		return humanPlayer.getScore() >= TOTAL_SCORE || leftPlayer.getScore() >= TOTAL_SCORE || acrossPlayer.getScore() >= TOTAL_SCORE || rightPlayer.getScore() >= TOTAL_SCORE;
	}
	
	/**
	 * Finds who has won the game of Hearts
	 * 
	 * The winner is the player who has the least amount of points when the game ends
	 */
	public void determineWinner(){
		
		// find which player has the least amount of points
		winner = HUMAN;
		for(int i = 0; i < 4; i++){
			
			if(players[i].getScore() < players[winner].getScore()){
				
				winner = i;
			}
		}
	}
}
