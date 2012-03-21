package snakes;

public class VerboseLog extends Log {
	@Override
	public void logInstance(String str) {
		System.out.println(str);
	}
}
