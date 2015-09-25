package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class MyView.
 */
public class MyView extends CommonView {
	
	/** The reader. */
	BufferedReader reader;
	
	/** The writer. */
	PrintWriter writer;
	
	/**
	 * Instantiates a new my view.
	 */
	public MyView() {
		in = System.in;
		reader = new BufferedReader(new InputStreamReader(in));
		out = System.out;
		writer = new PrintWriter(out);
	}

	/* (non-Javadoc)
	 * @see view.IView#display(view.IDisplayable)
	 */
	@Override
	public void display(IDisplayable displayable) {
		displayable.display(out);
	}
}
