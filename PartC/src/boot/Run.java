package boot;

import controller.MyController;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		MyModel model = new MyModel();
		MyView view = new MyView();
		MyController controller = new MyController(model, view);
		
		view.start();

	}

}
