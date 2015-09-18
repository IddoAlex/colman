package model;

import controller.IController;

public interface IModel {
	
	public void setController(IController aController);

	public void generateMaze3d(String mazeName, String arguments);
}
