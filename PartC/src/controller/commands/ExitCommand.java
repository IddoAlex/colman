package controller.commands;

import java.util.concurrent.TimeUnit;

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

		executor.shutdown();
		
		displayable.setMessage("Starting shutdown...");
		view.display(displayable);
		
		try {
			executor.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			displayable.setMessage("Awaiting termination interrupted");
			view.display(displayable);
		}
		
		displayable.setMessage("Shutdown complete. Good-bye!");
		view.display(displayable);
	}
}
