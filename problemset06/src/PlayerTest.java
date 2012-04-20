import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class PlayerTest {

    private Player[] testPlayers = new Player[1];

    @Test
    public Player[] initPlayer() {
        testPlayers[0] = new Player("TestPlayer1");
        testPlayers[0].chooseColor(Game.Color.RED);

        return testPlayers;
    }

    @Given("initPlayer")
    public void playerShouldBeInitialized(Player[] players) {
        Game game = new Game(players);
        assertTrue(game.getPlayers().length == 1);
    }
    
    @Given("initPlayer")
    public void playerShouldHaveScoreZero() {
        assertTrue(testPlayers[0].getScore() == 0);
    }
    
    @Given("initPlayer")
    public void playerShouldHaveAName() {
        assertTrue(!testPlayers[0].getName().equals(""));
    }
    
    @Given("initPlayer")
    public void playerShouldHaveAColor() {
        assertTrue(testPlayers[0].getColor() == Game.Color.RED);
    }
}