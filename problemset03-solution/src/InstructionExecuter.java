import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

/**
 * This class executes parsed turtle instructions on a board of booleans.
 * The board is maintained between executions, so executing instructions repeatedly
 * will add more markers to the board. See {@link LogoParserMaker} 
 * for details on the meaning of instructions.
 * 
 * <p>Example:<br>
 * <pre><code>
 * InstructionExecuter ie = new InstructionExecuter()
 * 		.execute(new Object[] {"dn",20 });
 * assertThat(ie.getBoard()[0][3],is(true)); 
 * </code></pre>
 * 
 * 
 * @author nes
 *
 */
public class InstructionExecuter {

	final int SIZE = 100;
	final boolean[][] board;

	int turtleX;
	int turtleY;

	public InstructionExecuter() {
		board = makeEmptyBoard();
	}

	/**
	 * Executes the list of instructions provided. Repeated calls will modify the same board. 
	 * The resulting board can be retrieved via {@link #getBoard()}.
	 * @param instructions a list of arrays, where each array has size 2. 
	 * The first element of each  array is a string. The second one is an integer.
	 * @throws TurtleOutOfBoundsException if the instructions move the turtle out of bounds.
	 */
	public void execute(List<Object[]> instructions) throws  TurtleOutOfBoundsException {
		for (Object[] instruction : instructions) {
			execute(instruction);
		}
	}

	void execute(Object[] instruction) {
		assert instruction.length == 2;
		String direction = (String) instruction[0];
		int steps = (Integer) instruction[1];
		for(int i=0;i<steps;i++) {
			step(direction);
			try {
				board[turtleX][turtleY] = true;
			} catch(ArrayIndexOutOfBoundsException ae) {
				throw new TurtleOutOfBoundsException();
			}
 		}
	}

	void step(String direction) {
		if (direction.equals("up")) {
			turtleY--;
		} else if (direction.equals("dn")) {
			turtleY++;
		} else if (direction.equals("lt")) {
			turtleX--;
		} else if (direction.equals("rt")) {
			turtleX++;
		} else {
			assert false;
		}
	}

	boolean[][] makeEmptyBoard() {
		boolean[][] board = new boolean[SIZE][];
		for (int i = 0; i < SIZE; i++)
			board[i] = new boolean[SIZE];
		return board;
	}
	
	@Test
	public void paintSome() {
		execute(new Object[] {"dn",20 });
		assertThat(board[0][3],is(true));
		assertThat(board[1][4],is(false));
	}

	/**
	 * Returns the board that the turtle walked over.
	 * @return an array of booleans. True in a position 
	 * that the turtle marked that field. False means it did not.
	 */
	public boolean[][] getBoard() {
		return board;
	}
}
