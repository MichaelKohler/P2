package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import ch.unibe.jexample.Given;
import org.junit.Test;

public class InstantLoseTest {
    private Player jack;
    private Player jill;
    private IGame game;

    @Test
    public void initTestScenario() {
        jack = new Player("Jack");
        jill = new Player("Jill");
        Player[] args = { jack, jill };
        game = new Game(12, args);
        game.setSquareToInstantLose(6);
        assertTrue(game.notOver());
        assertTrue(game.firstSquare().isOccupied());
        assertEquals(1, jack.position());
        assertEquals(1, jill.position());
        assertEquals(jack, game.currentPlayer());
    }
    
    @Given("initTestScenario")
    public void notLandingOnFieldDoesntRemove() {
        game.movePlayer(4); // Jack is now one field before the InstantLose
        assertTrue(game.playerStillPlaying(game.currentPlayer()));
    }

    @Given("notLandingOnFieldDoesntRemove")
    public void landingOnFieldRemovesPlayer() {
        game.movePlayer(3);
        assertFalse(game.playerStillPlaying(game.currentPlayer()));
    }
}
