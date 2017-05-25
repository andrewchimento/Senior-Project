package seniorproject.utilities;

/**
 * A collection of constant values to be used in context where cards are also used
 * 
 * These constants are generally interpreted as pixel values
 * 
 * @author Andrew
 */
public class CardDimensions {

	public static final int SOLITAIRE_CARD_WIDTH = 121;
	public static final int SOLITAIRE_CARD_HEIGHT = 175;
	public static final int HEARTS_CARD_WIDTH = (int) (SOLITAIRE_CARD_WIDTH * 0.8);
	public static final int HEARTS_CARD_HEIGHT = (int) (SOLITAIRE_CARD_HEIGHT * 0.8);
	public static final int BORDER_AMOUNT = 20;
	public static final int X_FUDGE = (int) SOLITAIRE_CARD_WIDTH / 2;
	public static final int SOLITAIRE_OFFSET = 30;
	public static final int HEARTS_OFFSET = HEARTS_CARD_WIDTH / 2;
	public static final double SCREEN_RATIO = 0.8;
	public static final float FONT_SIZE = 25;
}
