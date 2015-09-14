package controller.commands;

import model.IModel;
import view.IView;

public class DisplayCommand extends CommonCommand {

	public DisplayCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		// TODO Auto-generated method stub

	}

}
