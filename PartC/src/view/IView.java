package view;

import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public interface IView {
	
	public void setController(IController aController);
	public void setStringCommandMap(HashMap<String,ICommand> map);
	
	public void start();

}
