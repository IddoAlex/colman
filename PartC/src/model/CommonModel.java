package model;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import controller.IController;

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
}
