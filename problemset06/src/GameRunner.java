import java.util.List;
import java.util.Arrays;

import org.junit.Test;

import com.google.inject.Provider;

import static org.junit.Assert.assertTrue;

@interface IntegrationTest {}
//Annotation definition

/**
 * the |GameRunner| is responsible to run the whole program
 * as a smoke test.
 * 
 * @see * @see http://i0.kym-cdn.com/photos/images/newsfeed/000/085/444/1282786204310.jpg?1318992465
 */

public class GameRunner {

    private Game game;
    // TODO: dice rolling
    List<Player> playersQueue;
    private final Provider<Die> dieProvider = new DieProvider();
    private final Provider<Compass> compassProvider = new CompassProvider();

    @Test
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
