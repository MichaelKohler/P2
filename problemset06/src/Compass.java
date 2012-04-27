/**
 * The |Compass| decides in which direction the amoebes drift. At
 * the moment it is determined randomly. A compass has the same
 * direction as a traditional compass (i.e. north, south, west, east).
 *
 */
public final class Compass {

    public enum Direction { 
        NORTH("north"),
        SOUTH("south"),
        WEST("west"),
        EAST("east");
        
        private String rep;
        Direction(String s) {
            this.rep = s;
        }
        public String toString() {
            return this.rep;
        }
    }

    public static Direction direction;
    public static int ozonLayer;

    public Compass() {
        direction = Direction.SOUTH;
        ozonLayer = 0;
    }
    
    /**
     * returns a string representation of the object
     * 
     * @return String
     */
    @Override
    public String toString(){
		return "[direction="+this.direction+", ozon layer="+this.ozonLayer+"]";
    }
}
