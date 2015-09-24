package controller.commands;

import java.io.OutputStream;
import java.io.PrintWriter;

import exceptions.ModelException;
import model.IModel;
import view.IDisplayable;
import view.IView;

public class DisplaySolutionCommand extends CommonCommand {

	public DisplaySolutionCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		try {
			model.displaySolution(args);
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
