package controller.commands;

import java.util.concurrent.TimeUnit;

import exceptions.ColmanException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class ExitCommand.
 */
public class ExitCommand extends CommonCommand {

	/**
	 * Instantiates a new exit command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public ExitCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		MyDisplayable displayable = new MyDisplayable();
		
		try {
			model.exit();
		} catch (ColmanException e) {
			view.display(new MyDisplayable(e.getMessage()));
		}

		executor.shutdown();
		
		displayable.setMessage("Starting shutdown...");
		view.display(displayable);
		
		try {
			executor.awaitTermination(60, TimeUnit.SECONDS);
			displayable.setMessage("Shutdown complete. Good-bye!");
			view.display(displayable);
		} catch (InterruptedException e) {
			displayable.setMessage("Awaiting termination interrupted");
			view.display(displayable);
		}
	}
}
