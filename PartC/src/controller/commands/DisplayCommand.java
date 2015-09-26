package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class DisplayCommand.
 */
public class DisplayCommand extends CommonCommand {

	/**
	 * Instantiates a new display command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public DisplayCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.displayMaze(args[0]);
		} catch (ModelException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}

}
