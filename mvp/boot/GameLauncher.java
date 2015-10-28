package boot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import exceptions.ColmanException;
import gui.MyObservableGUI;
import model.MVPModel;
import model.MyObservableModel;
import presenter.MVPPresenter;
import presenter.Properties;
import view.MVPView;
import view.MyObservableCLI;

public class GameLauncher {
	Properties properties;
	
	String propertyFile;
	String solvingAlgorithm;
	String generationAlgorithm;
	String viewToUse;
	int numThreads;
	
	File file;
	MVPModel model;
	MVPView view;
	MVPPresenter presenter;
	
	public GameLauncher() {
		this("properties.xml");
	}
	
	public GameLauncher(String aPropertyFile) {
		propertyFile = aPropertyFile;
		file = new File(propertyFile);
		
		if(!file.exists()) {
			initProperties();
		}
		
		init();
	}

	private void init() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertyFile));
			solvingAlgorithm = properties.getProperty("Solving Algorithm");
			generationAlgorithm = properties.getProperty("Generation Algorithm");
			numThreads = Integer.parseInt(properties.getProperty("NumThreads"));
			viewToUse = properties.getProperty("View");
		} catch (FileNotFoundException | ColmanException | NumberFormatException e) {
			// default values:
			generationAlgorithm = "good";
			viewToUse = "MVP";
		}
		
		if(viewToUse!=null && viewToUse.toLowerCase().equals("cli")) {
			view = new MyObservableCLI();
		} else {
			view = new MyObservableGUI();
		}
		
		model = new MyObservableModel();
		model.setAmountThreads(numThreads);
		view.setAlgorithm(solvingAlgorithm);
		presenter = new MVPPresenter(model, view);
	}

	public void start() {
		view.start();
	}
	
	private void initProperties() {
		Properties p = new Properties();
		p.addProperty("Solving Algorithm", "manhatten");
		p.addProperty("NumThreads", "10");
		p.addProperty("ViewToUse", "MVP");
		p.addProperty("port", "5400");
		p.addProperty("ServerName", "localhost");
		
		try {
			p.save(new FileOutputStream(propertyFile));
		} catch (FileNotFoundException e) {
		}
	}

}
