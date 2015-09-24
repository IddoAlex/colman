package controller.commands;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.IModel;
import view.IDisplayable;
import view.IView;

public class DirCommand extends CommonCommand {

	public DirCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		File f = new File(args[0]);
		File[] files = f.listFiles();
		view.display(new IDisplayable() {

			@Override
			public void display(OutputStream out) {
				PrintWriter writer = new PrintWriter(out);
				if (files != null) {
					for (File file : files) {
						writer.println(file.getAbsolutePath());
					}
				} else {
					writer.println("No files found.");
				}
				writer.flush();
			}
		});
	}

}
