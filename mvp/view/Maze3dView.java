package view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;

import controller.IController;
import controller.commands.ICommand;

public class Maze3dView extends Observable implements MVPView {
	
	InputStream in;
	OutputStream out;
	BufferedReader reader;
	PrintWriter writer;
	IController controller;
	HashMap<String,ICommand> map;
	
	public Maze3dView() {
		in = System.in;
		reader = new BufferedReader(new InputStreamReader(in));
		out = System.out;
		writer = new PrintWriter(out);
	}
	
	@Override
	public void setInputStream(InputStream input) {
		this.in=input;
	}

	@Override
	public void setOutputStream(OutputStream output) {
		this.out=output;
	}

	@Override
	public void display(IDisplayable displayable) {
		
	}

	@Override
	public void start() {
		
	}
}
