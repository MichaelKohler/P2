import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class InputParserTest {

	public Action right1234;
	public Action up12;
	public Action left10;
	public Action down120;
	public Action up10000000000000000;
	
	@Before
	public void setUp() throws Exception {
		right1234 = new Action(Direction.RIGHT, 1234); 
		up12 = new Action(Direction.UP, 12);
		left10 = new Action(Direction.LEFT, 10);
		down120 = new Action(Direction.DOWN, 120);
		/*
		 * There are additional notes for this test below...
		 * up10000000000000000 = new Action(Direction.UP, 10000000000000000);
		 */
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInputParser() {
		InputParser parser = new InputParser();
		assertThat(parser, is(notNullValue()));
	}

	@Test
	public void testParseInput() {
		InputParser parser = new InputParser();
		
		ArrayList<Action> out1 = parser.parseInput("rt 1234\nup 12");
		assertThat(out1, is(notNullValue()));
		assertThat(out1.size(), is(equalTo(2)));
		assertThat(out1, Matchers.hasItem(right1234));
		assertThat(out1, Matchers.hasItem(up12));
		
		ArrayList<Action> out2 = parser.parseInput("lt 10\ndn 120");
		assertThat(out2, is(notNullValue()));
		assertThat(out2.size(), is(equalTo(2)));
		assertThat(out2, Matchers.hasItem(left10));
		assertThat(out2, Matchers.hasItem(down120));
		
		/*
		 * We cannot test this case because our constructor for |Action|
		 * accepts only integers. Means we can't create a dummy-Action
		 * for comparing. We would have to rewrite event the parser since
		 * it accepts only integers.
		 * 
		 * ArrayList<Action> out3 = parser.parseInput("up 1000000000000000")
		 * assertThat(out3, is(notNullValue()));
		 * assertThat(out3.size(), is(equalTo(1)));
		 * assertThat(out3, Matchers.hasItem(up1000000000000000));
		*/
	}
	
	

}
