import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.inject.Provider;

/**
 * real test for the amoebes class.
 */
public class AmoebeTest {
    private Provider<Compass> compassProvider;
    
    @Test
    public void amoebesColorShouldBeRed() {
        this.compassProvider = new CompassProvider();
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        assertTrue(amoebe.getColor() == Game.Color.RED); // AK with assertEquals, you'll get much better error messages, when tests fail
    }
}
