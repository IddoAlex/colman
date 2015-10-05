package gui;

import view.IDisplayable;
import view.MVPView;

public class MyObservableGUI extends MVPView {

		MazeWindow win = new MazeWindow("Maze Puzzle", 500, 300);
		
		public MyObservableGUI() {
		}

		@Override
		public void start() {
			win.run();
		}

		@Override
		public void display(IDisplayable displayable) {
			// TODO
		}
}
