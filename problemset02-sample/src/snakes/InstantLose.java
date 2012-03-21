package snakes;

public class InstantLose extends Square {

	public InstantLose(Game game, int position) {
		super(game, position);
	}

	@Override
	public boolean isInstantLose() {
		return true;
	}

	@Override
	public String toString() {
		return "[" + this.position + "It's a trap!]";
	}

	@Override
	public void enter(Player player) {
		System.out.println(player
				+ ": Darth Vader killed you instead of Obi-Wan!");
	}
}