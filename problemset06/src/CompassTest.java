import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class CompassTest {

    private Compass compass;

    @Test
    public Compass initCompass() {
        compass = new Compass();
        return compass;
    }

    @Given("initCompass")
    public Compass compassShouldChangeDirection(Compass compass) {
        assertTrue(Compass.direction == Compass.Direction.SOUTH);
        Compass.direction = Compass.Direction.NORTH;
        assertTrue(Compass.direction == Compass.Direction.NORTH);
        return compass;
    }

    @Given("initCompass")
    public Compass ozonLayerShouldChange(Compass compass) {
        assertTrue(Compass.ozonLayer == 0);
        Compass.ozonLayer = 5;
        assertTrue(Compass.ozonLayer == 5);
        return compass;
    }
}