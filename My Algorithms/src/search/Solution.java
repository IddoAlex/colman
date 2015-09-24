package search;

import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Solution.
 *
 * @param <T> the generic type
 */
public class Solution<T> {

	/** The list. */
	private LinkedList<State<T>> list;
	
	/**
	 * Instantiates a new solution.
	 */
	public Solution()
	{
		list = new LinkedList<>();
	}
	
	/**
	 * Adds the state.
	 *
	 * @param state the state
	 */
	public void addState(State<T> state)
	{
		list.add(state);
	}
	
	/**
	 * Prints the solution.
	 */
	public void printSolution()
	{
		for (State<T> state : list) {
			state.print();
		}
	}
}
