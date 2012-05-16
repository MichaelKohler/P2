import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Provider;

@RunWith(JExample.class)
public class CompassTest {

    private Compass compass;
    Provider<Compass> compassProvider;

    /*
     * AK check the behaviour, *not* the data of the tested class.
     * If your getter/setters do just that, don't test them. If there's
     * nothing lese in your class, it's evidence that there is something
     * wrong about it (or at least that it does not need to be tested).
     */
    @Test
    public Compass initCompass() {
    	this.compassProvider = new CompassProvider();
        compass = this.compassProvider.get();
        return compass;
    }

    @Given("initCompass")
    public Compass compassShouldChangeDirection(Compass compass) {
        assertTrue(Compass.direction == Compass.Direction.SOUTH);
        Compass.direction = Compass.Direction.NORTH;
        assertTrue(Compass.direction == Compass.Direction.NORTH); // AK you are aware, that this can not possibly fail, rihgt? (except if your JVM got blown to pieces)
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