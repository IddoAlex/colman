package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MazeWindow extends BasicWindow {
	Menu menuBar;
	Menu fileMenu;
	Menu helpMenu;

	MenuItem fileMenuHeader;
	MenuItem helpMenuHeader;

	MenuItem filePropertyItem;
	MenuItem fileSaveItem;
	MenuItem fileLoadItem;
	MenuItem fileExitItem;
	MenuItem helpHintItem;
	MenuItem helpAboutItem;

	String propertyFile;
	String saveMazeFile;
	String loadMazeFile;

	Button generateButton;
	Button solveButton;
	Button exitButton;

	Group sectionGroup;
	Button xSectionButton;
	Button ySectionButton;
	Button zSectionButton;

	Composite positionsComposite;
	Label currPositionLabel;
	Label currPositionText;
	Label goalPositionLabel;
	Label goalPositionText;

	Image backgroundImage;
	
	MazeDisplayer maze;

	SelectionListener exitSelectionListener;

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		actualInitWidgets();
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2, false));
		menuBar = new Menu(shell, SWT.BAR);
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenu = new Menu(shell, SWT.DROP_DOWN);

		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);

		fileMenuHeader.setMenu(fileMenu);
		fileMenuHeader.setText("File");

		helpMenuHeader.setMenu(helpMenu);
		helpMenuHeader.setText("Help");

		filePropertyItem = new MenuItem(fileMenu, SWT.PUSH);
		filePropertyItem.setText("Properties");

		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("Save");

		fileLoadItem = new MenuItem(fileMenu, SWT.PUSH);
		fileLoadItem.setText("Load");

		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("Exit");

		helpHintItem = new MenuItem(helpMenu, SWT.PUSH);
		helpHintItem.setText("Get Hint");

		helpAboutItem = new MenuItem(helpMenu, SWT.PUSH);
		helpAboutItem.setText("About");

		shell.setMenuBar(menuBar);

		generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate");
		generateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		positionsComposite = new Composite(shell, SWT.BORDER_SOLID);
		positionsComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		positionsComposite.setLayout(new RowLayout());

		currPositionLabel = new Label(positionsComposite, SWT.NONE);
		currPositionLabel.setText("Current: ");

		currPositionText = new Label(positionsComposite, SWT.NONE);
		currPositionText.setText("N\\A");

		goalPositionLabel = new Label(positionsComposite, SWT.NONE);
		goalPositionLabel.setText("Goal: ");

		goalPositionText = new Label(positionsComposite, SWT.NONE);
		goalPositionText.setText("N\\A");

		solveButton = new Button(shell, SWT.PUSH);
		solveButton.setText("Solve");
		solveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		maze = new Maze3DDisplayer(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		sectionGroup = new Group(shell, SWT.SHADOW_IN);
		sectionGroup.setText("Display Section");
		sectionGroup.setLayout(new GridLayout(1, true));
		sectionGroup.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 2));

		xSectionButton = new Button(sectionGroup, SWT.RADIO);
		xSectionButton.setText("X - Length");
		xSectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		ySectionButton = new Button(sectionGroup, SWT.RADIO);
		ySectionButton.setText("Y - Height");
		ySectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		zSectionButton = new Button(sectionGroup, SWT.RADIO);
		zSectionButton.setText("Z - Width");
		zSectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		exitButton = new Button(shell, SWT.PUSH);
		exitButton.setText("Exit");
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		exitButton.setEnabled(true);
		
		backgroundImage = new Image(display, "./resources/bar_image.jpg");
		shell.setBackgroundImage(backgroundImage);
	}

	public void setMazeData(int[][] crossSection) {
		maze.setMazeData(crossSection);
	}

	public void setCharacterPosition(int row, int col) {
		maze.setCharacterPosition(row, col);
	}
	
	public void setShellCloseListener(Listener listener) {
		shell.addListener(SWT.Close, listener);
	}

	public void setGenerateSelectionListener(SelectionListener sl) {
		generateButton.addSelectionListener(sl);
	}

	public void setSolveSelectionListener(SelectionListener sl) {
		solveButton.addSelectionListener(sl);
	}

	public void setExitSelectionListener(SelectionListener sl) {
		exitButton.addSelectionListener(sl);
	}

	public void setHelpHintItemSelectionListener(SelectionListener sl) {
		helpHintItem.addSelectionListener(sl);
	}

	public void setHelpAboutItemSelectionListener(SelectionListener sl) {
		helpAboutItem.addSelectionListener(sl);
	}

	public void setFilePropertyItemSelectionListener(SelectionListener sl) {
		filePropertyItem.addSelectionListener(sl);
	}

	public void setFileSaveItemSelectionListener(SelectionListener sl) {
		fileSaveItem.addSelectionListener(sl);
	}

	public void setFileLoadItemSelectionListener(SelectionListener sl) {
		fileLoadItem.addSelectionListener(sl);
	}

	public void setFileExitItemSelectionListener(SelectionListener sl) {
		fileExitItem.addSelectionListener(sl);
	}

	public void setXSectionRadioSelectionListener(SelectionListener sl) {
		xSectionButton.addSelectionListener(sl);
	}

	public void setYSectionRadioSelectionListener(SelectionListener sl) {
		ySectionButton.addSelectionListener(sl);
	}

	public void setZSectionRadioSelectionListener(SelectionListener sl) {
		zSectionButton.addSelectionListener(sl);
	}

	public void setMazeDisplayerKeyListener(KeyListener kl) {
		maze.addKeyListener(kl);
	}

	public void setCurrentPositionText(String posString) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				currPositionText.setText(posString);
				positionsComposite.layout();
			}
		});
	}

	public void setGoalPositionText(String posString) {
		display.syncExec(new Runnable() {

			@Override
			public void run() {
				goalPositionText.setText(posString);
				positionsComposite.layout();
			}
		});
	}
}
