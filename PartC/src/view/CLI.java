package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.commands.ICommand;

public class CLI {
	BufferedReader in;
	PrintWriter out;
	HashMap<String, ICommand> map;
	Thread mainThread;
	volatile boolean stayInLoop = true;

	public CLI(InputStream input, OutputStream output, HashMap<String, ICommand> aMap) {
		this.in = new BufferedReader(new InputStreamReader(input));
		this.out = new PrintWriter(output);
		this.map = aMap;
	}

	public void start() {
		
		out.println("Trying to open CLI thread...");
		out.flush();
		mainThread = new Thread(new Runnable() {
			String line;
			ICommand command;

			@Override
			public void run() {
				out.println("Opened new CLI thread!");
				out.flush();
				try {
					do {
						out.println("\nEnter a command:");
						out.flush();
						
						line = in.readLine();

						String joined;
						String[] splitted = line.split("\\s+");
						int lastWordIndex = splitted.length + 1;
						List<String> joinStrings;
						List<String> joingArgStrings;

						do {
							joinStrings = new ArrayList<>(--lastWordIndex);
							
							for (int i = 0; i < lastWordIndex; i++) {
								joinStrings.add(splitted[i]);
							}

							joined = String.join(" ", joinStrings);
							command = map.get(joined);

						} while (command == null && lastWordIndex > 0);

						if (command != null) {
							// build parameters string
							joingArgStrings = new ArrayList<>(splitted.length - lastWordIndex);
							
							for (int i = lastWordIndex; i < splitted.length; i++) {
								joingArgStrings.add(splitted[i]);
							}
							
							String arguments = String.join(" ", joingArgStrings);

							if (joined == "exit") {
								stayInLoop = false;
							}

							command.doCommand(arguments);
						} else {
							out.println("Command '" + line + "' is invalid!");
							out.flush();
						}

					} while (stayInLoop);
					out.println("Exitting CLI thread...");
					out.flush();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		mainThread.start();
	}

}
