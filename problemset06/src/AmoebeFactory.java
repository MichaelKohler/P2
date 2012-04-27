import com.google.inject.Provider;

public final class AmoebeFactory {
	private AmoebeFactory(Provider<Compass> compassProvider, Game.Color color){}
	public static Amoebe get( Provider<Compass> compassProvider, Game.Color color) {
		return new Amoebe(compassProvider, color);
	}
}
