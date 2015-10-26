package search;

import java.util.PriorityQueue;

// TODO: Auto-generated Javadoc
/**
 * Astar searcher, extends the BFSSearcher and overrides it's openList
 * implementation.
 *
 * @author Iddo
 * @version 1.0
 * @param <T>
 *            generic
 */
public class AstarSearcher<T> extends BFSSearcher<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6332645588872909455L;
	/** The heuristic. */
	private Heuristic<T> heuristic;

	public AstarSearcher() {}
	
	/**
	 * Instantiates a new astar searcher.
	 *
	 * @param h
	 *            the h
	 */
	public AstarSearcher(Heuristic<T> h) {
		heuristic = h;
	}

	/**
	 * Sets the heuristic.
	 *
	 * @param h
	 *            the new heuristic
	 */
	public void setHeuristic(Heuristic<T> h) {
		this.heuristic = h;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see search.BFSSearcher#search(search.Searchable)
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		if (heuristic != null) {
			openList = new PriorityQueue<>(new HeuristicStateComparator<>(heuristic, s.getGoalState()));
			return super.search(s);
		}
		
		return null;
	}
}
