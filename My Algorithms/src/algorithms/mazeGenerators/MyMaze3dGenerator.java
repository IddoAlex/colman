package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMaze3dGenerator extends CommonMaze3dGenerator {
	Random rnd = new Random();
	private Stack<Position> stack = new Stack<>();
	private int visitedCells = 0;
	
	@Override
	public Maze3d generate(int height, int width, int length) {
		boolean emptyStack = false;
		boolean exitNow = false;
		int totalCells = height*width*length;
		Position entry;
		Position currPos;
		Maze3d maze = new Maze3d(height, width, length);
		maze.initWalls();
		entry = generateEdgePosition(maze);
		maze.setEntryPosition(entry);
		currPos = entry;
		
		visitedCells++;
		
		ArrayList<Position> neighbours;
		int size;
		while (visitedCells < totalCells && (!exitNow))
		{
			neighbours = getNeighbourWithWallsIntact(maze, currPos);
			size = neighbours.size();
			if(size > 0)
			{
				stack.push(currPos);
				currPos=neighbours.get(rnd.nextInt(size));
				maze.setPass(currPos);
			}
			else
			{
				if(!emptyStack)
				{
					currPos = stack.pop();
				}
				else
				{
					// Tried to pop an empty stack.
					exitNow = true;
				}
			}
			visitedCells++;
			emptyStack = stack.isEmpty();
			maze.setExitPosition(currPos);
		}
		return maze;
	}
	
	private ArrayList<Position> getNeighbourWithWallsIntact(Maze3d maze, Position pos)
	{
		ArrayList<Position> intactNeighbours = new ArrayList<>();
		Position tmp = pos.clone();
		
		// IDK how to iterate over a few different functions
		tmp.moveBackwards();
		if(maze.isLegalPosition(tmp))
		{
			if(countPassValueNeighbours(maze, tmp) <= 1)
			{
				intactNeighbours.add(tmp);
			}
		}
		tmp = pos.clone();
		
		tmp.moveDown();
		if(maze.isLegalPosition(tmp))
		{
			if(countPassValueNeighbours(maze, tmp) <= 1)
			{
				intactNeighbours.add(tmp);
			}
		}
		tmp = pos.clone();
		
		tmp.moveForwards();
		if(maze.isLegalPosition(tmp))
		{
			if(countPassValueNeighbours(maze, tmp) <= 1)
			{
				intactNeighbours.add(tmp);
			}
		}
		tmp = pos.clone();
		
		tmp.moveLeft();
		if(maze.isLegalPosition(tmp))
		{
			if(countPassValueNeighbours(maze, tmp) <= 1)
			{
				intactNeighbours.add(tmp);
			}
		}
		tmp = pos.clone();
		
		tmp.moveRight();
		if(maze.isLegalPosition(tmp))
		{
			if(countPassValueNeighbours(maze, tmp) <= 1)
			{
				intactNeighbours.add(tmp);
			}
		}
		tmp = pos.clone();
		
		tmp.moveUp();
		if(maze.isLegalPosition(pos))
		{
			if(countPassValueNeighbours(maze, pos) <= 1)
			{
				intactNeighbours.add(pos);
			}
		}
		tmp = pos.clone();
		
		return intactNeighbours;
	}
	
	private int countPassValueNeighbours(Maze3d maze, Position pos)
	{
		int count=0;
		Position tmp = pos.clone();
		
		tmp.moveBackwards();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		tmp.moveDown();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		tmp.moveForwards();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		tmp.moveLeft();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		tmp.moveRight();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		tmp.moveUp();
		if(maze.getPositionValue(tmp)==0)
		{
			count++;
		}
		tmp=pos.clone();
		
		return count;
	}
	
}
