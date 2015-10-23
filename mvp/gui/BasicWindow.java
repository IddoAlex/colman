package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow implements Runnable {

	Display display;
	Shell shell;
	boolean widgetsInitialized = false;
	
	public BasicWindow(String title, int width, int height) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(width, height);
		shell.setText(title);
	}

	abstract void initWidgets();
	
	/**
	 * Lets you initialize the widgets before opening the window.
	 * Can be used to set listeners after initializing.
	 * Will initialize the widgets only once, if even run multiple times.
	 */
	public void actualInitWidgets() {
		if(!widgetsInitialized) {
			initWidgets();
			widgetsInitialized = true;
		}
	}

	@Override
	public void run() {
		actualInitWidgets();
		shell.open();
		// main event loop
		while (!shell.isDisposed()) { // while window isn't closed

			// 1. read events, put then in a queue.
			// 2. dispatch the assigned listener
			if (!display.readAndDispatch()) { // if the queue is empty
				display.sleep(); // sleep until an event occurs
			}
		} // shell is disposed

		display.dispose(); // dispose OS components
	}
}
