package controller.commands;

import java.io.File;

import model.IModel;
import view.IView;

public class DirCommand extends CommonCommand {

	public DirCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		File f = new File(args[0]);
		File[] files = f.listFiles();
		if(files!=null) {
			for (File file : files) {
				view.display(file.getAbsolutePath());
			}
		}
	}

}
