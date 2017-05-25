package seniorproject.minesweeper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import seniorproject.utilities.GameStats;
import seniorproject.utilities.StatsEndGameView;

/**
 * Pops up when the game of Minesweeper ends
 * 
 * Inherits from {@link seniorproject.utilities.StatsEndGameView StatsEndGameView}
 * 
 * @author Andrew
 */
public class MinesweeperEndGameView extends StatsEndGameView {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a MinesweeperEndGameView
	 * 
	 * @param isWin			whether or not the player has won
	 * @param stats			statistical information for a game of Minesweeper
	 * @param parentFrame	the window frame of the main menu (the parent frame that launched Minesweeper)
	 */
	public MinesweeperEndGameView(boolean isWin, MinesweeperStats stats, JFrame parentFrame){
		
		super(isWin, stats, parentFrame);
	}
	
	/**
	 * @see seniorproject.utilities.StatsEndGameView#makeStatsSection(seniorproject.utilities.GameStats)
	 */
	@Override
	protected JPanel makeStatsSection(GameStats stats){
		
		JPanel statsPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		// number of clicks done by player
		JLabel clicksLabel = new JLabel();
		clicksLabel.setText("Number of clicks: " + Integer.toString(((MinesweeperStats)stats).getNumClicks()));
		clicksLabel.setAlignmentX(CENTER_ALIGNMENT);
		clicksLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 1;
		statsPanel.add(clicksLabel, c);
		
		// total number of seconds it took to play
		JLabel timeLabel = new JLabel();
		timeLabel.setText("Total time: " + Integer.toString(stats.getTime()) + " seconds");
		timeLabel.setAlignmentX(CENTER_ALIGNMENT);
		timeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 2;
		statsPanel.add(timeLabel, c);
		
		return statsPanel;
	}
}
