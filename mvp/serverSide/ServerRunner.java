package serverSide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import exceptions.ColmanException;
import presenter.Properties;

public class ServerRunner {
	private static final int DEFAULT_PORT = 5400;
	private static final int DEFAULT_NUM_CLIENTS = 5;
	private static final String PROPERTY_SERVER_STRING = "serverProperty.xml";
	
	ModelServer model;
	ViewServer view;
	Properties properties;
	File serverPropertyFile;

	int port;
	int numClients;

	public ServerRunner() {
		init();
	}

	@SuppressWarnings("unused")
	private void init() {
		serverPropertyFile = new File(PROPERTY_SERVER_STRING);
		properties = new Properties();
		try {
			properties.load(new FileInputStream(serverPropertyFile));
			port = Integer.parseInt(properties.getProperty("Port"));
			numClients = Integer.parseInt(properties.getProperty("NumClients"));
		} catch (FileNotFoundException | ColmanException e) {
			port = DEFAULT_PORT;
			numClients = DEFAULT_NUM_CLIENTS;
			properties.addProperty("Port", port+"");
			properties.addProperty("NumClients", numClients+"");
			try {
				properties.save(new FileOutputStream(serverPropertyFile));
			} catch (FileNotFoundException e1) {
			}
		}

		model = new ModelServer(port, new MyClientHandler(), numClients);
		view = new ViewServer();

		ControllerServer controller = new ControllerServer(model, view);
	}

	public void start() {
		view.start();
	}

	public static void main(String[] args) {
		ServerRunner sr = new ServerRunner();
		sr.start();
	}

}
