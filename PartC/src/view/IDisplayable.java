package view;

import java.io.OutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDisplayable.
 */
public interface IDisplayable {
	
	/**
	 * Display.
	 *
	 * @param out the out
	 */
	void display(OutputStream out);

	void setMessage(String aMessage);

	String getMessage();

}
