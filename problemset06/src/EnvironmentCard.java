import com.google.inject.Provider;

/**
 * The |Environment| card decides in which direction the amoebes
 * drift in the round and how thick the ozon layer is. It is put on top
 * of the compass at phase 2 of every round.
 *
 */
public final class EnvironmentCard implements IEnvironmentCard {

	private final Provider<IDie> dieProvider;
	
    public EnvironmentCard(Provider<IDie> dieProvider) {
    	this.dieProvider = dieProvider;
        Compass.direction = calculateNewDirection();
        Compass.ozonLayer = calculateOzonLayer();
    }

    /**
     * calculate new direction
     */
    public Compass.Direction calculateNewDirection() {
        int directionNumber = dieProvider.get().roll(1, 4);
        switch (directionNumber) {
            case 1:
                return Compass.Direction.NORTH;
            case 2:
                return Compass.Direction.SOUTH;
            case 3:
                return Compass.Direction.EAST;
            case 4:
                return Compass.Direction.WEST;
            default:
                return Compass.Direction.SOUTH;
        }
    }

    /**
     * calculate ozon layer thickness
     */
    public int calculateOzonLayer() {
        return this.dieProvider.get().roll(0, 10);
    }
    
    @Override
    public String toString(){
    	//TODO
		return "[NOTHING YET.]";
    }
}
