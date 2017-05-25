package seniorproject.hearts;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class keeps track of the display of the scores for all players of Hearts
 * 
 * It breaks down the scores based on the round
 * It is displayed after each round and at the end of the game
 * 
 * @author Andrew
 */
public class HeartsScores extends JPanel{
	
	private static final long serialVersionUID = 1L;

	// names row gui objects
	private JLabel handLabel;
	private JLabel humanLabel;
	private JLabel leftLabel;
	private JLabel acrossLabel;
	private JLabel rightLabel;
	private final String HAND_TEXT = "Hand | ";
	private final String HUMAN_TEXT = "You | ";
	private final String LEFT_TEXT = "Left | ";
	private final String ACROSS_TEXT = "Across | ";
	private final String RIGHT_TEXT = "Right";	
	
	// total scores gui objects
	private JLabel totalScoreLabel;
	private JLabel humanTotalLabel;
	private JLabel leftTotalLabel;
	private JLabel acrossTotalLabel;
	private JLabel rightTotalLabel;
	private final String TOTAL_TEXT = "Total";
	
	/**
	 * The default constructor for a HeartsScores object
	 * 
	 * @return	a default HeartsScores object
	 */
	public HeartsScores(){
		
		setLayout(new GridBagLayout());
		
		// init gui objects
		handLabel = new JLabel();
		humanLabel = new JLabel();
		leftLabel = new JLabel();
		acrossLabel = new JLabel();
		rightLabel = new JLabel();
		totalScoreLabel = new JLabel();
		humanTotalLabel = new JLabel();
		leftTotalLabel = new JLabel();
		acrossTotalLabel = new JLabel();
		rightTotalLabel = new JLabel();
		
		// set up label texts
		handLabel.setText(HAND_TEXT);
		humanLabel.setText(HUMAN_TEXT);
		leftLabel.setText(LEFT_TEXT);
		acrossLabel.setText(ACROSS_TEXT);
		rightLabel.setText(RIGHT_TEXT);
		totalScoreLabel.setText(TOTAL_TEXT);
		
		// setup names row
		addToRow(0, handLabel, humanLabel, leftLabel, acrossLabel, rightLabel);
		
		// setup total scores row
		// set to ridiculously high number to ensure it's always at the bottom
		addToRow(1000, totalScoreLabel, humanTotalLabel, leftTotalLabel, acrossTotalLabel, rightTotalLabel);
	}
	
	/**
	 * Adds a group of 5 elements to a single row
	 * 
	 * The number of the row is given to position the elements of the row from the top
	 * 
	 * @param rowNum	the number of the row, ascending from top to bottom
	 * @param elemOne	the element in the first column
	 * @param elemTwo	the element in the second column
	 * @param elemThree	the element in the third column
	 * @param elemFour	the element in the fourth column
	 * @param elemFive	the element in the fifth column
	 */
	private void addToRow(int rowNum, JLabel elemOne, JLabel elemTwo, JLabel elemThree, JLabel elemFour, JLabel elemFive){
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = rowNum;
		JLabel[] elements = {elemOne, elemTwo, elemThree, elemFour, elemFive};
		for(int i = 0; i < 5; i++){
			
			gbc.gridx = i;
			add(elements[i], gbc);
			
			if(i != 4){
				
				gbc.gridx = i + 1;
				add(Box.createRigidArea(new Dimension(10, 1)), gbc);
			}
		}
	}

	/**
	 * Updates all of the scores from the players of the game of Hears
	 * 
	 * @param heartsModel	the Hearts model which the scores are based on
	 */
	public void updateScores(HeartsModel heartsModel) {

		JLabel handNumberLabel = new JLabel();
		// at this point, the new game is set up, so we're on the round after this one
		int roundNumber = heartsModel.getRoundNumber() - 1;
		handNumberLabel.setText(Integer.toString(roundNumber));
		
		// setup player round scores
		JLabel humanScoreLabel = new JLabel();
		humanScoreLabel.setText(Integer.toString(heartsModel.getHumanPlayer().getRoundPoints(roundNumber)));
		JLabel leftScoreLabel = new JLabel();
		leftScoreLabel.setText(Integer.toString(heartsModel.getLeftPlayer().getRoundPoints(roundNumber)));
		JLabel acrossScoreLabel = new JLabel();
		acrossScoreLabel.setText(Integer.toString(heartsModel.getAcrossPlayer().getRoundPoints(roundNumber)));
		JLabel rightScoreLabel = new JLabel();
		rightScoreLabel.setText(Integer.toString(heartsModel.getRightPlayer().getRoundPoints(roundNumber)));
		
		// add hand row
		addToRow(roundNumber, handNumberLabel, humanScoreLabel, leftScoreLabel, acrossScoreLabel, rightScoreLabel);
				
		// update total scores
		humanTotalLabel.setText(Integer.toString(heartsModel.getHumanPlayer().getScore()));
		leftTotalLabel.setText(Integer.toString(heartsModel.getLeftPlayer().getScore()));
		acrossTotalLabel.setText(Integer.toString(heartsModel.getAcrossPlayer().getScore()));
		rightTotalLabel.setText(Integer.toString(heartsModel.getRightPlayer().getScore()));
	}
}
