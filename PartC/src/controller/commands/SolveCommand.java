package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class SolveCommand.
 */
public class SolveCommand extends CommonCommand {

	/**
	 * Instantiates a new solve command.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public SolveCommand(IView view, IModel model) {
		super(view, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		String[] splitted = args[0].split(" ");
		String mazeName = splitted[0];
		String algorithmName = splitted[1];
		String[] positions = null;

		if (splitted.length == 5) {
			positions = new String[3];
			for (int i = 0; i < 3; i++) {
				positions[i] = splitted[2 + i];
			}
		}

		try {
			model.solve(mazeName, algorithmName, positions);
		} catch (ModelException e) {}
	}
}
