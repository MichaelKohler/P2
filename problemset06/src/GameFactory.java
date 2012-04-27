public final class GameFactory {
	private GameFactory( Player[] players ){}
	public static Game get( Player[] players ) {
		return new Game(new CompassProvider(), new IDieProvider(), new DieProvider(), players );
	}
}