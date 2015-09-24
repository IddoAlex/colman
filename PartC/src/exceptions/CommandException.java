package exceptions;

public class CommandException extends ControllerException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3238722405844916645L;

	public CommandException() {
		super();
	}

	public CommandException(String message) {
		super(message);
	}
}
