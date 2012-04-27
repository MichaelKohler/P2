public final class FoodstuffCubeFactory {
	private FoodstuffCubeFactory( Game.Color color ){}
	public static FoodstuffCube get( Game.Color color ) {
		return new FoodstuffCube(color);
	}
}
