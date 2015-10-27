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
		server=new ServerSocket(port);
		server.setSoTimeout(10*1000);
		threadpool=Executors.newFixedThreadPool(numOfClients);
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop){
					try {
						final Socket someClient=server.accept();
						if(someClient!=null){
							threadpool.execute(new Runnable() {									
								@Override
								public void run() {
									try{										
										clientHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
									}catch(IOException e){
										controller.display(e.getMessage());
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						controller.display("no client connected...");
					} 
					catch (IOException e) {
						controller.display(e.getMessage());
					}
				}
				controller.display("done accepting new clients.");
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
	}

	public void stop() throws Exception {
		stop=true;	
		// do not execute jobs in queue, continue to execute running threads
		controller.display("shutting down");
		threadpool.shutdown();
		// wait 10 seconds over and over again until all running jobs have finished
		@SuppressWarnings("unused")
		boolean allTasksCompleted=false;
		while(!(allTasksCompleted=threadpool.awaitTermination(10, TimeUnit.SECONDS)));
		
		controller.display("all the tasks have finished");

		mainServerThread.join();		
		controller.display("main server thread is done");
		
		server.close();
		controller.display("server is safely closed");
	}
}
