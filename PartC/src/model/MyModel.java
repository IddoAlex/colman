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
import exceptions.GenerateException;
import exceptions.ModelException;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import search.Searcher;
import search.Solution;
import view.IDisplayable;

public class MyModel extends CommonModel {

	public MyModel() {
		super();
	}

	@Override
	public void generateMaze3d(String mazeName, String arguments) throws GenerateException {
		if (mazeName == null || mazeName.isEmpty()) {
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
			maze = new MyMazeGenerator().generate(DEFAULT_SIZE, DEFAULT_SIZE, DEFAULT_SIZE);
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

		Maze3d maze = getMaze(name);
		controller.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				writer.println(maze.toString());
				writer.flush();
			}
		});
	}

	@Override
	public void displayCrossSection(String... args) throws CommandException, ModelException {
		if (args.length != 4) {
			throw new CommandException("Invalid number of arguments");
		}

		String section = args[0];
		int index = Integer.parseInt(args[1]);
		String mazeName = args[3];

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

		controller.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				for (int i = 0; i < crossSection.length; i++) {
					writer.print("[ ");
					for (int j = 0; j < crossSection[0].length; j++) {
						writer.print(crossSection[i][j] + " ");
					}

					writer.println("]");
				}

				writer.flush();
			}
		});
	}

	@Override
	public void saveMaze(String mazeName, String fileName) throws ModelException {
		Maze3d maze = getMaze(mazeName);

		try {
			MyCompressorOutputStream compressor = new MyCompressorOutputStream(new FileOutputStream(fileName));
			compressor.write(maze.toByteArray());
			compressor.close();
		} catch (IOException e) {
			throw new ModelException("IO Error with file path.");
		}
	}

	@Override
	public void loadMaze(String fileName, String mazeName) throws ModelException {
		try {
			MyDecompressorInputStream decompressor = new MyDecompressorInputStream(new FileInputStream(fileName));

			byte[] initBytes = new byte[12];
			decompressor.read(initBytes, 0, 12);
			ByteBuffer buffer = ByteBuffer.wrap(initBytes);

			int height, width, length;
			height = buffer.getInt();
			width = buffer.getInt();
			length = buffer.getInt();

			byte[] totalBytes = new byte[height * width * length + 36];
			decompressor.read(totalBytes);

			Maze3d maze = new Maze3d(totalBytes);
			map.put(mazeName, maze);

			decompressor.close();
		} catch (IOException e) {
			throw new ModelException("IO Error with file name.");
		}

	}

	@Override
	public void mazeSize(String[] args) throws ModelException {
		String mazeName = args[0];
		Maze3d maze = getMaze(mazeName);
		int totalMazeSize;
		totalMazeSize = maze.getHeight() * maze.getLength() * maze.getWidth() + 36;
		controller.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				writer.println("Maze '" + mazeName + "' total memory size: " + totalMazeSize + " bytes.");
				writer.flush();
			}
		});
	}

	@Override
	public void solve(String[] args) throws ModelException {
		String mazeName = args[0];
		String algorithmName = args[1];

		Maze3d maze = getMaze(mazeName);
		Searcher<Position> searcher = getAlgorithm(algorithmName);

		solutionMap.put(mazeName, searcher.search(new Maze3dSearchable(maze)));

		controller.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				writer.println("solution for "+ mazeName + " is ready.");
				writer.flush();
			}
		});
	}
	
	@Override
	public void displaySolution(String[] args) throws ModelException {
		String mazeName = args[0];
		Solution<Position> solution = solutionMap.get(mazeName);
		if(solution == null) {
			throw new ModelException("Solution for maze '" + mazeName + "' not found.");
		}
		
		controller.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				writer.println(solution.toString());
				writer.flush();
			}
		});
	}

	private Maze3d getMaze(String name) throws ModelException {
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


}
