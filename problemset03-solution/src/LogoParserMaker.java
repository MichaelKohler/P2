import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.error.ParserException;
import org.codehaus.jparsec.functors.Map;

/**
 * The grammer we're parsing: <pre>
 * up <i>n</i> Ð Move the turtle up by <i>n</i> steps. 
 * dn <i>n</i> Ð Move the turtle down by <i>n</i> steps. 
 * rt <i>n</i> Ð Rotate the Turtle <i>n</i> degrees to the right. 
 * lt <i>n</i> Ð Rotate the Turtle <i>n</i> degrees to the left.
 * </pre>
 * 
 * @author nes
 * 
 */
public class LogoParserMaker {

	/**
	 * Make a new parser that parses the turtle grammer, defined in the class comment. 
	 * The returned parser will accept valid programs, and throw a ParseException otherwise.
	 * The parser expects all integers in the input string to be of size <i>n</i> &lt; 2^31. 
	 * Otherwise, the parser will signal a ParserException. 
	 * @return The parser.
	 * @throws org.codehaus.jparsec.error.ParserException if the string does not satisfy 
	 * our grammer, or contains integers that are bigger than MAX_INT.
	 */
	public Parser<List<Object[]>> instructions() {
		return instruction().sepBy1(Scanners.WHITESPACES)
				.followedBy(Scanners.WHITESPACES.optional());
	}
	
	Parser<Object[]> instruction() {
		return Parsers
				.array(direction().followedBy(Scanners.WHITESPACES),
						integer());
	}

	Parser<String> string(String s) {
		return Scanners.string(s).retn(s);
	}

	Parser<String> direction() {
		return string("up").or(string("dn")).or(string("lt")).or(string("rt"));
	}

	Parser<Integer> integer() {
		return Scanners.INTEGER.map(new Map<String, Integer>() {
			@Override
			public Integer map(String from) {
				try {
					return Integer.parseInt(from);
				} catch(NumberFormatException nfe) {
					throw new ParserException(nfe,null, null, null);
				}
			}
		});
	}
}
