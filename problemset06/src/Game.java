import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import com.google.inject.Provider;

/**
 * The Game is the container for the whole application. It creates the board
 * and holds the round counter.
 */
/*
 * AK You did a great job implementing the ursuppe game and are seriously working
 * on the Guice ankle. While at places, you have too deeply nested code, in general
 * I have the feeling that you have a good grasp of the ideas of p2. 
 * 
 * Although I have to admit, I think you might be a bit overly fold of providers and
 * factories, but need to rethink what you're trying to do: separating the creation
 * from the usage of objects, so that you can dispatch on them. But create some (2?)
 * Guice modules for the game and see where it's leading.
 * 
 * BTW I admire your testing practice: nearly 90% test coverage!
 * 
 * So of course, your ursuppe has been...
 * 
 * ACCEPTED
 */
public final class Game {

    private final int DARKZONEBEGINNING = 20; // AK ...now I'm scared!

    public enum Color { 
        BLUE("blue"),
        GREEN("green"),
        RED("red");
        
        private String rep;
        Color(String s) {
            this.rep = s;
        }
        public String toString() {
            return this.rep;
        }
    }
    
    private final Board board;
    private int currentRound;
    private final Provider<Die> dieProvider;
    private final Provider<IDie> iDieProvider; // AK isn't this redundant?
	private final Provider<Compass> compassProvider;
    
    public Game(Provider<Compass> compassProvider, Provider<IDie> iDieProvider, Provider<Die> dieProvider, Player[] players) {
    	this.compassProvider = compassProvider;
    	this.dieProvider = dieProvider;
    	this.iDieProvider = iDieProvider;
        this.board = BoardFactory.get(compassProvider, dieProvider, players);
        System.out.println(this.board.toString());
        this.currentRound = 1;
    }

    /**
     * checks whether there is already a winner or not. If there is
     * one it returns the winner as |Player|. If there is no winner yet
     * this method returns null.
     * 
     * We do not check whether two or more players have the same
     * score within the dark zone. This could be a further improvement.
     * 
     * @return player    player who has won
     */
    public Player checkWinner() {
        Player winner = null;
        Player[] allPlayers = this.board.getPlayers();
        for (int i = 0; i < allPlayers.length; i++) {
            int currentPlayerScore = allPlayers[i].getScore();
            if (currentPlayerScore >= this.DARKZONEBEGINNING) {
                if (winner == null) {
                    winner = allPlayers[i];
                }
                else if (currentPlayerScore > winner.getScore()) {
                    winner = allPlayers[i];
                }
            }
        }
        //TODO
        //this is not yet immutable
        return winner;
    }

    /**
     * returns the number of the current round
     * 
     * @return this.currentRound
     */
    public int getCurrentRound() {
        return this.currentRound;
    }
    
    /**
     * increments the current round
     */
    public void incrementRound() {
        this.currentRound++;
    }
    
    /**
     * returns the list of players who are playing the game
     * 
     * @return this.board.getPlayers()
     */
    public Player[] getPlayers() {
        return this.board.getPlayers().clone();
    }
    
    /**
     * moves the player's amoebe in the specified direction
     * 
     * @param direction
     */
    public void move(Compass.Direction direction, Player currentPlayer) {
        boolean useTentacles = currentPlayer.hasGene("tentacles");
        for (int i = 0; i < Board.getBoardDimensions()[0]; i++) {
            for (int j = 0; j < Board.getBoardDimensions()[1]; j++) {
                List<Amoebe> amoebes = Collections.unmodifiableList(Board.board[i][j].getAmoebesForColor(currentPlayer.getColor()));
                for (int a = 0; a < amoebes.size(); a++) {
                    amoebes.get(a).drift(direction, useTentacles);
                }
            }
        }
        
    }
    
    /**
     * buys a gene and adds it to the player
     * 
     * @param number of gene to buy
     * @param player who is buying
     */
    private void buyGene(int numberOfGene, Player player) {
        Gene gene = null;
        switch (numberOfGene) {
            case 1: 
                if (player.getBp() >= 5)
                    gene = GeneFactory.get("streamlining", 5, 4);
                break;
            case 2:
                if (player.getBp() >= 5)
                    gene = GeneFactory.get("tentacle", 5, 4);
                break;
            case 3:
                if (player.getBp() >= 5)
                    gene = GeneFactory.get("lifeexpectancy", 5, 5);
                break;
            case 4:
                if (player.getBp() >= 6)
                    gene = GeneFactory.get("frugality", 6, 5);
                break;
           }
        if (gene == null)
            return;
        else
            System.out.println("Player " + player.getName() + " has bought the gene " + gene.getGene());
    }
    
    /**
     * does everything which needs to be done in phase 1.
     */
    public void phase1(Player player) {
        // drift
        List<Amoebe> allAmoebesForPlayer = new ArrayList<Amoebe>();
        for (int i = 0; i < Board.getBoardDimensions()[0]; i++) {
            for (int j = 0; j < Board.getBoardDimensions()[1]; j++) {
                List<Amoebe> amoebes = Board.board[i][j].getAmoebesForColor(player.getColor());
                if (amoebes != null)
                    allAmoebesForPlayer.addAll(amoebes);
            }
        }

        // feed & excretion & food shortage
        for (int i = 0; i < allAmoebesForPlayer.size(); i++) {
            Amoebe amoebe = allAmoebesForPlayer.get(i);
            boolean useTentacles = player.hasGene("tentacles");
            amoebe.drift(null, useTentacles);
            boolean useFrugality = player.hasGene("frugality");
            amoebe.eat(useFrugality); // this includes excrements
        }
        
        System.out.println(this.board.toString());
    }
    
    /**
     * does everything which needs to be done in phase 2.
     */
    public void phase2() {
        // new Environment card
        EnvironmentCard card = EnvironmentCardFactory.get(this.iDieProvider);
        System.out.println("New EnvironmentCard!");
    }
    
    /**
     * does everything which needs to be done in phase 3.
     */
    public void phase3() {
        Die die = this.dieProvider.get();
        for(Player player : getPlayers()){
            buyGene(die.roll(1, 4), player); // AK this would be a nice place to decide who handles the user interface -- the Player, the Game or something else entirely -- and move that decision to that class.
        }
    }
    
    /**
     * does everything which needs to be done in phase 4.
     */
    public void phase4(){
        for(Player player : getPlayers()){
            System.out.println("Player " + player.getName() + " gets 10 BP!");
            player.setBp(player.getBp() + 10);
        }
        
        for(int i=getPlayers().length-1; i>-1; i--){
            if (getPlayers()[i].getBp() >= 6) {
                System.out.println("Player " + getPlayers()[i].getName() + " lets his amoebe divide!");
                getPlayers()[i].splitAmoeba();
            }
        }
    }
    
    /**
     * does everything which needs to be done in phase 5.
     */
    public void phase5() {
        // deaths
        List<Amoebe> toBeKilled = new ArrayList<Amoebe>();
        
        for (int i = 0; i < Board.getBoardDimensions()[0]; i++) {
            for (int j = 0; j < Board.getBoardDimensions()[1]; j++) {// AK are you sure about this?
                List<Amoebe> allOfTheSquare = Collections.unmodifiableList(Board.board[i][j].getAmoebesList());
                for (int k = 0; k < allOfTheSquare.size(); k++) { // AK ... need to go deeper
                    for (int l = 0; l < this.getPlayers().length; l++) {
                        if (this.getPlayers()[l].getColor() == allOfTheSquare.get(k).getColor()) { 
                            int maxDamagePoints = this.getPlayers()[l].hasGene("lifeexpectancy") ? 3 : 2; 
                            if (allOfTheSquare.get(k).getDamagePoints() >= maxDamagePoints) {
                                toBeKilled.add(allOfTheSquare.get(k)); // AK ... kill-ception? Seriously, you shouldn't need to nest so deep to get all amoebas. You might delegate to the player (for (Player p : players) { corpses.addAll(p.removeKilledAmoebas()); }) or someone else
                            }
                        }
                    }
               }
            }
        }
        
        for (int i = 0; i < toBeKilled.size(); i++) {
            toBeKilled.get(i).die();
            toBeKilled.get(i).getSquare().setFoodstuffCubes();
        }
        
        System.out.println(this.board.toString());
    }
    
    /**
     * does everything which needs to be done in phase 6.
     * 
     * @return winner
     */
    public Player phase6(){
        for(int k=getPlayers().length - 1; k>-1; k--){
            Player player = getPlayers()[k];
            int amoebaScore = 0;
            for(int i=0; i<Board.getBoardDimensions()[0]; i++){
                for(int j=0; j<Board.getBoardDimensions()[1]; j++){
                    List<Amoebe> amoebes = Board.board[i][j].getAmoebesForColor(player.getColor());
                    amoebaScore += amoebes.size();
                }
            }

            int steps = 0;
            if (amoebaScore == 3) steps = 1; // AK in general prefer dynamic structures (e.g. a Map<Integer, Integer> amoebaCountToScore) to lengthy 
            if (amoebaScore == 4) steps = 2; // case distinctions.
            if (amoebaScore == 5) steps = 4;
            if (amoebaScore == 6) steps = 5;
            if (amoebaScore == 7) steps = 6;
            
            int geneScore = 0;
            if (player.getGenes().size() <= 6 && player.getGenes().size() >= 3)
                geneScore = player.getGenes().size() - 2;
            else if (player.getGenes().size() >= 6)
            	geneScore = 4;
            else
                geneScore = 0;
            
            System.out.print("Player " + player.getName() + " gets " + (geneScore + steps) + " points and has now ");
            player.addScore(geneScore + steps);
            System.out.print(player.getScore() + " points!\n");
        }
        
        System.out.println(this.board.toString());
        return this.checkWinner();
    }
    
    public String toString(){
    	return "[ board="+this.board+", current round="+this.currentRound+"]";
    }
}
