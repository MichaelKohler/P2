import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class PlayerTest {

    private Player[] testPlayers = new Player[1];
    
    @Test
    public Player[] initPlayer() {
        testPlayers[0] = new Player(null, null, "TestPlayer1");
        testPlayers[0].chooseColor(Game.Color.RED);
        
        Game game = GameFactory.get(testPlayers);
        assertTrue(game.getPlayers().length == 1);

        return testPlayers;
    }
}
