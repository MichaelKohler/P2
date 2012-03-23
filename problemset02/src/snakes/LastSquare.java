package snakes;

/*
 * The |LastSquare| is the last field on the board, hence landing on it
 * means winning the game.
 */
public class LastSquare extends Square {

	public LastSquare(Game game, int position) {
		super(game, position);
	}

	@Override
	public boolean isLastSquare() {
		return true;
	}
}
