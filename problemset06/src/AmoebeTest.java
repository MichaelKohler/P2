import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.google.inject.Provider;

public class AmoebeTest {
    private Provider<Compass> compassProvider;
	
    @Test
    public void amoebesColorShouldBeRed() {
    	this.compassProvider = new CompassProvider();
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        assertTrue(amoebe.getColor() == Game.Color.RED); // AK with assertEquals, you'll get much better error messages, when tests fail
    }
    
    @Test
    public void amoebeShouldBeSetToASquare() {
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        int[] position = {0, 0};
        Square square = SquareFactory.get(position);
        amoebe.setSquare(square);
        assertTrue(amoebe.getSquare().getPosition()[0] == 0);
        assertTrue(amoebe.getSquare().getPosition()[1] == 0);
    }
    
    @Test
    public void amoebeShouldHaveCorrectDamagePoints() {
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        assertTrue(amoebe.getDamagePoints() == 0);
        amoebe.addDamagePoint();
        assertTrue(amoebe.getDamagePoints() == 1);
    }
    
    @Test
    public void amoebeShouldDie() {
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        int[] position = {0, 0};
        Square square = SquareFactory.get(position);
        amoebe.setSquare(square);
        square.enterSquare(amoebe);
        assertTrue(square.getAmoebesList().size() == 1);
        amoebe.die();
        assertTrue(square.getAmoebesList().size() == 0);
    }
    
    @Test
    public void amoebeShouldExcrement() {
        Amoebe amoebe = AmoebeFactory.get(this.compassProvider, Game.Color.RED);
        int[] position = {0, 0};
        Square square = SquareFactory.get(position);
        amoebe.setSquare(square);
        square.enterSquare(amoebe);
        assertTrue(square.getFoodcubes().size() == 6);
        amoebe.excrement();
        assertTrue(square.getFoodcubes().size() == 8);
    }
}