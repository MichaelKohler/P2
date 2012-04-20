/**
 * The |Gene| helps the player to achieve some goals better. Genes cost though.
 * Every gene has a price, which needs to be paid from the BP count of
 * the player. A |Gene| is just a gene and can't do anything on its own. The power
 * of a gene is implemented within the whole code.
 *
 */
public class Gene {
	
	private String gene;
	private int price;
	private int mutationPoints;
	
	public Gene(String gene, int price, int mutationPoints){
		this.gene = gene;
		this.price = price;
		this.mutationPoints = mutationPoints;
	}

	/**
	 * returns the name of the gene
	 * 
	 * @return name of the gene
	 */
	public String getGene(){
		return gene;
	}
	
	/**
	 * returns the mutation points of the gene
	 * 
	 * @return the mutation points of the gene
	 */
	public int getMutationPoints(){
		return mutationPoints;
	}
	
	/**
	 * returns the price of the gene
	 * 
	 * @return price of the gene
	 */
	public int getPrice(){
		return price;
	}
	
	
}