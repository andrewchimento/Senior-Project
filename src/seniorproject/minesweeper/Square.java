package seniorproject.minesweeper;

import java.util.ArrayList;

import seniorproject.utilities.Pair;

/**
 * Square class for use in Minesweeper
 * 
 * This class holds all information about a Square, including whether it has been clicked, whether it is a mine, whether it has been flagged, and how many squares adjeacent to this are mines
 * It also holds positional data about the square, such as its own coordinates and the coordinates of all adjacent squares
 * 
 * @author Andrew
 */
public class Square {

	private boolean isClicked;
	private boolean isMine;
	private boolean isFlag;
	private int x;
	private int y;
	private int numAdjMines;
	private ArrayList<Pair<Integer,Integer>> adjSquareCoords;

	/**
	 * A constructor for a Square object which takes x and y coordinates
	 * 
	 * Sets this x and y coordinates
	 * Also sets the coordinate for adjecent squares
	 * 
	 * @param x	the x coordinate of the square
	 * @param y	the y coordinate of the square
	 */
	public Square(int x, int y){
		
		// set coordinates of square
		this.x = x;
		this.y = y;
		
		// set coordinates of adjacent squares
		adjSquareCoords = new ArrayList<Pair<Integer,Integer>>();
		adjSquareCoords.add(new Pair<Integer,Integer>(x-1, y-1));
		adjSquareCoords.add(new Pair<Integer,Integer>(x, y-1));
		adjSquareCoords.add(new Pair<Integer,Integer>(x+1, y-1));
		adjSquareCoords.add(new Pair<Integer,Integer>(x-1, y));
		adjSquareCoords.add(new Pair<Integer,Integer>(x+1, y));
		adjSquareCoords.add(new Pair<Integer,Integer>(x-1, y+1));
		adjSquareCoords.add(new Pair<Integer,Integer>(x, y+1));
		adjSquareCoords.add(new Pair<Integer,Integer>(x+1, y+1));
	}

	@Override
	public String toString(){
		
		String returnString = "";
		
		returnString += "isClicked: " + String.valueOf(isClicked) + "\n";
		returnString += "isMine: " + String.valueOf(isMine) + "\n";
		returnString += "isFlag: " + String.valueOf(isFlag) + "\n";
		returnString += "x: " + Integer.toString(x) + "\n";
		returnString += "y: " + Integer.toString(y) + "\n";
		returnString += "numAdjMines: " + Integer.toString(numAdjMines) + "\n";
		
		return returnString;
	}
	
	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getNumAdjMines() {
		return numAdjMines;
	}

	public void setNumAdjMines(int numAdjMines) {
		this.numAdjMines = numAdjMines;
	}
	
	public ArrayList<Pair<Integer, Integer>> getAdjSquareCoords() {
		return new ArrayList<Pair<Integer,Integer>>(adjSquareCoords);
	}
}
