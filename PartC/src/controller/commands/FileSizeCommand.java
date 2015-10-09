package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class FileSizeCommand.
 */
public class FileSizeCommand extends CommonCommand {

	/**
	 * Instantiates a new file size command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public FileSizeCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		// IO Command

		String fileName = args[0];
		try {
			model.getFileSize(fileName);
		} catch (ModelException e) {}
	}
}
