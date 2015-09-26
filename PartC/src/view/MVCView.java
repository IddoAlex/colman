package view;

import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public interface MVCView extends IView {

	
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
}
