package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import controller.IController;
import controller.commands.ICommand;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonView.
 */
public abstract class CommonView implements MVCView {
	
	/** The in. */
	InputStream in;
	
	/** The out. */
	OutputStream out;
	
	/** The controller. */
	IController controller;
	
	/** The map. */
	HashMap<String,ICommand> map;
	
	/** The cli. */
	CLI cli;
	
	/* (non-Javadoc)
	 * @see view.IView#setController(controller.IController)
	 */
	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}
	
	/* (non-Javadoc)
	 * @see view.IView#setStringCommandMap(java.util.HashMap)
	 */
	@Override
	public void setStringCommandMap(HashMap<String, ICommand> map) {
		this.map=map;
		cli = new CLI(in, out, map);
	}
	
	/* (non-Javadoc)
	 * @see view.IView#setInputStream(java.io.InputStream)
	 */
	@Override
	public void setInputStream(InputStream input) {
		this.in=input;
	}

	/* (non-Javadoc)
	 * @see view.IView#setOutputStream(java.io.OutputStream)
	 */
	@Override
	public void setOutputStream(OutputStream output) {
		this.out=output;
	}

	/* (non-Javadoc)
	 * @see view.IView#start()
	 */
	@Override
	public void start() {
		cli.start();
	}
}
