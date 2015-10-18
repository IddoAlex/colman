package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicDialog extends Dialog {
	Shell shell;
	int retVal;
	
	public BasicDialog(Shell parent) {
		super(parent);
	}

	public BasicDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	abstract protected void initWidgets();
	
	public int open() {
		initWidgets();
		shell.pack();
		shell.open();
		shell.layout();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return retVal;
	}
}
