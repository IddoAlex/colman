package search;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Interface Searchable.
 *
 * @param <T> the generic type
 */
public interface Searchable<T> extends Serializable {
	
	/**
	 * Gets the start state.
	 *
	 * @return the start state
	 */
	State<T> getStartState();

	/**
	 * Gets the goal state.
	 *
	 * @return the goal state
	 */
	State<T> getGoalState();

	/**
	 * Gets the all possible states.
	 *
	 * @param s the s
	 * @return the all possible states
	 */
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
}
