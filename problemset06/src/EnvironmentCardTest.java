import org.junit.Test;

import com.google.inject.Provider;

import static org.junit.Assert.*;

/**
 * |EnvironmentCardTest| tests the class |EnvironmentCard|
 */
public class EnvironmentCardTest {

    @Test
    public void calculateOzonLayerShouldWorkCorrectly() {
    	Provider<IDie> iDieProvider = new IDieProvider();
        EnvironmentCard card = EnvironmentCardFactory.get(iDieProvider);
        assertTrue(Compass.direction == Compass.Direction.EAST);
        assertTrue(Compass.ozonLayer == 3);
    }
}
