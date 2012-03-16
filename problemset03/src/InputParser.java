import java.util.ArrayList;
import java.util.List;

import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.functors.Tuple3;


/*
 * Parser.java
 * Authors:
 *   Michael Kohler - 11-108-289
 *   Lukas Diener - 11-123-213
 *
 * This class is responsible for parsing a multi-line input string,
 * containing direction commands (lt, rt, dn, up) as well as distances.
 * The parseInput method returns a list of |Action| elements.
 */

public class InputParser {
    
    public InputParser(){
		    //no constructor code.
	  }
	
	/*
	 * Returns a List of actions based on the input string. If there is no
	 * valid input, an empty List is returned. The List consists of Instances
	 * of the Action class, defined in Action.java.
	 *
	 * The description string shouldn't be null.
	 * 
	 *
	 * @param    description    user input to parse
	 * @return    List<Action>    a list of parsed actions
	 */
	public ArrayList<Action> parseInput(String in){
		assert in != null;
		List<Tuple3<Direction, Void, String>> parsedList = inputParser.parse(in);
		ArrayList<Action> actionList = new ArrayList<Action>();
		int invalidDirectionCounter = 0;
		for(int i=0; i<parsedList.size();i++){
			Direction dir = parsedList.get(i).a;
			/* This is an ugly hack, because the parser can not choose (or we don't have the knowledge)
			 * to accept only certain keywords (up, dn, ...). So the parser returns |null| if the direction
			 * is an invalid string. Has to be caught here.
			 */
			// AK: Parsers.or(Scanners.string("up"), Scanners.string("dn"), Scanners.string("lt"), Scanners.string("rt")).source()
			// would do the job (yeah, I had to read that one up too), but then it would not show anything at all, so it might not be what
			// you want.
			if(dir == null){
				invalidDirectionCounter++;
				continue;
			}
			int dist = Integer.parseInt(parsedList.get(i).c);
			System.out.println("Parsed "+dir+" "+dist);
			Action act = new Action(dir, dist);
			actionList.add(act);
		}
		// AK nice sanity check!
		assert actionList.size() == parsedList.size()-invalidDirectionCounter;
		return actionList;
	}
	
	/*
	 * This Parser splits the input at the newline character and politely asks actionParser()
	 * to parse each line. 
	 */
	private static org.codehaus.jparsec.Parser<List<Tuple3<Direction, Void, String>>> inputParser = actionParser()
		.sepBy(Scanners.string("\n"));
	
	/*
	 * The actionParser parses the three following parsers (in this order):
	 * - direction() (see below)
	 * - any whitespace			
	 * - Any integer (the distance)
	 * and returns a Tuple3 consisting of a |Direction|, void, and an integer
	 */
	private static org.codehaus.jparsec.Parser<Tuple3<Direction, Void, String>> actionParser() {
	    return org.codehaus.jparsec.Parsers.tuple(direction(), Scanners.WHITESPACES, Scanners.INTEGER);
	}
			
	
	/*
	 * The direction() parser finds identifiers, and returns the appropriate
	 * |Direction|. Otherwise, null is returned.
	 */
	private static org.codehaus.jparsec.Parser<Direction> direction() {
		return (Scanners.IDENTIFIER)
        .map(new org.codehaus.jparsec.functors.Map<String, Direction>() {
		    public Direction map(String arg0) {
		        if(arg0.equals("up")) return Direction.UP;
		        if(arg0.equals("dn")) return Direction.DOWN;
		        if(arg0.equals("lt")) return Direction.LEFT;
		        if(arg0.equals("rt")) return Direction.RIGHT;
		        else return null;
		    }
        });
	}
}
