package gwap.exceptions;


/**
 * @author shruti
 */
public class PlayerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlayerNotFoundException(){
		super("No players online");
	}
	public PlayerNotFoundException(Throwable arg) {
		super("No players online", arg);
	}

	public PlayerNotFoundException(String error, Throwable arg) {
		super(error, arg);
	}
}
