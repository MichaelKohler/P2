package snakes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Kudos group 09
public class Game {
	private List<ISquare> squares;
	private int size;
	private Queue<Player> players;
	private Player winner;

	private boolean invariant() {
		return squares.size() > 3 && size == squares.size()
				&& (players.size() > 1 || winner != null);
	}

	public Game(int size, Player[] initPlayers) {
		this.size = size;
		this.addSquares(size);
		this.addPlayers(initPlayers);
		assert invariant();
	}

	public boolean isValidPosition(int position) {
		return position >= 1 && position <= size;
	}

	public static void main(String args[]) {
		Log.setLogging(new VerboseLog());
		(new GameTest()).newGame().play(new Die());
	}

	public void play(Die die) {
		Log.log("Initial state: " + this);
		while (this.notOver()) {
			int roll = die.roll();
			Log.log(this.currentPlayer() + " rolls " + roll + ":  "
					+ this);
			this.movePlayer(roll);
		}
		Log.log("Final state:   " + this);
		Log.log(this.winner() + " wins!");
	}

	public boolean notOver() {
		return winner == null;
	}

	public boolean isOver() {
		return !this.notOver();
	}

	public Player currentPlayer() {
		return players.peek();
	}

	public void movePlayer(int roll) {
		assert roll >= 1 && roll <= 6;
		Player currentPlayer = players.remove(); // from front of queue
		currentPlayer.moveForward(roll);
		if (!currentPlayer.square().isInstantLose())
			players.add(currentPlayer); // to back of the queue
		if (currentPlayer.wins()) {
			winner = currentPlayer;
		} else if(players.isEmpty()){
			winner = new Player("nobody");	
		}
		assert invariant();
	}

	public void setSquare(int position, ISquare square) {
		// Do not change the type of the first or last square!
		assert !this.getSquare(position).isLastSquare()
				&& !this.getSquare(position).isFirstSquare();
		this.initSquare(position, square);
		assert invariant();
	}

	public Player winner() {
		return winner;
	}
	
	private Player[] getPlayers(){
		return players.toArray(new Player[0]);
	}
	
	public Player getRandomPlayer(){
		Player[] players = this.getPlayers();
		int size = players.length;
		if(size < 1)
			return null;
		int result = (int) (size * Math.random());
		return players[result];
	}

	public ISquare firstSquare() {
		return squares.get(0);
	}

	public ISquare getSquare(int position) {
		assert this.isValidPosition(position);
		return squares.get(position - 1);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (ISquare square : squares) {
			buffer.append(square.toString());
		}
		return buffer.toString();
	}

	private void addSquares(int size) {
		squares = new ArrayList<ISquare>();
		for (int i = 0; i < size; i++) {
			Square square = new Square(this, i + 1);
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

	private void initSquare(int position, ISquare square) {
		assert this.isValidPosition(position) && square != null;
		squares.set(position - 1, square);
	}

	public void setSquareToScrambleUp(int position) {
		this.setSquare(position, new ScrambleUp(this, position));
	}
	
	public void setSquareToLadder(int position, int transport) {
		this.setSquare(position, new Ladder(transport, this, position));
	}

	public void setSquareToSnake(int position, int transport) {
		this.setSquare(position, new Snake(transport, this, position));
	}

	public ISquare findSquare(int position, int moves) {
		assert position + moves <= 2 * size - 1; // can't go more than size-1
													// moves backwards past end
		int target = position + moves;
		if (target > size) { // reverse direction if we go past the end
			target = size - (target - size);
		}
		return this.getSquare(target);
	}

}
