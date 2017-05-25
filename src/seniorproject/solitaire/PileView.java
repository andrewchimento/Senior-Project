package seniorproject.solitaire;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;

/**
 * PileView instance controls and displays a Pile instance in a game of Solitaire
 * 
 * Extends {@link CardStackView CardStackView} class
 * 
 * @author Andrew
 */
public class PileView extends CardStackView {
	
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
	private JLabel downCard;
	private int offset;
	
	/**
	 * A constructor for a PileView instance
	 * 
	 * @param pileModel the underlying PileModel this instance will control
	 * @return	a PileView instance
	 */
	public PileView(Pile pileModel){
		
		super(pileModel);
		
		offset = 0;
		
		layeredPane = new JLayeredPane();
		add(layeredPane);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
	}
	
	/**
	 * @see Pile#hasDownCards()
	 */
	public boolean hasDownCards(){
		
		return ((Pile)stackModel).hasDownCards();
	}
	
	/**
	 * @see Pile#hasUpCards()
	 */
	public boolean hasUpCards(){
		
		return ((Pile)stackModel).hasUpCards();
	}
	
	/**
	 * Determines whether or not a card has other cards underneath it
	 * 
	 * A card is considered "underneath" another card when it is lower in the stack
	 * 
	 * @param cardToTest	this is the card we are looking underneath for other cards
	 * @return				true if the card has other cards underneath it, false if not
	 */
	public boolean hasUnderCards(CardView cardToTest){
		
		// gather up the face up cards
		ArrayList<CardView> faceUpCards = new ArrayList<CardView>();
		for(CardView cardView : cards){
			
			if(cardView.getCard().isFaceUp()){
				
				faceUpCards.add(cardView);
			}
		}
		
		// if the card has cards under it, it won't be the last card
		return faceUpCards.indexOf(cardToTest) != cards.size() - 1;
	}
	
	/**
	 * Gets all of the cards underneath a certain card in a pile
	 * 
	 * A card is considered "underneath" another card when it is lower in the stack
	 * 
	 * @param fromCard	the first card to look under
	 * @return			the list of cards that are underneath the given card
	 */
	public ArrayList<CardView> getUnderCards(CardView fromCard){
		
		ArrayList<CardView> underCards = new ArrayList<CardView>();
		for(int i = cards.indexOf(fromCard) + 1; i < cards.size(); i++){
			
			underCards.add(cards.get(i));
		}
		
		return underCards;
	}
	
	/**
	 * Adds a list of cards to the PileView, all at one time
	 * 
	 * Used when a group of cards are moved from one pile to another, all at once
	 * 
	 * @param otherCardsView	the list of cards to be added to the view
	 * @param view				the SolitaireView this PileView instance is a part of
	 */
	public void addOtherCards(ArrayList<CardView> otherCardsView, SolitaireView view){
		
		ArrayList<Card> otherCards = new ArrayList<Card>();
		for(CardView cardView : otherCardsView){
			
			cards.push(cardView);
			otherCards.add(cardView.getCard());
			
			cardView.setBounds(getX(), getY() + CardDimensions.SOLITAIRE_CARD_HEIGHT + offset + CardDimensions.BORDER_AMOUNT, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
			view.add(cardView, 0);
			cardView.getCard().setIsFaceUp(true);
			
			// bump up offset
			offset += CardDimensions.SOLITAIRE_OFFSET;
		}
		
		((Pile)stackModel).addCards(otherCards);
	}
	
	/**
	 * @see Pile#removeCards(int)
	 */
	public void removeCards(int numCards){

		((Pile)stackModel).removeCards(numCards);
		for(int i = 0; i < numCards; i++){
			
			cards.pop();
			
			// correct offset for positioning
			if(offset > 0){
				
				offset -= CardDimensions.SOLITAIRE_OFFSET;
			}
		}
	}
	
	/**
	 * Displays the initial set of cards for the PileView
	 * 
	 * @param view	the SolitaireView this PileView instance is a part of
	 */
	public void showInitCards(SolitaireView view){
		
		int pileNum = ((Pile)stackModel).getPileNum();
		
		// pile one doesn't have any face-down cards
		if(pileNum != 1){
			
			// setup cardView
			downCard = new JLabel();
			downCard.setBounds(0, offset, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
			offset += CardDimensions.SOLITAIRE_OFFSET;
			downCard.setBackground(Color.BLACK);
			downCard.setOpaque(true);
			
			layeredPane.add(downCard, 0);
		}
		
		showTopCard(view);
	}
	
	/**
	 * Displays the card on the top of the pile
	 * 
	 * The top card of a pile is the card on the top of the stack
	 * 
	 * @param view	the SolitaireView this PileView instance is a part of
	 */
	public void showTopCard(SolitaireView view){
		
		Card card = stackModel.getTopCard();
		// tell model it's been flipped
		card.setIsFaceUp(true);
		
		// setup cardView
		CardView cardView = new CardView(card, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
		cardView.drawOnLayeredPane(view, getX(), getY() + CardDimensions.SOLITAIRE_CARD_HEIGHT + offset + CardDimensions.BORDER_AMOUNT);
		cardView.addMouseMotionListener(view);
		cardView.addMouseListener(view);
		
		cards.add(cardView);
		
		// bump up offset
		offset += CardDimensions.SOLITAIRE_OFFSET;
	}
	
	/**
	 * Flips over the last face-down card in the pile
	 * 
	 * The last card must be positioned so it is not indented vertically
	 * 
	 * @param view	the SolitaireView this PileView instance is a part of
	 */
	public void flipFinalDownCard(SolitaireView view){

		layeredPane.remove(downCard);
		offset = 0;
		
		// move the newly flipped card back up, because the offset is messed up
		cards.lastElement().setBounds(getX(), getY() + CardDimensions.SOLITAIRE_CARD_HEIGHT + offset + CardDimensions.BORDER_AMOUNT, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
		offset += CardDimensions.SOLITAIRE_OFFSET;
	}
	
	/**
	 * @see seniorproject.solitaire.CardStackView#placeCard(seniorproject.solitaire.SolitaireView, seniorproject.utilities.CardView, int)
	 */
	@Override
	protected void placeCard(CardView cardView, SolitaireView view, int spacing){
		
		// position the new top card in the foreground
		cardView.setBounds(getX(), getY() + spacing + offset, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
		view.add(cardView, 0);
		cardView.getCard().setIsFaceUp(true);
		offset += CardDimensions.SOLITAIRE_OFFSET;
	}
}
