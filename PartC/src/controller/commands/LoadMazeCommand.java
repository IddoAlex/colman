package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadMazeCommand.
 */
public class LoadMazeCommand extends CommonCommand {

	/**
	 * Instantiates a new load maze command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public LoadMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		// IO Command
		String[] splitted = args[0].split(" ");
		
		try {
			model.loadMaze(splitted[0], splitted[1]);
		} catch (ModelException e) {}

	}

}
