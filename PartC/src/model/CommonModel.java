package model;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMazeGenerator;
import controller.IController;
import exceptions.GenerateException;
import exceptions.ModelException;
import view.IDisplayable;

public abstract class CommonModel implements IModel {
	
	int DEFAULT_SIZE = 5;
	IController controller;
	HashMap<String, Maze3d> map;
	
	public CommonModel() {
		map = new HashMap<>();
	}

	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}

	@Override
	public void generateMaze3d(String mazeName, String arguments) throws GenerateException {
		if(mazeName == null || mazeName.isEmpty()) {
			throw new GenerateException("No name given for maze");
		}
		
		Maze3d maze;
		String[] parameters = arguments.split(" ");
		
		if (parameters.length == 3) {
			int height = Integer.parseInt(parameters[0]);
			int width = Integer.parseInt(parameters[1]);
			int length = Integer.parseInt(parameters[2]);
			maze = new MyMazeGenerator().generate(height, width, length);
		} else {
			maze = new MyMazeGenerator().generate(DEFAULT_SIZE,DEFAULT_SIZE,DEFAULT_SIZE);
		}
		
		map.put(mazeName, maze);
		
		controller.display(new IDisplayable() {
			
			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				writer.println("Maze '" + mazeName + "' is ready");
				writer.flush();
			}
		});
	}
	
	@Override
	public void displayMaze(String name) throws ModelException {
		if(name == null || name.isEmpty()) {
			throw new ModelException("No name given for maze");
		}
		
		Maze3d maze = map.get(name);
		controller.display(new IDisplayable() {
			
			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				
				if(maze==null) {
					writer.println("Maze name '"+name+"' not found.");
				} else {
					writer.println(maze.toString());
				}
				
				writer.flush();
			}
		});
	}

}
