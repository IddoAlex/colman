package exceptions;

public class ViewException extends MVCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3600902299661917326L;

	public ViewException() {
		super();
	}
	
	public ViewException(String message) {
		super(message);
	}
}
