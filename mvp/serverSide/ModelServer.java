package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ModelServer {
	ControllerServer controller;

	int port;
	ServerSocket server;

	ClientHandler clientHandler;
	int numOfClients;
	ExecutorService threadpool;

	volatile boolean stop;

	Thread mainServerThread;

	public ModelServer(int port, ClientHandler handler, int numOfClients) {
		this.port = port;
		this.numOfClients = numOfClients;
		clientHandler = handler;
	}

	public void setController(ControllerServer controllerServer) {
		controller = controllerServer;
	}

	public void start() throws Exception {
		if (mainServerThread != null) {
			throw new Exception("Server already running...");
		}
		
		server = new ServerSocket(port);
		server.setSoTimeout(10 * 1000);
		threadpool = Executors.newFixedThreadPool(numOfClients);

		controller.display("Server is up, waiting for clients...");

		mainServerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stop) {
					try {
						final Socket someClient = server.accept();
						if (someClient != null) {
							threadpool.execute(new Runnable() {
								@Override
								public void run() {
									try {
										controller.display("Handling client...");
										clientHandler.handleClient(someClient.getInputStream(),
												someClient.getOutputStream());
										someClient.close();
										controller.display("Done handling client");
									} catch (IOException e) {
										controller.display(e.getMessage());
									}
								}
							});
						}
					} catch (SocketTimeoutException e) {
						controller.display("no client connected...");
					} catch (IOException e) {
						controller.display(e.getMessage());
					}
				}
			} // end of the mainServerThread task
		});

		mainServerThread.start();
	}

	public void stop() throws Exception {
		if (!stop) {
			stop = true;
			// do not execute jobs in queue, continue to execute running threads
			controller.display("shutting server down");
			threadpool.shutdown();
			// wait 5 seconds over and over again until all running jobs have
			// finished
			@SuppressWarnings("unused")
			boolean allTasksCompleted = false;
			while (!(allTasksCompleted = threadpool.awaitTermination(5, TimeUnit.SECONDS)))
				;

			mainServerThread.join();

			server.close();
			controller.display("server is safely closed");
		}
	}
}
