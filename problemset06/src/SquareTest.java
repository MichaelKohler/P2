import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class SquareTest {

    @Test
    public void positionShouldBeCorrect() {
        int[] pos = { 2, 2};
        Square square = new Square(pos);
        assertTrue("Failure: square was not on field 2;2!", square.getPosition()[0] == 2 &&
                        square.getPosition()[1] == 2);
    }
}