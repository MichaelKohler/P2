import static org.junit.Assert.*;
import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import com.google.inject.Provider;

@Category(IIntegrationTests.class)
@RunWith(JExample.class)
public class PlayerIntegrationTest {

    private Player[] testPlayers = new Player[1];
    private Provider<Die> dieProvider = new DieProvider();
    private Provider<Compass> compassProvider = new CompassProvider();

    @Test
    public Player[] initPlayer() {
        testPlayers[0] = PlayerFactory.get(compassProvider, dieProvider, "TestPlayer1");
        testPlayers[0].chooseColor(Game.Color.RED);
        

        return testPlayers;
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