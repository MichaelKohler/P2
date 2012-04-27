import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.google.inject.Provider;

/**
 * A |Player| is initialized for every player who wants to play. For this
 * Problemset we assume it is always 3. A player has a name, a color and
 * a score.
 */
public final class Player {
	
	private final String name;
	private Game.Color color;
	private int score;
	private List<Gene> genes = new ArrayList<Gene>();
	private int bp;
	private final Provider<Die> dieProvider;
	private final Provider<Compass> compassProvider;
	
	public Player(Provider<Compass> compassProvider, Provider<Die> dieProvider, String playerName) {
		this.compassProvider = compassProvider;
		this.dieProvider = dieProvider;
		this.name = playerName;
		this.color = null;
		this.score = 0;
		this.bp = 4;
	}
	
	/**
	 * Sets the color of the players. Available colors can be found in Game.Color
	 * 
	 * @param color   appropriate color for the player
	 */
	public void chooseColor(Game.Color color) {
		this.color = color;
		System.out.println("Player " + this.name + " has been fully initialized.. Color: " +
                this.color.toString() + ".. Score: " + this.score + "\n");
	}
	
	/**
	 * returns the name of the player
	 * 
	 * @return this.name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * returns the color of the player
	 * 
	 * @return this.color
	 */
	public Game.Color getColor() {
		return this.color;
	}
	
	/**
	 * returns the player's score. Initially (i.e. in round 1) it is 0.
	 * 
	 * @return this.score
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * return the genes
	 */
	public List<Gene> getGenes(){
		return Collections.unmodifiableList(this.genes);
	}
	
	/**
	 * returns whether the player has the specified
	 * gene or not.
	 * 
	 * @param name of the gene
	 * @return boolean
	 */
	public boolean hasGene(String name) {
		boolean findSuccess = false;
	    for (int i = 0; i < this.genes.size(); i++) {
	        findSuccess = this.genes.get(i).getGene().equals(name);
	    }
	    return findSuccess;
	}
	
	/**
	 * adds a gene to the player
	 */
	public void addGene(Gene gene) {
		this.genes.add(gene);
	}
	
	/**
	 * return the BP count
	 * 
	 * @return this.bp
	 */
	public int getBp(){
		return this.bp;
	}
	
	/**
	 * set the BP count
	 * 
	 * @param number
	 */
	public void setBp(int number){
		this.bp = number;
	}
	
	/**
	 * add the number of points to the score
	 * 
	 * @param score
	 */
	public void addScore(int score) {
	    this.score += score;
	}
	
	/**
	 * split the amoeba. This happens in phase 4.
	 */
	public void splitAmoeba(){
	    Amoebe TMPamoebe = AmoebeFactory.get(this.compassProvider, this.color);
	    Amoebe newAmoebe = TMPamoebe.divide();
	    Die die = this.dieProvider.get();
	    int[] position = { -1, -1 };
	    do {
	        position[0] = die.roll(1, 5) - 1;
	        position[1] = die.roll(1, 5) - 1;
	    } while (position[0] == 2 && position[1] == 2);
	    // TODO: check if an amoeba is in a field right next to this
	    //            otherwise the move is not allowed
	    Board.board[position[0]][position[1]].enterSquare(newAmoebe);
	    newAmoebe.setSquare(Board.board[position[0]][position[1]]);
	    System.out.println("new Amoeba placed on field (" + position[0] + " / " + position[1] + ")");
	    this.setBp(this.getBp() - 6);
	}

	/**
     * returns a string representation of the object
     * 
     * @return String
     */
	@Override
    public String toString(){
		return "[ name="+this.name+", color="+this.color+", score="+this.score+", genes="+this.genes+", bp="+this.bp+"]";
    }
}
