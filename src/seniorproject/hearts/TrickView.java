package seniorproject.hearts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;

/**
 * Controls the display and layout for the trick area in a game of Hearts
 * 
 * The TrickView takes up the entire middle of the screen and in addition to displaying the trick cards, also displays the passing message
 * The cards area is split into 3 rows: one for the across card, one for both sides, and one for the player's card
 * 
 * @author Andrew
 */
public class TrickView extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private String PASSING_MESSAGE = "Select 3 cards to pass";
	
	private JLabel passingMessage;
	private JPanel messageArea;
	private JButton passButton;
	private JPanel cardArea;
	private JPanel acrossCardRow;
	private JPanel sideCardRow;
	private JPanel humanCardRow;
	
	// for proper spacing
	private boolean leftAdded;
	private boolean rightAdded;
	private Component spacingBox;
	
	/**
	 * A constructor for a TrickView instance
	 * 
	 * @param view	the HeartsView object to which the TrickView will be added
	 * @return		a TrickView instance
	 */
	public TrickView(HeartsView view){
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setBackground(new Color(0, 0, 150));
		setOpaque(true);
		
		leftAdded = false;
		rightAdded = false;
		// this box is used for spacing in the side card row
		spacingBox = Box.createRigidArea(new Dimension(CardDimensions.HEARTS_CARD_HEIGHT + CardDimensions.HEARTS_CARD_WIDTH, 0));
			
		// init gui objects
		messageArea = new JPanel();
		passingMessage = new JLabel();
		passButton = new JButton();
		cardArea = new JPanel();
		acrossCardRow = new JPanel();
		sideCardRow = new JPanel();
		humanCardRow = new JPanel();
		
		// set up the message area
		messageArea.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		messageArea.setOpaque(false);
		
		// the initial message to be displayed is to tell the user to pass cards
		setUpPassingMessage(view);
		
		add(messageArea, BorderLayout.SOUTH);
		
		// set up area where cards will be kept
		cardArea.setLayout(new GridLayout(3, 0));
		cardArea.setOpaque(false);
		acrossCardRow.setOpaque(false);
		acrossCardRow.setLayout(new GridBagLayout());
		sideCardRow.setOpaque(false);
		sideCardRow.setLayout(new GridBagLayout());
		humanCardRow.setOpaque(false);
		humanCardRow.setLayout(new GridBagLayout());
		cardArea.add(acrossCardRow);
		cardArea.add(sideCardRow);
		cardArea.add(humanCardRow);
		add(cardArea, BorderLayout.CENTER);		
	}
	
	/**
	 * Sets up the passing message
	 * 
	 * Will show a short message and contains a button which the HeartsView objects listens to
	 * 
	 * @param view	the HeartsView object to which the TrickView will be added
	 */
	public void setUpPassingMessage(HeartsView view){
		
		passingMessage.setText(PASSING_MESSAGE);
		// increase size of font
		passingMessage.setFont(passingMessage.getFont().deriveFont((float) 25.0));
		passingMessage.setForeground(Color.PINK);
		messageArea.add(passingMessage);
		
		// set up pass button
		passButton.setText("Pass");
		passButton.addActionListener(view);
		passButton.setBackground(Color.YELLOW);
		// increase size of font
		passButton.setFont(passingMessage.getFont().deriveFont((float) 15.0));
		messageArea.add(passButton);
	}
	
	/**
	 * Hides the passing message for a round of play
	 */
	public void removePassMessage(){
		
		passingMessage.setVisible(false);
		passButton.setVisible(false);
	}
	
	/**
	 * Shows the passing message to begin the passing process
	 */
	public void showPassingMessage(){
		
		passingMessage.setVisible(true);
		passButton.setVisible(true);
	}
	
	/**
	 * Adds a single card to the trick area
	 * 
	 * Because both left's and right's cards are on the same row, certain attention must be paid to the spacing
	 * 
	 * @param card		the card to be added
	 * @param direction	the direction from which the card is added
	 */
	public void addCard(Card card, HeartsDirections direction){

		switch(direction){
		case LEFT:
		{
			RotatedCardView cardView = new RotatedCardView(card, CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT, direction);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			sideCardRow.add(cardView, gbc);
			
			// if the right card is already added, remove the spacing box it added
			if(rightAdded){
				
				sideCardRow.remove(spacingBox);
				gbc.gridx = 1;
				sideCardRow.add(Box.createRigidArea(new Dimension(CardDimensions.HEARTS_CARD_WIDTH, 0)), gbc);
			}
			else{
				
				// else, add some spacing of our own
				sideCardRow.add(spacingBox);
			}
			
			leftAdded = true;
			break;
		}
		case RIGHT:
		{
			// if the left card has not been added yet, we need to add a spacing box of the width of a card
			if(leftAdded){
				
				sideCardRow.remove(spacingBox);
				sideCardRow.add(Box.createRigidArea(new Dimension(CardDimensions.HEARTS_CARD_WIDTH, 0)));
			}
			if(!leftAdded){
				
				// else add some spacing of our own
				sideCardRow.add(spacingBox);
			}
			
			RotatedCardView cardView = new RotatedCardView(card, CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT, direction);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 2;
			sideCardRow.add(cardView, gbc);
			
			rightAdded = true;
			break;
		}
		case ACROSS:
		{
			RotatedCardView cardView = new RotatedCardView(card, CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT, direction);
			acrossCardRow.add(cardView, new GridBagConstraints());
			break;
		}
		// human area
		default:
		{
			CardView cardView = new CardView(card, CardDimensions.HEARTS_CARD_WIDTH, CardDimensions.HEARTS_CARD_HEIGHT);
			humanCardRow.add(cardView, new GridBagConstraints());
			break;
		}
		}
		
		revalidate();
		repaint();
	}
	
	/**
	 * Removes all of the cards from the trick area and refreshes the view
	 */
	public void removeCards(){
		
		sideCardRow.removeAll();
		acrossCardRow.removeAll();
		humanCardRow.removeAll();
		
		leftAdded = false;
		rightAdded = false;
		
		revalidate();
		repaint();
	}
}
