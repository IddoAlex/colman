package controller.commands;

import exceptions.MVCException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class DisplayCrossSectionCommand.
 */
public class DisplayCrossSectionCommand extends CommonCommand {

	/**
	 * Instantiates a new display cross section command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public DisplayCrossSectionCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		try {
			model.displayCrossSection(args);
		} catch (MVCException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}
	}
}
