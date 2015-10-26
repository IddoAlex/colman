package search;

import algorithms.mazeGenerators.Position;

// TODO: Auto-generated Javadoc
/**
 * The Class MazeManhattanHeuristic.
 */
public class MazeManhattanHeuristic implements Heuristic<Position> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1867230572541137022L;

	/* (non-Javadoc)
	 * @see search.Heuristic#getHeuristicCost(search.State, search.State)
	 */
	@Override
	public double getHeuristicCost(State<Position> curr, State<Position> end) {
		double cost = Math.abs((curr.getState().getLength()- end.getState().getLength()))
				+ Math.abs((curr.getState().getHeight() - end.getState().getHeight()))
				+ Math.abs((curr.getState().getWidth() - end.getState().getWidth()));
		return cost;
	}

}
