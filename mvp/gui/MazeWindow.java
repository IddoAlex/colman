package gui;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
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
	Button generateButton;
	Button exitButton;
	
	Observable observable;
	
	public MazeWindow(String title, int width, int height) {
		super(title, width, height);
		observable = new Observable();
	}

	@Override
	void initWidgets() {
		shell.setLayout(new GridLayout(2,false));
		menuBar = new Menu(shell,SWT.BAR);
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
				
		MazeDisplayer maze=new Maze3D(shell, SWT.BORDER);
		maze.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,1,3));
		
		exitButton=new Button(shell, SWT.PUSH);
		exitButton.setText("Exit");
		exitButton.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		exitButton.setEnabled(true);
		
		filePropertyItem.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd=new FileDialog(shell,SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				propertyFile = fd.open();
				observable.notifyObservers("PROPERTY: "+propertyFile);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		exitButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	}
	
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}
	
}
