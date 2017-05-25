package seniorproject.utilities;

/**
 * A simple ordered pair class
 * 
 * @author Elliot from www.stackoverflow.com
 * @param <X>	the left coordinate
 * @param <Y>	the right coordinate
 */
public class Pair<X,Y> {
    private X x;
    private Y y;
    public Pair(X x, Y y){
        this.x = x;
        this.y = y;
    }
    public X getX(){ return x; }
    public Y getY(){ return y; }
    public void setX(X x){ this.x = x; }
    public void setR(Y y){ this.y = y; }
    
    /**
	 * Converts the object to string form
	 * Used primarily in debugging
	 */
    @Override
    public String toString(){
    	
    	return x.toString() + ", " + y.toString();
    }
}