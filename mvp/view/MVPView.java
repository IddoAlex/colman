package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;

import controller.IController;

public abstract class MVPView extends Observable implements IView {

	InputStream in;
	OutputStream out;
	BufferedReader reader;
	PrintWriter writer;
	IController controller;
	LinkedList<String> lines;

	@Override
	public void setInputStream(InputStream input) {
		this.in = input;
	}

	@Override
	public void setOutputStream(OutputStream output) {
		this.out = output;
	}

	@Override
	public void display(IDisplayable displayable) {
		if (displayable != null) {
			displayable.display(out);
		}
	}

	@Override
	public void start() {
		// IO command
		new Thread(new Runnable() {

			@Override
			public void run() {
				String line = null;
				MyDisplayable displayable = new MyDisplayable();
				
				displayable.setMessage("Please input commands.");
				display(displayable);

				do {
					try {
						line = reader.readLine();
						lines.add(line);
						setChanged();
						notifyObservers();
					} catch (IOException e) {
						displayable.setMessage(e.getMessage());
						display(displayable);
					}
				} while (!(line.toLowerCase().equals("exit")));

				displayable.setMessage("Stopping user input.");
				display(displayable);
			}
		}).start();
	}

	public String getNextLine() {
		return lines.pop();
	}

}
