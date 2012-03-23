package snakes;

/*
 * The |Die| randomly defines how many squares (a positive number
 * and always less or equal to |FACES|) the player moves forward.
 */
public class Die implements IDie {
	static final int FACES = 6;
	
	/*
     * @see snakes.IDie#roll()
     */
	@Override
    public int roll() {
		int result = 1 + (int) (FACES * Math.random());
		assert result >= 1 && result <= FACES;
		return result;
	}
}
