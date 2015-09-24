package model;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.IController;

public abstract class CommonModel implements IModel {

	IController controller;
	HashMap<String, Maze3d> map;

	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}

	@Override
	public void generateMaze3d(String mazeName, String arguments) {
		Maze3d maze;
		String[] parameters = arguments.split(" ");
		
		if (parameters.length == 3) {
			int height = Integer.parseInt(parameters[0]);
			int width = Integer.parseInt(parameters[1]);
			int length = Integer.parseInt(parameters[2]);
			maze = new Maze3d(height, width, length);
		} else {
			maze = new Maze3d();
		}
		
		map.put(mazeName, maze);
	}

}
