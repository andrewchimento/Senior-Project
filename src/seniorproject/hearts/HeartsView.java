package seniorproject.hearts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;
import seniorproject.utilities.Suit;

/**
 * Controls the display and layout for a game of Hearts
 * 
 * Contains a reference to exactly one HeartsModel instance
 * Implements a MouseListener and ActionListener to respond to mouse and component events
 * 
 * @author Andrew
 */
public class HeartsView extends JPanel implements MouseListener, ActionListener{

	private static final long serialVersionUID = 1L;
	
	private HeartsModel heartsModel;
	
	private HumanView humanView;
	private ComputerView acrossComputerView;
	private ComputerView leftComputerView;
	private ComputerView rightComputerView;
	private PlayerView[] playerViews;
	private TrickView trickView;
	private JPanel middleArea;
	private HeartsScores heartsScores;
	
	/**
	 * A constructor for a HeartsView object
	 * 
	 * @param heartsModel	the underlying HertsModel object
	 * @return				a HeartsView instance
	 */
	public HeartsView(HeartsModel heartsModel){

		this.heartsModel = heartsModel;
		
		setLayout(new BorderLayout());
		
		// init gui objects
		humanView = new HumanView(heartsModel.getHumanPlayer());
		acrossComputerView = new ComputerView(heartsModel.getAcrossPlayer(), false);
		leftComputerView = new ComputerView(heartsModel.getLeftPlayer(), true);
		rightComputerView = new ComputerView(heartsModel.getRightPlayer(), true);
		playerViews = new PlayerView[4];
		playerViews[0] = humanView;
		playerViews[1] = acrossComputerView;
		playerViews[2] = leftComputerView;
		playerViews[3] = rightComputerView;
		trickView = new TrickView(this);
		middleArea = new JPanel();
		heartsScores = new HeartsScores();
		
		// set up gui
		setPreferredSize(new Dimension(new Double(Toolkit.getDefaultToolkit().getScreenSize().width * CardDimensions.SCREEN_RATIO).intValue(), new Double(Toolkit.getDefaultToolkit().getScreenSize().height * CardDimensions.SCREEN_RATIO).intValue()));
		setBackground(new Color(0, 150, 0));
		setOpaque(true);
		
		// set up middle area
		// user and across areas
		humanView.addCards(this);
		acrossComputerView.addCards(this);
		
		middleArea.setLayout(new BorderLayout());
		middleArea.add(acrossComputerView, BorderLayout.NORTH);
		middleArea.add(trickView, BorderLayout.CENTER);
		middleArea.add(humanView, BorderLayout.SOUTH);
		middleArea.setOpaque(false);
		
		add(middleArea, BorderLayout.CENTER);

		// set up side areas
		leftComputerView.addCards(this);
		rightComputerView.addCards(this);
		
		add(leftComputerView, BorderLayout.WEST);
		add(rightComputerView, BorderLayout.EAST);
	}
	
	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
		JComponent source = (JComponent) e.getSource();
		if(source instanceof CardView){
			
			CardView cardView = (CardView) source;
			Card card = cardView.getCard();
			
			// passing setup
			if(heartsModel.isPassing()){
				
				Human human = heartsModel.getHumanPlayer();
				
				// toggles passing card selection
				if(human.hasPassingCard(card)){
					
					human.removePassingCard(card);
					cardView.setBorder(null);
				}
				else{
					
					if(human.getNumPassingCards() >= 3){
						
						JOptionPane.showMessageDialog(this, "You can only select 3 cards to pass");
						return;
					}
					else{
						
						human.addPassingCard(card);
						cardView.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					}
				}
				
				return;
			}
			
			if(heartsModel.isHumanTurn()){
				
				if(heartsModel.canAdd(card, heartsModel.getHumanPlayer())){
					
					addCard(card, HeartsDirections.HUMAN);
					heartsModel.getHumanPlayer().removeCard(card);
					humanView.refreshCards(this);
				}
				else{
					
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "You cannot play the " + card.toString());
					return;
				}
				
				heartsModel.nextTurn();
				
				// check to see if the trick is ended
				if(heartsModel.isEndOfTrick()){
					
					endTrick();
					
					if(heartsModel.isHumanTurn()){
						
						return;
					}
				}
				else{
					
					// if it was the human turn after the end of the trick, we would have returned
					computerTurn();
				}				
			}
		}
	}
	
	/**
	 * Adds a card to the trick
	 * 
	 * @param card		the card to add to the trick
	 * @param direction	the direction from which the card is being added
	 */
	private void addCard(Card card, HeartsDirections direction){
		
		if(heartsModel.isFirstTurn()){
			
			heartsModel.setIsFirstTurn(false);
			heartsModel.setTrickSuit(card.getSuit());
			heartsModel.setFirstPlayer(direction);
		}
		else{
			
			// hearts is broken when the first hearts card is played in a trick, as long as it ins't the first card
			if(card.getSuit() == Suit.HEARTS && !heartsModel.isHeartsBroken()){
				
				heartsModel.setIsHeartsBroken(true);
			}
		}
		
		// add to view
		trickView.addCard(card, direction);
		// add to model
		heartsModel.addTrickCard(card, direction);
	}
	
	/**
	 * Runs a single computer's turn
	 * 
	 * Has the computer run the logic for their card choice and then plays the card
	 */
	private void computerTurn(){
		
		// coming into this function, we assume that the player going is a computer, so we can cast that way 
		// that is, the function that calls this must check that the current turn is a computer turn
		Computer computer = (Computer) heartsModel.getCurrentTurnPlayer();
		
		// get the card that the computer wants to place
		Card card = computer.chooseCard(heartsModel);
		
		// get direction that card coming from
		HeartsDirections direction = computer.getDirection();
		
		// add card to the view at the proper position (based on direction)
		// the card should already be removed from the computer model
		addCard(card, direction);
		
		// remove a card from the computer's view
		getPlayerView(computer).refreshCards(this);
		
		// display the move
		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), direction + " player played " + card.toString());
		
		// go to next turn
		heartsModel.nextTurn();
		
		// check to see if the trick is ended
		if(heartsModel.isEndOfTrick()){
			
			endTrick();
		}
		else{
			
			// call this function again if it isn't the human's turn
			if(!heartsModel.isHumanTurn()){
				
				computerTurn();
			}
		}
	}

	/**
	 * Determines the PlayerView object associated with a Player object
	 * 
	 * This is used because when the computer makes their turn, we only know the model, not the view
	 * 
	 * @param player	the Player object whose PlayerView we desire
	 * @return			the PlayerView object whose Player model corresponds to the given one
	 */
	private PlayerView getPlayerView(Player player){
		
		if(player.getDirection() == HeartsDirections.HUMAN){
			
			return humanView;
		}
		else if(player.getDirection() == HeartsDirections.LEFT){
			
			return leftComputerView;
		}
		else if(player.getDirection() == HeartsDirections.ACROSS){
			
			return acrossComputerView;
		}
		else{
			
			return rightComputerView;
		}
	}
	
	/**
	 * Ends a single trick
	 * 
	 * The trick is over when each player has played a single card
	 * Gives the trick cards to the winner
	 * Checks for the end of the round
	 */
	private void endTrick(){
		
		Player player = heartsModel.endTrick();
		JOptionPane.showMessageDialog(this, player.getDirection() + " won that trick");
		trickView.removeCards();
		
		// check for end of round
		if(heartsModel.isRoundOver()){
			
			endRound();
			return;
		}
		
		if(heartsModel.isHumanTurn()){
			
			return;
		}
		else{
			
			computerTurn();	
		}
	}
	
	/**
	 * Ends a single round of Hearts
	 * 
	 * The round is over when each player has played all of their cards
	 * Updates and displays the cards
	 * Checks for the end of the game and ends it if necessary
	 * Refreshes the display for another round
	 */
	private void endRound(){
		
		heartsModel.endRound();
		
		heartsScores.updateScores(heartsModel);
		JOptionPane.showMessageDialog(this, heartsScores);
		
		if(heartsModel.gameOver()){
			
			heartsModel.determineWinner();
			
			// show the end game view for Hearts
			JFrame endGameFrame = new HeartsEndGameView(heartsModel.isHumanWin(), (JFrame) SwingUtilities.getWindowAncestor(this), heartsScores);
			endGameFrame.pack();
			endGameFrame.setVisible(true);
			// center window on screen
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			endGameFrame.setLocation(dim.width/2-endGameFrame.getSize().width/2, dim.height/2-endGameFrame.getSize().height/2);
		}
		else{
			
			for(PlayerView playerView : playerViews){
				
				playerView.refreshCards(this);
			}
			
			trickView.showPassingMessage();
		}
	}
	
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		// waits until the user has selected 3 cards
		if(heartsModel.getHumanPlayer().getNumPassingCards() == 3){
			
			heartsModel.passCards();
		}
		else{
			
			return;
		}
		
		humanView.refreshCards(this);
		trickView.removePassMessage();
		
		heartsModel.determineFirstPlayer();
		
		// as long as it's not the user's turn, the computer can start
		if(!heartsModel.isHumanTurn()){

			computerTurn();
		}
		else{
			
			JOptionPane.showMessageDialog(this, "It is your turn");
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
