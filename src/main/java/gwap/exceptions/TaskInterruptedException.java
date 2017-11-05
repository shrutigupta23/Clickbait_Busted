package gwap.exceptions;

/**
 * @author shruti
 */
public class TaskInterruptedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaskInterruptedException(){
		super("Error launching scheduled task");
	}
	public TaskInterruptedException(Throwable arg) {
		super("Error launching scheduled task", arg);
	}

	public TaskInterruptedException(String error, Throwable arg) {
		super(error, arg);
	}
}

