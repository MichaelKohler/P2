package snakes;

/**
 * General solution to shutting up the output.
 */
public abstract class Log {
	private static Log instance = new NullLog();
	public static void setLogging(Log log) {
		instance = log;
	}
	public static void log(String str) {
		instance.logInstance(str);
	}
	
	public abstract void logInstance(String str);
}
