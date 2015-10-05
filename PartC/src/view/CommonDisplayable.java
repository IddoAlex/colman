package view;

import java.io.OutputStream;
import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonDisplayable.
 */
public abstract class CommonDisplayable implements IDisplayable {
	
	/** The message. */
	String message;
	
	/* (non-Javadoc)
	 * @see view.IDisplayable#display(java.io.OutputStream)
	 */
	@Override
	public void display(OutputStream out) {
		PrintWriter writer = new PrintWriter(out);
		writer.println(message);
		writer.flush();
	}

	/**
	 * Sets the message.
	 *
	 * @param aMessage the new message
	 */
	@Override
	public void setMessage(String aMessage) {
		this.message = aMessage;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}
}
