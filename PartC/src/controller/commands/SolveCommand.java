package controller.commands;

import java.io.OutputStream;
import java.io.PrintWriter;

import exceptions.ModelException;
import model.IModel;
import view.IDisplayable;
import view.IView;

public class SolveCommand extends CommonCommand {

	public SolveCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					model.solve(args);
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
		}).start();
	}

}
