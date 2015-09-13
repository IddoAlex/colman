package search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

// TODO: Auto-generated Javadoc
/**
 * BFSSearcher extends CommonSearcher.
 * Finds a solution to a given maze using the Best-First-Search algorithm,
 * having the cost be based off the distance of each state from the beginning of the maze.
 *
 * @author Iddo
 * @version 1.0
 * @param <T> Generic
 */
public class BFSSearcher<T> extends CommonSearcher<T> {
	
	/**
	 * Instantiates a new BFS searcher.
	 */
	public BFSSearcher() {
		openList = new PriorityQueue<State<T>>(new Comparator<State<T>>() {
			public int compare(State<T> o1, State<T> o2){
				return (int)(o1.getCost() - o2.getCost());
			}
		});
	}

	/**
	 * Best First Search algorithm.
	 * Uses a PriorityQueue for open states,
	 * and a HashSet for closed (seen) states.
	 *
	 * @param s the s
	 * @return the solution
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		addToOpenList(s.getStartState());
		HashSet<State<T>> closedSet = new HashSet<State<T>>();

		while (openList.size() > 0) {
			State<T> n = popOpenList();// dequeue
			closedSet.add(n);

			if (n.equals(s.getGoalState()))
				return backTrace(n, s.getStartState());
			// private method, back traces through the parents

			ArrayList<State<T>> successors = s.getAllPossibleStates(n); // however
																		// it is
																		// implemented
			for (State<T> state : successors) {
				state.setCameFrom(n);
				state.setCost(n.getCost()+1);
				if (!(closedSet.contains(state)) && !(openList.contains(state))) {
					addToOpenList(state);
				} else {
					Collection<State<T>> col;
					boolean openHas = openList.contains(state);

					// Checks who contains the state in question
					if (openHas) {
						col = openList;
					} else {
						col = closedSet;
					}

					Iterator<State<T>> it = col.iterator();

					while (it.hasNext()) {
						State<T> curr = it.next();
						if (state.equals(curr)) {
							if (state.getCost() < curr.getCost()) {
								if (!openHas) { // If it's not in the opened list, add the state with the better cost to the open list.
									openList.add(state);
								} else { // if it's already in open list, adjust it's cost and set parent
									it.remove();
									col.add(curr);
									//curr.setCost(state.getCost());
									//curr.setCameFrom(state.getCameFrom());
								}
							}
						}
					}
				}
			}
		}

		// If reached here, means loop finished going through the openList, and
		// no state was equal to the goalState.
		return null;
	}

}
