package search;

import java.util.PriorityQueue;

// TODO: Auto-generated Javadoc
/**
 * Implements the Searcher interface.
 * This is an abstract class, which has common functionality for searchers.
 * 
 * implements the backTrace and evaluatedNodes logic, which are the same for all searchers.
 *
 * @author Iddo
 * @version 1.0
 * @param <T> the generic type
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The open list. */
	protected PriorityQueue<State<T>> openList;
	
	/** The evaluated nodes. */
	private int evaluatedNodes;

	/**
	 * Instantiates a new common searcher.
	 */
	public CommonSearcher() {
	}

	/* (non-Javadoc)
	 * @see search.Searcher#search(search.Searchable)
	 */
	@Override
	public abstract Solution<T> search(Searchable<T> s);

	/* (non-Javadoc)
	 * @see search.Searcher#getNumberOfNodesEvaluated()
	 */
	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}

	/**
	 * Pop open list.
	 *
	 * @return the state
	 */
	protected State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();
	}

	/**
	 * Adds the to open list.
	 *
	 * @param state the state
	 */
	protected void addToOpenList(State<T> state) {
		openList.add(state);
	}

	/**
	 * Back trace.
	 *
	 * @param goalState the goal state
	 * @param startState the start state
	 * @return the solution
	 */
	protected Solution<T> backTrace(State<T> goalState, State<T> startState) {
		Solution<T> s = new Solution<T>();
		State<T> currState = goalState;
		
		while (!startState.equals(currState)) {
			s.addState(currState);
			currState = currState.getCameFrom();
		}
		
		// add final stage (where currState reached startState)
		s.addState(currState);
		return s;
	}
}
