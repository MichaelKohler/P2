import com.google.inject.Provider;

public final class BoardFactory {
	private BoardFactory( Provider<Compass> compassProvider, Provider<Die> dieProvider, Player[] players){}
	public static Board get(Provider<Compass> compassProvider, Provider<Die> dieProvider, Player[] players) {
		return new Board(compassProvider, dieProvider, players);
	}
}
