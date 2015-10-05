package controller.commands;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;

import model.IModel;
import view.IDisplayable;
import view.IView;

// TODO: Auto-generated Javadoc
/**
 * The Class DirCommand.
 */
public class DirCommand extends CommonCommand {

	/**
	 * Instantiates a new dir command.
	 *
	 * @param view
	 *            the view
	 * @param model
	 *            the model
	 */
	public DirCommand(IView view, IModel model) {
		super(view, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.commands.ICommand#doCommand(java.lang.String[])
	 */
	@Override
	public void doCommand(String... args) {
		// IO Command, so execute in a thread.

		executor.execute(new Runnable() {

			@Override
			public void run() {
				File f = new File(args[0]);
				File[] files = f.listFiles();
				view.display(new IDisplayable() {
					String message;

					@Override
					public void display(OutputStream out) {
						PrintWriter writer = new PrintWriter(out);
						if (files != null) {
							for (File file : files) {
								message+=file.getAbsolutePath() + "\n";
							}
						} else {
							message+="No files found.";
						}
						
						writer.println(getMessage());
						writer.flush();
					}

					@Override
					public void setMessage(String aMessage) {
						this.message = aMessage;
					}

					@Override
					public String getMessage() {
						return message;
					}
				});
			}
		});
	}

}
