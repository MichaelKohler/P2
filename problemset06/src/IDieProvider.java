import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.inject.Provider;

public class IDieProvider implements Provider<IDie> {
	protected void configure() {
        // Do nothing.
    }

	@Override
	public IDie get() {
		IDie mockedDie = mock(IDie.class);
        when(mockedDie.roll(anyInt(), anyInt())).thenReturn(3);
		return mockedDie;
	}
}