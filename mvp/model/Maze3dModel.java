package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import demo.Maze3dSearchable;
import exceptions.*;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import search.Searcher;
import search.Solution;

public class Maze3dModel extends Observable implements MVPModel {

	private static final int DEFAULT_SIZE = 5;
	
	HashMap<String, Maze3d> map;
	
	HashMap<String, Searcher<Position>> algorithmMap;
	
	HashMap<String, Solution<Position>> solutionMap;
	
	public Maze3dModel() {
		map = new HashMap<>();
		algorithmMap = new HashMap<>();
		solutionMap = new HashMap<>();
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
		
		//notifyObservers();
	}

	@Override
	public void displayMaze(String name) throws ModelException {

		Maze3d maze = getMaze(name);
	}

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
	}

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

	@Override
	public void mazeSize(String[] args) throws ModelException {
		String mazeName = args[0];
		Maze3d maze = getMaze(mazeName);
		int totalMazeSize;
		totalMazeSize = maze.getHeight() * maze.getLength() * maze.getWidth() + 36;
		//controller.display(new MyDisplayable("Maze '" + mazeName + "' total memory size: " + totalMazeSize + " bytes."));
	}

	@Override
	public void solve(String name, String algorithm) throws ModelException {

		Maze3d maze = getMaze(name);
		Searcher<Position> searcher = getAlgorithm(algorithm);

		solutionMap.put(name, searcher.search(new Maze3dSearchable(maze)));

		//controller.display(new IDisplayable() 
	}
	
	@Override
	public void displaySolution(String[] args) throws ModelException {
		String mazeName = args[0];
		Solution<Position> solution = solutionMap.get(mazeName);
		if(solution == null) {
			throw new ModelException("Solution for maze '" + mazeName + "' not found.");
		}
		
		//controller.display(new IDisplayable() {

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
