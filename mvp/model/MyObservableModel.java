package model;

import java.util.HashMap;

import algorithms.mazeGenerators.Position;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;

public class MyObservableModel extends MVPModel {
	
	public MyObservableModel() {
		map = new HashMap<>();
		algorithmMap = new HashMap<>();
		solutionMap = new HashMap<>();
		
		initAlgorithmMap();
	}
	
	private void initAlgorithmMap() {
		algorithmMap.put("bfs", new BFSSearcher<Position>());
		algorithmMap.put("manhattan", new AstarSearcher<Position>(new MazeManhattanHeuristic()));
		algorithmMap.put("air", new AstarSearcher<Position>(new MazeAirHeuristic()));
	}
}
