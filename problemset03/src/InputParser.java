import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * Parser.java
 * Authors:
 *   Michael Kohler - 11-108-289
 *   Lukas Diener - 11-123-213
 *
 * This class is responsible for parsing a multi-line input string,
 * containing direction commands (lt, rt, dn, up) as well as distances.
 * It returns a list of 
 */

public class InputParser {
    
    /* This regular expression finds all occurrences of a string consisting of
     * one of the four direction commands (at the line start), followed by one
     * or more whitespace characters, followed by one or more numbers, followed
     * by the line ending.
     */
    private final static Pattern pattern = Pattern.compile("^(up|dn|lt|rt)\\s+(-*[0-9]+)$",
                                                           Pattern.MULTILINE);

	/**
	 * Returns a List of actions based on the input string. If there is no
	 * valid input, an empty List is returned. The List consists of Instances
	 * of the Action class, defined in Action.java.
	 *
	 * The description string shouldn't be null.
	 * The returned |actionList|'s size shouldn't be negative.
	 * 
	 *
	 * @param     description    user input to parse
	 * @return    List<Action>    a list of parsed actions
	 */
	public static ArrayList<Action> parseInput(String input){
	    assert input != null;
	    ArrayList<Action> actionList = new ArrayList<Action>();
        Matcher matcher = pattern.matcher(input);
        
        while (matcher.find()) {
            matcher.group();
            Direction dir = null;
            if(matcher.group(1).equals("up")){
                dir = Direction.UP;
            }
            if(matcher.group(1).equals("dn")){
                dir = Direction.DOWN;
            }
            if(matcher.group(1).equals("lt")){
                dir = Direction.LEFT;
            }
            if(matcher.group(1).equals("rt")){
                dir = Direction.RIGHT;
            }
            actionList.add(new Action(dir, Integer.parseInt(matcher.group(2))));
        }
        
		assert actionList.size() >= 0;
		return actionList;
	}
}