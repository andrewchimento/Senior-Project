package seniorproject.solitaire;

import java.util.Stack;

import javax.swing.JLabel;

import seniorproject.utilities.CardView;

/**
 * This is the base class for any CardStackView model
 * 
 * Controls and displays a CardStack model
 * Extended by {@link FoundationView FoundationView} and {@link PileView PileView} classes
 * 
 * @author Andrew
 */
public abstract class CardStackView extends JLabel {

	private static final long serialVersionUID = 1L;
	protected CardStack stackModel;
	protected Stack<CardView> cards;
	
	/**
	 * Constructor for a CardStackView object
	 * 
	 * @param stackModel	the underlying CardStack model that this instance will display and control
	 * @return				a CardStackView object which controls and displays a CardStack
	 */
	public CardStackView(CardStack stackModel){
		
		this.stackModel = stackModel;
		cards = new Stack<CardView>();
	}
	
	public int getNumCards(){
		
		return stackModel.getNumCards();
	}
	
	public CardView getCardView(int index){
		
		return cards.get(index);
	}
	
	/**
	 * Determines whether or not the CardStack contains a certain card
	 * 
	 * @param cardView	the card to look for
	 * @return			true if the CardStack has the card, false if not
	 */
	public boolean hasCard(CardView cardView){
		
		return cards.contains(cardView);
	}
	
	/**
	 * Finds the index of the given card in the 
	 * 
	 * This simply returns what the corresponding CardStack model function returns
	 * 
	 * @param cardView	the card to look for
	 * @return			the index of the card, -1 if not found
	 */
	public int findCard(CardView cardView){
		
		return stackModel.findCard(cardView.getCard());
	}
	
	/**
	 * Adds a card to the CardStackView
	 * 
	 * This method queries the CardStack model to determine if the cad can be added
	 * If the card can be added, it adds the card to the SolitaireView display
	 * 
	 * @param card		the card to be added
	 * @param view		the SolitaireView to which the card will be added
	 * @param spacing	how much space to put between this card and the origin of the CardStackView
	 * @return			true if the card was added, false if not
	 */
	public boolean addCard(CardView card, SolitaireView view, int spacing){
		
		if(stackModel.addCard(card.getCard())){
			
			// actually put the card on the screen
			placeCard(card, view, spacing);
			
			// add the new card to the view stack 
			cards.push(card);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Adds a card to the graphical display of the CardStackView
	 * 
	 * The CardView cardView is actually placed above the SolitiareView view
	 * This is so that the card can be dragged around without disappearing behind the view
	 * 
	 * @param view		the SolitaireView to which the card will be added
	 * @param cardView	the graphical representation of the card to be added
	 * @param spacing	how much space to put between this card and the origin of the CardStackView
	 */
	abstract protected void placeCard(CardView cardView, SolitaireView view, int spacing);
}
