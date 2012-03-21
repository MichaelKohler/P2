package snakes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import ch.unibe.jexample.Given;
import ch.unibe.jexample.JExample;

@RunWith(JExample.class)
public class InstantLoseTest {
	private Player jack;
	private Player jill;

	@Test
	public Game newGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		Player[] args = { jack, jill };
		Game game = new Game(12, args);
		game.setSquareToLadder(2, 4);
		game.setSquareToLadder(7, 2);
		game.setSquare(5, new InstantLose(game, 5));
		assertTrue(game.notOver());
		assertTrue(game.firstSquare().isOccupied());
		assertEquals(1, jack.position());
		assertEquals(1, jill.position());
		assertEquals(jack, game.currentPlayer());
		assertTrue(game.getSquare(5).isInstantLose());
		return game;
	}

	@Given("newGame")
	public Game initialStrings(Game game) {
		assertEquals("Jack", jack.toString());
		assertEquals("Jill", jill.toString());
		assertEquals("[1<Jack><Jill>]", game.firstSquare().toString());
		assertEquals("[5It's a trap!]", game.getSquare(5).toString());
		return game;
	}

	@Given("initialStrings")
	public void moveJillOnInstantLose(Game game) {
		game.movePlayer(3);
		assertEquals(4, jack.position());
		assertEquals(jill, game.currentPlayer());
		game.movePlayer(4);
		assertTrue(game.getSquare(5).isOccupied() != true);
		assertEquals(jack, game.currentPlayer());
		game.movePlayer(2);
		assertEquals(jack, game.currentPlayer());
		game.movePlayer(6);
		assertTrue(game.isOver());
		assertTrue(jack.wins());
	}
}