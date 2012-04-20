import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class AmoebeTest {
    
    @Test
    public void amoebesColorShouldBeRed() {
        Amoebe amoebe = new Amoebe(Game.Color.RED);
        assertTrue(amoebe.getColor() == Game.Color.RED);
    }
    
    @Test
    public void amoebeShouldBeSetToASquare() {
        Amoebe amoebe = new Amoebe(Game.Color.RED);
        int[] position = {0, 0};
        Square square = new Square(position);
        amoebe.setSquare(square);
        assertTrue(amoebe.getSquare().getPosition()[0] == 0);
        assertTrue(amoebe.getSquare().getPosition()[1] == 0);
    }
    
    @Test
    public void amoebeShouldHaveCorrectDamagePoints() {
        Amoebe amoebe = new Amoebe(Game.Color.RED);
        assertTrue(amoebe.getDamagePoints() == 0);
        amoebe.addDamagePoint();
        assertTrue(amoebe.getDamagePoints() == 1);
    }
    
    @Test
    public void amoebeShouldDie() {
        Amoebe amoebe = new Amoebe(Game.Color.RED);
        int[] position = {0, 0};
        Square square = new Square(position);
        amoebe.setSquare(square);
        square.enterSquare(amoebe);
        assertTrue(square.getAmoebesList().size() == 1);
        amoebe.die();
        assertTrue(square.getAmoebesList().size() == 0);
    }
    
    @Test
    public void amoebeShouldExcrement() {
        Amoebe amoebe = new Amoebe(Game.Color.RED);
        int[] position = {0, 0};
        Square square = new Square(position);
        amoebe.setSquare(square);
        square.enterSquare(amoebe);
        assertTrue(square.getFoodcubes().size() == 6);
        amoebe.excrement();
        assertTrue(square.getFoodcubes().size() == 8);
    }
}