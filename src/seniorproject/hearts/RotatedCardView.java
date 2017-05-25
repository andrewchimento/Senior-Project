package seniorproject.hearts;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import seniorproject.utilities.Card;
import seniorproject.utilities.CardView;

/**
 * Creates a rotated version of the CardView class to display in a game of Hearts
 * 
 * When a card is played from any computer (i.e. any direction that is not from the player), the card must be rotated
 * The actual rotating functionality (rotate(BufferedImage) was written by Aleksandr Movsesyan from StackOverflow.com and adapted to work in my RotatedCardView class
 * This class extends the {@link CardView CardView} class
 * 
 * @author Andrew
 */
public class RotatedCardView extends CardView{

	private static final long serialVersionUID = 1L;

	private int degrees;
	private double theta;
	
	/**
	 * A constructor for the RotatedCardView class
	 * 
	 * @param cardModel	the underlying Card object
	 * @param width		the width of the card to be displayed
	 * @param height	the height of the card to be displayed
	 * @param direction	the direction the card is coming from
	 */
	public RotatedCardView(Card cardModel, int width, int height, HeartsDirections direction){
		
		switch(direction){
		case LEFT:
			degrees = 90;
			theta = Math.toRadians(90);
			this.width = height;
			this.height = width;
			break;
		case RIGHT:
			degrees = 270;
			theta = Math.toRadians(270);
			this.width = height;
			this.height = width;
			break;
		case ACROSS:
			degrees = 180;
			theta = Math.toRadians(180);
			this.width = width;
			this.height = height;
			break;
		default:
			break;
		}
		
		BufferedImage img = null;
		try{
			img = ImageIO.read(new File("res/cards/" + cardModel.getValue() + "_of_" + cardModel.getSuit() + ".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		img = rotate(img);
		ImageIcon icon = new ImageIcon(img.getScaledInstance(this.width, this.height, Image.SCALE_DEFAULT));
		setIcon(icon);
	}
	
	// The following code was written by Aleksandr Movsesyan from StackOverflow.com and adapted to work in my RotatedCardView class
	public BufferedImage rotate(BufferedImage image)
	{
	  /*
	   * Affline transform only works with perfect squares. The following
	   *   code is used to take any rectangle image and rotate it correctly.
	   *   To do this it chooses a center point that is half the greater
	   *   length and tricks the library to think the image is a perfect
	   *   square, then it does the rotation and tells the library where
	   *   to find the correct top left point. The special cases in each
	   *   orientation happen when the extra image that doesn't exist is
	   *   either on the left or on top of the image being rotated. In
	   *   both cases the point is adjusted by the difference in the
	   *   longer side and the shorter side to get the point at the 
	   *   correct top left corner of the image. NOTE: the x and y
	   *   axes also rotate with the image so where width > height
	   *   the adjustments always happen on the y axis and where
	   *   the height > width the adjustments happen on the x axis.
	   *   
	   */
	  AffineTransform xform = new AffineTransform();

	  if (image.getWidth() > image.getHeight())
	  {
	    xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
	    xform.rotate(theta);

	    int diff = image.getWidth() - image.getHeight();

	    switch (degrees)
	    {
	    case 90:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	      break;
	    case 180:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	      break;
	    default:
	      xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
	      break;
	    }
	  }
	  else if (image.getHeight() > image.getWidth())
	  {
	    xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
	    xform.rotate(theta);

	    int diff = image.getHeight() - image.getWidth();

	    switch (degrees)
	    {
	    case 180:
	      xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	      break;
	    case 270:
	      xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	      break;
	    default:
	      xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
	      break;
	    }
	  }
	  else
	  {
	    xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
	    xform.rotate(theta);
	    xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
	  }

	  AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

	  return op.filter(image, null);
	}
}
