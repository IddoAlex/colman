package model;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import controller.IController;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;
import search.Searcher;
import search.Solution;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonModel.
 */
public abstract class CommonModel implements MVCModel {
	
	/** The default size. */
	int DEFAULT_SIZE = 5;
	
	/** The controller. */
	IController controller;
	
	/** The map. */
	HashMap<String, Maze3d> map;
	
	/** The algorithm map. */
	HashMap<String, Searcher<Position>> algorithmMap;
	
	/** The solution map. */
	HashMap<String, Solution<Position>> solutionMap;
	
	ExecutorService threadPool;
	
	/**
	 * Instantiates a new common model.
	 */
	public CommonModel() {
		map = new HashMap<>();
		algorithmMap = new HashMap<>();
		solutionMap = new HashMap<>();
		
		initAlgorithmMap();
	}

	/**
	 * Inits the algorithm map.
	 */
	private void initAlgorithmMap() {
		algorithmMap.put("bfs", new BFSSearcher<Position>(0));
		algorithmMap.put("manhattan", new AstarSearcher<Position>(new MazeManhattanHeuristic()));
		algorithmMap.put("air", new AstarSearcher<Position>(new MazeAirHeuristic()));
	}

	/* (non-Javadoc)
	 * @see model.IModel#setController(controller.IController)
	 */
	@Override
	public void setController(IController aController) {
		this.controller = aController;
	}
	
	@Override
	public void setAmountThreads(int numThreads) {
		if(threadPool == null) {
			threadPool = Executors.newFixedThreadPool(numThreads);
		}
	}
}
