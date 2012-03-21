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
		Game game = new Game(6, args);
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
	    game.movePlayer(1);
	    assertEquals(2, jack.position());
		assertEquals(1, jill.position());
        game.movePlayer(3);
		assertTrue(game.notOver());
		assertEquals(4, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Test
	public Game newerGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		Game game = new Game(6, args);
		game.setSquareToScrambleUp(3);
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
	
	@Given("newerGame")
	public Game jackToScrambleUp(Game game) {
	    game.movePlayer(1);
	    assertEquals(2, jack.position());
		assertEquals(1, jill.position());
        game.movePlayer(2);
		assertTrue(game.notOver());
		assertEquals(3, jack.position());
		assertEquals(2, jill.position());
		assertEquals(jack, game.currentPlayer());
		return game;
	}
}
