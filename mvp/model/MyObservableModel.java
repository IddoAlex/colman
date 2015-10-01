package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import controller.ControllerThreadPool;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
import search.Searcher;
import search.Solution;

public class MyObservableModel extends MVPModel {
	
	public MyObservableModel() {
		threadPool = ControllerThreadPool.getExecutor();
		mapsFile = new File("maps.bin");
		initModel();
	}
	
	@SuppressWarnings("unchecked")
	private void initModel() {
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(mapsFile);
			gis = new GZIPInputStream(fis);
			ois = new ObjectInputStream(gis);
			map = (HashMap<String, Maze3d>) ois.readObject();
			algorithmMap = (HashMap<String, Searcher<Position>>) ois.readObject();
			solutionMap = (HashMap<String, Solution<Position>>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			map = new HashMap<>();
			algorithmMap = new HashMap<>();
			solutionMap = new HashMap<>();
			initAlgorithmMap();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// Do nothing
			}
		}
	}

	private void initAlgorithmMap() {
		algorithmMap.put("bfs", new BFSSearcher<Position>());
		algorithmMap.put("manhattan", new AstarSearcher<Position>(new MazeManhattanHeuristic()));
		algorithmMap.put("air", new AstarSearcher<Position>(new MazeAirHeuristic()));
	}
}
