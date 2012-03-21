package snakes;

/**
 * The ISquare interface represents a field of a game a player may enter during
 * a match. A square knows it's position on the game board. And provides the
 * methods {@code ISquare.enter} and {@code ISquare.leave} so a player may enter or leave a Square.
 * <p>
 * Also the methods {@code ISquare.moveAndLand} and {@code ISquare.landHereOrGoHome}
 * help to find the correct square to land on when the player moves.
 */
public interface ISquare {
	/**
	 * the count of this square starting at the first square.
	 * 
	 * @return a positive non-zero integer
	 */
	public int position();

	/**
	 * returns the square the player may enter after moving the specified number
	 * towards the end.
	 * 
	 * @param moves
	 *            - the number squares the player may proceed
	 * @return the square the player may enter
	 */
	public ISquare moveAndLand(int moves);

	/**
	 * says if this square is the first square of the game
	 * 
	 * @return true if it is the first square - false otherwise
	 */
	public boolean isFirstSquare();

	/**
	 * says if this square is the last square of the game
	 * 
	 * @return true if it is the last square - false otherwise
	 */
	public boolean isLastSquare();

	/**
	 * indicates to the Square that the player is now on the square
	 * 
	 * @param player
	 *            - the player to enter the square
	 */
	public void enter(Player player);

	/**
	 * indicates to the Square that the player is not on the square anymore
	 * 
	 * @param player
	 *            - the player to leave the square
	 */
	public void leave(Player player);

	public boolean isOccupied();

	/**
	 * returns the square the player should enter after moving here. If this
	 * square is for some reason not enterable this square returns a different
	 * square (e.g the first square)
	 * 
	 * @return the square the player should enter
	 * */
	public ISquare landHereOrGoHome();

	/**
	 * says if a field is an instant lose field
	 * 
	 * @ret true if the player should be removed from the game after entering
	 *      this square</p> false otherwise
	 * */
	public boolean isInstantLose();
}
