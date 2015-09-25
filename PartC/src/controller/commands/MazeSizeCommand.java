package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeSizeCommand.
 */
public class MazeSizeCommand extends CommonCommand {

	/**
	 * Instantiates a new maze size command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public MazeSizeCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.mazeSize(args);
		} catch (ModelException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}

}
