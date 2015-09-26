package boot;

import model.MyObservableModel;
import presenter.MVPPresenter;
import view.MyObservableView;

public class Program {

	public static void main(String[] args) {
		MyObservableModel model = new MyObservableModel();
		MyObservableView view = new MyObservableView();
		
		MVPPresenter presenter = new MVPPresenter(model, view);
		
		view.start();
	}

}
