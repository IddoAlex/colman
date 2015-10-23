package controller.commands;

import exceptions.ModelException;
import model.IModel;
import view.IView;

public class GetPositionsCommand extends CommonCommand {

	public GetPositionsCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		try {
			model.getPositions(args[0]);
		} catch (ModelException e) {
		}
	}

}
