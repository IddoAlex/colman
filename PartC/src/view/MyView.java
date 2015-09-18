package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MyView extends CommonView {
	BufferedReader in;
	PrintWriter out;
	
	public MyView() {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
	}

	@Override
	public void display(String text) {
		out.println(text);
	}


}
