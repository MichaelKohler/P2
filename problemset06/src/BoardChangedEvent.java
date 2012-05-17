
public class BoardChangedEvent extends UrsuppeEvent {
    private Board board;
    
    public BoardChangedEvent(Board board) {
        this.board = board;
    }
    
    public Board getBoard() {
        return this.board;
    }
}
