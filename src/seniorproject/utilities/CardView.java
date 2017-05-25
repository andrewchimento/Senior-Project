package seniorproject.utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * CardView instances are used in the GUI's of Solitaire and Hearts. They each control their member cardModel
 * 
 * @author Andrew
 */
public class CardView extends JLabel{

	protected static final long serialVersionUID = 1L;
	protected Card cardModel;
	protected int width;
	protected int height;
	
	/**
	 * Default constructor of a CardView
	 * 
	 * Used for initializing data structures
	 * 
	 * @return a default CardView object
	 */
	public CardView(){}
	
	/**
	 * @param cardModel	the cardModel to associate this CardView instance with
	 * @param width		the pixel width of the CardView
	 * @param height	the pixel height of the CardView
	 * @return			a CardView object
	 * @exception		IOException if the image file can't be found
	 */
	public CardView(Card cardModel, int width, int height){
		
		this.cardModel = cardModel;
		this.width = width;
		this.height = height;
		
		// set the icon of the CardView
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("res/cards/" + cardModel.getValue() + "_of_" + cardModel.getSuit() + ".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		ImageIcon icon = new ImageIcon(img.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT));
		setIcon(icon);
	}
	
	public Card getCard(){
		
		return cardModel;
	}
	
	/**
	 * Draws a CardView onto a layered pane
	 * 
	 * Because a layered pane has a null layout, the bounds must be set explicitly
	 * 
	 * @param view	the view that the card is being drawn on
	 * @param x		the x coordinate that the upper left corner of the CardView is drawn on
	 * @param y		the y coordinate that the upper left corner of the CardView is drawn on
	 */
	public void drawOnLayeredPane(JLayeredPane view, int x, int y){
		
		setBounds(x, y, width, height);
		view.add(this, 0);
	}
	
	/**
	 * Converts the object to string form
	 * Used primarily in debugging
	 * 
	 * @return the String object which represents the CardView object
	 */
	@Override
	public String toString(){
		
		return cardModel.toString();
	}
}
