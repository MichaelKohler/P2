public final class GeneFactory {
	private GeneFactory( String gene, int price, int mutationPoints ){}
	public static Gene get( String gene, int price, int mutationPoints ) {
		return new Gene( gene, price, mutationPoints );
	}
}