package model;

import controller.IController;
import exceptions.GenerateException;
import exceptions.MVCException;
import exceptions.ModelException;

public interface IModel {
	
	public void setController(IController aController);

	public void generateMaze3d(String mazeName, String arguments) throws GenerateException;

	public void displayMaze(String name) throws ModelException;

	public void displayCrossSection(String... args) throws MVCException;

	public void saveMaze(String mazeName, String fileName) throws ModelException;

	public void loadMaze(String fileName, String mazeName) throws ModelException;

	public void mazeSize(String[] args) throws ModelException;

	public void solve(String[] args) throws ModelException;

	public void displaySolution(String[] args) throws ModelException;

}
