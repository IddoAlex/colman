package boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import exceptions.ColmanException;
import model.MyObservableModel;
import presenter.MVPPresenter;
import presenter.Properties;
import view.MyObservableCLI;

public class Program {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
	/*	MyObservableModel model = new MyObservableModel();
		MyObservableView view = new MyObservableView();
		
		MVPPresenter presenter = new MVPPresenter(model, view);
		
		view.start();*/
		String solvingAlgorithm;
		String generateAlgorithm;
		int numThreads;
		
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("properties.xml"));
			solvingAlgorithm = prop.getProperty("Solving Algorithm");
			generateAlgorithm = prop.getProperty("Generation Algorithm");
			numThreads = Integer.parseInt(prop.getProperty("NumThreads"));
			//ControllerThreadPool.setNumThreadPools(numThreads);
			// Instead, to call setNumThreads in the model
		} catch (FileNotFoundException | ColmanException e) {
			
		}
		System.out.println("Done");
	}

}
