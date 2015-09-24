package exceptions;

public class ControllerException extends MVCException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4612947276551704216L;

	public ControllerException() {
		super();
	}

	public ControllerException(String message) {
		super(message);
	}
}
