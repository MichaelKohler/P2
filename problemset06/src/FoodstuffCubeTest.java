import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class FoodstuffCubeTest {

    @Test
    public void colorShouldBeCorrect() {
        FoodstuffCube cube = new FoodstuffCube(Game.Color.BLUE);
        assertTrue("Failure: the cube's color was not blue!", cube.getColor() == Game.Color.BLUE);
    }
    
    @Test
    public void repShouldBeCorrect() {
        FoodstuffCube cube = new FoodstuffCube(Game.Color.BLUE);
        assertTrue("Failure: the string is not what it should be!", cube.toString().equals("[ color=blue]"));
    }
}
