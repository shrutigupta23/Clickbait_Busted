package gwap.exceptions;

/**
 * @author shruti
 */
public class RemoteTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RemoteTaskException(){
		super("Error reading remote task results");
	}
	public RemoteTaskException(Throwable arg) {
		super("Error reading remote task results", arg);
	}

	public RemoteTaskException(String error, Throwable arg) {
		super(error, arg);
	}
}

