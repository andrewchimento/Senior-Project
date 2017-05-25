package seniorproject.utilities;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * The basic model for an EndGameView
 * 
 * Objects of this class are created when a game ends. All games end with an EndGameView or a child of it being launched
 * {@link seniorproject.utilities.StatsEndGameView}
 * {@link seniorproject.hearts.HeartsEndGameView}
 * {@link seniorproject.minesweeper.MinesweeperEndGameView}
 * {@link seniorproject.solitaire.SolitaireEndGameView}
 * 
 * @author Andrew
 */
public class EndGameView extends JFrame implements ActionListener {

	static final long serialVersionUID = 1L;
	
	private final String WIN_MESSAGE = "You Won!";
	private final String LOSE_MESSAGE = "You Lost!";
	private final String OK_TEXT = "Ok";
	private final int HIGH_NUM = 1000;
	
	private JLabel endMessageLabel;
	private JButton okButton;
	
	protected JPanel panel;
	private JFrame parentFrame;

	/**
	 * The constructor for an EndGameView instance
	 * 
	 * This EndGameView simply displays if the user has won or lost, and shows an OK button for them to click. 
	 * Pressing the OK button closes both this window and the game module window.
	 * Afterwards, the user is brought back to the main menu.
	 * 
	 * @param isWin			whether or not the player has won
	 * @param parentFrame	the frame over which the EndGameView will be displayed
	 * @return				a basic EndGameView instance
	 */
	public EndGameView(boolean isWin, JFrame parentFrame){
		
		this.parentFrame = parentFrame;
		
		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		// setup the end message label
		endMessageLabel = new JLabel();
		if(isWin){
			
			endMessageLabel.setText(WIN_MESSAGE);
		}
		else{
			
			endMessageLabel.setText(LOSE_MESSAGE);
		}
		endMessageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 0;
		panel.add(endMessageLabel, c);
		
		// setup the ok button
		okButton = new JButton();
		okButton.setText(OK_TEXT);
		okButton.addActionListener(this);
		// set this to a high number, so it is always on the bottom
		c.gridy = HIGH_NUM;
		panel.add(okButton, c);
		
		// spacing
		c.gridy = HIGH_NUM + 1;
		panel.add(Box.createRigidArea(new Dimension(0, 10)), c);
		
		add(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		
		Object source = event.getSource();
		
		if(source == okButton){
			
			// close the end game view screen
			dispose();
			// close the game itself
			parentFrame.dispatchEvent(new WindowEvent(parentFrame, WindowEvent.WINDOW_CLOSING));
		}
	}
}