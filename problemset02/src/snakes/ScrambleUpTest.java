package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ch.unibe.jexample.JExample;
import ch.unibe.jexample.Given;
import org.junit.Test;

import org.junit.runner.RunWith;

@RunWith(JExample.class)
public class ScrambleUpTest {
	
	private Player jack;
	private Player jill;

	@Test
	public Game newGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		Game game = new Game(12, args);
		game.setSquareToScrambleUp(4);
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("newGame")
	public Game jillToScrambleUp(Game game) {
	    game.movePlayer(1); // jack lands on field 2
	    assertTrue(game.notOver());
	    assertEquals(2, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jill, game.currentPlayer());
		
        game.movePlayer(3); // jill lands on field 4 -> Scramble Up
		assertTrue(game.notOver());
		assertEquals(4, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jack, game.currentPlayer());
		
		return game;
	}
	
	@Given("jillToScrambleUp")
    public Game notLandingOnField4DoesntScramble(Game game) {
        game.movePlayer(1); // jack lands on field 5
        assertTrue(game.notOver());
        assertEquals(5, jack.position());
        assertEquals(2, jill.position());
        assertEquals(jill, game.currentPlayer());
        
        return game;
    }
	
	@Given("notLandingOnField4DoesntScramble")
	public Game jackToScrambleUp(Game game) {
	    game.movePlayer(4); // jill lands on field 6
	    assertTrue(game.notOver());
	    assertEquals(6, jill.position());
	    assertEquals(5, jack.position());
	    assertEquals(jack, game.currentPlayer());
	    
	    game.movePlayer(1); // jack lands on field 6 -> back to 1
	    assertTrue(game.notOver());
	    assertEquals(1, jack.position());
	    assertEquals(6, jill.position());
	    assertEquals(jill, game.currentPlayer());
	    
	    game.movePlayer(1); // jill lands on field 7
	    assertTrue(game.notOver());
	    assertEquals(7, jill.position());
	    assertEquals(1, jack.position());
        assertEquals(jack, game.currentPlayer());
	   
	    game.movePlayer(3); // jack lands on field 4 -> ScrambleUp
	    assertTrue(game.notOver());
	    assertEquals(7, jack.position());
	    assertEquals(4, jill.position());
	    
	    return game;
	}
}
