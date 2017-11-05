package gwap.exceptions;


/**
 * @author shruti
 * An exception to encapsulate database related errors
 */
public class DatabaseOperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseOperationException(){
		super("Error in carrying out database operation");
	}
	public DatabaseOperationException(Throwable arg) {
		super("Error in carrying out database operation", arg);
	}

	public DatabaseOperationException(String error, Throwable arg) {
		super(error, arg);
	}
}
