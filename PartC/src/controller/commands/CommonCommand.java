package controller.commands;

import model.IModel;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonCommand.
 */
public abstract class CommonCommand implements ICommand {
	
	/** The view. */
	IView view;
	
	/** The model. */
	IModel model;
	
	/**
	 * Instantiates a new common command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public CommonCommand(IView view, IModel model) {
		this.view = view;
		this.model = model;
	}
}
