import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ActionTest {
	public Action right1234;
	public Action up12;
	public Action left10;
	public Action down120;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAction() {
		right1234 = new Action(Direction.RIGHT, 1234); 
		assertThat(right1234, is(notNullValue()));
		up12 = new Action(Direction.UP, 12);
		assertThat(up12, is(notNullValue()));
		left10 = new Action(Direction.LEFT, 10);
		assertThat(left10, is(notNullValue()));
		down120 = new Action(Direction.DOWN, 120);
		assertThat(down120, is(notNullValue()));
	}

	@Test
	public void testGetDirection() {
		right1234 = new Action(Direction.RIGHT, 1234);
		Direction dir1 = right1234.getDirection();
		assertThat(dir1, is(notNullValue()));
		assertThat(dir1, is(equalTo(Direction.RIGHT)));
		
		up12 = new Action(Direction.UP, 12);
		Direction dir2 = up12.getDirection();
		assertThat(dir2, is(notNullValue()));
		assertThat(dir2, is(equalTo(Direction.UP)));
		
		left10 = new Action(Direction.LEFT, 10);
		Direction dir3 = left10.getDirection();
		assertThat(dir3, is(notNullValue()));
		assertThat(dir3, is(equalTo(Direction.LEFT)));
		
		down120 = new Action(Direction.DOWN, 120);
		Direction dir4 = down120.getDirection();
		assertThat(dir4, is(notNullValue()));
		assertThat(dir4, is(equalTo(Direction.DOWN)));
	}

	@Test
	public void testGetDistance() {
		right1234 = new Action(Direction.RIGHT, 1234);
		int dir1 = right1234.getDistance();
		assertThat(dir1, is(notNullValue()));
		assertThat(dir1, is(equalTo(1234)));
		
		up12 = new Action(Direction.UP, 12);
		int dir2 = up12.getDistance();
		assertThat(dir2, is(notNullValue()));
		assertThat(dir2, is(equalTo(12)));
		
		left10 = new Action(Direction.LEFT, 10);
		int dir3 = left10.getDistance();
		assertThat(dir3, is(notNullValue()));
		assertThat(dir3, is(equalTo(10)));
		
		down120 = new Action(Direction.DOWN, 120);
		int dir4 = down120.getDistance();
		assertThat(dir4, is(notNullValue()));
		assertThat(dir4, is(equalTo(120)));
	}

}
