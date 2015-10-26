package demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import search.Searchable;
import search.State;

public class Maze3dSearchable implements Searchable<Position> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3115395649153098467L;
	private Maze3d _maze;
	
	public Maze3dSearchable(Maze3d maze) {
		_maze = maze;
	}
	
	@Override
	public State<Position> getStartState() {
		return new State<Position>(_maze.getStartPosition());
	}

	@Override
	public State<Position> getGoalState() {
		return new State<Position>(_maze.getGoalPosition());
	}

	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		ArrayList<Position> list = _maze.getPossibleMoves(s.getState());
		ArrayList<State<Position>> stateList = new ArrayList<>();
		
		for (Position pos : list) {
			if(_maze.isPass(pos)) {
				stateList.add(new State<Position>(pos));
			}
		}
		
		return stateList;
	}

}
