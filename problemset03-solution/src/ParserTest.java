import static org.junit.Assert.*;

import org.codehaus.jparsec.error.ParserException;
import org.junit.Test;


public class ParserTest {
	LogoParserMaker parserMaker = new LogoParserMaker();
	
	@Test
	public void shouldParseInstruction() {
		Object[] res = parserMaker.instruction().parse("up 23");
		assertArrayEquals(new Object[] { "up", 23 }, res);
	}
	
	@Test(expected=ParserException.class)
	public void shouldNotParseInstructions() {
		System.out.println(parserMaker.instructions().parse("up 23\nfn 28\n").size());
	}
	
	@Test
	public void shouldParseInstructions() {
		Object[] res = parserMaker.instructions().parse("up 23\ndn 28\n").get(1);
		assertArrayEquals(new Object[] { "dn", 28 }, res);
	}
	
	@Test
	public void shouldNotParseBigNumbers() {
		parserMaker.instructions().parse("up 22222222222222222222222223\ndn 28\n").get(1);
	}
}
