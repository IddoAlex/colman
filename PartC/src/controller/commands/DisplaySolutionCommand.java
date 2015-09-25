package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class DisplaySolutionCommand.
 */
public class DisplaySolutionCommand extends CommonCommand {

	/**
	 * Instantiates a new display solution command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public DisplaySolutionCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.displaySolution(args);
		} catch (ModelException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}
}
