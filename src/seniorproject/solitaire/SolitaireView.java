package seniorproject.solitaire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardDimensions;
import seniorproject.utilities.CardView;

/**
 * GUI portion of the Solitaire module with which the user interacts
 * 
 * @author Andrew
 */
public class SolitaireView extends JLayeredPane implements MouseMotionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	private final String DECK_NAME = "Deck";
	private final int NUM_PILES = 7;
	
	private SolitaireModel solitaireModel;
	
	// gui objects
	private JPanel background;
	private JPanel topArea;
	private JPanel pileArea;
	private PileView pileOne;
	private PileView pileTwo;
	private PileView pileThree;
	private PileView pileFour;
	private PileView pileFive;
	private PileView pileSix;
	private PileView pileSeven;
	private ArrayList<PileView> pileViews;
	private JPanel foundationArea;
	private FoundationView foundationOne;
	private FoundationView foundationTwo;
	private FoundationView foundationThree;
	private FoundationView foundationFour;
	private ArrayList<FoundationView> foundationViews;
	private JPanel deckArea;
	private JLabel deck;
	private JLabel drawSpot;
	private ArrayList<CardView> drawSpotCards;
	
	// for card movement
	private int cardStartPosX = 0;
	private int cardStartPosY = 0;
	private CardView movingCard;
	private ArrayList<CardView> otherCards;
	private ArrayList<Point> pilePositions;
	private ArrayList<Point> foundationPositions;
	private boolean movingFromDrawSpot;
	private int movingFromPile = 0;
	
	// stat tracking
	private SolitaireStats stats;
	private boolean madeFirstMove = false;
	private boolean timerNotStarted = true;
	private Timer timer;
	
	/**
	 * Constructor for a SolitaireView display
	 * 
	 * Responsible for creation of entire Solitaire GUI
	 * Creates piles, foundations, deck, draw spot areas
	 * 
	 * @param solitaireModel	the underlying SolitaireModel object
	 * @return					a SolitaireView instance
	 */
	public SolitaireView(SolitaireModel solitaireModel){
		
		this.solitaireModel = solitaireModel;
		stats = new SolitaireStats();
		
		// initialize gui objects
		pileOne = new PileView(solitaireModel.getPile(1));
		pileTwo = new PileView(solitaireModel.getPile(2));
		pileThree = new PileView(solitaireModel.getPile(3));
		pileFour = new PileView(solitaireModel.getPile(4));
		pileFive = new PileView(solitaireModel.getPile(5));
		pileSix = new PileView(solitaireModel.getPile(6));
		pileSeven = new PileView(solitaireModel.getPile(7));
		pileViews = new ArrayList<PileView>();
		pilePositions = new ArrayList<Point>();
		foundationOne = new FoundationView(solitaireModel.getFoundation(1));
		foundationTwo = new FoundationView(solitaireModel.getFoundation(2));
		foundationThree = new FoundationView(solitaireModel.getFoundation(3));
		foundationFour = new FoundationView(solitaireModel.getFoundation(4));
		foundationViews = new ArrayList<FoundationView>();
		foundationPositions = new ArrayList<Point>();
		drawSpotCards = new ArrayList<CardView>();
		otherCards = new ArrayList<CardView>();
		
		// set up gui
		// this layer is responsible for holding everything that is not moving, aka the background
		background = new JPanel(new BorderLayout());
		// set up playing area
		setPreferredSize(new Dimension(new Double(Toolkit.getDefaultToolkit().getScreenSize().width * CardDimensions.SCREEN_RATIO).intValue(), new Double(Toolkit.getDefaultToolkit().getScreenSize().height * CardDimensions.SCREEN_RATIO).intValue()));
		background.setBackground(new Color(0, 150, 0));
		background.setOpaque(true);
		background.setBorder(BorderFactory.createEmptyBorder(CardDimensions.BORDER_AMOUNT, 0, 0, 0));
		background.addMouseListener(this);
		background.setName("background");
		
		// make the component areas
		// top area is a wrapper for the deck/draw spot area and foundations
		topArea = new JPanel(new BorderLayout());
		
		// deck area is another wrapper for positioning
		deckArea = makeDeckArea();
		deckArea.setOpaque(false);
		topArea.add(deckArea, BorderLayout.WEST);
		
		// setup the deck
		deck.setBackground(Color.black);
		deck.setOpaque(true);
		deck.addMouseListener(this);
		deck.setName(DECK_NAME);
		
		// setup foundation area
		foundationArea = makeFoundationArea();
		foundationArea.setOpaque(false);
		topArea.add(foundationArea, BorderLayout.EAST);
		
		topArea.setPreferredSize(new Dimension(getPreferredSize().width, new Double(CardDimensions.SOLITAIRE_CARD_HEIGHT).intValue()));
		topArea.setBackground(Color.WHITE);
		topArea.setOpaque(false);
		background.add(topArea, BorderLayout.NORTH);
		
		// pile area is on the bottom
		pileArea = makePileArea();
		pileArea.setOpaque(false);
		background.add(pileArea, BorderLayout.CENTER);
		
		background.setBounds(0, 0, new Double(Toolkit.getDefaultToolkit().getScreenSize().width * CardDimensions.SCREEN_RATIO).intValue(), new Double(Toolkit.getDefaultToolkit().getScreenSize().height * CardDimensions.SCREEN_RATIO).intValue());
		add(background, 0);
	}
	
	/**
	 * Creates the dispay where the deck and draw spot are helt
	 * 
	 * @return a JPanel which holds the display where the deck and draw spot are contained
	 */
	private JPanel makeDeckArea(){
		
		// define the underlying FlowLayout
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(20);
		flowLayout.setVgap(0);
		JPanel deckArea = new JPanel(flowLayout);
		
		// deck needs the cards of the deck and the cards that have been drawn
		deck = new JLabel();
		deck.setPreferredSize(new Dimension(CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT));
		deck.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		deckArea.add(deck);
		
		drawSpot = new JLabel();
		drawSpot.setPreferredSize(new Dimension(CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT));
		drawSpot.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		deckArea.add(drawSpot);
		
		return deckArea;
	}
	
	/**
	 * Creates the display where the FoundationViews will be
	 * 
	 * This uses a custom FlowLayout
	 * All foundations are set up in the same default manner
	 * 
	 * @return a JPanel which holds the display of the FoundationViews
	 */
	private JPanel makeFoundationArea(){
		
		// define the underlying FlowLayout
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(CardDimensions.SOLITAIRE_CARD_WIDTH / 3);
		flowLayout.setVgap(0);
		JPanel foundationArea = new JPanel(flowLayout);		
		
		// setup all foundations
		FoundationView[] foundations = {foundationOne, foundationTwo, foundationThree, foundationFour};
		for(FoundationView foundation : foundations){
			
			foundation.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			foundation.setOpaque(false);
			foundation.setPreferredSize(new Dimension(CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT));
			foundationViews.add(foundation);
			foundationArea.add(foundation);
		}
		
		return foundationArea;
	}
	
	/**
	 * Creates the display where the PileViews will be
	 * 
	 * This uses a custom FlowLayout
	 * All piles are set up in the same default manner
	 * 
	 * @return a JPanel which holds the display of the PileViews
	 */
	private JPanel makePileArea(){
		
		// define the underlying FlowLayout
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(CardDimensions.SOLITAIRE_CARD_WIDTH / 2);
		flowLayout.setVgap(40);
		JPanel pileArea = new JPanel(flowLayout);		
		
		// setup all piles
		PileView[] piles = {pileOne, pileTwo, pileThree, pileFour, pileFive, pileSix, pileSeven};
		for(PileView pile : piles){

			pile.setPreferredSize(new Dimension(CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT * 3));
			pile.setOpaque(false);
			pileViews.add(pile);
			pileArea.add(pile);
		}
				
		return pileArea;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(!SwingUtilities.isLeftMouseButton(e)){
			
			return;
		}
		
		JLabel card = (JLabel)e.getSource();
		moveToFront(card);
		card.setLocation(card.getX() + e.getX() - (CardDimensions.SOLITAIRE_CARD_WIDTH / 2), card.getY() + e.getY() - (CardDimensions.SOLITAIRE_CARD_HEIGHT / 2));
		
		// if this is a multi-card move, need to move the rest of them
		if(!otherCards.isEmpty()){
			
			for(CardView cardView : otherCards){
				
				moveToFront(cardView);
				cardView.setLocation(cardView.getX() + e.getX() - (CardDimensions.SOLITAIRE_CARD_WIDTH / 2), cardView.getY() + e.getY() - (CardDimensions.SOLITAIRE_CARD_HEIGHT / 2));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(!SwingUtilities.isLeftMouseButton(e)){
			
			return;
		}
		
		JComponent source = (JComponent)e.getSource();
		
		// drawing a card from the deck
		if(source.getName() == DECK_NAME){

			// drawing last card
			// re-color the deck to indicate no cards left
			if(solitaireModel.getNumCardsInDeck() == 1){

				deck.setOpaque(false);
				revalidate();
				repaint();
			}
			// recycle the drawn cards back to the deck
			else if(solitaireModel.getNumCardsInDeck() == 0){
				
				// unless they're are no more cards left
				if(solitaireModel.getNumCardsInDeck() <= 0 && solitaireModel.getNumCardsInDrawSpot() <= 0){
					
					return;
				}
				
				solitaireModel.recycleDrawnCards();
				deck.setOpaque(true);
				revalidate();
				repaint();
					
				// remove the old cards from the view
				while(!drawSpotCards.isEmpty()){

					remove(drawSpotCards.get(drawSpotCards.size() - 1));
					drawSpotCards.remove(drawSpotCards.size() - 1);
				}
			}
				
			drawCardFromDeck();
			
			stats.setNumMoves(stats.getNumMoves() + 1);
			
			return;
		}
		
		// check to see if the player clicked over a pile to flip a card
		int pileNum = checkPileClick(e);
		if(pileNum > 0){
			
			PileView pileView = pileViews.get(pileNum - 1);

			pileView.showTopCard(this);
			if(!pileView.hasDownCards()){

				pileView.flipFinalDownCard(this);
			}
			
			stats.setNumMoves(stats.getNumMoves() + 1);
		}
	}
	
	/**
	 * Draws a card from the deck and adds it to the top of the draw spot
	 */
	private void drawCardFromDeck(){
		
		// add the card to the draw spot
		Card card = solitaireModel.drawFromDeck();
		CardView cardView = new CardView(card, CardDimensions.SOLITAIRE_CARD_WIDTH, CardDimensions.SOLITAIRE_CARD_HEIGHT);
		cardView.drawOnLayeredPane(this, drawSpot.getX(), drawSpot.getY() + CardDimensions.BORDER_AMOUNT);
		cardView.addMouseMotionListener(this);
		cardView.addMouseListener(this);
		drawSpotCards.add(cardView);
	}
	
	/**
	 * Determines if the player clicked over a PileView
	 * 
	 * This is called when the player clicks on a pile to flip over the top card
	 * 
	 * @param e	the MouseEvent that was the pile being clicked
	 * @return	the pile number that was clicked, 0 if no pile was clicked
	 */
	private int checkPileClick(MouseEvent e){
		
		// get location of the mouse event
		int x = e.getX();
		int y = e.getY();
		
		// check if the click took place over a PileView
		int pileNum = 1;
		for(Point pilePosition : pilePositions){
			
			// get the coordinates of the PileView, with some added fudge space
			int left = (int)pilePosition.getX();
			int right = (int)pilePosition.getX() + CardDimensions.SOLITAIRE_CARD_WIDTH;
			int top = (int)pilePosition.getY();
			int bottom = (int)pilePosition.getY() + CardDimensions.SOLITAIRE_CARD_HEIGHT;
		
			// check if the click was in the range of the PileView
			boolean overX = (x > left) && (x < right);
			boolean overY = (y > top) && (y < bottom);
			if(overX && overY){
				
				// check to see if the player can draw a card, i.e. if there are no face up cards
				if(!pileViews.get(pileNum - 1).hasUpCards()){
					
					return pileNum;
				}
			}
			
			pileNum++;
		}
		
		return 0;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		// only handle left mouse clicks
		if(!SwingUtilities.isLeftMouseButton(e)){
			
			return;
		}
		
		// start the timer
		if(!madeFirstMove && timerNotStarted){

			timer = new Timer(1000, new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
						
					stats.setTime(stats.getTime() + 1);
				}
			});
			timer.setInitialDelay(0);
			timer.start();
			
			timerNotStarted = false;
			madeFirstMove = true;
		}
		
		Object source = e.getSource();
		
		// moving a card
		if(source instanceof CardView){

			// get defaults
			movingCard = (CardView)source;
			movingFromDrawSpot = false;
			cardStartPosX = movingCard.getX();
			cardStartPosY = movingCard.getY();

			// find which pile the card was moved from
			int pileNum = 1;
			for(PileView pileView : pileViews){
				
				if(pileView.hasCard(movingCard)){
					
					movingFromPile = pileNum;
					// find if the card has other cards under it
					// if it does, need to move those too
					if(pileView.hasUnderCards(movingCard)){
						
						// gather the rest of the cards
						otherCards = pileView.getUnderCards(movingCard);
					}
					break;
				}
				pileNum++;
			}
			
			// moved from the draw spot
			if(pileNum == NUM_PILES + 1){
				
				movingFromDrawSpot = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		// only track left mouse button
		if(!SwingUtilities.isLeftMouseButton(e)){
			
			return;
		}
		
		// check if the card was put on a legitimate position
		if(e.getSource() instanceof CardView){
			
			CardView cardView = (CardView)e.getSource();
			int pileNum = checkOverPile(cardView);
			int foundationNum = checkOverFoundation(cardView);
			
			// check for placement over pile
			if(pileNum > 0){
				
				// try to add the card to the pile
				if(pileViews.get(pileNum - 1).addCard(cardView, this, CardDimensions.SOLITAIRE_CARD_HEIGHT + CardDimensions.BORDER_AMOUNT)){
					
					// need to tell the model that the card was taken from the draw spot
					if(movingFromDrawSpot){
						
						// remove the card from the draw spot array so we don't delete them later on
						drawSpotCards.remove(movingCard);
						// make sure the model knows, too
						solitaireModel.movedFromDrawSpot();
					}
					// multiple cards were moved
					// need to add the other cards, if there are any
					// these cards should have already been checked for placement, because they're already stacked
					else if(!otherCards.isEmpty()){
							
						pileViews.get(pileNum - 1).addOtherCards(otherCards, this);
						pileViews.get(movingFromPile - 1).removeCards(otherCards.size() + 1);
					}
					// only one card was moved
					else{
					
						pileViews.get(movingFromPile - 1).removeCards(1);
					}
					
					stats.setNumMoves(stats.getNumMoves() + 1);
				}
				// couldn't add to pile
				else{
					
					snapBack();
				}
			}
			// check for placement over foundation
			else if(foundationNum > 0){
				
				// can't add multiple cards to a foundation at once
				if(!otherCards.isEmpty()){
					
					snapBack();
				}
				
				if(foundationViews.get(foundationNum - 1).addCard(cardView, this, foundationArea.getX())){
					
					// need to tell the model that the card was taken from the draw spot
					if(movingFromDrawSpot){
						
						// remove the card from the drawspot array so we don't delete them later on
						drawSpotCards.remove(movingCard);
						// make sure the model knows, too
						solitaireModel.movedFromDrawSpot();
					}
					else{
						
						pileViews.get(movingFromPile - 1).removeCards(1);
					}
					
					stats.setNumMoves(stats.getNumMoves() + 1);
				}
				else{
					
					snapBack();
				}
			}
			// didn't place over anything
			else{
				
				snapBack();
			}
			
			// reset the other cards, because this move is over
			otherCards.clear();
			
			revalidate();
			repaint();
			
			// check for end of game
			if(solitaireModel.endOfGame()){
				
				endGame();
			}
		}
	}
	
	/**
	 * Returns a card to its original position if the card movement was determined to be invalid
	 * 
	 * This will also return any cards that were moved along with the moved card
	 */
	private void snapBack(){
		
		// snap back moved card
		movingCard.setLocation(cardStartPosX, cardStartPosY);
		
		// snap back the other cards, if there are any
		int offset = CardDimensions.SOLITAIRE_OFFSET;
		if(!otherCards.isEmpty()){
			
			for(CardView cardView : otherCards){
				
				cardView.setLocation(cardStartPosX, cardStartPosY + offset);
				offset += CardDimensions.SOLITAIRE_OFFSET;
			}
		}
	}
	
	/**
	 * Determines whether a CardView object is released over a PileView
	 * 
	 * Uses the Point coordinates saved during initialization to determine if a CardView is hovering over a PileView
	 * This is used when dragging and releasing a CardView over a PileView, in order to place a card in a pile
	 * 
	 * @param cardView	the CardView object that was moved
	 * @return			true if the CardView is over a PileView, false if not
	 */
	private int checkOverPile(CardView cardView){
		
		int pileNum = 1;
		int x = cardView.getX();
		int y = cardView.getY();
		for(Point pilePosition : pilePositions){
			
			// get the coordinates of the FoundationView, with some added fudge space
			int left = (int)pilePosition.getX() - CardDimensions.X_FUDGE;
			int right = (int)pilePosition.getX() + CardDimensions.X_FUDGE;
			int top = (int)pilePosition.getY();
			int bottom = (int)pilePosition.getY() + pileOne.getHeight();
			
			// check whether or not the card is over the pile
			boolean overX = (x > left) && (x < right);
			boolean overY = (y > top) && (y < bottom);
			if(overX && overY){
			
				return pileNum;
			}
			pileNum++;
		}
		
		return 0;
	}
	
	/**
	 * Determines whether a CardView object is released over a FoundationView
	 * 
	 * Uses the Point coordinates saved during initialization to determine if a CardView is hovering over a FoundationView
	 * This is used when dragging and releasing a CardView over a FoundationView, in order to place a card in a foundation
	 * 
	 * @param cardView	the CardView object that was moved
	 * @return			true if the CardView is over a FoundationView, false if not
	 */
	private int checkOverFoundation(CardView cardView){
		
		int foundationNum = 1;
		int x = cardView.getX();
		int y = cardView.getY();
		for(Point foundationPosition : foundationPositions){

			// get the coordinates of the FoundationView, with some added fudge space
			int left = (int)foundationPosition.getX() - CardDimensions.X_FUDGE;
			int right = (int)foundationPosition.getX() + CardDimensions.X_FUDGE;
			int top = (int)foundationPosition.getY();
			int bottom = (int)foundationPosition.getY() + pileOne.getHeight();
			
			// check whether or not the card is over the foundation
			boolean overX = (x > left) && (x < right);
			boolean overY = (y > top) && (y < bottom);
			if(overX && overY){
			
				return foundationNum;
			}
			foundationNum++;
		}
		
		return 0;
	}
	
	/**
	 * Ends a game of Solitiare
	 * 
	 * Launches the end game statistics view
	 */
	private void endGame(){
		
		timer.stop();
		
		// show the end game view for Solitaire
		JFrame endGameFrame = new SolitaireEndGameView(true, stats, (JFrame) SwingUtilities.getWindowAncestor(this));
		endGameFrame.pack();
		endGameFrame.setVisible(true);
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		endGameFrame.setLocation(dim.width/2-endGameFrame.getSize().width/2, dim.height/2-endGameFrame.getSize().height/2);
	}

	/**
	 * To be called after the frame is packed, from the class which launches the frame
	 * 
	 * This function positions the GUI piles and frames
	 * This must be done after the frame is packed, because their positioning depends on numbers that are initialized during the pack
	 */
	public void onPack() {

		for(PileView pileView : pileViews){
			
			pileView.showInitCards(this);
			
			// this is the point relative to the parent, the wrapper JPanel PileArea
			Point point = pileView.getLocation();
			// thus, we must add the top area, and the top area's border
			pilePositions.add(new Point((int)point.getX(), (int)(point.getY() + topArea.getHeight() + CardDimensions.BORDER_AMOUNT)));
		}
		
		for(FoundationView foundationView : foundationViews){

			// this is the point relative to the parent, the wrapper JPanel PileArea
			Point point = foundationView.getLocation();
			// must add the wrapper position to x
			foundationPositions.add(new Point((int)(point.getX() + foundationArea.getX()), (int)point.getY()));
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}
}