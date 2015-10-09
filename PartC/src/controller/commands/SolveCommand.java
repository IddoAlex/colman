package controller.commands;

import java.util.ArrayList;
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
	 * @param view the view
	 * @param model the model
	 */
	public SolveCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		String[] splitted = args[0].split(" ");
		ArrayList<String> buffer = new ArrayList<>();
		String mazeName;
		String algorithmName = splitted[splitted.length-1];
		
		for (int i = 0; i < splitted.length-1; i++) {
			buffer.add(splitted[i]);
		}
		
		mazeName = String.join(" ", buffer);
		try {
			model.solve(mazeName,algorithmName);
		} catch (ModelException e) {}
	}
}
