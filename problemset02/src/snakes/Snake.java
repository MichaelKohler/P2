package snakes;

/*
 * A |Snake| is a |Ladder| with a down transport mechanism.
 */
public class Snake extends Ladder {

	public Snake(int transport, IGame game, int position) {
		super(transport, game, position);
	}

	@Override
	protected String squareLabel() {
		return this.destination().position() + "<-" + position;
	}

}
