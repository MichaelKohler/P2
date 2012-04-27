import com.google.inject.Provider;

public final class PlayerFactory {
	private PlayerFactory( Provider<Compass> compassProvider, Provider<Die> dieProvider, String name ){}
	public static Player get( Provider<Compass> compassProvider, Provider<Die> dieProvider, String name ) {
		return new Player(compassProvider, dieProvider, name);
	}
}
