import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.functors.Map;
import org.junit.Test;

/**
 * Authors:
 *      Michael Kohler - 11-108-289
 *      Lukas Diener - 11-123-213
 *
 * This class is responsible to parse the user input and
 * return an |Action| which is processed by |BoardMaker|.
 */

public class InputParser {
	
	public List<Action> parse(String in){
		methodBodies.clear();
		methodNames.clear();
		return instructions().parse(in);
	}
	
	HashMap<String, List<Action>> methodBodies = new HashMap<String, List<Action>>();
	ArrayList<Parser<String>> methodNames = new ArrayList<Parser<String>>();
	
	public Parser<List<Action>> instructionOrRepeat(){
		return Parsers.or(methodDefinition(), commandBlock(), repeatBlock(), methodCall());
	}
	
	@Test
	public void commandBlockTest() {
		assertThat(commandBlock().parse("rt 100 lt 100"), is(Arrays.asList(new Action(Direction.rt,100), new Action(Direction.lt, 100))));
		assertThat(commandBlock().parse("fd 12 lt 100"), is(Arrays.asList(new Action(Direction.fd,12), new Action(Direction.lt, 100))));
		assertThat(commandBlock().parse("bd 100 fd 100"), is(Arrays.asList(new Action(Direction.bd,100), new Action(Direction.fd, 100))));
		assertThat(commandBlock().parse("lt 1 lt 0"), is(Arrays.asList(new Action(Direction.lt,1), new Action(Direction.lt, 0))));
	}
	public Parser<List<Action>> commandBlock(){
		return commandParser().sepBy1(Scanners.WHITESPACES);
	}
	
	@Test
	public void instructionsTest() {
		methodBodies.clear();
		methodNames.clear();
		assertThat(instructions().parse("to test [ fd 999 ] repeat 2 [ test lt 100 ] "), is(Arrays.asList(new Action(Direction.fd, 999),
																										new Action(Direction.lt, 100),
																										new Action(Direction.fd, 999),
																										new Action(Direction.lt, 100))));
		assertThat(instructions().parse("to test [ fd 999 repeat 2 [ fd 1992 ] ] to test2 [ bd 12 ] rt 12 repeat 1 [ rt 100 lt 100 test ] fd 35 test repeat 2 [ bd 12 ]"), is(Arrays.asList(new Action(Direction.rt, 12),
																																									new Action(Direction.rt, 100),
																																									new Action(Direction.lt, 100),
																																									new Action(Direction.fd, 999),
																																									new Action(Direction.fd, 1992),
																																									new Action(Direction.fd, 1992),
																																									new Action(Direction.fd, 35),
																																									new Action(Direction.fd, 999),
																																									new Action(Direction.fd, 1992),
																																									new Action(Direction.fd, 1992),
																																									new Action(Direction.bd, 12),
																																									new Action(Direction.bd, 12))));
		
	}
	
	public Parser<List<Action>> instructions() {
		return instructionOrRepeat().sepBy1(Scanners.WHITESPACES)
				.followedBy(Scanners.WHITESPACES.optional())
				.map(new org.codehaus.jparsec.functors.Map<List<List<Action>>, List<Action>>() {
					public List<Action> map(List<List<Action>> from) {
						//DEBUG System.out.println("found instructions: "+from.size());
						List<Action> retVal = new ArrayList<Action>();
						for(int i=0; i<from.size(); i++){
							if(from.get(i)!= null)
								retVal.addAll(from.get(i));
						}
						return retVal;
					}
				});
	}
	
	@Test
	public void commandParserTest() {
		assertThat(commandParser().parse("rt 12"), is(new Action(Direction.rt, 12)));
	}
	
	/** this parses a single command of the form rt 12 **/
	private Parser<Action> commandParser(){
		return Parsers.array(direction().followedBy(Scanners.WHITESPACES), integer())
			.map(new Map<Object[], Action>() {
				public Action map(Object[] from) {
					int amount = Integer.parseInt(from[1].toString());
					Direction dir = Direction.valueOf(from[0].toString());
					//DEBUG System.out.println("commandParser(): "+dir.toString()+" "+amount);
					return new Action(dir, amount);
				}
			});
	}
	
	@Test
	public void repeatBlockTest() {
		assertThat(repeatBlock().parse("repeat 3 [ lt 12 rt 7 fd 1 ]"), is(Arrays.asList(new Action(Direction.lt, 12),new Action(Direction.rt, 7),new Action(Direction.fd, 1),
																					new Action(Direction.lt, 12),new Action(Direction.rt, 7),new Action(Direction.fd, 1),
																					new Action(Direction.lt, 12),new Action(Direction.rt, 7),new Action(Direction.fd, 1))));
	}
	
	
	/** this parses repeat blocks of the form repeat 3 [ lt 12 ]
	 *  and returns the content (inflated) as a List<Action>
	 **/
	private Parser<List<Action>> repeatBlock() {
		return Parsers.array(Scanners.string("repeat").followedBy(Scanners.WHITESPACES),
				integer().followedBy(Scanners.WHITESPACES.optional()),
				Scanners.string("[").followedBy(Scanners.WHITESPACES.optional()),
				Parsers.or(commandBlock(), methodCall()).sepBy1(Scanners.WHITESPACES).followedBy(Scanners.WHITESPACES.next(Scanners.string("]")).next(Scanners.WHITESPACES.optional().peek())))
				.map(new Map<Object[], List<Action>>() {
					public List<Action> map(Object[] from) {
						int loops = (Integer) from[1];
						List<Action> actions = unnestList((List<List<Action>>) from[3]);
						//DEBUG System.out.println("found loop body: "+actions.size()+" instructions found.");
						List<Action> returnValue = new ArrayList<Action>();
						for(int i=0; i<loops; i++){
							returnValue.addAll(actions);
						}
						return returnValue;
					}
				});
	}
	
	@Test
	public void methodDefinitionTest() {
		assertThat(methodDefinition().parse("to myFunc [ lt 12 rt 7 fd 1 ]"), is((List<Action>) new ArrayList<Action>()));
		assertThat(methodDefinition().parse("to testFunc [ lt 12 rt 7 fd 1 repeat 3 [ fd 1992 ] ]"), is((List<Action>) new ArrayList<Action>()));
	}
	
	
	/** this parses a method definition of the form to myFunction [ rt 12 ]
	 *  and stores it in the methodNames and methodBodies list for later use
	 */
	private Parser<List<Action>> methodDefinition() {
		return Parsers.array(Scanners.string("to")
					.followedBy(Scanners.WHITESPACES.optional()),
				Scanners.IDENTIFIER
					.followedBy(Scanners.WHITESPACES.optional()),
				bracketOpenSpace(),
				Parsers.or(commandBlock(), repeatBlock()).sepBy1(Scanners.WHITESPACES).followedBy(Scanners.WHITESPACES.optional())
					.followedBy(bracketCloseSpace()))
				.map(new Map<Object[], List<Action>>() {
					public List<Action> map(Object[] from) {
						String methodName = (String) from[1];
						List<Action> methodBody = unnestList((List<List<Action>>) from[3]);		
						//DEBUG System.out.println("found method: "+methodName+", body: "+methodBody.size()+" instructions found.");
						methodNames.add(Scanners.string(methodName).retn(methodName));
						methodBodies.put(methodName, methodBody);
						return new ArrayList<Action>();
					}
				});
	}
	
	@Test
	public void methodCallTest() {
		/*assertThat(methodDefinition(), methodCall().sepBy1(Scanners.WHITESPACES))
				.parse("to test [ lt 12 rt 7 fd 1 repeat 3 [ fd 12 ] ] test"), is(Arrays.asList(new Action(Direction.lt,12), new Action(Direction.rt,7), new Action(Direction.fd,1),
																								new Action(Direction.fd,12),new Action(Direction.fd,12),new Action(Direction.fd,12))));
	*/}
	
	
	/** this parses a method call of the form to myFunction
	 *  and expands it using the methodBodies list
	 */
	private Parser<List<Action>> methodCall() {
		return Parsers.array(Scanners.WHITESPACES.optional(),Scanners.IDENTIFIER)
			.map(new Map<Object[], List<Action>>() {
			public List<Action> map(Object[] from) {
				String methodName = (String)from[1];
				if(methodBodies.containsKey(methodName)){
					//DEBUG System.out.println("#Method found: "+methodName);
					List<Action> retVal = new ArrayList<Action>(methodBodies.get(methodName));
					Collections.copy(retVal, methodBodies.get(methodName));
					return retVal;
				}else{
					System.out.println("PARSER EXCEPTION: "+methodName+" NOT FOUND!!!!11!");
					return null;
				}
			}
		});
	}

	Parser<String> string(String s) {
		return Scanners.string(s).retn(s);
	}

	/** this parses a direction command of the form rt 12
	 *  and returns it
	 */
	Parser<String> direction() {
		ArrayList<Parser<String>> commandNames = new ArrayList<Parser<String>>();
		commandNames.add(string("fd"));
		commandNames.add(string("bd"));
		commandNames.add(string("lt"));
		commandNames.add(string("rt"));
		return Parsers.or(commandNames);
	}
	
	/** This method takes a List of Lists and converts
	 *  them into a non-nested List
	 * 
	 * @param in Input List
	 * @return The unnested List
	 */
	private List<Action> unnestList(List<List<Action>> in){
		List<Action> temp = new ArrayList<Action>();
		for(int i=0; i<in.size(); i++){
			temp.addAll(in.get(i));
		}
		return temp;
	}
	
	Parser<Void> bracketOpenSpace(){
		return (Scanners.string("[").followedBy(Scanners.WHITESPACES.optional()));
	}
	
	Parser<Void> bracketCloseSpace(){
		return (Scanners.string("]").followedBy(Scanners.WHITESPACES.optional().peek()));
	}
	
	/** this is unused. The idea was to store all defined methods' names
	 *  in methodNames. This parser could then look up the method with
	 *  Parsers.or(). When any name matches the input, the method is 
	 *  expanded with the values in methodBodies. This never worked though
	 *  so we switched to using Scanners.INDENTIFIER and then checking whether
	 *  a method with this name is present in methodBodies.
	 */
	Parser<String> functionCall(){
		return Parsers.or(methodNames);
	}

	Parser<Integer> integer() {
		return Scanners.INTEGER.map(new Map<String, Integer>() {
			@Override
			public Integer map(String from) {
				try {
					return Integer.parseInt(from);
				} catch(NumberFormatException nfe) {
					//DEBUG System.out.println("NumberFormatException");
					return null;
				}
			}
		});
	}
}