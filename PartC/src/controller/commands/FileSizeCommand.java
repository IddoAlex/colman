package controller.commands;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.IModel;
import view.IDisplayable;
import view.IView;

public class FileSizeCommand extends CommonCommand {

	public FileSizeCommand(IView view, IModel model) {
		super(view, model);
	}

	@Override
	public void doCommand(String... args) {
		String fileName = args[0];
		try {
			File file = new File(fileName);
			long fileSize = file.length();

			view.display(new IDisplayable() {

				@Override
				public void display(OutputStream out) {
					PrintWriter writer = new PrintWriter(out);
					writer.println("File size: " + fileSize);
					writer.flush();
				}
			});
		} catch (NullPointerException e) {
			view.display(new IDisplayable() {

				@Override
				public void display(OutputStream out) {
					PrintWriter writer = new PrintWriter(out);
					writer.println(e.getMessage());
					writer.flush();
				}
			});
		}
	}

}
