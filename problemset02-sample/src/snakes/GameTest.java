package snakes;

public class GameTest {

	private Player niko;
	private Player joel;
	private Player jack;
	private Player jill;

	public Game newGame() {
		jack = new Player("Jack");
		jill = new Player("Jill");
		niko = new Player("Niko");
		joel = new Player("Joel"); 
		Player[] args = { jack, jill, joel, niko };
		Game game = new Game(12, args);
		game.setSquareToLadder(2, 4);
		game.setSquareToLadder(7, 2);
		game.setSquareToSnake(11, -6);
		game.setSquareToScrambleUp(5);
		game.setSquare(6, new InstantLose(game, 6));
		return game;
	}
}
