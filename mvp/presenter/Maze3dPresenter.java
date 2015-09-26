package presenter;

import java.util.Observable;

import model.MVPModel;
import view.MVPView;

public class Maze3dPresenter implements MVPPresenter {
	MVPModel model;
	MVPView view;

	public Maze3dPresenter(MVPModel aModel, MVPView aView) {
		this.model = aModel;
		this.view = aView;
	}
	
	@Override
	public void update(Observable observable, Object arg) {
		if(observable == model) {
			
		} else if(observable == view) {
			
		}
	}

}
