/**
 * 
 */

/**
 * Authors:
 *      Michael Kohler - 11-108-289 
 *      Lukas Diener - 11-123-213
 *
 */
public class Action {
	private Direction direction;
	private int amount;
	
	public Action(Direction direction, int amount){
		this.direction = direction;
		this.amount = amount;		
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public int getAmount(){
		return this.amount;
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
	      this.amount == p.amount;
	}
	
	@Override
	public String toString(){
		return this.direction+" "+this.amount;
	}
}
