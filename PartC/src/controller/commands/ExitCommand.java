package controller.commands;

import model.IModel;
import view.IView;

public class ExitCommand extends CommonCommand {

	public ExitCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		
	}

}
