package snakes;

public class ScrambleUp extends Square {

	private Player randomPlayer;

	public ScrambleUp(Game game, int position) {
		super(game, position);
	}

	public ISquare landHereOrGoHome() {
		if (this.isOccupied()) {
			return game.firstSquare();
		}
		randomPlayer = game.getRandomPlayer();
		if (randomPlayer == null)
			return this;
		int position = randomPlayer.position();
		randomPlayer.setPosition(this);

		return game.getSquare(position);
	}

	public String toString() {
		return "[Xx" + this.squareLabel() + this.player() + "xX]";
	}

}
