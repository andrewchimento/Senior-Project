package seniorproject.hearts;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import seniorproject.utilities.EndGameView;

/**
 * Pops up when the game of Hearts ends
 * 
 * Inherits from {@link seniorproject.utilities.StatsEndGameView StatsEndGameView}
 * 
 * @author Andrew
 */
public class HeartsEndGameView extends EndGameView{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a HeartsEndGameView instance
	 * 
	 * @param isWin			whether or not the player has won
	 * @param parentFrame	the window frame of the main menu (the parent frame that launched Solitaire)
	 * @param scores		the HeartsScores object which keeps track of the players' scores
	 */
	public HeartsEndGameView(boolean isWin, JFrame parentFrame, HeartsScores scores) {
		
		super(isWin, parentFrame);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		panel.add(scores, c);
	}
}
