package search;

import java.util.Comparator;

// TODO: Auto-generated Javadoc
/**
 * The Class HeuristicStateComparator.
 *
 * @author Iddo
 * @param <T> the generic type
 */
public class HeuristicStateComparator<T> implements Comparator<State<T>> {

	/** The heuristic. */
	private Heuristic<T> heuristic;
	
	/** The goal. */
	private State<T> goal;

	/**
	 * Instantiates a new heuristic state comparator.
	 *
	 * @param h the h
	 * @param goal the goal
	 */
	public HeuristicStateComparator(Heuristic<T> h, State<T> goal) {
		heuristic = h;
		this.goal = goal;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(State<T> o1, State<T> o2) {
		// Calculates difference of cost of getting to each state + estimated
		// heuristic
		return (int) ((heuristic.getHeuristicCost(o1, goal) + o1.getCost())
				- (heuristic.getHeuristicCost(o2, goal) + o2.getCost()));
	}

}
