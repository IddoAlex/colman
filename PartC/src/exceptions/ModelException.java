package exceptions;

public class ModelException extends MVCException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5700105840243867262L;

	public ModelException() {
		super();
	}
	
	public ModelException(String message) {
		super(message);
	}
}
