package gui;

import view.MVPView;

public class GUIView extends MVPView {

	public static void main(String[] args) {
		MazeWindow win = new MazeWindow("maze example", 500, 300);
		win.run();
	}
}
