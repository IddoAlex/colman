package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

// TODO: Auto-generated Javadoc
/**
 * The Interface IView.
 */
public interface IView {
	
	/**
	 * Sets the controller.
	 *
	 * @param aController the new controller
	 */
	public void setController(IController aController);
	
	/**
	 * Sets the string command map.
	 *
	 * @param map the map
	 */
	public void setStringCommandMap(HashMap<String,ICommand> map);
	
	/**
	 * Sets the input stream.
	 *
	 * @param input the new input stream
	 */
	public void setInputStream(InputStream input);
	
	/**
	 * Sets the output stream.
	 *
	 * @param output the new output stream
	 */
	public void setOutputStream(OutputStream output);
	
	/**
	 * Display.
	 *
	 * @param displayable the displayable
	 */
	public void display(IDisplayable displayable);
	
	/**
	 * Start.
	 */
	public void start();

}
