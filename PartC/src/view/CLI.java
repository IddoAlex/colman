package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.commands.ICommand;

public class CLI {
	BufferedReader in;
	PrintWriter out;
	HashMap<String, ICommand> map;
	Thread mainThread;
	volatile boolean stayInLoop = true;

	public CLI(InputStream input, OutputStream output, HashMap<String,ICommand> aMap) {
		this.in = new BufferedReader(new InputStreamReader(input));
		this.out = new PrintWriter(output);
		this.map = aMap;
	}

	public void start() {

		mainThread = new Thread(new Runnable() {
			String line;
			ICommand command;
			
			@Override
			public void run() {
				try {
					do {
						line=in.readLine();
						// TODO
						/*
						 * Analyze the 'line' argument to match specific command, and take it's arguments and pass to doCommand. 
						 */
						
						command = map.get(line); // NOT GOOD
						
						if(line=="exit") {							
							stayInLoop = false;
						}
						
						if(command!=null) {
							command.doCommand();
						} else {
							out.println("Command name '"+ line +"' doesn't exist!");
						}
						
					} while(stayInLoop);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		mainThread.start();
	}

}
