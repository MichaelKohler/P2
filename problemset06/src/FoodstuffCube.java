/**
 * The |FoodstuffCube| is used as food for the amoebe. An amoebe has
 * to eat 3 |FoodstuffCube| per square it lands on in order to survive.
 * The cubes have different colors (each per player). These colors are
 * defined in Game.Color.
 */
public class FoodstuffCube {

    private Game.Color color;

    public FoodstuffCube(Game.Color color) {
         this.color = color;
    }

    /**
     * returns the color of the |FoodstuffCube|
     * 
     * @return this.color
     */
    public Game.Color getColor() {
        assert this.color != null;
        return this.color;
    }
}
