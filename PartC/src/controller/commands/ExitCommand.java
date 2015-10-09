package controller.commands;

import model.IModel;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class ExitCommand.
 */
public class ExitCommand extends CommonCommand {

	/**
	 * Instantiates a new exit command.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public ExitCommand(IView view, IModel model) {
		super(view, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		model.exit();
	}
}
