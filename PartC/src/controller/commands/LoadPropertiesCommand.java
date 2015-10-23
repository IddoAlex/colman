package controller.commands;

import model.IModel;
import view.IView;

public class LoadPropertiesCommand extends CommonCommand {

	public LoadPropertiesCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		model.loadProperties(args[0]);
	}

}
