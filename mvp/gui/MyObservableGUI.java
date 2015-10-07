package gui;

import java.util.Observable;
import java.util.Observer;

import view.IDisplayable;
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
			// TODO
		}

		@Override
		public void update(Observable observable, Object arg) {
			String argument = (String)arg;
			String type = argument.split(":")[0];
			switch(type.toLowerCase()) {
			case "property":
				String fileName = argument.split(":")[1];
				notifyObservers(arg);
				break;
			}
		}
}
