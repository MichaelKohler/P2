import com.google.inject.Provider;

public class DieProvider implements Provider<Die> {
	protected void configure() {
        // Do nothing.
    }

	@Override
	public Die get() {
		Die die = new Die();
		return die;
	}
}