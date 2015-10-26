package search;

import java.io.Serializable;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class Solution.
 *
 * @param <T> the generic type
 */
public class Solution<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public LinkedList<State<T>> getSolutionList() {
		return list;
	}
	
	public String toString() {
		String solutionString = "";
		
		for(State<T> state: list) {
			solutionString += state.toString() +"\n";
		}
		
		return solutionString;
	}
}
