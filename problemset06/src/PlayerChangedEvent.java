
public class PlayerChangedEvent extends UrsuppeEvent {
    private Player nextPlayer;
    
    public PlayerChangedEvent(Player newPlayer) {
        this.nextPlayer = newPlayer;
    }
    
    public Player getNextPlayer() {
        return this.nextPlayer;
    }

}
