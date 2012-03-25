package snakes;

// AK extracted this via Eclipse: Game->extract interface
public interface IGame {

	public abstract boolean isValidPosition(int position);

	public abstract void play(IDie die);

	public abstract boolean notOver();

	public abstract boolean isOver();

	public abstract Player currentPlayer();

	public abstract void movePlayer(int roll);

	public abstract void setSquare(int position, ISquare square);

	public abstract Player winner();

	public abstract void checkWinner(Player currentPlayer);

	public abstract ISquare firstSquare();

	public abstract ISquare getSquare(int position);

	public abstract String toString();

	public abstract void removePlayer(Player player);

	public abstract void exchangePlayers(ISquare iSquare, Player playerToSwap);

	public abstract boolean playerStillPlaying(Player player);

	public abstract void setSquareToLadder(int position, int transport);

	public abstract void setSquareToSnake(int position, int transport);

	public abstract void setSquareToInstantLose(int position);

	public abstract void setSquareToScrambleUp(int position);

	public abstract ISquare findSquare(int position, int moves);

	public abstract Player getRandomOtherPlayer();

	public abstract void playOneRound(IDie die);

}