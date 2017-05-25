package seniorproject.utilities;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An EndGameView child which uses statistics such as time and number of clicks to display information to the user
 * 
 * This is an abstract class, so it cannot be instantiated
 * 
 * @author Andrew
 */
public abstract class StatsEndGameView extends EndGameView{

	private static final long serialVersionUID = 1L;

	/**
	 * A constructor for a StatsEndGameView
	 * 
	 * Because this class is abstract, this can only be called via a child class
	 * 
	 * @param isWin			whether or not the human player won
	 * @param stats			the GameStats model that the view will populate its components with
	 * @param parentFrame	the parent frame the StatsEndGameView is displayed over
	 * @return				a StatsEndGameView object
	 */
	public StatsEndGameView(boolean isWin, GameStats stats, JFrame parentFrame) {
		
		super(isWin, parentFrame);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		panel.add(makeStatsSection(stats), c);
	}

	/**
	 * Creates the display for the game's statistics which will be added to the StatsEndGameView instance's panel
	 * 
	 * This function will be overloaded in whatever child class extends StatsEndGameView
	 * When the function is overloaded, the stats object will be cast to the relevant game's statistics object
	 * 
	 * @param stats	the statistics of whatever game was being played
	 * @return		a JPanel object that displays the passed stats object
	 */
	abstract protected JPanel makeStatsSection(GameStats stats);
}
