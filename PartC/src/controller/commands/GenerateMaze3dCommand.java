package controller.commands;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import algorithms.mazeGenerators.Maze3d;
import exceptions.CommandException;
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

		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				try {
					model.generateMaze3d(mazeName, arguments);
					return model.getMaze(mazeName);
				} catch (CommandException e) {
					view.display(new MyDisplayable(e.getMessage()));
				}
				return null;
			}
		});
	}
}
