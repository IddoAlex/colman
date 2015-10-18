package gui;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import view.IDisplayable;
import view.IMazeDisplayable;
import view.MVPView;

public class MyObservableGUI extends MVPView implements Observer {

	MazeWindow win;

	public MyObservableGUI() {
		win = new MazeWindow("Maze Puzzle", 500, 300);
		win.addObserver(this);
	}

	@Override
	public void start() {
		win.run();
	}

	@Override
	public void display(IDisplayable displayable) {
		if (displayable instanceof IMazeDisplayable) {
			IMazeDisplayable mazeDisplayable = (IMazeDisplayable) displayable;
			win.setMazeData(mazeDisplayable.getMazeCrossSection());
		} else {
			String message = displayable.getMessage();
			String reason = message.split(":")[0];
			if (reason.toLowerCase().equals("exception")) {
				MessageBoxCreator.createErrorMessageBox(win.shell, message);
			} else {
				MessageBoxCreator.createNotificationMessageBox(win.shell, message);
			}
		}
	}

	@Override
	public void update(Observable observable, Object arg) {
		String notifiedString = "";
		String line = (String) arg;
		String[] splitted = line.split(":");
		String type;
		String argument;

		if (splitted.length > 1) {
			type = splitted[0];
			argument = splitted[1];
		} else {
			type = line;
			argument = null;
		}

		String fileName;
		String mazeName;
		File file;

		switch (type.toLowerCase()) {
		case "property":
			fileName = line.split(":")[1];
			System.out.println(fileName);
			// notifyObservers(arg);
			break;
		case "save":
			file = new File(argument);
			fileName = file.getName();
			mazeName = fileName.split(".")[0];
			notifiedString = "save maze " + mazeName + " " + argument;
			break;
		case "load":
			file = new File(argument);
			fileName = file.getName();
			mazeName = fileName.split(".")[0];
			notifiedString = "load maze " + argument + " " + mazeName;
			break;
		case "generate":
			notifiedString = "generate 3d maze " + argument;
			break;
		case "solve":
		case "exit":
			break;
		default:
			break;
		}

		setChanged();
		notifyObservers(notifiedString);
	}
}
