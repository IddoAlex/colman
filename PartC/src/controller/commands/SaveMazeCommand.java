package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

public class SaveMazeCommand extends CommonCommand {

	public SaveMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		try {
			model.saveMaze(args[0], args[1]);
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
