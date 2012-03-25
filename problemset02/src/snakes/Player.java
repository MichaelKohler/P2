package snakes;

/*
 * A |Player| is a player in the game defining all actions players can do.
 */
public class Player {
	
	private String name;
	private ISquare square;

	private boolean invariant() {
		return name != null
			&& square != null;
	}

	public Player(String name) {
		this.name = name;
		// invariant holds only after joining a game
	}

	public void joinGame(IGame game) {
		square = game.firstSquare();
		square.enter(this);	
		assert invariant();
	}
	
	public void leaveGame(IGame game, Square squareLandedOn) {
	    square = squareLandedOn;
	    game.removePlayer(this);
	}

	public int position() {
		assert invariant();
		return square.position();
	}

	public void moveForward(int moves) {
		assert moves > 0;
		square.leave(this);
		square = square.moveAndLand(moves);
		square.enter(this);
	}
	
	public void changeWithOtherPlayer(IGame game, ScrambleUp squareLandedOn) {
	    game.exchangePlayers(squareLandedOn, this);
	}
	
	public String toString() {
		return name;
	}

	public ISquare square() {
		return square;
	}

	public void changeSquare(ISquare newSquare) {
	    square = newSquare;
	}

	public boolean wins() {
		return square.isLastSquare();
	}

}
