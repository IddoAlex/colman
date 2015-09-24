package controller;

import java.util.HashMap;

import controller.commands.DirCommand;
import controller.commands.DisplayCommand;
import controller.commands.DisplayCrossSectionCommand;
import controller.commands.DisplaySolutionCommand;
import controller.commands.ExitCommand;
import controller.commands.FileSizeCommand;
import controller.commands.GenerateMaze3dCommand;
import controller.commands.ICommand;
import controller.commands.LoadMazeCommand;
import controller.commands.MazeSizeCommand;
import controller.commands.SaveMazeCommand;
import controller.commands.SolveCommand;
import model.IModel;
import view.IView;

public abstract class CommonController implements IController {
	IModel model;
	IView view;
	HashMap<String,ICommand> map;
	
	public CommonController(IModel aModel, IView aView) {
		this.model = aModel;
		this.view = aView;
		
		model.setController(this);
		view.setController(this);
		
		initStringCommandMap();
	}

	private void initStringCommandMap() {
		map.put("dir", new DirCommand(view, model));
		map.put("generate 3d maze", new GenerateMaze3dCommand(view, model));
		map.put("display", new DisplayCommand(view, model));
		map.put("display cross section by", new DisplayCrossSectionCommand(view, model));
		map.put("save maze", new SaveMazeCommand(view,model));
		map.put("load maze", new LoadMazeCommand(view, model));
		map.put("maze size", new MazeSizeCommand(view, model));
		map.put("file size", new FileSizeCommand(view, model));
		map.put("solve", new SolveCommand(view, model));
		map.put("display solution", new DisplaySolutionCommand(view, model));
		map.put("exit", new ExitCommand(view, model));
	}
}
