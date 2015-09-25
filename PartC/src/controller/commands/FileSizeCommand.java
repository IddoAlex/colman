package controller.commands;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.IModel;
import view.IDisplayable;
import view.IView;
import view.MyDisplayable;

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
		MyDisplayable displayable = new MyDisplayable();

		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					File file = new File(fileName);
					long fileSize = file.length();
					displayable.setMessage("File size: " + fileSize);
					view.display(displayable);
				} catch (NullPointerException e) {
					displayable.setMessage(e.getMessage());
					view.display(displayable);
				}
			}
		});
	}
}
