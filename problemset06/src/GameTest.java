import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class GameTest {

    private Player[] testPlayers = new Player[3];

    @Test
    public Player[] initBoard() {
        testPlayers[0] = new Player("TestPlayer1");
        testPlayers[1] = new Player("TestPlayer2");
        testPlayers[2] = new Player("TestPlayer3");

        testPlayers[0].chooseColor(Game.Color.BLUE);
        testPlayers[1].chooseColor(Game.Color.GREEN);
        testPlayers[2].chooseColor(Game.Color.RED);

        Board testBoard = new Board(testPlayers);

        return testPlayers;
    }

    @Given("initBoard")
    public void playersShouldBeInitialized(Player[] players) {
        Game game = new Game(players);
        assertTrue(game.getPlayers().length == 3);
    }
}