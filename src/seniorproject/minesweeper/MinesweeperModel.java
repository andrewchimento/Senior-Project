 package seniorproject.minesweeper;

import java.util.ArrayList;
import java.util.Random;

import seniorproject.utilities.Pair;

/**
 * Contains and controls the logic behind a Minesweeper game
 * 
 * Can track multiple board sizes, depending on the difficulty selected
 * The board is created and initialized after the game has already been created (i.e.: separate from the constructor)
 * This is to ensure that the first square clicked does not result in uncovering a mine
 * 
 * @author Andrew
 */
public class MinesweeperModel {
	
	private Square[][] board;
	// board dimensions
	private int numSquaresVertRow;
	private int numSquaresHorRow;
	private int numMines;
	// keep track for win condition 
	private int numUnopenedSquares;
	
	// constants for difficulties
	private final int EASY_NUM_SQUARES = 9;
	private final int MED_NUM_SQUARES = 16;
	private final int HARD_NUM_SQUARES_VERT = 16;
	private final int HARD_NUM_SQUARES_HOR = 30;
	
	private final int EASY_NUM_MINES = 10;
	private final int MED_NUM_MINES = 40;
	private final int HARD_NUM_MINES = 99;
	
	/**
	 * Constructor for a MinesweeperModel object
	 * 
	 * Uses the given difficulty level to determine the number of squares in the grid and the overall number of mines to be placed
	 * 
	 * @param difficulty	the difficulty chosen by the user; can be Easy, Medium, or Hard
	 * @return				a MinesweeperModel object
	 */
	public MinesweeperModel(Difficulty difficulty){
		
		switch(difficulty){
		case EASY:
			numSquaresVertRow = EASY_NUM_SQUARES;
			numSquaresHorRow = EASY_NUM_SQUARES;
			numMines = EASY_NUM_MINES;
			break;
		case MEDIUM:
			numSquaresVertRow = MED_NUM_SQUARES;
			numSquaresHorRow = MED_NUM_SQUARES;
			numMines = MED_NUM_MINES;
			break;
		case HARD:
			numSquaresVertRow = HARD_NUM_SQUARES_VERT;
			numSquaresHorRow = HARD_NUM_SQUARES_HOR;
			numMines = HARD_NUM_MINES;
			break;
		}
		
		numUnopenedSquares = (numSquaresVertRow * numSquaresHorRow);
	}
	
	/**
	 * Initializes the board for a game of Minesweeper
	 * 
	 * This function is called after the first square is clicked by the player
	 * This is because the first-clicked square cannot be a mine, as per the rules of Minesweeper
	 * 
	 * @param firstClickedX	the x coordinate of the first-clicked square
	 * @param firstClickedY	the y coordinate of the first-clicked square
	 */
	public void initBoard(int firstClickedX, int firstClickedY){
		
		// create the board to be the size determined by the difficulty
		board = new Square[numSquaresHorRow][numSquaresVertRow];
		
		// initialize all squares on the board
		for(int i = 0; i < numSquaresHorRow; i++){
			
			for(int j = 0; j < numSquaresVertRow; j++){
				
				board[i][j] = new Square(i, j);
			}
		}
		
		setMines(firstClickedX, firstClickedY);
		
		setNumbers();
	}
	
	/**
	 * Places mines on the board
	 * 
	 * The mines are placed randomly; i.e.: each x and y coordinate are randomly chosen
	 * A spot for a mine is chosen to make sure that the square the player first clicked on is not a mine
	 * 
	 * @param firstClickedX	the x coordinate of the first-clicked square
	 * @param firstClickedY	the y coordinate of the first-clicked square
	 */
	private void setMines(int firstClickedX, int firstClickedY){
		
		Random random = new Random();
		
		int counter = numMines;
		while(counter > 0){
			
			// get random coordinates for the mine
			int x = random.nextInt(numSquaresHorRow);
			int y = random.nextInt(numSquaresVertRow);
			
			// don't place a mine where the player first clicked
			if(x == firstClickedX && y == firstClickedY){
				
				continue;
			}
			
			// check if a mine has already been placed here
			// if it has, choose a new spot without decrementing counter
			if(board[x][y].isMine()){
				
				continue;
			}
			
			board[x][y].setMine(true);
			
			counter--;
		}
	}
	
	/**
	 * Counts the number of adjacent mines for each square on the board
	 * 
	 * This is used during initialization phase, and must be done after the mines have been placed
	 */
	private void setNumbers(){
		
		for(int i = 0; i < numSquaresHorRow; i++){
			
			for(int j = 0; j < numSquaresVertRow; j++){
				
				// check if top left from square is a mine
				if((i != 0 && j != 0) && board[i-1][j-1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if top center from square is a mine
				if(j != 0 && board[i][j-1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if top right from square is a mine
				if((i != numSquaresHorRow - 1 && j != 0) && board[i+1][j-1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if middle left from square is a mine
				if(i != 0 && board[i-1][j].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if middle right from square is a mine
				if(i != numSquaresHorRow - 1 && board[i+1][j].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if bottom left from square is a mine
				if((i != 0 && j != numSquaresVertRow - 1) && board[i-1][j+1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
				
				// check if bottom center from square is a mine
				if(j != numSquaresVertRow - 1 && board[i][j+1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}

				// check if bottom right from square is a mine
				if((i != numSquaresHorRow - 1 && j != numSquaresVertRow - 1) && board[i+1][j+1].isMine()){
					
					board[i][j].setNumAdjMines(board[i][j].getNumAdjMines() + 1);
				}
			}
		}
	}
	
	public int getNumSquaresVertRow(){
		
		return numSquaresVertRow;
	}
	
	public int getNumSquaresHorRow(){
		
		return numSquaresHorRow;
	}
	
	public Square getSquare(int x, int y){
		
		return board[x][y];
	}
	
	public int getNumMines(){
		
		return numMines;
	}
	
	public int getNumUnopenedSquares(){
		
		return numUnopenedSquares;
	}
	
	public void setNumUnopenedSquares(int numUnopenedSquares){
		
		this.numUnopenedSquares = numUnopenedSquares;
	}
	
	/**
	 * Checks if the player has won
	 * 
	 * This is true because the objective of Minesweeper is to clear all non-mine squares
	 * 
	 * @return true when the number of unopened squares (a square that has not yet been cleared) is equal to the number of mines left 
	 */
	public boolean isWin(){
		
		return numUnopenedSquares == numMines;
	}
	
	/**
	 * Checks if the number of mines a square is adjacent to is equal to the number of adjacent flagged squares
	 * 
	 * This is important when processing a middle mouse click
	 * 
	 * @param x	the x coordinate of the clicked square
	 * @param y	the y coordinate of the clicked square
	 * @return	true if the number of adjacent flags is equal to the number of adjacent mines, false if not
	 */
	public boolean checkAdjFlags(int x, int y){
		
		Square square = getSquare(x, y);
		// record the initial number of mines
		int numMines = square.getNumAdjMines();

		// total all 
		int numFlags = 0;
		ArrayList<Pair<Integer,Integer>> adjSquareCoords = square.getAdjSquareCoords();
		for(Pair<Integer,Integer> squareCoords : adjSquareCoords){
			
			int xToCheck = squareCoords.getX();
			int yToCheck = squareCoords.getY();
			
			// if either x or y are negative, this is outside of bounds of board
			if(xToCheck < 0 || yToCheck < 0 || xToCheck >= numSquaresHorRow || yToCheck >= numSquaresVertRow){
				
				continue;
			}
			
			if(getSquare(xToCheck, yToCheck).isFlag()){
				
				numFlags++;
			}
		}
		
		return numFlags == numMines;
	}
}
