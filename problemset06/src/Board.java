/**
 * The |Board| is the playground. It holds all equipment required by the game.
 * Further it is responsible to create the whole playground.
 */
public class Board {
    public static Square[][] board;
    private Player[] players;

    public Board(Player[] players) {
        board = new Square[5][5];
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
    }
    
    /**
     * creates all the squares required
     */
    private void createSquares() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int[] position = { i, j};
                board[i][j] = new Square(position);
            }
        }
    }
    
    /**
     * ask square to add amoebe
     * 
     * @param amoebe   amoebe which needs to be placed
     */
    
    /**
     * sets the amoebes to random squares, two for each player
     */
    private void setAmoebesToSquares() {
        Die die = new Die();
        for (int i = 0; i < this.players.length; i++) {
            Game.Color color = this.players[i].getColor();
            boolean statusOK = false;
            int randomRow, randomCol;
            do {
                randomRow = die.roll(1, 5) - 1;
                randomCol = die.roll(1, 5) - 1;
                statusOK = Board.board[randomRow][randomCol].getAmoebesList().size() == 0;
            } while (!statusOK);
            Amoebe amo1 = new Amoebe(color);
            board[randomRow][randomCol].enterSquare(amo1);
            
            statusOK = false;
            do {
                randomRow = die.roll(1, 5) - 1;
                randomCol = die.roll(1, 5) - 1;
                statusOK = Board.board[randomRow][randomCol].getAmoebesList().size() == 0;
            } while (!statusOK);
            Amoebe amo2 = new Amoebe(color);
            board[randomRow][randomCol].enterSquare(amo2);
        }
    }
    
    /**
     * returns the array of Players which participate in the game
     * 
     * @param  this.players
     */
    public Player[] getPlayers() {
        return this.players;
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
     * draw the board in ASCII
     */
    public void draw(){
    	StringBuilder str = new StringBuilder();
    	str.append("\n");

    	for (int x=0; x<Board.getBoardDimensions()[0];x++)
    	{
	    	for (int y=0; y<Board.getBoardDimensions()[1];y++)
	    	{
	    		if (!(y == 2 && x == 2))
	    		    str.append(Board.board[x][y].getFood() + "\t");
	    		else
	    			str.append("Compass!!\t\t");
	    	}

	    	str.append("\n");
	
	    	for (int y=0; y<Board.getBoardDimensions()[1];y++)
	    	{
	    		if (!(y == 2 && x == 2))
	    		    str.append(Board.board[x][y].getAmoebes() + "\t");
	    	    else
	    		    str.append("Compass!!\t\t");
	    	}

	    	str.append("\n\n");
    	}
    	System.out.println(str.toString());
    }
}
