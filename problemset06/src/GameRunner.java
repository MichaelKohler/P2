import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Provider;

import static org.junit.Assert.assertTrue;

/**
 * the |GameRunner| is responsible to run the whole program
 * as a smoke test.
 * 
 * @see  http://i0.kym-cdn.com/photos/images/newsfeed/000/085/444/1282786204310.jpg?1318992465
 */

/*
 * AK It's not as bad as you put it, a simple trick can make this a normal
 * application and not a test. I didn't have trouble compiling your problemset
 * I only saw that some tests were somewhat uninteresting (getter/setters), 
 * which probably slipped by me on the ursuppe correction.
 * 
 * Another thing is that you should decouple the game from its representation,
 * i.e. not having the game provide the GUI. That way you can have both
 * a GUI and a TextUI on two different runners. If you are interested, 
 * this is probably best solved with Observers and messages:
 *    1) make some messages: https://github.com/zombiecalypse/Ursuppe-Sample/tree/master/src/com/acme/ursuppe/events
 *    2) register the gui as observer: https://github.com/zombiecalypse/Ursuppe-Sample/blob/master/src/com/acme/ursuppe/runners/TextUi.java#L24
 *    3) dispatch all the messages that you can get: https://github.com/zombiecalypse/Ursuppe-Sample/blob/master/src/com/acme/ursuppe/runners/TextUi.java#L34
 *    4) do something interesting with the information: https://github.com/zombiecalypse/Ursuppe-Sample/blob/master/src/com/acme/ursuppe/runners/TextUi.java#L46
 *    
 *    
 * As a sidenote: your problemset is also...
 * 
 * ACCEPTED
 */
public class GameRunner {

    private Game game;
    // TODO: dice rolling
    List<Player> playersQueue;
    private final Provider<Die> dieProvider = new DieProvider();
    private final Provider<Compass> compassProvider = new CompassProvider();
    
    public static void main(String[] args) {
    	(new GameRunner()).shouldRunRandomGame();
    }

    public void shouldRunRandomGame() {
        initGame();
        createPlayerQueueFromDice();
        play();
        
        try {
            Thread.sleep(100000);
            /* If rainbows were something bad, we'd puke rainbows while
             * looking at this code. Seriously, using a Test to run the game
             * doesn't seem to be a good idea now. Before
             * we thought it would be great, but now it makes us quite
             * uncomfortable. Since this PS is about GUI implementation, we'll
             * just ignore this fact even though our tests won't complete.
             * 
             * AK well, the easy way around using tests for this is 
             * added above. One of the great advantages of java over
             * C(++) is that multiple `main` methods are not a problem.
             * In eclipse, you can even choose from them when you Run.
             */
        } catch (Exception ex) {}
    }

    public void initGame() {
        Player player1 = PlayerFactory.get(compassProvider, dieProvider, "Player1");
        player1.chooseColor(Game.Color.RED);
        Player player2 = PlayerFactory.get(compassProvider, dieProvider, "Player2");
        player2.chooseColor(Game.Color.BLUE);
        Player player3 = PlayerFactory.get(compassProvider, dieProvider, "Player3");
        player3.chooseColor(Game.Color.GREEN);
        Player[] players = { player1, player2, player3 };
        playersQueue = Arrays.asList(players); // AK this also works as
//        playersQueue = Arrays.asList(player1, player2, player3);
        assertTrue(playersQueue.size() == 3);
        
        game = GameFactory.get(players);
        game.setState("Game ready..");
    }
    
    /**
     * creates the player queue (according to the rolled die)
     */
    private void createPlayerQueueFromDice() {
        // TODO: create queue
    	// AK Lazy programmers solution (do before making Game):
//    	Collections.shuffle(playersQueue);
    }
    
    /**
     * creates the player queue (according to the score)
     */
    private void createPlayerQueue() {
        // TODO: create queue
    }
    
    /**
     * plays the game
     */
    public void play() {
        Player winner = null;
        while (winner == null) {
            if (game.getCurrentRound() != 1) {
                createPlayerQueue();
            }
            System.out.println("Round " + game.getCurrentRound() + " has started!");
            for (int i = 0; i < playersQueue.size(); i++) {
                game.setState("It's " + playersQueue.get(i).getName() + "'s turn!");
                Die decisions = this.dieProvider.get();
                int number = decisions.roll(1, 2);
                if (number == 1) {
                    game.phase1(playersQueue.get(i));
                }
                else {
                    int action = decisions.roll();
                    Compass.Direction direction = Compass.Direction.SOUTH;
                    switch (action) {
                        case 1:
                            direction = Compass.Direction.WEST;
                            break;
                        case 2:
                            direction = Compass.Direction.NORTH;
                            break;
                        case 3:
                            direction = Compass.Direction.SOUTH;
                            break;
                        case 4:
                            direction = Compass.Direction.EAST;
                            break;
                        case 5:
                            continue; // AK shouldn't I be able to move my amoeba anyway?
                        case 6:
                            int randomDirection = decisions.roll(1, 4);
                            direction = getDirectionForDecision(randomDirection);
                    }
                    game.move(direction, playersQueue.get(i));
                    int bp = playersQueue.get(i).getBp();
                    if (!playersQueue.get(i).hasGene("streamlining"))
                        playersQueue.get(i).setBp(bp - 1);
                }
            }
            game.phase2();
            game.phase3();
            game.phase4();
            game.phase5();
            winner = game.phase6();
            int currentRound = game.getCurrentRound();
            game.incrementRound();
            assertTrue(game.getCurrentRound() == currentRound + 1);
        }
        assertTrue(winner != null);
        game.setState(winner.getName() + " has won!");
    }
    
    private Compass.Direction getDirectionForDecision(int direction) {
        switch (direction) {
	        case 1:
	            return Compass.Direction.NORTH;
	        case 2:
	        	return Compass.Direction.WEST;
	        case 3:
	        	return Compass.Direction.EAST;
	        case 4:
	        	return Compass.Direction.SOUTH;
	        default:
	        	return Compass.Direction.SOUTH;
        }
    }

}
