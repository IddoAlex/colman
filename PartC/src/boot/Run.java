package boot;

import controller.MyController;
import model.MyModel;
import view.MyView;

// TODO: Auto-generated Javadoc
/**
 * The Class Run.
 */
public class Run {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		MyModel model = new MyModel();
		MyView view = new MyView();
		MyController controller = new MyController(model, view);
		
		view.start();

	}

}
