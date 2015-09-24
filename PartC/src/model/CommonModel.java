package model;

import java.util.HashMap;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import controller.IController;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
import search.Searcher;
import search.Solution;

public abstract class CommonModel implements IModel {
	
	int DEFAULT_SIZE = 5;
	IController controller;
	HashMap<String, Maze3d> map;
	HashMap<String, Searcher<Position>> algorithmMap;
	HashMap<String, Solution<Position>> solutionMap;
	
	public CommonModel() {
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

	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}
}
