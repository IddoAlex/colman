package gui;

import java.util.Observer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
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

	MazeDisplayer maze;
	
	SelectionListener exitSelectionListener;
	
	ObservableMember observable;

	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		observable = new ObservableMember();
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
		
		shell.addListener(SWT.Close, new Listener()
	    {
	        public void handleEvent(Event event)
	        {
	        	int retVal;
	            retVal = MessageBoxCreator.createMessageBox(shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO, "Confirmation", "Are you sure?");
	            event.doit = retVal == SWT.YES;
	            if(event.doit) {
	            	observable.changeAndNotifyObservers("EXIT");
	            }
	        }
	    });

		exitSelectionListener = new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		};
		
		generateButton = new Button(shell, SWT.PUSH);
		generateButton.setText("Generate");
		generateButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		maze = new Maze3D(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));
		
		solveButton = new Button(shell, SWT.PUSH);
		solveButton.setText("Solve");
		solveButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
		
		sectionGroup = new Group(shell, SWT.SHADOW_IN);
		sectionGroup.setText("Display Section");
		sectionGroup.setLayout(new GridLayout(1,true));
		sectionGroup.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,2));
		
	    xSectionButton = new Button(sectionGroup, SWT.RADIO);
	    xSectionButton.setText("X");
	    xSectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
	    ySectionButton = new Button(sectionGroup, SWT.RADIO);
	    ySectionButton.setText("Y");
	    ySectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));
	    zSectionButton = new Button(sectionGroup, SWT.RADIO);
	    zSectionButton.setText("Z");
	    zSectionButton.setLayoutData(new GridData(SWT.FILL, SWT.None, false, false, 1, 1));

		exitButton = new Button(shell, SWT.PUSH);
		exitButton.setText("Exit");
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		exitButton.setEnabled(true);
		
		
		initMainWindowItemsListeners();
		initFileMenuItemsListeners();
		initHelpMenuItemsListeners();
	}
	
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}

	private void initMainWindowItemsListeners() {
		generateButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeDialog dgd = new GenerateMazeDialog(shell);
				int retVal;
				retVal=dgd.open();
				if(retVal==SWT.OK) {
					observable.changeAndNotifyObservers("GENERATE: " +dgd.getMazeName() + " " + dgd.getHeight() + " " + dgd.getWidth() + " " + dgd.getLength());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		solveButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		exitButton.addSelectionListener(exitSelectionListener);
	}

	private void initHelpMenuItemsListeners() {
		helpHintItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBoxCreator.createErrorMessageBox(shell, "Not yet implemented!");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		helpAboutItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBoxCreator.createNotificationMessageBox(shell, "About", "Created by: Iddo Alexander\nCourse: Algorithmic Java Development\nLecturer: Eli K.");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

	private void initFileMenuItemsListeners() {
		filePropertyItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				propertyFile = fd.open();
				if(propertyFile!=null) {
					observable.changeAndNotifyObservers("PROPERTY: " + propertyFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		fileSaveItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setText("save");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				saveMazeFile = fd.open();
				if(saveMazeFile!=null) {
					observable.changeAndNotifyObservers("SAVE: " + saveMazeFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		fileLoadItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("load");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				loadMazeFile = fd.open();
				if(loadMazeFile!=null) {
					observable.changeAndNotifyObservers("LOAD: " + loadMazeFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		fileExitItem.addSelectionListener(exitSelectionListener);
	}

	public void setMazeData(int[][] crossSection) {
		maze.setMazeData(crossSection);
	}
}
