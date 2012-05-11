import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.Provider;

/**
 * The |Board| is the playground. It holds all equipment required by the game.
 * Further it is responsible to create the whole playground.
 */
public final class Board {
    final static Square[][] board = new Square[5][5];
    private final Player[] players;
    private final Provider<Die> dieProvider;
    private final Provider<Compass> compassProvider;
    private JFrame boardGUIFrame;
    private JLabel boardLabel;
    private JLabel stateLabel;

    public Board(Provider<Compass> compassProvider, Provider<Die> dieProvider, Player[] players) {
         this.compassProvider = compassProvider;
         this.dieProvider = dieProvider;
        this.players = players;
        initBoard();
    }
    
    /**
     * invariant for assertions
     */
    private boolean invariant() {
        return board.length == 5 && board[0].length == 5 &&
            this.players.length != 0;
    }
    
    /**
     * initialize the whole board and make it ready for playing
     */
    private void initBoard() {
         assert invariant();
        createSquares();
        setAmoebesToSquares();
        createGUI();
    }
    
    /**
     * creates all the squares required
     */
    private void createSquares() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int[] position = { i, j};
                board[i][j] = SquareFactory.get(position);
                /*
                 * While I admit, that this static factory method does get the
                 * `new` out of view, it does not add much flexibility to the
                 * application (but it does add complexity, a dangerous combination).
                 * I think, you actually mean to inject the factory as an instance,
                 * so that you can rebind the factory if needed.
                 */
            }
        }
    }
    
    // AK You seem to a) be missing a method or b) have a comment too many
    /**
     * ask square to add amoebe
     * 
     * @param amoebe   amoebe which needs to be placed
     */
    
    /**
     * sets the amoebes to random squares, two for each player
     */
    private void setAmoebesToSquares() {
        Die die = dieProvider.get();
        for (int i = 0; i < this.players.length; i++) {
            Game.Color color = this.players[i].getColor();
            boolean statusOK = false; // AK this sounds a bit over-dramatic, "freeSquare" seems more appropriate
            int randomRow, randomCol;
            do {
                randomRow = die.roll(1, 5) - 1; // AK could you maybe implement a `choose(List<Square> from) : Square` function that
                randomCol = die.roll(1, 5) - 1; // guarantees that even if I'm unlucky (or have badly mocked), always halts?
                statusOK = Board.board[randomRow][randomCol].getAmoebesList().size() == 0;
            } while (!statusOK);
            Amoebe amo1 = AmoebeFactory.get(this.compassProvider, color);
            board[randomRow][randomCol].enterSquare(amo1);
            
            statusOK = false; // AK don't repeat yourselves, you could have done the same with a simple for-loop here. 
            do {              // remember that more code is more place for error
                randomRow = die.roll(1, 5) - 1;
                randomCol = die.roll(1, 5) - 1;
                statusOK = Board.board[randomRow][randomCol].getAmoebesList().size() == 0;
            } while (!statusOK);
            Amoebe amo2 = AmoebeFactory.get(this.compassProvider, color);
            board[randomRow][randomCol].enterSquare(amo2);
        }
    }
    
    /**
     * Creates the whole GUI. The GUI has a board (ASCII) and displays a Label
     * which informs the user about the Game state (e.g. whose turn it is).
     */
    private void createGUI() {
        boardGUIFrame = new JFrame();
        boardGUIFrame.setVisible(true);
        boardGUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardGUIFrame.setSize(600, 400);
        boardGUIFrame.setResizable(true);
        boardGUIFrame.setTitle("Ursuppe - Problemset09");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        String boardsStringRep = this.toString();
        boardLabel = new JLabel(boardsStringRep);
        panel.add(boardLabel, BorderLayout.CENTER);
        
        stateLabel = new JLabel();
        panel.add(stateLabel, BorderLayout.SOUTH);
        
        boardGUIFrame.add(panel);
        boardGUIFrame.repaint();
    }
    
    /**
     * returns the array of Players which participate in the game
     * 
     * @param  this.players
     */
    public Player[] getPlayers() {
        return this.players.clone();
    }
    
    /**
     * returns the |Board|'s dimensions
     * 
     * @return dim    the dimension of the board
     */
    public static int[] getBoardDimensions() {
         int[] dim = new int[2];
         dim[0] = board.length;
         dim[1] = board[0].length;
         return dim;
    }
    
    /**
     * get an ASCII string representation
     */
    @Override
    public String toString(){ // AK this might have better been place in a separate class, so that you can easily extend/switch this representation
        StringBuilder str = new StringBuilder();
        str.append("<html><br />");

        for (int x=0; x<Board.getBoardDimensions()[0];x++)
        {
             for (int y=0; y<Board.getBoardDimensions()[1];y++)
             {
                   if (!(y == 2 && x == 2))
                       str.append(Board.board[x][y].getFood() + "&nbsp;&nbsp;&nbsp;&nbsp;");
                   else
                        str.append("Compass!!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
              }

              str.append("<br />");
     
              for (int y=0; y<Board.getBoardDimensions()[1];y++)
              {
                   if (!(y == 2 && x == 2))
                       str.append(Board.board[x][y].getAmoebes() + "&nbsp;&nbsp;&nbsp;&nbsp;");
                  else
                       str.append("Compass!!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
              }

              str.append("<br /><br />");
         }
         str.append("</html>");
         return str.toString();
    }
 
    /**
     * repaints the GUI representation of the whole Board.
     */
     public void repaintGUI() {
         boardLabel.setText(this.toString());
         this.boardGUIFrame.repaint();
     }
     
     /**
      * sets the new text of the GUI's state label. The new
      * text is not allowed to be null.
      * 
      * @param new text to set
      */
      public void setStateLabel(String newText) {
          assert newText != null;
          this.stateLabel.setText(newText);
      }
}
