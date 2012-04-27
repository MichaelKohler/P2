import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Provider;

@RunWith(JExample.class)
public class GameTest {

    private Player[] testPlayers = new Player[3];
    private static Provider<Compass> compassProvider = new CompassProvider();
    private static Provider<Die> dieProvider = new DieProvider();
    
    @Test
    public Player[] initBoard() {
        testPlayers[0] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer1");
        testPlayers[1] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer2");
        testPlayers[2] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer3");

        testPlayers[0].chooseColor(Game.Color.BLUE);
        testPlayers[1].chooseColor(Game.Color.GREEN);
        testPlayers[2].chooseColor(Game.Color.RED);

        Board testBoard = BoardFactory.get(compassProvider, dieProvider, testPlayers);

        return testPlayers;
    }

    @Given("initBoard")
    public void playersShouldBeInitialized(Player[] players) {
        Game game = GameFactory.get(players);
        assertTrue(game.getPlayers().length == 3);
    }
}