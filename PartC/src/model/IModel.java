package model;

import controller.IController;
import exceptions.GenerateException;
import exceptions.ModelException;

public interface IModel {
	
	public void setController(IController aController);

	public void generateMaze3d(String mazeName, String arguments) throws GenerateException;

	public void displayMaze(String name) throws ModelException;
}
