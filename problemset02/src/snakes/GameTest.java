package snakes;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(JExample.class)
public class GameTest {
    public Player jack;
    public Player jill;
    
    @Test
    public Game setup() {
        this.jack = new Player("jack");
        this.jill = new Player("jill");
        Player[] players = {jack, jill};
        Game game = new Game(10, players);
        assertTrue(game != null);
        return game;
    }

    @Given("setup")
    public Game playTest(Game game) {
        IDie mockedDie = mock(IDie.class);
        
        when(mockedDie.roll()).thenReturn(1);
        game.play(mockedDie);
        
        // verify mock
        assertEquals(game.winner(), jack);
        
        return game;
    }
    
    /*
     * MK I tried to add further tests, but I don't see any Test I could make
     * using Mocks that really make sense.
     * @AK: could you give me an example?
     */
}
