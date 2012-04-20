import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * |EnvironmentCardTest| tests the class |EnvironmentCard|
 */
public class EnvironmentCardTest {

    @Test
    public void calculateOzonLayerShouldWorkCorrectly() {
        IDie mockedDie = mock(IDie.class);
        when(mockedDie.roll(anyInt(), anyInt())).thenReturn(3);
        EnvironmentCard card = new EnvironmentCard(mockedDie);
        assertTrue(Compass.direction == Compass.Direction.EAST);
        assertTrue(Compass.ozonLayer == 3);
    }
}
