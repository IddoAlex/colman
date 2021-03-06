package presenter;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import controller.commands.DirCommand;
import controller.commands.DisplayCommand;
import controller.commands.DisplayCrossSectionCommand;
import controller.commands.DisplaySolutionCommand;
import controller.commands.ExitCommand;
import controller.commands.FileSizeCommand;
import controller.commands.GenerateMaze3dCommand;
import controller.commands.GetPositionsCommand;
import controller.commands.ICommand;
import controller.commands.LoadMazeCommand;
import controller.commands.LoadPropertiesCommand;
import controller.commands.MazeSizeCommand;
import controller.commands.MoveDirectionCommand;
import controller.commands.SaveMazeCommand;
import controller.commands.SolveCommand;
import model.MVPModel;
import search.Solution;
import view.IMazeDisplayable;
import view.ISolutionDisplayable;
import view.MVPView;
import view.MyDisplayable;

public class MVPPresenter implements Observer {
	MVPModel model;
	MVPView view;
	HashMap<String, ICommand> map;

	public MVPPresenter(MVPModel aModel, MVPView aView) {
		this.model = aModel;
		this.view = aView;

		model.addObserver(this);
		view.addObserver(this);

		map = new HashMap<>();
		initStringCommandMap();
	}

	@Override
	public void update(Observable observable, Object arg) {
		if (observable == view) {
			analyzeViewUpdate((String) arg);
		} else if (observable == model) {
			analyzeModelUpdate(arg);
		}
	}

	private void initStringCommandMap() {
		map.put("dir", new DirCommand(view, model));
		map.put("generate 3d maze", new GenerateMaze3dCommand(view, model));
		map.put("display", new DisplayCommand(view, model));
		map.put("display cross section by", new DisplayCrossSectionCommand(view, model));
		map.put("save maze", new SaveMazeCommand(view, model));
		map.put("load maze", new LoadMazeCommand(view, model));
		map.put("maze size", new MazeSizeCommand(view, model));
		map.put("file size", new FileSizeCommand(view, model));
		map.put("solve", new SolveCommand(view, model));
		map.put("display solution", new DisplaySolutionCommand(view, model));
		map.put("exit", new ExitCommand(view, model));
		map.put("load properties", new LoadPropertiesCommand(view, model));
		map.put("get positions", new GetPositionsCommand(view, model));
		map.put("move direction", new MoveDirectionCommand(view, model));
	}

	private void analyzeViewUpdate(String line) {
		ICommand command;
		String joined;
		String[] splitted = line.toLowerCase().split("\\s+");
		int lastWordIndex = splitted.length + 1;
		List<String> joinStrings;
		List<String> joingArgStrings;

		do {
			joinStrings = new ArrayList<>(--lastWordIndex);

			for (int i = 0; i < lastWordIndex; i++) {
				joinStrings.add(splitted[i]);
			}

			joined = String.join(" ", joinStrings);
			command = map.get(joined);

		} while (command == null && lastWordIndex > 0);

		if (command != null) {
			// build parameters string
			joingArgStrings = new ArrayList<>(splitted.length - lastWordIndex);

			for (int i = lastWordIndex; i < splitted.length; i++) {
				joingArgStrings.add(splitted[i]);
			}

			String arguments = String.join(" ", joingArgStrings);

			command.doCommand(arguments);
		} else {
			view.display(new MyDisplayable("Command '" + line + "' is invalid!"));
		}
	}

	private void analyzeModelUpdate(Object arg) {
		MyDisplayable displayable = new MyDisplayable();

		if (arg instanceof Maze3d) {
			Maze3d maze = (Maze3d) arg;
			displayable.setMessage(maze.toString());
		} else if (arg instanceof String) {
			String line = (String) arg;
			displayable.setMessage(line);
		} else if (arg instanceof Solution<?>) {
			Solution<?> theSolution = (Solution<?>) arg;
			displayable = null;
			
			view.display(new ISolutionDisplayable<Position>() {

				String message;
				@SuppressWarnings("unchecked")
				Solution<Position> solution = (Solution<Position>) theSolution;
				
				@Override
				public void display(OutputStream out) {
					PrintWriter pw = new PrintWriter(out);
					pw.println(solution.toString());
				}

				@Override
				public void setMessage(String aMessage) {
					message = aMessage;
				}

				@Override
				public String getMessage() {
					return message;
				}

				@Override
				public Solution<Position> getSolution() {
					return solution;
				}
			});
		} else if (arg instanceof int[][]) {
			int[][] crossSection = (int[][]) arg;
			displayable = null; // so wouldn't print twice

			view.display(new IMazeDisplayable() {
				String message;

				@Override
				public void display(OutputStream out) {
					PrintWriter writer = new PrintWriter(out);
					for (int i = 0; i < crossSection.length; i++) {
						message += "[ ";
						for (int j = 0; j < crossSection[0].length; j++) {
							message += crossSection[i][j] + " ";
						}

						message += "]\n";
					}

					writer.println(getMessage());
					writer.flush();
				}

				@Override
				public void setMessage(String aMessage) {
					this.message = aMessage;
				}

				@Override
				public String getMessage() {
					return message;
				}

				@Override
				public int[][] getMazeCrossSection() {
					return crossSection;
				}
			});
		}

		view.display(displayable);
	}
}
