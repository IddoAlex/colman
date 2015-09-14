package controller.commands;

import model.IModel;
import view.IView;

public abstract class CommonCommand implements ICommand {
	IView view;
	IModel model;

	public CommonCommand(IView view, IModel model) {
		this.view = view;
		this.model = model;
	}
}
