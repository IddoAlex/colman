package testsIGNORE;

import gui.MyObservableGUI;
import model.MyObservableModel;
import presenter.MVPPresenter;

public class Tests {

	public static void main(String[] args) {
		MyObservableModel model = new MyObservableModel();
		MyObservableGUI gui = new MyObservableGUI();
		
		MVPPresenter presenter = new MVPPresenter(model, gui);
		
		gui.start();
	}
}
