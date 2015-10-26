package search;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Defines a function which all heuristics must have.
 *
 * @author Iddo
 * @version 1.0
 * @param <T> the generic type
 */
public interface Heuristic<T> extends Serializable {
	
	/**
	 * Gets the heuristic cost.
	 *
	 * @param curr the curr
	 * @param goal the goal
	 * @return the heuristic cost
	 */
	public double getHeuristicCost(State<T> curr, State<T> goal);
}
