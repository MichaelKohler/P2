import java.util.List;

import org.codehaus.jparsec.Parser;

/**
 * This class converts a string representation of turtle instructions into a valid board.
 * 
 * @author nes
 *
 */
class BoardMaker {
	
	final Parser<List<Object[]>> instructionsParser;
	
	public BoardMaker() {
		instructionsParser = new LogoParserMaker().instructions();
		
	}
	
	/**
	 * Converts the <code>instructionString</code> into a board of booleans.
	 * If the string is ungrammatical, a {@code org.codehaus.jparsec.error.ParserException} 
	 * is raised. If the interpretation of the string leads the turtle out of bounds, 
	 * a {@code TurtleOutOfBoundsException} is thrown. For the accepted grammar, see {@link LogoParserMaker}.
	 * @param instructionString, the 
	 * @return The board that results from the given turtle instructions. 
	 *   A true entry means that the turtle marked the field, a false entry means it is unmarked
	 * @throws org.codehaus.jparsec.error.ParserException if the string is ungrammatical.
	 * 	        TurtleOutOfBoundsException if the interpretation moves the turtle out of bounds.
	 * @See {@link LogoParserMaker}
	 */
	public boolean[][] makeBoardFrom(String instructionString) 
			throws org.codehaus.jparsec.error.ParserException,
			  TurtleOutOfBoundsException {
		InstructionExecuter executeInstructions = new InstructionExecuter();
		List<Object[]> instructions = instructionsParser.parse(instructionString);
		executeInstructions.execute(instructions);
		return executeInstructions.getBoard();
	}

}
