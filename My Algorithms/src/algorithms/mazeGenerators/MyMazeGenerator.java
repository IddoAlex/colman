package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends CommonMaze3dGenerator {

	private Stack<Position> stack = new Stack<Position>();
	public boolean[][][] poVisited;
	int x,y,z;
	
	@Override
	public Maze3d generate(int x, int y, int z) {
		this.x=x;
		this.y=y;
		this.z=z;
		int visitedCells = 1, totalCells = (x * y * z);

		Maze3d maze = new Maze3d(x, y, z);
		maze.initWalls();
		maze.setEntryPosition(generateEdgePosition(maze));
		maze.setExitPosition(generateEdgePosition(maze));
		Position currp = maze.getStartPosition();
//		maze.goalP=new Position (maze.startP);
		Random rand = new Random();
		poVisited = new boolean[x][y][z];

		for (int i = 0; i < maze.getHeight(); i++)
			for (int j = 0; j < maze.getWidth(); j++)
				for (int k = 0; k < maze.getLength(); k++) {

					poVisited[i][j][k] = false;
				}
		poVisited[currp.getHeight()][currp.getWidth()][currp.getLength()] = true;
		currp.visited = true;
	//	poVisited[maze.getGoalPosition().getX()][maze.getGoalPosition().getY()][maze.getGoalPosition().getZ()] = true;
	//	currp.visited = true;
		stack.push(currp);
		while (!stack.isEmpty()&&visitedCells < totalCells) {
			ArrayList<Position> neighbors = FindAllUnvisitedNeighbors(currp, maze);
			if (neighbors.size() > 0) {
				Position chosenNeighbor = neighbors.get(rand.nextInt(neighbors.size()));
				chosenNeighbor.visited = true;
	//			System.out.println(currp);
				currp = chosenNeighbor;
				poVisited[currp.getHeight()][currp.getWidth()][currp.getLength()] = true;
				stack.push(currp);
				visitedCells++;
				maze.setPass(currp);
			} else if (!stack.isEmpty()) {
				currp = stack.pop();
				visitedCells++;
			}

		}
		maze.setPass(maze.getGoalPosition());
		return maze;
	}

	private ArrayList<Position> FindAllUnvisitedNeighbors(Position currentPosition, Maze3d maze) {
		ArrayList<Position> neighbors = new ArrayList<Position>();

		if (currentPosition.getHeight() < maze.getHeight() - 1
				&& poVisited[currentPosition.getHeight() + 1][currentPosition.getWidth()][currentPosition.getLength()] == false&&checkVisited(currentPosition.getHeight()+1, currentPosition.getWidth(), currentPosition.getLength())) {
			neighbors.add(new Position(currentPosition.getHeight() + 1, currentPosition.getWidth(), currentPosition.getLength()));
		}
		if (currentPosition.getHeight() > 0
				&& poVisited[currentPosition.getHeight() - 1][currentPosition.getWidth()][currentPosition.getLength()] == false&&checkVisited(currentPosition.getHeight()-1, currentPosition.getWidth(), currentPosition.getLength())) {
			neighbors.add(new Position(currentPosition.getHeight() - 1, currentPosition.getWidth(), currentPosition.getLength()));
		}
		if (currentPosition.getWidth() < maze.getWidth() - 1
				&& poVisited[currentPosition.getHeight()][currentPosition.getWidth() + 1][currentPosition.getLength()] == false&&checkVisited(currentPosition.getHeight(), currentPosition.getWidth()+1, currentPosition.getLength())) {
			neighbors.add(new Position(currentPosition.getHeight(), currentPosition.getWidth() + 1, currentPosition.getLength()));
		}
		if (currentPosition.getWidth() > 0 && poVisited[currentPosition.getHeight()][currentPosition.getWidth() -1][currentPosition.getLength()] == false&&checkVisited(currentPosition.getHeight(), currentPosition.getWidth()-1, currentPosition.getLength())) {
			neighbors.add(new Position(currentPosition.getHeight(), currentPosition.getWidth() -1, currentPosition.getLength()));

		}
		if (currentPosition.getLength() < maze.getLength() - 1 &&poVisited[currentPosition.getHeight()][currentPosition.getWidth()][currentPosition.getLength()+1] == false&&checkVisited(currentPosition.getHeight(), currentPosition.getWidth(), currentPosition.getLength()+1)) {
			neighbors.add(new Position(currentPosition.getHeight(), currentPosition.getWidth() , currentPosition.getLength()+1));
		}
		if (currentPosition.getLength() > 0 && poVisited[currentPosition.getHeight()][currentPosition.getWidth()][currentPosition.getLength()-1] == false&&checkVisited(currentPosition.getHeight(), currentPosition.getWidth(), currentPosition.getLength()-1)) {
			neighbors.add(new Position(currentPosition.getHeight(), currentPosition.getWidth() , currentPosition.getLength()-1));

		}

		return neighbors;

	}

	private boolean checkVisited(int i,int j,int k)
	{
		int counter=0;
		if(i<x-1){
			if (poVisited[i+1][j][k]==true)
			counter++;}
		if(i>0){
			if (poVisited[i-1][j][k]==true)
			counter++;}
		if(j<y-1){
			if (poVisited[i][j+1][k]==true)
			counter++;}
		if(j>0){
			if (poVisited[i][j-1][k]==true)
			counter++;}
		if(k<z-1){
			if (poVisited[i][j][k+1]==true)
			counter++;}
		if(k>0){
			if (poVisited[i][j][k-1]==true)
			counter++;}
		
		if (counter>1)
			return false;
		return true;
		
	}
	

}
