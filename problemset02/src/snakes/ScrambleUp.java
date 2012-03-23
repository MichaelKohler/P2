package snakes;

/*
 * The |ScrambleUp| field is a normal board field which
 * scrambles up the player landing on it with a random player.
 */
public class ScrambleUp extends Square implements ISquare {

    public ScrambleUp(Game game, int position) {
        super(game, position);
    }

    @Override
    public void enter(Player player) {
        assert this.player == null;
        this.player = player;
        this.player.changeWithOtherPlayer(game, this);
    }
    
    @Override
    protected String squareLabel() {
        return position + "<->";
    }
    
    public void enter(Player player, boolean swapping) {
        assert this.player == null;
        this.player = player;
    }
}

