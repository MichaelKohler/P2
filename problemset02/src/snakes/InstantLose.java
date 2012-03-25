package snakes;

/*
 * The |InstantLose| is a field which lets the player landing on it lose.
 */
public class InstantLose extends Square implements ISquare {

	public InstantLose(IGame game, int position) {
		super(game, position);
	}
	
	@Override
    public void enter(Player player) {
        assert this.player == null;
        this.player = player;
        this.player.leaveGame(game, this); // AK nice!
    }
	
	@Override
    protected String squareLabel() {
        return position + "||";
    }
}