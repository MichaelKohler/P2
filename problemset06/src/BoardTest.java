import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Provider;

@RunWith(JExample.class)
public class BoardTest {

    private Player[] testPlayers = new Player[3];
    private Provider<Die> dieProvider = new DieProvider();
    private Provider<Compass> compassProvider = new CompassProvider();

    @Test
    public Board initBoard() {
    	
        testPlayers[0] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer1");
        testPlayers[1] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer2");
        testPlayers[2] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer3");

        testPlayers[0].chooseColor(Game.Color.BLUE);
        testPlayers[1].chooseColor(Game.Color.GREEN);
        testPlayers[2].chooseColor(Game.Color.RED);

        Board testBoard = BoardFactory.get(this.compassProvider, this.dieProvider, testPlayers);

        return testBoard;
    }
    
    @Given("initBoard")
    public Board boardSizeShouldBeCorrect(Board testBoard) {
        assertTrue(Board.getBoardDimensions()[0] == 5 &&
                         Board.getBoardDimensions()[1] == 5);

    	return testBoard;
    }
}