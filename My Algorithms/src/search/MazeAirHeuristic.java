package search;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeAirHeuristic.
 */
public class MazeAirHeuristic implements Heuristic<Position> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271519720194952129L;

	/* (non-Javadoc)
	 * @see search.Heuristic#getHeuristicCost(search.State, search.State)
	 */
	@Override
	public double getHeuristicCost(State<Position> curr, State<Position> end) {
		int heightDiff = curr.getState().getHeight() - end.getState().getHeight();
		int widthDiff = curr.getState().getWidth() - end.getState().getWidth();
		int lengthDiff = curr.getState().getLength() - end.getState().getLength();
		double cost=Math.sqrt(Math.pow(heightDiff,2) + Math.pow(widthDiff,2) + Math.pow(lengthDiff,2));
		return cost;
	}

}
