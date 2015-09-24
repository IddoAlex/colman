package controller.commands;

import java.util.ArrayList;

import model.IModel;
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
		
		model.generateMaze3d(mazeName, arguments);
	}

}
