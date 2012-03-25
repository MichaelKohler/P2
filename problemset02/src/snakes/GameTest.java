package snakes;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/*
 * AK You tested quite extensively, but it seems, you still haven't enabled
 * assertions. I also added an example for a useful test with mocking,
 * and I'm sorry if I messed up your code... >_>
 * 
 * For your good work, your problemset is of course 
 * 
 * ACCEPTED
 */
@RunWith(JExample.class)
public class GameTest {
    public Player jack;
    public Player jill;
    public Player jason;
    
    @Test
    public IGame setup() {
        this.jack = new Player("jack");
        this.jill = new Player("jill");
        this.jason = new Player("jason"); // AK added this
        Player[] players = {jack, jill, jason};
        IGame game = new Game(10, players);
        assertTrue(game != null);
        return game;
    }

    @Given("setup")
    public IGame playTest(IGame game) {
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
    
    // AK gladly! I changed the play() method slightly and put the inside part of the loop into a separate method
    @Given("setup")
    public IGame scrambleUpTest(IGame game) {
    	game.setSquareToScrambleUp(5);
    	IDie mockedDie = mock(IDie.class);
        IGame gameSpy = spy(game); // AK lets most calls though...
        when(mockedDie.roll()).thenReturn(4);
    	when(gameSpy.getRandomOtherPlayer()).thenReturn(jason); // ...except for this one
    	
        game.playOneRound(mockedDie);
        assertThat((Integer)jason.square().position(), equalTo(5));
    	// AK and here we are done testing that the ScrambleUpSquare does what it is supposed to
        // independent from the game.
    	return game;
    }
}
