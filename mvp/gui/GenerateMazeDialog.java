package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GenerateMazeDialog extends BasicDialog {

	String mazeName;
	int mazeHeight;
	int mazeWidth;
	int mazeLength;

	Label nameLabel;
	Label heightLabel;
	Label widthLabel;
	Label lengthLabel;
	
	Label spacer;

	Text nameText;
	Text heightText;
	Text widthText;
	Text lengthText;

	Button okButton;
	Button cancelButton;

	public GenerateMazeDialog(Shell parent) {
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public GenerateMazeDialog(Shell parent, int style) {
		super(parent, style);
		retVal = SWT.CANCEL; // default
	}

	@Override
	protected void initWidgets() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setLayout(new GridLayout(12, true));
		//shell.setSize(300, 200);
		shell.setText("Generate maze");

		nameLabel = new Label(shell, SWT.NULL);
		nameLabel.setText("Maze Name:");
		nameLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		nameText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		heightLabel = new Label(shell, SWT.NULL);
		heightLabel.setText("Maze Height:");
		heightLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		heightText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		heightText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		widthLabel = new Label(shell, SWT.NULL);
		widthLabel.setText("Maze Width:");
		widthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		widthText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		widthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		lengthLabel = new Label(shell, SWT.NULL);
		lengthLabel.setText("Maze Length:");
		lengthLabel.setLayoutData(new GridData(SWT.FILL,SWT.None,false,false,4,1));
		
		lengthText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		lengthText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 8, 1));
		
		spacer = new Label(shell, SWT.NONE);
		spacer.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));

		okButton = new Button(shell, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		okButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (trySetMembers()) {
					retVal = SWT.OK;
					shell.close();
				} else {
					MessageBoxCreator.createErrorMessageBox(shell, "Illegal values entered.");
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,3,1));
		cancelButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				retVal = SWT.CANCEL;
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	public String getMazeName() {
		return mazeName;
	}

	public void setMazeName(String aMazeName) {
		this.mazeName = aMazeName;
	}

	public int getHeight() {
		return mazeHeight;
	}

	public void setHeight(int height) {
		this.mazeHeight = height;
	}

	public int getWidth() {
		return mazeWidth;
	}

	public void setWidth(int width) {
		this.mazeWidth = width;
	}

	public int getLength() {
		return mazeLength;
	}

	public void setLength(int length) {
		this.mazeLength = length;
	}

	private boolean trySetMembers() {
		try {
			this.mazeHeight = Integer.parseInt(heightText.getText());
			this.mazeLength = Integer.parseInt(lengthText.getText());
			this.mazeWidth = Integer.parseInt(widthText.getText());
		} catch (NumberFormatException e) {
			return false;
		}
		
		mazeName = nameText.getText();
		return (mazeName!=null && mazeName != "");
	}

	
}
