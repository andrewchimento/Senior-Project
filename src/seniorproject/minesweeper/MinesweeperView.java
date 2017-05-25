package seniorproject.minesweeper;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import seniorproject.utilities.Pair;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;

/**
 * The graphical representation of the 
 * 
 * This is presented via a JPanel object which is then added to the frame created in {@link seniorproject.mainmenu.MainMenuView MainMenuView}
 * The user interacts with the MinesweeperView with the mouse
 * 
 * @author Andrew
 */
public class MinesweeperView extends JPanel implements MouseListener {

	// constants
	private static final long serialVersionUID = 1L;
	
	private final int SQUARE_SIZE = 30;
	private final String MINE_COUNT_LABEL_TEXT = "Mines left: ";
	private final int DEF_MINE_NUM = -1;
	
	private MinesweeperModel minesweeeperModel;
	
	// graphical representation of playing area
	private JButton squares[][];
	
	// stat tracking
	private MinesweeperStats stats;
	private Timer timer;
	private JLabel timerLabel;
	private JLabel mineCountActual;
	
	/**
	 * A constructor for a MinesweeperView object
	 * 
	 * Creates the underlying MinesweeeperModel as well as the MinesweeperStats object to track time, clicks, etc.
	 * Sets up GUI consisting of a stats section (timer and number of mines left) and the playing area (a grid of squares)
	 * 
	 * @param difficulty	the difficulty chosen by the user; either Easy, Medium, or Hard
	 */
	public MinesweeperView(Difficulty difficulty){
		
		super();
		
		minesweeeperModel = new MinesweeperModel(difficulty);
		squares = new JButton[minesweeeperModel.getNumSquaresHorRow()][minesweeeperModel.getNumSquaresVertRow()];
		stats = new MinesweeperStats();
		
		// set up stat tracking
		stats.setNumMinesLeft(minesweeeperModel.getNumMines());
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		// set up stats section
		c.gridy = 0;
		add(setUpStats(), c);
		
		// set up game section
		c.gridy = 1;
		add(setUpGame(difficulty), c);
	}
	
	/**
	 * Creates the stats section to be displayed above the playing area
	 * 
	 * This section will consist of the current number of seconds since the first click and the number of mines the player has not flagged
	 * 
	 * @return	a JPanel which displays the stats section
	 */
	private JPanel setUpStats(){
		
		JPanel statsScreen = new JPanel(new FlowLayout());
		
		// set up mine count
		JLabel mineCountLabel = new JLabel(MINE_COUNT_LABEL_TEXT);
		mineCountActual = new JLabel(Integer.toString(minesweeeperModel.getNumMines()));
		JPanel mineInfo = new JPanel(new GridLayout(0, 2));
		mineInfo.add(mineCountLabel);
		mineInfo.add(mineCountActual);
		
		statsScreen.add(mineInfo);
		
		// set up timer display
		// the timer doesn't actually start until the first square is clicked
		timerLabel = new JLabel("0");
		
		statsScreen.add(timerLabel);
		
		return statsScreen;
	}
	
	/**
	 * Creates the GUI playing area
	 * 
	 * This section is filled with a grid of JButtons which respond to user clicks
	 * The MinesweeperView object responds to the clicks done by the user
	 * 
	 * @param difficulty	the difficulty chosen by the user; either Easy, Medium, or Hard
	 * @return	a JPanel which displays the playing area
	 */
	private JPanel setUpGame(Difficulty difficulty){
		
		JPanel gameScreen = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// add JButtons to the JPanel
		for(int i = 0; i < minesweeeperModel.getNumSquaresHorRow(); i++){
			
			c.gridx = i;
			
			for(int j = 0; j < minesweeeperModel.getNumSquaresVertRow(); j++){
				
				// create the square as a JButton
				JButton square = new JButton();
				square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				square.setName(Integer.toString(i) + "," + Integer.toString(j));
				square.addMouseListener(this);
				square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/default.png")));
				square.setBorder(new LineBorder(Color.BLACK));
				
				c.gridy = j;
				
				// add to view
				gameScreen.add(square, c);
				
				// add to array keeping track of buttons
				squares[i][j] = square;
			}
		}
		
		return gameScreen;
	}
	
	@Override
	public void mousePressed(MouseEvent event){

		// increase number of clicks
		stats.setNumClicks(stats.getNumClicks() + 1);
		
		if(event.getSource() instanceof JButton){
			
			// Find out which square was pressed
			JButton square = (JButton) event.getSource();
			String name = square.getName();
			
			// Get coordinates of square from the JButton's name
			String[] coords = name.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);
			
			// Run logic for click
			if(SwingUtilities.isRightMouseButton(event)){
				
				handleRightClick(minesweeeperModel.getSquare(x, y), square);
			}
			else if(SwingUtilities.isLeftMouseButton(event)){
				
				// some things to do if it is the first click
				if(stats.getNumClicks() == 1){
					
					timer = new Timer(1000, new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent event){
								
							stats.setTime(stats.getTime() + 1);
							timerLabel.setText(Integer.toString(stats.getTime()));
						}
					});
					timer.setInitialDelay(0);
					timer.start();
					
					// the first click can't be a mine, so we wait for the first click before setting the mines
					minesweeeperModel.initBoard(x, y);					 
				}
				
				handleLeftClick(minesweeeperModel.getSquare(x, y), x, y, square);
			}
			else if(SwingUtilities.isMiddleMouseButton(event)){
			
				handleMiddleClick(minesweeeperModel.getSquare(x, y), x, y);
			}
		}
		
		// check for end of game
		// game ends when the number of unopened squares = number of bombs
		if(minesweeeperModel.isWin()){
			
			endGame(true);
		}
	}
	
	/**
	 * Processes a right mouse click
	 * 
	 * When a player right clicks on the board, they indicate that they want to toggle flag placement on the clicked square
	 * A flag can only be placed on a non-clicked square
	 * When a flag is placed, the mine counter goes down by one; when a flag is picked up, it increases by one
	 * 
	 * @param squareModel	the instance of the Square that was clicked
	 * @param square		the graphical JButton that represents the Square
	 */
	private void handleRightClick(Square squareModel, JButton square){
		
		// Can't set flag for clicked square
		if(squareModel.isClicked()){
			
			return;
		}
		
		// If the flag is not set, put a flag down
		if(!squareModel.isFlag()){
			
			squareModel.setFlag(true);
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/flag.png")));
			stats.setNumMinesLeft(stats.getNumMinesLeft() - 1);
		}
		// Else if the flag is set, pick the flag up
		else{
			
			squareModel.setFlag(false);
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/default.png")));
			stats.setNumMinesLeft(stats.getNumMinesLeft() + 1);
		}
		
		// update mine counter
		mineCountActual.setText(Integer.toString(stats.getNumMinesLeft()));
	}
	
	/**
	 * Process a left mouse click
	 * 
	 * When a player clicks on a square with the left mouse click, the square is cleared
	 * A square cannot be clicked if it has already been clicked or if it has been marked by a flag
	 * If the player left clicks a square with a mine underneath, they lose
	 * 
	 * @param squareModel	the instance of the Square that was clicked
	 * @param x				the x coordinate of the clicked Square
	 * @param y				the x coordinate of the clicked Square
	 * @param square		the graphical JButton that represents the Square
	 */
	private void handleLeftClick(Square squareModel, int x, int y, JButton square){
		
		// if the square is already clicked, there is no need to process it
		if(squareModel.isClicked()){
			
			return;
		}
		
		// If the flag is set, the player cannot click the square
		if(squareModel.isFlag()){
			
			return;
		}
		
		// If it is a mine, player loses
		if(squareModel.isMine()){
			
			endGame(false);
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/mine_first.png")));
			return;
		}
		
		openSquare(squareModel, x, y, square);
	}
	
	/**
	 * Ends a game of Minesweeper
	 * 
	 * Triggers visual effects for the end of the game, such as revealing non-clicked squares and showing the end game stats
	 * 
	 * @param isWin	whether or not the human player has won
	 */
	private void endGame(boolean isWin){
		
		timer.stop();
		
		revealBoard();
		
		// show the end game view for Minesweeper
		JFrame endGameFrame = new MinesweeperEndGameView(isWin, stats, (JFrame) SwingUtilities.getWindowAncestor(this));
		endGameFrame.pack();
		endGameFrame.setVisible(true);
		// center end game view window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		endGameFrame.setLocation(dim.width/2-endGameFrame.getSize().width/2, dim.height/2-endGameFrame.getSize().height/2);
	}
	
	/**
	 * Flips over all non-clicked squares
	 * 
	 * This is triggered when the game ends, either when the player wins or loses
	 */
	private void revealBoard(){
		
		for(int i = 0; i < minesweeeperModel.getNumSquaresHorRow(); i++){
			
			for(int j = 0; j < minesweeeperModel.getNumSquaresVertRow(); j++){
				
				Square square = minesweeeperModel.getSquare(i, j);
				
				// don't process mines that have already been clicked
				if(square.isClicked()){
					
					continue;
				}
				
				// show a square as either a mine or as the number of adjacent mines to the square
				if(square.isMine()){
					
					setIcon(DEF_MINE_NUM, squares[i][j]);
				}
				else{
					
					setIcon(square.getNumAdjMines(), squares[i][j]);	
				}
			}
		}
	}
	
	/**
	 * Changes the picture of the square to match the number of adjacent mines
	 * 
	 * @param numAdjMines	the number of squares adjacent to this one that have mines under them
	 * @param square		the graphical representation of the square to set the icon of
	 */
	private void setIcon(int numAdjMines, JButton square){
		
		switch(numAdjMines){

		case DEF_MINE_NUM:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/mine_other.png")));
			break;
		case 0:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/zero_mines.png")));
			break;
		case 1:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/one_mines.png")));	
			break;
		case 2:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/two_mines.png")));	
			break;
		case 3:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/three_mines.png")));	
			break;
		case 4:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/four_mines.png")));	
			break;
		case 5:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/five_mines.png")));	
			break;
		case 6:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/six_mines.png")));	
			break;
		case 7:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/seven_mines.png")));	
			break;
		case 8:
			square.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/eight_mines.png")));	
			break;
		}
	}
	
	/**
	 * Reveals what is under an individual square
	 * 
	 * A square can reveal a mine or the number of adjacent mines
	 * When a blank square is clicked (a square with no adjacent mines) it must be processed differently ({@link #blankSquareClicked(int, int) blankSquareClicked(int, int)})
	 * 
	 * @param squareModel	the instance of the Square that was clicked
	 * @param x				the x coordinate of the clicked Square
	 * @param y				the x coordinate of the clicked Square
	 * @param square		the graphical JButton that represents the Square
	 */
	private void openSquare(Square squareModel, int x, int y, JButton square){
		
		// square is blank, and must be processed differently
		if(squareModel.getNumAdjMines() == 0){
			
			blankSquareClicked(x, y);
		}
		else{
			
			setIcon(squareModel.getNumAdjMines(), square);
			minesweeeperModel.setNumUnopenedSquares(minesweeeperModel.getNumUnopenedSquares() - 1);
		}
	
		squareModel.setClicked(true);
	}
	
	
	/**
	 * Handles a clicked square that has no adjacent mines
	 * 
	 * When a square that has no adjacent mines is clicked, all adjacent blank squares that have not been clicked or are not flagged must also be processed
	 * This is a recursive function to handle the processing of an individual blank square
	 * 
	 * @param x	the x coordinate of the clicked square
	 * @param y the y coordinate of the clicked square
	 */
	private void blankSquareClicked(int x, int y){
		
		// if either x or y value is negative or above the maximum row size, we've hit the bounds of the grid, so return
		if(x < 0 || y < 0 || x >= minesweeeperModel.getNumSquaresHorRow() || y >= minesweeeperModel.getNumSquaresVertRow()){
			
			return;
		}
		
		Square squareModel = minesweeeperModel.getSquare(x, y);
		// base cases
		if(squareModel.getNumAdjMines() > 0 || squareModel.isFlag() || squareModel.isClicked()){
			
			if(!squareModel.isClicked()){
				setIcon(squareModel.getNumAdjMines(), squares[x][y]);
				minesweeeperModel.setNumUnopenedSquares(minesweeeperModel.getNumUnopenedSquares() - 1);
			}
			squareModel.setClicked(true);
			return;
		}
		
		// at this point, the square must be blank, because it has no adjacent mines and has not been flagged
		// set it as clicked and change the icon, so we know it's been processed
		if(!squareModel.isClicked()){
			
			setIcon(squareModel.getNumAdjMines(), squares[x][y]);
			minesweeeperModel.setNumUnopenedSquares(minesweeeperModel.getNumUnopenedSquares() - 1);
		}
		squareModel.setClicked(true);
		
		// then gather up the adjacent squares and go through them one by one, until none of the adjacent ones are blank
		// get adjacent squares
		ArrayList<Pair<Integer,Integer>> adjSquareCoords = minesweeeperModel.getSquare(x, y).getAdjSquareCoords();
		for(Pair<Integer,Integer> squareCoords : adjSquareCoords){
						
			blankSquareClicked(squareCoords.getX(), squareCoords.getY());
		}
	}
	
	/**
	 * Handles a middle mouse click
	 * 
	 * When the player clicks a square with the middle mouse button, they are trying to clear multiple squares with a single click
	 * Runs through all adjacent squares to clear them
	 * The surrounding uncleared squares can be cleared when the number of flags put down is equal to the number of adjacent mines for that square
	 * If the player has incorrectly flagged squares that have no mines under them and therefore are clearing squares with mines under them, they can lose
	 * 
	 * @param squareModel	the instance of the Square that was clicked
	 * @param x				the x coordinate of the clicked Square
	 * @param y				the x coordinate of the clicked Square
	 */
	private void handleMiddleClick(Square squareModel, int x, int y){
		
		// can't clear a square with no adjacent mines
		if(!(squareModel.getNumAdjMines() > 0)){

			return;
		}
		
		// assertion: square is numbered
		// check if the numbered square has n amount of flags on adjacent squares (n = number of adjacent mines)
		if(!minesweeeperModel.checkAdjFlags(x, y)){

			return;
		}
		
		// assertion: surrounding squares can be cleared
		ArrayList<Pair<Integer,Integer>> adjSquareCoords = minesweeeperModel.getSquare(x, y).getAdjSquareCoords();
		for(Pair<Integer,Integer> squareCoords : adjSquareCoords){
						
			int xToClear = squareCoords.getX();
			int yToClear = squareCoords.getY();
			
			// if either x or y are negative, this is out of bounds of grid
			if(xToClear < 0 || yToClear < 0 || xToClear >= minesweeeperModel.getNumSquaresHorRow() || yToClear >= minesweeeperModel.getNumSquaresVertRow()){
				
				continue;
			}
			
			Square squareToClear = minesweeeperModel.getSquare(xToClear, yToClear);
			
			// don't clear a flagged square
			if(squareToClear.isFlag()){
				
				continue;
			}
			
			// if one of the squares to be cleared is a mine, this means that the player put a flag down in the wrong position
			// this means they hit the mine, and they lose
			if(squareToClear.isMine()){
				
				endGame(false);
				squares[xToClear][yToClear].setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("res/minesweeper/mine_first.png")));
				continue;
			}
			
			if(!squareToClear.isClicked()){
				
				openSquare(squareToClear, xToClear, yToClear, squares[xToClear][yToClear]);	
			}
		}
	}

	// additional mouse events that are not used
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
