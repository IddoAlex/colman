package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

public abstract class CommonView implements IView {
	
	InputStream in;
	OutputStream out;
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
		cli = new CLI(in, out, map);
	}
	
	@Override
	public void setInputStream(InputStream input) {
		this.in=input;
	}

	@Override
	public void setOutputStream(OutputStream output) {
		this.out=output;
	}

	@Override
	public void start() {
		cli.start();
	}
}
