public final class SquareFactory {
	private SquareFactory(Game.Color color){}
	public static Square get(int[] position) {
		return new Square(position);
	}
}
