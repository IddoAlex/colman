package view;

import search.Solution;

public interface ISolutionDisplayable<T> extends IDisplayable {

	public Solution<T> getSolution();
}
