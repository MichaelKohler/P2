import com.google.inject.Provider;

public class CompassProvider implements Provider<Compass> {
	protected void configure() {
		// Do nothing.
	}

	@Override
	public Compass get() {
		// TODO Auto-generated method stub
		return new Compass();
	}
}
