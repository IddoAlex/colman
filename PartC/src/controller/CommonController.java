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
import view.IDisplayable;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonController.
 */
public abstract class CommonController implements IController {
	
	/** The model. */
	IModel model;
	
	/** The view. */
	IView view;
	
	/** The map. */
	HashMap<String,ICommand> map;
	
	/**
	 * Instantiates a new common controller.
	 *
	 * @param aModel the a model
	 * @param aView the a view
	 */
	public CommonController(IModel aModel, IView aView) {
		this.model = aModel;
		this.view = aView;
		
		model.setController(this);
		view.setController(this);
		
		map = new HashMap<>(12);
		initStringCommandMap();
		
		view.setStringCommandMap(map);
	}

	/**
	 * Inits the string command map.
	 */
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
	
	/* (non-Javadoc)
	 * @see controller.IController#display(view.IDisplayable)
	 */
	@Override
	public void display(IDisplayable displayable) {
		view.display(displayable);
	}
	
}
