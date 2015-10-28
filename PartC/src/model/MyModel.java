package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import demo.Maze3dSearchable;
import exceptions.CommandException;
import exceptions.ModelException;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import search.Searcher;
import search.Solution;
import view.IMazeDisplayable;
import view.MyDisplayable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyModel.
 */
public class MyModel extends CommonModel {

	/**
	 * Instantiates a new my model.
	 */
	public MyModel() {
		super();
	}

	/* (non-Javadoc)
	 * @see model.IModel#generateMaze3d(java.lang.String, java.lang.String)
	 */
	@Override
	public void generateMaze3d(String mazeName, String arguments) throws ModelException {
		if (mazeName == null || mazeName.isEmpty()) {
			throw new ModelException("No name given for maze");
		}

		Maze3d maze;
		String[] parameters = arguments.split(" ");

		if (parameters.length == 3) {
			int height = Integer.parseInt(parameters[0]);
			int width = Integer.parseInt(parameters[1]);
			int length = Integer.parseInt(parameters[2]);
			maze = new MyMazeGenerator().generate(height, width, length);
		} else {
			maze = new MyMazeGenerator().generate(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
		}

		map.put(mazeName, maze);

		controller.display(new MyDisplayable("Maze '" + mazeName + "' is ready"));
	}

	/* (non-Javadoc)
	 * @see model.IModel#displayMaze(java.lang.String)
	 */
	@Override
	public void displayMaze(String name) throws ModelException {

		Maze3d maze = getMaze(name);
		controller.display(new MyDisplayable(maze.toString()));
	}

	/* (non-Javadoc)
	 * @see model.IModel#displayCrossSection(java.lang.String[])
	 */
	@Override
	public void displayCrossSection(String... args) throws CommandException, ModelException {
		String[] splitted = args[0].split(" ");
		if (splitted.length != 4) {
			throw new CommandException("Invalid number of arguments");
		}

		String section = splitted[0];
		int index = Integer.parseInt(splitted[1]);
		String mazeName = splitted[3];

		Maze3d maze = getMaze(mazeName);
		int[][] crossSection;
		switch (section.toLowerCase()) {
		case "x":
			crossSection = maze.getCrossSectionByX(index);
			break;
		case "y":
			crossSection = maze.getCrossSectionByY(index);
			break;
		case "z":
			crossSection = maze.getCrossSectionByZ(index);
			break;
		default:
			throw new CommandException("Cannot get cross section by " + section);
		}

		controller.display(new IMazeDisplayable() {
			String message;

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				for (int i = 0; i < crossSection.length; i++) {
					message+="[ ";
					for (int j = 0; j < crossSection[0].length; j++) {
						message+=crossSection[i][j] + " ";
					}

					message+="]\n";
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

	/* (non-Javadoc)
	 * @see model.IModel#saveMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveMaze(String mazeName, String fileName) throws ModelException {
		Maze3d maze = getMaze(mazeName);

		try {
			FileOutputStream fos = new FileOutputStream(fileName) ;
			MyCompressorOutputStream compressor = new MyCompressorOutputStream(fos);
			compressor.write(maze.toByteArray());
			compressor.close();
		} catch (IOException e) {
			throw new ModelException("IO Error with file path.");
		}
	}

	/* (non-Javadoc)
	 * @see model.IModel#loadMaze(java.lang.String, java.lang.String)
	 */
	@Override
	public void loadMaze(String fileName, String mazeName) throws ModelException {
		try {
			MyDecompressorInputStream decompressor = new MyDecompressorInputStream(new FileInputStream(fileName));

			byte[] initBytes = new byte[12];
			decompressor.read(initBytes, 0, 12);
			ByteBuffer bufferInit = ByteBuffer.wrap(initBytes);

			int height, width, length;
			height = bufferInit.getInt();
			width = bufferInit.getInt();
			length = bufferInit.getInt();
			
			int totalSize = height * width * length + 36;

			byte[] totalBytes = new byte[totalSize];
			ByteBuffer bufferTotal = ByteBuffer.wrap(totalBytes);
			bufferTotal.put(initBytes);
			decompressor.read(totalBytes, 12, totalSize-12);

			Maze3d maze = new Maze3d(totalBytes);
			map.put(mazeName, maze);

			decompressor.close();
		} catch (IOException e) {
			throw new ModelException("IO Error with file name.");
		}
	}

	/* (non-Javadoc)
	 * @see model.IModel#mazeSize(java.lang.String[])
	 */
	@Override
	public void mazeSize(String[] args) throws ModelException {
		String mazeName = args[0];
		Maze3d maze = getMaze(mazeName);
		int totalMazeSize;
		totalMazeSize = maze.getHeight() * maze.getLength() * maze.getWidth() + 36;
		controller.display(new MyDisplayable("Maze '" + mazeName + "' total memory size: " + totalMazeSize + " bytes."));
	}

	/* (non-Javadoc)
	 * @see model.IModel#solve(java.lang.String, java.lang.String)
	 */
	@Override
	public void solve(String name, String algorithm, String... position) throws ModelException {

		Maze3d maze = getMaze(name);
		Searcher<Position> searcher = getAlgorithm(algorithm);

		solutionMap.put(name, searcher.search(new Maze3dSearchable(maze)));

		controller.display(new MyDisplayable("solution for "+ name + " is ready."));
	}
	
	/* (non-Javadoc)
	 * @see model.IModel#displaySolution(java.lang.String[])
	 */
	@Override
	public void displaySolution(String[] args) throws ModelException {
		String mazeName = args[0];
		Solution<Position> solution = solutionMap.get(mazeName);
		if(solution == null) {
			throw new ModelException("Solution for maze '" + mazeName + "' not found.");
		}
		
		controller.display(new MyDisplayable(solution.toString()));
	}

	/**
	 * Gets the maze.
	 *
	 * @param name the name
	 * @return the maze
	 * @throws ModelException the model exception
	 */
	public Maze3d getMaze(String name) throws ModelException {
		Maze3d maze;
		if (name == null || name.isEmpty()) {
			throw new ModelException("No name given for maze");
		}

		maze = map.get(name);
		if (maze == null) {
			throw new ModelException("Maze named '" + name + "' not found");
		}

		return maze;
	}

	/**
	 * Gets the algorithm.
	 *
	 * @param algorithmName the algorithm name
	 * @return the algorithm
	 * @throws ModelException the model exception
	 */
	private Searcher<Position> getAlgorithm(String algorithmName) throws ModelException {
		Searcher<Position> algorithm;
		if (algorithmName == null || algorithmName.isEmpty()) {
			throw new ModelException("No name given for algorithm");
		}

		algorithm = algorithmMap.get(algorithmName);
		if (algorithm == null) {
			throw new ModelException("Maze named '" + algorithmName + "' not found");
		}

		return algorithm;
	}

	@Override
	public Solution<Position> getSolution(String mazeName) throws ModelException {
		Solution<Position> solution;
		if (mazeName == null || mazeName.isEmpty()) {
			throw new ModelException("No name given for maze");
		}

		solution = solutionMap.get(mazeName);
		if (solution == null) {
			throw new ModelException("Maze named '" + mazeName + "' not found");
		}

		return solution;
	}

	@Override
	public void exit() {
		// Shouldn't do anything in MVC
	}

	@Override
	public void getFileSize(String fileName) throws ModelException {
		// Shouldn't do anything in MVC
	}

	@Override
	public void loadProperties(String fileName) {
		// Shouldn't do anything in MVC
	}

	@Override
	public void getPositions(String mazeName) {
		// Shouldn't do anything in MVC
	}

	@Override
	public void moveDirection(String mazeName, String position, String direction) {
		// Shouldn't do anything in MVC
	}
}
