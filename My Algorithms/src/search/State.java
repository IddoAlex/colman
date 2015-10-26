package search;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class State.
 *
 * @param <T> the generic type
 */
public class State<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 550240802931656281L;

	/** The state. */
	private T state; // the state represented
	
	/** The cost. */
	private double cost; // cost to reach this state
	
	/** The came from. */
	private State<T> cameFrom; // the state we came from to this state
	
	public State() {
		cost=0;
	}

	/**
	 * Instantiates a new state.
	 *
	 * @param state the state
	 */
	public State(T state) { // CTOR
		this.state = state;
		cost = 0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State<T> other = (State<T>) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
	/**
	 * Sets the came from.
	 *
	 * @param state the new came from
	 */
	public void setCameFrom(State<T> state) {
		this.cameFrom = state;
	}

	/**
	 * Gets the came from.
	 *
	 * @return the came from
	 */
	public State<T> getCameFrom() {
		return this.cameFrom;
	}

	/**
	 * Sets the cost.
	 *
	 * @param cost the new cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setState(T aState) {
		this.state = aState;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public T getState()	{
		return state;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}
	
	/**
	 * Prints the.
	 */
	public void print()
	{
		System.out.println(state.toString());
	}
	
	public String toString() {
		return state.toString();
	}
}
