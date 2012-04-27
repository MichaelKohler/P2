import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.Provider;

/**
 * An |Amoebe| is a playing figure. The player needs to feed it with |FoodstuffCube|s.
 * An amoebe has the same color as the player it's belonging to.
 * 
 */
public final class Amoebe {

    private final Game.Color color;
    private Square square;
    private int damagePoints;
    private ArrayList<FoodstuffCube> eatenCubes = new ArrayList<FoodstuffCube>();
    private final Provider<Compass> compassProvider;
    
    public Amoebe(Provider<Compass> compassProvider, Game.Color color) {
    	this.compassProvider = compassProvider;
        this.color = color;
        this.square = null;
        this.damagePoints = 0;
    }
    /**
    
     * returns the color of the amoebe
     * 
     * @return this.color
     */
    public Game.Color getColor() {
        return this.color;
    }
    
    /**
     * returns the damage points of the amoebe
     * 
     * @return this.damagePoints
     */
    public int getDamagePoints() {
        return this.damagePoints;
    }
    
    /**
     * returns the square the amoebe is standing on
     * 
     * @return this.square
     */
    public Square getSquare() {
        return new Square(this.square.getPosition());
    }
    
    /**
     * sets the square the amoebe is standing on
     * 
     * @param square    the square the amoebe is standing on
     */
    public void setSquare(Square square) {
        this.square = square;
    }
    
    /**
     * get a damage point
     */
    public void addDamagePoint() {
        this.damagePoints++;
    }
    
    /**
     * makes the amoebe eat
     * 
     * @param useFrugality
     */
    public void eat(boolean useFrugality) {
        List<FoodstuffCube> cubes = Collections.unmodifiableList(this.square.getFoodcubes());
        List<FoodstuffCube> removeCubeList = new ArrayList<FoodstuffCube>();
        int eatenCubes = 0;
        Game.Color firstColorEaten = null;
        int cubesToEat = useFrugality ? 2 : 3;
        for (int i = 0; i < cubes.size(); i++) {
            if (cubes.get(i).getColor() != this.color && eatenCubes <= cubesToEat) {
                if (firstColorEaten == null) {
                    removeCubeList.add(cubes.get(i));
                    this.eatenCubes.add(cubes.get(i));
                    eatenCubes++;
                    firstColorEaten = cubes.get(i).getColor();
                }
                else {
                    if (cubes.get(i).getColor() != firstColorEaten) {
                        removeCubeList.add(cubes.get(i));
                        this.eatenCubes.add(cubes.get(i));
                        eatenCubes++;
                    }
                }
            }
        }
        
        for (int i = 0; i < removeCubeList.size(); i++) {
            this.square.removeFoodstuffCube(removeCubeList.get(i));
        }
        
        if (eatenCubes < cubesToEat) {
            this.addDamagePoint();
        }
        else {
            this.excrement();
        }
    }
    
    /**
     * makes the amoebe poo
     */
    public void excrement() {
        for (int i = 0; i < 2; i++) {
            FoodstuffCube cube = FoodstuffCubeFactory.get(this.color);
            this.square.placeFoodstuffCube(cube);
        }
    }
    
    /**
     * divide the amoebe
     */
    public Amoebe divide() {
        return AmoebeFactory.get(this.compassProvider, this.color);
    }
    
    /**
     * lets the amoebe die
     */
    public void die() {
        this.square.removeAmoebe(this);
    }
    
    /**
     * lets the amoebe drift
     * 
     * @param Direction
     * @param boolean
     */
    public void drift(Compass.Direction direction, boolean useTentacles) {
        int amoebePosX = this.square.getPosition()[0];
        int amoebePosY = this.square.getPosition()[1];
        boolean notNorth = amoebePosY == 0;
        boolean notSouth = amoebePosY == 4;
        boolean notEast = amoebePosX == 4;
        boolean notWest = amoebePosX == 0;
        
        Compass compass = this.compassProvider.get();
        Compass.Direction oldDirection = Compass.direction;
        if (direction != null) {
            Compass.direction = direction;
        }
        
        FoodstuffCube[] carryCubes = new FoodstuffCube[2];
        if (useTentacles) {
            if (this.square.getFoodcubes().size() >= 2) {
                carryCubes[1] = this.square.getFoodcubes().get(1);
                this.square.getFoodcubes().remove(carryCubes[0]);
                carryCubes[2] = this.square.getFoodcubes().get(2);
                this.square.getFoodcubes().remove(carryCubes[1]);
            }
        }
        
        switch (Compass.direction) {
        case NORTH:
            if (!notNorth && !(amoebePosX == 2 && amoebePosY - 1 == 2)) {
                this.square.removeAmoebe(this);
                Board.board[amoebePosX][amoebePosY - 1].enterSquare(this);
                if (useTentacles && carryCubes.length > 0) {
                    Board.board[amoebePosX][amoebePosY - 1].placeFoodstuffCube(carryCubes[0]);
                    Board.board[amoebePosX][amoebePosY - 1].placeFoodstuffCube(carryCubes[1]);
                }
            }
            break;
        case SOUTH:
            if (!notSouth && !(amoebePosX == 2 && amoebePosY + 1 == 2)) {
                this.square.removeAmoebe(this);
                Board.board[amoebePosX][amoebePosY + 1].enterSquare(this);
                if (useTentacles && carryCubes.length > 0) {
                    Board.board[amoebePosX][amoebePosY + 1].placeFoodstuffCube(carryCubes[0]);
                    Board.board[amoebePosX][amoebePosY + 1].placeFoodstuffCube(carryCubes[1]);
                }
            }
            break;
        case EAST:
            if (!notEast && !(amoebePosX + 1 == 2 && amoebePosY == 2)) {
                this.square.removeAmoebe(this);
                Board.board[amoebePosX + 1][amoebePosY].enterSquare(this);
                if (useTentacles && carryCubes.length > 0) {
                    Board.board[amoebePosX + 1][amoebePosY].placeFoodstuffCube(carryCubes[0]);
                    Board.board[amoebePosX + 1][amoebePosY].placeFoodstuffCube(carryCubes[1]);
                }
            }
            break;
        case WEST:
            if (!notWest && !(amoebePosX - 1 == 2 && amoebePosY == 2)) {
                this.square.removeAmoebe(this);
                Board.board[amoebePosX - 1][amoebePosY].enterSquare(this);
                if (useTentacles && carryCubes.length > 0) {
                    Board.board[amoebePosX - 1][amoebePosY].placeFoodstuffCube(carryCubes[0]);
                    Board.board[amoebePosX - 1][amoebePosY].placeFoodstuffCube(carryCubes[1]);
                }
            }
            break;
        }
        
        // restore old compass direction
        Compass.direction = oldDirection;
    }
    
    /**
     * returns a string representation of the object
     * 
     * @return String
     */
    @Override
    public String toString(){
		return "[color="+this.color+", square="+this.square+", damage points="+this.damagePoints+", eaten cubes="+this.eatenCubes+"]";
    }
}