package seniorproject.hearts;

import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;

/**
 * Runs the display and layout for the player's card area
 * 
 * Extends {@link PlayerView PlayerView} class
 * 
 * @author Andrew
 */
public class HumanView extends PlayerView{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param playerModel
	 */
	public HumanView(Player playerModel) {
		
		super(playerModel);
		
		this.isVertical = false;
	}
	
	/**
	 * @see seniorproject.hearts.PlayerView#addCards(seniorproject.hearts.HeartsView)
	 */
	@Override
	public void addCards(HeartsView view){
		
		for(int i = 0; i < playerModel.getNumCardsInHand(); i++){
			
			CardView cardView = new CardView(playerModel.getCard(i), CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT);
			cardView.drawOnLayeredPane(cardsArea, offset, 0);
			cardView.addMouseListener(view);
			offset += CardDimensions.HEARTS_OFFSET;
		}
		
		cardsArea.setPreferredSize(getCardsAreaSize());
		cardsArea.setOpaque(false);
		add(cardsArea);
	}
}
