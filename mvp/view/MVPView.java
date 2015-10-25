package view;

import java.util.HashSet;
import java.util.Observable;

public abstract class MVPView extends Observable implements IView {
	private static final String DEFAULT_SOLVING_ALGORITHM = "air";
	protected String currAlgorithm;
	HashSet<String> legalAlgorithms;
	
	public MVPView() {
		legalAlgorithms = new HashSet<>();
		legalAlgorithms.add("air");
		legalAlgorithms.add("manhatten");
		legalAlgorithms.add("bfs");
		
		currAlgorithm = DEFAULT_SOLVING_ALGORITHM;
	}

	public void setAlgorithm(String algorithm) {
		if(legalAlgorithms.contains(algorithm)) {
			currAlgorithm = algorithm;
		}
	}
}
