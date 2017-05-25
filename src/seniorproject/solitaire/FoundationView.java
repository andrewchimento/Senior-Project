package seniorproject.solitaire;

import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;

/**
 * FoundationView instance controls and displays a Foundation instance in a game of Solitaire
 * 
 * Extends {@link CardStackView CardStackView} class
 * 
 * @author Andrew
 */
public class FoundationView extends CardStackView {

	private static final long serialVersionUID = 1L;

	/**
	 * A constructor for a FoundationView instance
	 * 
	 * @param foundationModel the underlying FoundationModel
	 * @return	a FoundationView instance
	 */
	public FoundationView(Foundation foundationModel){
		
		super(foundationModel);
	}

	/**
	 * @see seniorproject.solitaire.CardStackView#placeCard(seniorproject.solitaire.SolitaireView, seniorproject.utilities.CardView, int)
	 */
	@Override
	protected void placeCard(CardView cardView, SolitaireView view, int spacing){

		cardView.setBounds(getX() + spacing, getY() + CardDimensions.BORDER_AMOUNT, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
		view.add(cardView, 0);
	}
}
