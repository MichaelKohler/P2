/**
 * Action.java
 * Autoren:
 *   Michael Kohler - 11-108-289
 *   Lukas Diener - 11-123-213
 *
 * This class is responsible to hold the actions, defined as direction
 * and distance. This is what the parser returns to the caller. 
 * 
 * Directions are stored as an enum of type Direction, defined
 * in Direction.java
 * 
 * Public getters for convenience.
 */

public class Action {
	private Direction direction;
	private int distance;
	
	public Action(Direction direction, int distance){
		assert direction != null;
		assert new Integer(distance) != null;
		this.direction = direction;
		this.distance = distance;
	}

	/**
	 * Returns the direction.
	 * 
	 * This shouldn't be null.
	 * 
	 * @params    none
	 * @return    direction    the direction to walk
	 */
	public Direction getDirection(){
		assert this.direction != null;
		return this.direction;
	}
	
	/**
	 * Returns the distance to walk.
	 * 
	 * The distance shouldn't be null. (not asserted)
	 * 
	 * @param    none
	 * @return    distance    distance to walk
	 */
	public int getDistance(){
		return this.distance;
	}
	
	@Override
	public boolean equals(Object other) {
	    if(other == this) {
	      return true;
	    }
	    if(!(other instanceof Action)) {
	      return false;
	    }
	 
	    Action p = (Action) other;
	    return this.direction.equals(p.direction) &&
	      this.distance == p.distance;
	}
	
	@Override
	public String toString(){
		return this.direction+" "+this.distance;
	} 
}
