package controller.commands;

import java.util.ArrayList;
import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class GenerateMaze3dCommand.
 */
public class GenerateMaze3dCommand extends CommonCommand {

	/**
	 * Instantiates a new generate maze3d command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public GenerateMaze3dCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		String[] splitted = args[0].split(" ");
		String mazeName = splitted[0];
		ArrayList<String> argu = new ArrayList<>();
		
		for(int i=1; i<splitted.length; i++) {
			argu.add(splitted[i]);
		}
		String arguments = String.join(" ", argu);

		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					model.generateMaze3d(mazeName, arguments);
				} catch (ModelException e) {
					view.display(new MyDisplayable(e.getMessage()));
				}
			}
		});
	}
}
