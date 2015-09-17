package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public interface IView {
	
	public void setController(IController aController);
	public void setStringCommandMap(HashMap<String,ICommand> map);
	public void setInputStream(InputStream input);
	public void setOutputStream(OutputStream output);
	
	public void start();

}
