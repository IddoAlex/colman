package serverSide;

public class ControllerServer {
	ModelServer model;
	ViewServer view;

	public ControllerServer(ModelServer m, ViewServer v) {
		model = m;
		view = v;
		
		model.setController(this);
		view.setController(this);
	}
	
	public void display(String text) {
		view.display(text);
	}

	public void startModel() {
		try {
			model.start();
		} catch (Exception e) {
			view.display(e.getMessage());
		}
	}

	public void stopModelServer() {
		try {
			model.stop();
		} catch (Exception e) {
			view.display(e.getMessage());
		}
	}
}
