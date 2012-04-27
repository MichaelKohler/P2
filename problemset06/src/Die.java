/**
 * The |Die| class is responsible for returning a random number between 1 and 6.
 */
public final class Die implements IDie {
	private static final int FACES = 6;
    
    /**
     * Returns the number rolled which has to be in between 1 and 6.
     * 
     * @param none
     * @return result    number of eyes on the die
     */
    public int roll() {
        int result = 1 + (int) (FACES * Math.random());
        assert result >= 1 && result <= FACES;
        return result;
    }
    
    /**
     * Returns a number between the first and second parameter. It is used
     * to place the amoebes randomly on the board.
     * 
     * @param low minimum number
     *              high maximum number
     */
    public int roll(int low, int high) {
        assert low < high;
        long range = (long) high - (long) low + 1;
        long fraction = (long) (range * Math.random());
        int randomNumber = (int) (fraction + low);
        return randomNumber;
    }
    
    /**
     * Returns the number of faces
     */
    public static int getFaces(){
    	return FACES;
    }
    
    /**
     * returns a string representation of the object
     * 
     * @return String
     */
    @Override
    public String toString(){
		return "[ faces="+this.FACES+"]";
    }
}