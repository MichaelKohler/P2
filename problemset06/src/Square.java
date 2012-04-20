import java.util.ArrayList;

/**
 * The |Square| is a field on the board. It can hold |FoodstuffCubes| and |Amoebe|s.
 * There is no restriction in terms of the amount to be placed on a |Square|.
 */
public class Square {

	private ArrayList<FoodstuffCube> foodstuffCubes = new ArrayList<FoodstuffCube>();
	private ArrayList<Amoebe> amoebes = new ArrayList<Amoebe>();
	private int[] position;

    public Square(int[] position) {
    	this.position = position;
    	setFoodstuffCubes();
    }
    
    /**
     * sets two foodstuff cubes of every color to the square
     */
    public void setFoodstuffCubes() {
        for (int i = 0; i < 2; i++) {
            FoodstuffCube cube = new FoodstuffCube(Game.Color.BLUE);
            this.foodstuffCubes.add(cube);
        }
        for (int i = 0; i < 2; i++) {
            FoodstuffCube cube = new FoodstuffCube(Game.Color.GREEN);
            this.foodstuffCubes.add(cube);
        }
        for (int i = 0; i < 2; i++) {
            FoodstuffCube cube = new FoodstuffCube(Game.Color.RED);
            this.foodstuffCubes.add(cube);
        }
    }
    
    /**
     * returns the position of the square within the board
     * 
     * @return this.position
     */
    public int[] getPosition() {
        return this.position;
    }
    
    /**
     * enter a square with an |Amoebe|. There are no restrictions though
     * the amoebe to be placed on the field shouldn't be null.
     * 
     * @param amoebe    amoebe which enters the square
     */
    public void enterSquare(Amoebe amoebe) {
    	assert(amoebe != null);
    	this.amoebes.add(amoebe);
    	amoebe.setSquare(this);
    }
    
    /**
     * returns the amoebe representation of the square
     * 
     * @return string
     */
    public String getAmoebes(){
    	int redCounter = 0;
    	int blueCounter = 0;
    	int greenCounter = 0;
    	for(int i=0; i<this.amoebes.size(); i++){
    		Amoebe amoebe = this.amoebes.get(i);
    		switch(amoebe.getColor()){
	    		case RED:
	    			redCounter++;
	    			break;
	    		case GREEN:
	    			greenCounter++;
	    			break;
	    		case BLUE:
	    			blueCounter++;
	    			break;
    		}
    	}
    	return "r: "+redCounter+", b: "+blueCounter+", g: "+greenCounter;
    }
    
    public ArrayList<Amoebe> getAmoebesForColor(Game.Color color){
    	ArrayList<Amoebe> amoebesList = new ArrayList<Amoebe>();
    	for(int i=0; i<this.amoebes.size(); i++){
    		if(this.amoebes.get(i).getColor() == color)
    			amoebesList.add(this.amoebes.get(i));
    	}
    	return amoebesList;
    }
    
    /**
     * returns the food representation of the square
     * 
     * @return string
     */

    public String getFood(){
    	int redCounter = 0;
    	int blueCounter = 0;
    	int greenCounter = 0;
    	for(int i=0; i<this.foodstuffCubes.size(); i++){
    		switch(this.foodstuffCubes.get(i).getColor()){
	    		case RED:
	    			redCounter++;
	    			break;
	    		case GREEN:
	    			greenCounter++;
	    			break;
	    		case BLUE:
	    			blueCounter++;
	    			break;
    		}
    	}
    	return "r: "+redCounter+", b: "+blueCounter+", g: "+greenCounter;
    }
    
    /**
     * returns the foodcubes placed on this square
     * 
     * @return ArrayList   foodcubes
     */
    public ArrayList<FoodstuffCube> getFoodcubes(){
    	return this.foodstuffCubes;
    }
    
    /**
     * returns the amoebes placed on this square
     * 
     * @return ArrayList   amoebes
     */
    public ArrayList<Amoebe> getAmoebesList(){
    	return this.amoebes;
    }
    
    /**
     * removes an amoebe from the square
     */
    public void removeAmoebe(Amoebe amoebe) {
        this.amoebes.remove(amoebe);
    }
    
    /**
     * place a foodstuff cube on the square
     * 
     * @param cube   the cube to be placed
     */
    public void placeFoodstuffCube(FoodstuffCube cube) {
        this.foodstuffCubes.add(cube);
    }
    
    /**
     * remove a foodstuff cube off the square
     * 
     * @param cube    cube to be removed
     */
    public void removeFoodstuffCube(FoodstuffCube cube) {
        this.foodstuffCubes.remove(cube);
    }
}