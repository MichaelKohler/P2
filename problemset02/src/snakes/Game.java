package snakes;

/*
 * The |Game| is the core class which defines everything general about the game.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/*
 * AK Your documentation leaves many questions open, but your implementation is
 * interesting and you are very observant about the details, e.g. the invariant.
 * You should be more careful about the documentation in the future, because
 * I can't always be this lenient, but for this time your problemset02 was
 * 
 * ACCEPTED
 */
public class Game implements IGame {
	private List<ISquare> squares;
	private int size;
	private Queue<Player> players;
	private ArrayList<Player> removedPlayers;
	private Player winner;
	
	private boolean invariant() {
		return squares.size() > 3
			&& size == squares.size()
			&& players.size() > 1;
	}
	
	// AK well observed!
	// this is needed since the assertion would be false when a player
	// lands on the InstantLose field
	private boolean invariantWithoutPlayerSize() {
        return squares.size() > 3
            && size == squares.size();
    }

	public Game(int size, Player[] initPlayers) {
		this.size = size;
		this.removedPlayers = new ArrayList<Player>();
		this.addSquares(size);
		this.addPlayers(initPlayers);
		assert invariant();
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#isValidPosition(int)
	 */
	@Override
	public boolean isValidPosition(int position) {
		return position>=1 && position<=size;
	}

	public static void main(String args[]) {
		(new SimpleGameTest()).newGame().play(new Die());
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#play(snakes.IDie)
	 */
	@Override
	public void play(IDie die) {
		System.out.println("Initial state: " + this);
		while (this.notOver()) {
			playOneRound(die);
		}
		System.out.println("Final state:   " + this);
		System.out.println(this.winner() + " wins!");
	}

	@Override
	public
	void playOneRound(IDie die) {
		// AK I factored this out, to make the play method more testable
		int roll = die.roll();
		System.out.println(this.currentPlayer() + " rolls " + roll + ":  " + this);
		this.movePlayer(roll);
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#notOver()
	 */
	@Override
	public boolean notOver() {
		return winner == null;
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#isOver()
	 */
	@Override
	public boolean isOver() {
		return !this.notOver();
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#currentPlayer()
	 */
	@Override
	public Player currentPlayer() {
		return players.peek();
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#movePlayer(int)
	 */
	@Override
	public void movePlayer(int roll) {
		assert roll>=1 && roll<=6;
		Player currentPlayer = players.remove(); // from front of queue
		currentPlayer.moveForward(roll);
		if (!removedPlayers.contains(currentPlayer)) { // if not removed
		    players.add(currentPlayer); // to back of the queue
		}
		checkWinner(currentPlayer);
		assert invariantWithoutPlayerSize();
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#setSquare(int, snakes.ISquare)
	 */
	@Override
	public void setSquare(int position, ISquare square) {
		// Do not change the type of the first or last square!
		assert !this.getSquare(position).isLastSquare()
			&& !this.getSquare(position).isFirstSquare();
		this.initSquare(position, square);
		assert invariant();
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#winner()
	 */
	@Override
	public Player winner() {
		return winner;
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#checkWinner(snakes.Player)
	 */
	@Override
	public void checkWinner(Player currentPlayer) {
	    if (currentPlayer.wins()) {
            winner = currentPlayer;
        }
	    if (players.size() == 1) {
	        winner = players.element(); // we can assume that this is the winner
	    }
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#firstSquare()
	 */
	@Override
	public ISquare firstSquare() {
		return squares.get(0);
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#getSquare(int)
	 */
	@Override
	public ISquare getSquare(int position) {
		assert this.isValidPosition(position);
		return squares.get(position - 1);
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (ISquare square : squares) {
			buffer.append(square.toString());
		}
		return buffer.toString();
	}

	private void addSquares(int size) {
		squares = new ArrayList<ISquare>();
		for(int i=0; i<size; i++) {
			Square square = new Square(this, i+1);
			squares.add(square);
		}
		this.initSquare(1, new FirstSquare(this, 1));
		this.initSquare(size, new LastSquare(this, size));
	}

	private void addPlayers(Player[] initPlayers) {
		players = new LinkedList<Player>();
		for (Player player : initPlayers) {
			player.joinGame(this);
			players.add(player);
		}
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#removePlayer(snakes.Player)
	 */
	@Override
	public void removePlayer(Player player) {
	    players.remove(player);
	    System.out.println("Player " + player +
	                       " was removed due to landing on InstantLose");
	    removedPlayers.add(player);
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#exchangePlayers(snakes.ISquare, snakes.Player)
	 */
	@Override
	public void exchangePlayers(ISquare iSquare, Player playerToSwap) {
	    Player otherPlayer = getRandomOtherPlayer();
	    Square playerToSwapSquare = (Square) getSquare(playerToSwap.position());
        Square otherPlayerSquare = (Square) getSquare(otherPlayer.position());
        // set squares (leave old fields and enter new fields)
        playerToSwapSquare.leave(playerToSwap);
	    playerToSwapSquare.enter(otherPlayer, true);
	    otherPlayerSquare.leave(otherPlayer);
	    otherPlayerSquare.enter(playerToSwap); // no second parameter necessary since
	                                           // we're not landing on a ScambleUp field
	    // set players (update the square information for the players)
	    playerToSwap.changeSquare(otherPlayerSquare);
	    otherPlayer.changeSquare(playerToSwapSquare);
	    
        System.out.println("Player " + playerToSwap + " was swapped with player " +
                           otherPlayer + "!");
	}
	
	public Player getRandomOtherPlayer() {
	    ArrayList<Player> playersCopy = new ArrayList<Player>();
	    playersCopy.addAll(players); // all players except the current player
	    Random random = new Random();
	    int index = random.nextInt(playersCopy.size());
	    return playersCopy.get(index);
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#playerStillPlaying(snakes.Player)
	 */
	@Override
	public boolean playerStillPlaying(Player player) {
	    return players.contains(player);
	}

	private void initSquare(int position, ISquare square) {
		assert this.isValidPosition(position) && square != null;
		squares.set(position-1, square);
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#setSquareToLadder(int, int)
	 */
	public void setSquareToLadder(int position, int transport) {
		this.setSquare(position, new Ladder(transport, this, position));
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#setSquareToSnake(int, int)
	 */
	@Override
	public void setSquareToSnake(int position, int transport) {
		this.setSquare(position, new Snake(transport, this, position));
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#setSquareToInstantLose(int)
	 */
	@Override
	public void setSquareToInstantLose(int position) {
	    this.setSquare(position, new InstantLose(this, position));
	}
	
	/* (non-Javadoc)
	 * @see snakes.IGame#setSquareToScrambleUp(int)
	 */
	@Override
	public void setSquareToScrambleUp(int position) {
	    this.setSquare(position, new ScrambleUp(this, position));
	}

	/* (non-Javadoc)
	 * @see snakes.IGame#findSquare(int, int)
	 */
	@Override
	public ISquare findSquare(int position, int moves) {
		assert position + moves <= 2*size - 1; // can't go more than size-1 moves backwards past end
		int target = position + moves;
		if (target > size) { // reverse direction if we go past the end
			target = size - (target - size);
		}
		return this.getSquare(target);
	}

}
