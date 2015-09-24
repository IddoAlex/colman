package controller.commands;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import exceptions.CommandException;
import model.IModel;
import view.IDisplayable;
import view.IView;

public class GenerateMaze3dCommand extends CommonCommand {

	public GenerateMaze3dCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		String mazeName = args[0];
		ArrayList<String> argu = new ArrayList<>();
		
		for(int i=1; i<args.length; i++) {
			argu.add(args[i]);
		}
		String arguments = String.join(" ", argu);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					model.generateMaze3d(mazeName, arguments);
				} catch (CommandException e) {
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
