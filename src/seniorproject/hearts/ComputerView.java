package seniorproject.hearts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import seniorproject.utilities.CardDimensions;

/**
 * ComputerView instance controls and displays the underlying Computer instance
 * 
 * Extends {@link PlayerView PlayerView} class
 * 
 * @author Andrew
 */
public class ComputerView extends PlayerView{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for a ComputerView instance
	 * 
	 * @param playerModel	the underlying player model this ComputerView controls
	 * @param isVertical	whether or not the ComputerView will be displayed vertically (ie: LEFT or RIGHT position)
	 * @return				a ComputerView constructor
	 */
	public ComputerView(Player playerModel, boolean isVertical) {
		
		super(playerModel);

		this.isVertical = isVertical;
		
		// different layout manager for a vertical layout
		if(isVertical){
			
			setLayout(new GridBagLayout());
		}
	}

	/**
	 * @see seniorproject.hearts.PlayerView#addCards(seniorproject.hearts.HeartsView)
	 */
	@Override
	public void addCards(HeartsView view) {

		if(isVertical){
			
			for(int i = 0; i < playerModel.getNumCardsInHand(); i++){
				
				// setup card
				JLabel cardView = new JLabel();
				cardView.setPreferredSize(new Dimension(CardDimensions.HEARTS_CARD_HEIGHT, CardDimensions.HEARTS_CARD_WIDTH));
				cardView.setMinimumSize(new Dimension(CardDimensions.HEARTS_CARD_HEIGHT, CardDimensions.HEARTS_CARD_WIDTH));
				cardView.setBackground(Color.BLACK);
				cardView.setOpaque(true);
				cardView.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				
				// position card
				cardView.setBounds(0, offset, CardDimensions.HEARTS_CARD_HEIGHT, CardDimensions.HEARTS_CARD_HEIGHT);
				offset += CardDimensions.HEARTS_OFFSET;
				
				cardsArea.add(cardView, 0);
			}
		}
		else{
			
			for(int i = 0; i < playerModel.getNumCardsInHand(); i++){

				// setup card
				JLabel cardView = new JLabel();
				cardView.setPreferredSize(new Dimension(CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT));
				cardView.setMinimumSize(new Dimension(CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT));
				cardView.setBackground(Color.BLACK);
				cardView.setOpaque(true);
				cardView.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				
				// position card
				cardView.setBounds(offset, 0, CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT);
				offset += CardDimensions.HEARTS_OFFSET;
				
				cardsArea.add(cardView, 0);
			}
		}
		
		// setup cardsArea
		cardsArea.setPreferredSize(getCardsAreaSize());
		cardsArea.setOpaque(false);
		add(cardsArea, new GridBagConstraints());
	}
}
