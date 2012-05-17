
public class PlayerWinsEvent extends UrsuppeEvent {
    private Player winner;
    
    public PlayerWinsEvent(Player winner) {
        this.winner = winner;
    }
    
    public Player getWinner() {
        return this.winner;
    }

}
