package seniorproject.hearts;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import seniorproject.utilities.CardDimensions;

/**
 * Controls and displays the a single player's layout in a game of Hearts
 * 
 * This base class is extended by the {@link ComputerView ComputerView} and {@link HumanView HumanView} classes
 * 
 * @author Andrew
 */
public abstract class PlayerView extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Player playerModel;
	protected int offset = 0;
	protected final int CARD_SPACING = 5;
	protected JLayeredPane cardsArea;
	protected boolean isVertical;
	
	/**
	 * Constructor for a PlayerView object
	 * 
	 * @param playerModel	the underlying Player object
	 * @return				a PlayerView instance
	 */
	public PlayerView(Player playerModel){
		
		this.playerModel = playerModel;
		cardsArea = new JLayeredPane();
		
		setLayout(new FlowLayout());

		// inner spacing
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(false);
	}
	
	public Player getPlayerModel(){
		
		return playerModel;
	}
	
	/**
	 * Calculates the amount of space the player's deck of cards takes up
	 * 
	 * This is important because certain components will overlap and get cut off if the space is wrong
	 * 
	 * @return	the amount of space the player's deck of cards takes up
	 */
	public Dimension getCardsAreaSize(){
		
		int width = CardDimensions.HEARTS_CARD_WIDTH;
		int height = CardDimensions.HEARTS_CARD_HEIGHT;
		
		if(playerModel.getNumCardsInHand() > 1){
			
			// initial card sets width to 1 * card width
			width = CardDimensions.HEARTS_CARD_WIDTH;
						
			// every other card in the deck then adds 0.5 * card width after that
			width += (playerModel.getNumCardsInHand() - 1) * ((CardDimensions.HEARTS_CARD_WIDTH) / 2); 
		}
		
		if(isVertical){
			
			return new Dimension(height, width);
		}
		else{
			
			return new Dimension(width, height);
		}
	}
	
	/**
	 * Refreshes the PlayerView display
	 * 
	 * This is called whenever the cards in front of the player must be updated
	 * Removes all cards then adds them all back
	 * 
	 * @param view	the HeartsView object to which the PlayerView belongs
	 */
	public void refreshCards(HeartsView view){
		
		cardsArea.removeAll();
		offset = 0;
		addCards(view);
		cardsArea.revalidate();
		cardsArea.repaint();
	}
	
	/**
	 * Adds the player's deck to the display
	 * 
	 * This is different in each child class because the player sees all of their own cards, but does not see any of the computers' cards
	 * 
	 * @param view	the HeartsView object to which the PlayerView belongs
	 */
	abstract public void addCards(HeartsView view);
}
