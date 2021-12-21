
public class Point {
	
	/**
     * Attributs
     */
    public final int x;
    public final int y;

    /**
     * Constructeur de l'objet point
     * 
     * @param x 
     * @param y
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur par copie de l'objet point
     * 
     * @param p 
     */
    public Point(Point p){
        this.x = p.x;
        this.y = p.y;
    }
    
    @Override
    public String toString() {
    	return "x = "+this.x + " y = "+ this.y;
    }

}
