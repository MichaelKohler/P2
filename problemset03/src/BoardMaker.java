import java.util.ArrayList;

/**
 * BoardMaker.java
 * Authors:
 *   Michael Kohler - 11-108-289
 *   Lukas Diener - 11-123-213
 * 
 * This class is responsible to fill up the matrix which is used to print the
 * path. No path means, that the matrix is filled up with zeros. It receives a
 * description string and is responsible to call the parser to get appropriate
 * information about the user input program. The resulting array is necessary to
 * draw the correct path.
 */

class BoardMaker {

    private boolean[][] board;
    private int currentPositionX;
    private int currentPositionY;

    public BoardMaker() {
        this.board = new boolean[100][100];
        this.currentPositionX = 0;
        this.currentPositionY = 0;
        prepareBoard();
    }

    /**
     * Invariant which checks if the board is not null and has a size of
     * 100x100.
     * 
     * @param none
     * @return - whether it is not null and its correct size
     */
    private boolean boardInvariant() {
        return this.board != null && boardSizeIsCorrect();
    }

    /**
     * Checks whether the board is still 100x100 or not. Since the _board is a
     * square array, we assume that all elements have the same length. Hence
     * we're only checking the first element.
     * 
     * @param void
     * @return correct whether the board size is correct or not
     */
    private boolean boardSizeIsCorrect() {
        return this.board.length == 100 && this.board[0].length == 100;
    }

    /**
     * Returns the filled up matrix so TurtleShower can paint the board. If
     * there is no path, all elements of the matrix are zeros. Ones mark the
     * path accordingly.
     * 
     * The description string shouldn't be empty. _board should already exist
     * and be 100x100.
     * 
     * @param description
     *            user input which needs to be parsed first
     * @return boolean[][] matrix with the path marked accordingly
     */
    public boolean[][] makeBoardFrom(String description) {
        assert !description.isEmpty() && boardInvariant();
        prepareBoard();
        ArrayList<Action> actionList = callParseInput(description);
        updatePathInMatrix(actionList);
        resetCurrentPosition();
        return this.board;
    }

    /**
     * Prepares the board, i.e. filling it up with zeros. This method can also
     * be used to reset the board to its initial state.
     * 
     * the board shouldn't be null and should be 100x100 after preparation. the
     * X and Y positions should be 0 after performing the preparation.
     * 
     * @param void
     * @return void
     */
    private void prepareBoard() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                this.board[i][j] = false;
            }
        }
        assert boardInvariant();
        assert this.currentPositionX >= 0 && this.currentPositionY >= 0;
    }

    /**
     * Calls the parser and gets the list of Actions from it.
     * 
     * The Action list shouldn't have a negativ size, which is checked in the
     * updatePathInMatrix method.
     * 
     * @param description
     *            user input
     * @return actionList list of Actions we need to perform
     */
    private ArrayList<Action> callParseInput(String description) {
        ArrayList<Action> actionList = new ArrayList<Action>();
        actionList = InputParser.parseInput(description);
        return actionList;
    }

    /**
     * Updates the matrix with the action we got back from the parser.
     * 
     * The list can't have a negative size. The board should still be 100x100
     * and not null after updating it.
     * 
     * @param actionsToPerform
     *            list of action we need to perform
     * @return void
     */
    private void updatePathInMatrix(ArrayList<Action> actionsToPerform) {
        assert actionsToPerform.size() >= 0;
        for (int i = 0; i < actionsToPerform.size(); i++) {
            callActionPerformer(actionsToPerform.get(i));
        }
        assert boardInvariant();
    }

    /**
     * Performs the update of the specified Action.
     * 
     * @param action
     *            Action which should be performed on the board
     * @return void
     */
    private void callActionPerformer(Action action) {
        int steps = action.getDistance();
        switch (action.getDirection()) {
            case UP:
                if (steps >= 0)
                    moveUp(steps);
                else
                    moveDown(steps);
                break;
            case DOWN:
                if (steps >= 0)
                    moveDown(steps);
                else
                    moveUp(steps);
                break;
            case LEFT:
                if (steps >= 0)
                    moveLeft(steps);
                else
                    moveRight(steps);
                break;
            case RIGHT:
                if (steps >= 0)
                    moveRight(steps);
                else
                    moveLeft(steps);
                break;
        }
    }

    /**
     * Moves up.
     * 
     * The Y position should not be negative after performing the action.
     * Stop at the boarder of the board.
     * 
     * @param steps
     *            number of steps to perform
     * @return void
     */
    private void moveUp(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (this.currentPositionY - 1 >= 0) {
                this.board[this.currentPositionX][this.currentPositionY - 1] = true;
                this.currentPositionY--;
            }
        }
        assert this.currentPositionY >= 0;
    }

    /**
     * Moves down.
     * 
     * The Y position should not be negative after performing the action.
     * Stop at the boarder of the board.
     * 
     * @param steps
     *            number of steps to perform
     * @return void
     */
    private void moveDown(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (this.currentPositionY + 1 <= 99) {
                this.board[this.currentPositionX][this.currentPositionY + 1] = true;
                this.currentPositionY++;
            }
        }
        assert this.currentPositionY >= 0;
    }

    /**
     * Moves left.
     * 
     * The X position should not be negative after performing the action.
     * Stop at the boarder of the board.
     * 
     * @param steps
     *            number of steps to perform
     * @return void
     */
    private void moveLeft(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (this.currentPositionX - 1 >= 0) {
                this.board[this.currentPositionX - 1][this.currentPositionY] = true;
                this.currentPositionX--;
            }
        }
        assert this.currentPositionX >= 0;
    }

    /**
     * Moves right.
     * 
     * The X position should not be negative after performing the action.
     * Stop at the boarder of the board.
     * 
     * @param steps
     *            number of steps to perform
     * @return void
     */
    private void moveRight(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (this.currentPositionX <= 98) {
                this.board[this.currentPositionX + 1][this.currentPositionY] = true;
                this.currentPositionX++;
            }
        }
        assert this.currentPositionX >= 0;
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
        this.currentPositionX = 0;
        this.currentPositionY = 0;
    }
}
