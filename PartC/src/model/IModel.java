package model;

import controller.IController;
import exceptions.GenerateException;
import exceptions.MVCException;
import exceptions.ModelException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModel.
 */
public interface IModel {
	
	/**
	 * Sets the controller.
	 *
	 * @param aController the new controller
	 */
	public void setController(IController aController);

	/**
	 * Generate maze3d.
	 *
	 * @param mazeName the maze name
	 * @param arguments the arguments
	 * @throws GenerateException the generate exception
	 */
	public void generateMaze3d(String mazeName, String arguments) throws GenerateException;

	/**
	 * Display maze.
	 *
	 * @param name the name
	 * @throws ModelException the model exception
	 */
	public void displayMaze(String name) throws ModelException;

	/**
	 * Display cross section.
	 *
	 * @param args the args
	 * @throws MVCException the MVC exception
	 */
	public void displayCrossSection(String... args) throws MVCException;

	/**
	 * Save maze.
	 *
	 * @param mazeName the maze name
	 * @param fileName the file name
	 * @throws ModelException the model exception
	 */
	public void saveMaze(String mazeName, String fileName) throws ModelException;

	/**
	 * Load maze.
	 *
	 * @param fileName the file name
	 * @param mazeName the maze name
	 * @throws ModelException the model exception
	 */
	public void loadMaze(String fileName, String mazeName) throws ModelException;

	/**
	 * Maze size.
	 *
	 * @param args the args
	 * @throws ModelException the model exception
	 */
	public void mazeSize(String[] args) throws ModelException;

	/**
	 * Solve.
	 *
	 * @param name the name
	 * @param algorithm the algorithm
	 * @throws ModelException the model exception
	 */
	public void solve(String name, String algorithm) throws ModelException;

	/**
	 * Display solution.
	 *
	 * @param args the args
	 * @throws ModelException the model exception
	 */
	public void displaySolution(String[] args) throws ModelException;

}
