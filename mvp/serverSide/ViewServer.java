package serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ViewServer {
	ControllerServer controller;
	Thread cliThread;

	BufferedReader in;
	PrintWriter out;

	public ViewServer() {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
	}

	public void setController(ControllerServer aController) {
		controller = aController;
	}

	public void start() {
		if (cliThread == null) {
			cliThread = new Thread(new Runnable() {
				boolean keepRunning = true;
				String line;

				@Override
				public void run() {
					out.println("Server CLI Started");
					out.flush();
					do {
						try {
							line = in.readLine();
							switch (line.toLowerCase()) {
							case "start":
								controller.startModel();
								break;
							case "exit":
								keepRunning = false;
								display("Exiting, please wait...");
							case "stop":
								controller.stopModelServer();
								break;
							default:
								out.println("Available inputs:\nstart, stop, exit");
								out.flush();
								break;
							}

						} catch (IOException e) {
							out.println(e.getMessage());
							out.flush();
						}

					} while (keepRunning);

					out.println("Exited server CLI");
					out.flush();
				}
			});

			cliThread.start();
		}
	}

	public void display(String text) {
		out.println(text);
		out.flush();
	}
}
