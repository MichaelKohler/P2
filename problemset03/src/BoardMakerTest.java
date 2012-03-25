import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BoardMakerTest {
	boolean[][] dummyBoard;
	boolean[] boardDn12;
	boolean[] boardUp1;
	boolean[][] boardRt10;
	
	@Before
	public void setUp() throws Exception {
		dummyBoard = new boolean[100][100];
		for (int i = 0; i < dummyBoard.length; i++) {
            for (int j = 0; j < dummyBoard[i].length; j++) {
            	dummyBoard[i][j] = false;
            }
        }
		
		/* This is a test for the dn 12 action. First index contains
		 * an array with 12 true fields
		 */
		// AK using control structures in tests is a dangerous thing -
		// in this case, it seems difficult to avoid it, but in general,
		// prefer literals and simple constructions.
		boardDn12 = new boolean[100];
		for(int i=0; i<100; i++){
			boardDn12[i] = false;
		}
		for(int i=0; i<12; i++){
			boardDn12[i] = true;
		}
		
		
		/* This is a test for the up 1 action. First index contains
		 * an array with 1 true field
		 */
		boardUp1 = new boolean[100];
		for(int i=0; i<100; i++){
			boardUp1[i] = false;
		}
		for(int i=0; i<1; i++){
			boardUp1[i] = true;
		}
		
		/* This is a test for the rt 10 action. The first 10 indexes
		 * contain an array with 1 true field.
		 */
		boardRt10 = new boolean[100][100];
		for(int i=0; i<100; i++){
			boardRt10[i] = new boolean[100];
			for(int j=0; j<100; j++){
				boardRt10[i][j] = false;
			}
		}
		for(int i=0; i<10; i++){
			boardRt10[i][0] = true;
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBoardMaker() {
		BoardMaker maker1 = new BoardMaker();
		assertThat(maker1, is(notNullValue())); // AK this test isn't actually testing anything
		/* Probably nothing more to test */
	}

	@Test
	public void testMakeBoardFrom() {
		
		BoardMaker maker1 = new BoardMaker();
		assertThat(maker1, is(notNullValue()));
		
		/* This testcase led us to a bug. The first field [0,0] has never
		 * been drawn.
		 */
		boolean[][] board1 = maker1.makeBoardFrom("dn 12");
		assertThat(board1, is(notNullValue()));
		assertThat(board1, Matchers.hasItemInArray(boardDn12));
		
		// AK Make tests as small as possible, so that you can isolate errors
		/* When moving up at the start, only [0,0] should be
		 * painted.
		 */
		boolean[][] board2 = maker1.makeBoardFrom("up 1");
		assertThat(board2, is(notNullValue()));
		assertThat(board2, Matchers.hasItemInArray(boardUp1));
		
		
		boolean[][] board3 = maker1.makeBoardFrom("rt 10");
		assertThat(board3, is(notNullValue()));
		assertThat(board3, is(equalTo(boardRt10)));
		
	}

}
