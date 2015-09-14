package view;

import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public abstract class CommonView implements IView {
	
	IController controller;
	HashMap<String,ICommand> map;
	CLI cli;

	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}
	
	@Override
	public void setStringCommandMap(HashMap<String, ICommand> map) {
		this.map=map;
	}

	@Override
	public void start() {
		cli.start();
	}
	
}
