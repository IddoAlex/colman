package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MyView extends CommonView {
	BufferedReader reader;
	PrintWriter writer;
	
	public MyView() {
		in = System.in;
		reader = new BufferedReader(new InputStreamReader(in));
		out = System.out;
		writer = new PrintWriter(out);
	}

	@Override
	public void display(IDisplayable displayable) {
		displayable.display(out);
	}
}
