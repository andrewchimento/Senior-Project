package seniorproject.mainmenu;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import seniorproject.hearts.HeartsModel;
import seniorproject.hearts.HeartsView;
import seniorproject.minesweeper.Difficulty;
import seniorproject.minesweeper.MinesweeperView;
import seniorproject.solitaire.SolitaireModel;
import seniorproject.solitaire.SolitaireView;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main menu of the 
 * 
 * This class serves as the launcher for all game modules and also for the executable, as it contains main
 * 
 * @author Andrew
 */
public class MainMenuView extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static JFrame mainMenuFrame; 
	
	private JButton minesweeperStart;
	private JButton minesweeperEasyButton;
	private	JButton minesweeperMediumButton;
	private JButton minesweeperHardButton;
	private JButton solitaireStart;
	private JButton heartsStart;
	private JLabel whatPlayLabel;
	private JFrame minesweeperDifficultyFrame;
	
	private final String WHAT_PLAY_TEXT = "What would you like to play?";	
	private final String MINESWEEPER_TEXT = "Minesweeper";
	private final String SOLITAIRE_TEXT = "Solitaire";
	private final String HEARTS_TEXT = "Hearts";
	private final String EASY_TEXT = "Easy";
	private final String MEDIUM_TEXT = "Medium";
	private final String HARD_TEXT = "Hard";
	private final String DIFFICULTY_TEXT = "Please select the difficulty:";
	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		
		mainMenuFrame = new JFrame();
		mainMenuFrame.setResizable(false);
		
		// Set up main menu
		mainMenuFrame.getContentPane().add(new MainMenuView());
		
		// tell the program to shut down when the main menu is closed
		mainMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		mainMenuFrame.pack();
		mainMenuFrame.setVisible(true);
		
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainMenuFrame.setLocation(dim.width/2-mainMenuFrame.getSize().width/2, dim.height/2-mainMenuFrame.getSize().height/2);
	}

	/**
	 * Default constructor for the MainMenuView class
	 * 
	 * Creates a panel consisting of a what-to-play prompt and buttons for game selection
	 * 
	 * @return a default MainMenuView object
	 */
	public MainMenuView(){
		
		// initialize gui objects
		minesweeperStart = new JButton();
		solitaireStart = new JButton();
		heartsStart = new JButton();
		whatPlayLabel = new JLabel();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		// configure gui objects
		// what to play prompt
		whatPlayLabel.setText(WHAT_PLAY_TEXT);
		whatPlayLabel.setAlignmentX(CENTER_ALIGNMENT);
		whatPlayLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		c.gridy = 0;
		add(whatPlayLabel, c);
		
		// selection buttons
		minesweeperStart.setText(MINESWEEPER_TEXT);
		minesweeperStart.setAlignmentX(CENTER_ALIGNMENT);
		minesweeperStart.addActionListener(this);
		c.gridy = 1;
		add(minesweeperStart, c);
		
		// add spacing between components
		c.gridy = 2;
		add(Box.createRigidArea(new Dimension(0, 10)), c);
		
		solitaireStart.setText(SOLITAIRE_TEXT);
		solitaireStart.setAlignmentX(CENTER_ALIGNMENT);
		solitaireStart.addActionListener(this);
		c.gridy = 3;
		add(solitaireStart, c);
		
		// add spacing between components
		c.gridy = 4;
		add(Box.createRigidArea(new Dimension(0, 10)), c);
		
		heartsStart.setText(HEARTS_TEXT);
		heartsStart.setAlignmentX(CENTER_ALIGNMENT);
		heartsStart.addActionListener(this);
		c.gridy = 5;
		add(heartsStart, c);
		
		// add spacing between components
		c.gridy = 6;
		add(Box.createRigidArea(new Dimension(0, 10)), c);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		
		if(source == minesweeperStart){
			
			showMinesweeperDifficulty();
		}
		else if(source == minesweeperEasyButton){
			
			startMinesweeper(Difficulty.EASY);
		}
		else if(source == minesweeperMediumButton){
			
			startMinesweeper(Difficulty.MEDIUM);
		}
		else if(source == minesweeperHardButton){
	
			startMinesweeper(Difficulty.HARD);
		}
		else if(source == solitaireStart){
			
			startSolitaire();
		}
		else if(source == heartsStart){
			
			startHearts();
		}
	}
	
	/**
	 * Shows the difficulty screen for Minesweeper
	 * 
	 * Converts the original main menu frame into this selection screen
	 * Pops up after the user clicks the Minesweeper button on the original main menu selection screen
	 * Can choose Easy, Medium, or Hard
	 */
	private void showMinesweeperDifficulty(){
		
		minesweeperDifficultyFrame = new JFrame();
		minesweeperDifficultyFrame.setResizable(false);
				
		// add gui components
		minesweeperDifficultyFrame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		JLabel difficultyLabel = new JLabel();
		difficultyLabel.setText(DIFFICULTY_TEXT);
		c.gridy = 0;
		minesweeperDifficultyFrame.add(difficultyLabel, c);
		
		JPanel difficultyButtons = new JPanel(new FlowLayout());
		
		minesweeperEasyButton = new JButton();
		minesweeperEasyButton.setText(EASY_TEXT);
		minesweeperEasyButton.addActionListener(this);
		difficultyButtons.add(minesweeperEasyButton);
					
		minesweeperMediumButton = new JButton();
		minesweeperMediumButton.setText(MEDIUM_TEXT);
		minesweeperMediumButton.addActionListener(this);
		difficultyButtons.add(minesweeperMediumButton);
					
		minesweeperHardButton = new JButton();
		minesweeperHardButton.setText(HARD_TEXT);
		minesweeperHardButton.addActionListener(this);
		difficultyButtons.add(minesweeperHardButton);
					
		c.gridy = 1;
		minesweeperDifficultyFrame.add(difficultyButtons, c);
		
		minesweeperDifficultyFrame.pack();
		minesweeperDifficultyFrame.setVisible(true);
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		minesweeperDifficultyFrame.setLocation(dim.width/2-minesweeperDifficultyFrame.getSize().width/2, dim.height/2-minesweeperDifficultyFrame.getSize().height/2);
		
		mainMenuFrame.setVisible(false);
	}
	
	/**
	 * Starts the Minesweeper game module
	 * 
	 * Launches a new frame with a created MinesweeperView object
	 * Hides the main menu frame
	 * 
	 * @param difficulty	the difficulty level, as chosen during the 
	 */
	private void startMinesweeper(Difficulty difficulty){
		
		// close the select difficulty menu
		minesweeperDifficultyFrame.dispose();
		
		// set up Minesweeper
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().add(new MinesweeperView(difficulty));
		frame.pack();
		frame.setVisible(true);
		
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		// this is so when the Minesweeper window closes, the main menu frame will reappear
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    
		    	mainMenuFrame.setVisible(true);
		    }
		});
		
		mainMenuFrame.setVisible(false);
	}
	
	/**
	 * Starts the Solitaire game module
	 * 
	 * Launches a new frame with a created SolitaireView object
	 * Hides the main menu frame
	 * 
	 * @param difficulty	the difficulty level, as chosen during the 
	 */
	private void startSolitaire(){
		
		// set up solitaire
		JFrame frame = new JFrame();
		frame.setResizable(false);
		SolitaireView view = new SolitaireView(new SolitaireModel());
		frame.getContentPane().add(view);
		frame.pack();
		view.onPack();
		frame.setVisible(true);
		
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		// when the Solitaire window closes, the main menu frame will reappear
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    
		    	mainMenuFrame.setVisible(true);
		    }
		});
		
		mainMenuFrame.setVisible(false);
	}
	
	/**
	 * Starts the Hearts game module
	 * 
	 * Launches a new frame with a created HeartsView object
	 * Hides the main menu frame
	 */
	private void startHearts(){
		
		// set up hearts
		JFrame frame = new JFrame();
		frame.setResizable(false);
		HeartsView view = new HeartsView(new HeartsModel());
		frame.getContentPane().add(view);
		frame.pack();
		frame.setVisible(true);
				
		// center window on screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
			
		// when the Hearts window closes, the main menu frame will reappear
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				    
		    	mainMenuFrame.setVisible(true);
		    }
		});
				
		mainMenuFrame.setVisible(false);
	}
}