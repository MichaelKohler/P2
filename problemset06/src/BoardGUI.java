import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BoardGUI implements TypedObserver<UrsuppeEvent> {
    private JFrame boardGUIFrame;
    private JLabel boardLabel;
    private JLabel stateLabel;
    private Board board;

    public BoardGUI(Board board) {
        this.board = board;
        
        boardGUIFrame = new JFrame();
        boardGUIFrame.setVisible(true);
        boardGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardGUIFrame.setSize(600, 400);
        boardGUIFrame.setResizable(true);
        boardGUIFrame.setTitle("Ursuppe - Problemset10");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        String boardsStringRep = this.board.toString();
        boardLabel = new JLabel();
        boardLabel.setText(boardsStringRep);
        panel.add(boardLabel, BorderLayout.CENTER);
        
        stateLabel = new JLabel();
        panel.add(stateLabel, BorderLayout.SOUTH);
        
        boardGUIFrame.add(panel);
        boardGUIFrame.repaint();
    }
    
    public void bindObserver(GameRunner runner) {
        runner.registerObserver(this);
    }
    
    public void setLabel(String newText) {
        this.stateLabel.setText(newText);
    }
    
    @Override
    public void notify(UrsuppeEvent e) {
        if (e instanceof BoardChangedEvent) dispatch((BoardChangedEvent) e);
        if (e instanceof PlayerChangedEvent) dispatch((PlayerChangedEvent) e);
        if (e instanceof PlayerWinsEvent) dispatch((PlayerWinsEvent) e);
    }
    
    private void dispatch(BoardChangedEvent e) {
    	boardLabel.setText(e.getBoard().toString()); // AK FTFY
        this.boardGUIFrame.repaint();
    }
    
    private void dispatch(PlayerChangedEvent e) {
        this.stateLabel.setText("It's player's " + e.getNextPlayer().getName() + " turn!");
    }
    
    private void dispatch(PlayerWinsEvent e) {
        this.stateLabel.setText("Player " + e.getWinner().getName() + " wins!");
    }
}