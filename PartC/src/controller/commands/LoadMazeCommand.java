package controller.commands;

import java.io.OutputStream;
import java.io.PrintWriter;

import exceptions.ModelException;
import model.IModel;
import view.IDisplayable;
import view.IView;

public class LoadMazeCommand extends CommonCommand {

	public LoadMazeCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		try {
			model.loadMaze(args[0], args[1]);
		} catch (ModelException e) {
			view.display(new IDisplayable() {

				@Override
				public void display(OutputStream out) {
					PrintWriter writer = new PrintWriter(out);
					writer.println(e.getMessage());
					writer.flush();
				}
			});
		}
	}

}
