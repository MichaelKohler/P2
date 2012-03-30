import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/*
 * BoardMaker.java
 * Authors:
 *      Michael Kohler - 11-108-289 
 *      Lukas Diener - 11-123-213
 *      
 * |BoardMaker| configures all the things we need to for drawing
 * the board. After every input we redraw the board and every time
 * |BoardMaker| is called and performs its actions.
 */
public class BoardMaker {
	
	private List<Line2D> lineList;
    private int currentPositionX;
    private int currentPositionY;
    private int heading;

    public BoardMaker() {
        resetCurrentPosition();
    }

    /**
     * Returns the list of |Line2D| to draw on the board.
     * 
     * The description string shouldn't be empty.
     * 
     * @param description
     *            user input which needs to be parsed first
     * @return lineList    the list of lines to draw
     */
    public List<Line2D> makeBoardFrom(String description) {
        assert !description.isEmpty();
        List<Action> actionList = callParseInput(description);
        assembleLineList(actionList);
        resetCurrentPosition();
        return this.lineList;
    }

    /**
     * Calls the parser and gets the list of Actions from it.
     * 
     * The Action list shouldn't have a negative size, which is checked in the
     * updatePathInMatrix method.
     * 
     * @param description
     *            user input
     * @return actionList list of Actions we need to perform
     */
    private List<Action> callParseInput(String description) {
        List<Action> actionList = new ArrayList<Action>();
        InputParser parser = new InputParser();
        try{
        	actionList = parser.parse(description);
        	 return actionList;
        }catch(org.codehaus.jparsec.error.ParserException e){
        	return actionList;
        }
       
    }
    
    /**
     * Assembles the |lineList| that we draw on the board.
     * 
     * @param     actions    List of Actions to perform.
     * @return     void
     */
    private void assembleLineList(List<Action> actions) {
    	List<Line2D> tempList = new ArrayList<Line2D>();
    	for (Action action : actions) {
    		Line2D line = getLineForAction(action);
    		if(line != null)
    			tempList.add(line);
    	}
    	
    	this.lineList = tempList;
    }
    
    /**
     * Sets the |Line2D| for the Action. This acts like a converter.
     * 
     * @param    action    Action to perform
     * @return   line        return the line we need to draw
     */
    
    @Test
    public void getLineForActionTest() {
    	Action rot = new Action(Direction.rt, 90);
    	Action trans = new Action(Direction.bd, 100);
    	Line2D line1 = getLineForAction(rot);
    	Line2D line2 = getLineForAction(trans);
    	assertThat(currentPositionY, is(700/2));
    	assertThat(currentPositionX, is(1000/2-100));
    }
    
    private Line2D getLineForAction(Action action) {
    	int amount = action.getAmount();
    	Line2D line = null;
    	
    	double headingInRad = (heading+90)*Math.PI/180;
    	
        switch (action.getDirection()) {
            case fd:
            	double stepsY = Math.sin(headingInRad)*amount;
            	double stepsX = Math.cos(headingInRad)*amount;
            	Point2D srcPoint = new Point2D.Double(this.currentPositionX, 
            														   this.currentPositionY);
            	Point2D destPoint = new Point2D.Double(this.currentPositionX + stepsX,
            														     this.currentPositionY + stepsY);
            	line = new Line2D.Double(srcPoint, destPoint);
            	this.currentPositionX += stepsX;
            	this.currentPositionY += stepsY;
            	break;
            case bd:
            	stepsY = -Math.sin(headingInRad)*amount;
            	stepsX = -Math.cos(headingInRad)*amount;
            	srcPoint = new Point2D.Double(this.currentPositionX, 
            														   this.currentPositionY);
            	destPoint = new Point2D.Double(this.currentPositionX + stepsX,
            														     this.currentPositionY + stepsY);
            	line = new Line2D.Double(srcPoint, destPoint);
            	this.currentPositionX += stepsX;
            	this.currentPositionY += stepsY;
            	break;
            case rt:
            	this.heading += amount;
                break;
            case lt:
                this.heading -= amount;
            default:
            	line = null;
        }
        return line; // we can return the line here since we instantiate it every time.
    }
    
    /**
     * Reset the current position since we'll draw everything new at the next iteration.
     * 
     * The positions shouldn't be null before and after the method.
     * 
     * @param none
     * @return void
     */
    private void resetCurrentPosition() {
    	int height = 700;
    	int width = 1000;
        this.currentPositionX = width/2;
        this.currentPositionY = height/2;
        this.heading = 180;
    }
}