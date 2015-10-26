package demo;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import search.AstarSearcher;
import search.BFSSearcher;
import search.MazeAirHeuristic;
import search.MazeManhattanHeuristic;

public class Demo {

	public void run() {
		
		//maze1.printMaze();
		
		//Maze3d maze = new MyMaze3dGenerator().generate(8, 8, 8);
		Maze3d maze = new MyMazeGenerator().generate(15, 15, 15);
		maze.printMaze();
		
		System.out.println("Entry: " +maze.getStartPosition() + ", Goal: " + maze.getGoalPosition());
		
		BFSSearcher<Position> bfs = new BFSSearcher<>(0);
		
		Maze3dSearchable mazeAdapter = new Maze3dSearchable(maze);

		AstarSearcher<Position> star1 = new AstarSearcher<>(new MazeAirHeuristic());
		AstarSearcher<Position> star2 = new AstarSearcher<>(new MazeManhattanHeuristic());
		
		bfs.search(mazeAdapter);

		star1.search(mazeAdapter);

		star2.search(mazeAdapter);

		System.out.println("Bfs developed: " + bfs.getNumberOfNodesEvaluated());
		
		System.out.println("Air Developed States: " + star1.getNumberOfNodesEvaluated());
		
		System.out.println("Manhattan Developed States: " + star2.getNumberOfNodesEvaluated());

	}

}
