package controller.commands;

import java.util.ArrayList;

import exceptions.ModelException;
import model.IModel;
import view.IView;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveMazeCommand.
 */
public class SaveMazeCommand extends CommonCommand {

	/**
	 * Instantiates a new save maze command.
	 *
	 * @param view the view
	 * @param model the model
	 */
	public SaveMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	/* (non-Javadoc)
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		// IO Command
		String[] splitted = args[0].split(" ");
		String fileName = splitted[splitted.length-1];
		
		ArrayList<String> buffer = new ArrayList<>();
		for(int i=0;i<splitted.length-1;i++) {
			buffer.add(splitted[i]);
		}
		
		String mazeName = String.join(" ", buffer);
		
		executor.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					model.saveMaze(mazeName, fileName);
				} catch (ModelException e) {
					view.display(new MyDisplayable(e.getMessage()));
				}
			}
		});
	}
}
