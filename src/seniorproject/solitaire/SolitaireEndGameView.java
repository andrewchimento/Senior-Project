package seniorproject.solitaire;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import seniorproject.utilities.GameStats;
import seniorproject.utilities.StatsEndGameView;

/**
 * Pops up when the game of Solitaire ends
 * 
 * Inherits from {@link seniorproject.utilities.StatsEndGameView StatsEndGameView}
 * 
 * @author Andrew
 */
public class SolitaireEndGameView extends StatsEndGameView{

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for a SolitaireEndGameView instance
	 * 
	 * @param isWin			whether or not the player has won
	 * @param stats			statistical information for a game of Solitaire
	 * @param parentFrame	the window frame of the main menu (the parent frame that launched Solitaire)
	 */
	public SolitaireEndGameView(boolean isWin, GameStats stats, JFrame parentFrame) {
		
		super(isWin, stats, parentFrame);
	}

	/**
	 * @see seniorproject.utilities.StatsEndGameView#makeStatsSection(seniorproject.utilities.GameStats)
	 */
	@Override
	protected JPanel makeStatsSection(GameStats stats) {

		JPanel statsPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		// number of moves
		JLabel movesLabel = new JLabel();
		movesLabel.setText("Number of moves: " + Integer.toString(((SolitaireStats)stats).getNumMoves()));
		movesLabel.setAlignmentX(CENTER_ALIGNMENT);
		movesLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 1;
		statsPanel.add(movesLabel, c);
		
		// time for completion
		JLabel timeLabel = new JLabel();
		timeLabel.setText("Total time: " + Integer.toString(stats.getTime()) + " seconds");
		timeLabel.setAlignmentX(CENTER_ALIGNMENT);
		timeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 2;
		statsPanel.add(timeLabel, c);
		
		return statsPanel;
	}
}
