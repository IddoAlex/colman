package controller.commands;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import exceptions.ModelException;
import model.IModel;
import view.IDisplayable;
import view.IView;

public class DisplayCommand extends CommonCommand {

	public DisplayCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		ArrayList<String> buffer = new ArrayList<>();
		for(int i=0;i<args.length;i++) {
			buffer.add(args[i]);
		}
		
		String name = String.join(" ", buffer);
		try {
			model.displayMaze(name);
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
