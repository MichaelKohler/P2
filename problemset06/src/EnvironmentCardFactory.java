import com.google.inject.Provider;

public final class EnvironmentCardFactory {
	private EnvironmentCardFactory(Provider<IDie> iDieProvider ){}
	public static EnvironmentCard get(Provider<IDie> iDieProvider ) {
		return new EnvironmentCard( iDieProvider );
	}
}