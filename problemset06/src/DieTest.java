import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Copied from Problemset02.
 *
 */
public class DieTest {
	private static final int MAX = 2000; // AK I know Oscar used this test first, but I think, you should use a more reasonable MAX value, so to assure, that the probability of a false positive (an error where there is none) is sufficiently small. e.g. 2000
	
	@Test
	public void testInRange() {
		IDie die = new Die();
		for (int i = 1;i<=MAX;i++) {
			int result = die.roll();
			assertTrue(result >= 1 && result <= Die.getFaces());
		}
	}

	@Test
	public void testMinReached() {
		assertTrue(reached(1));
	}

	@Test
	public void testMaxReached() {
		assertTrue(reached(Die.getFaces()));
	}
	
	private boolean reached(int value) {
		IDie die = new Die();
		for (int i = 1; i<=MAX; i++) {
			if (die.roll() == value) {
				return true;
			}
		}
		return false;
	}
}
