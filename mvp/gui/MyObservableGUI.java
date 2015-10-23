package gui;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenerators.Position;
import view.IDisplayable;
import view.IMazeDisplayable;
import view.MVPView;

public class MyObservableGUI extends MVPView {

	MazeWindow win;

	String propertyFile;
	String saveMazeFile;
	String loadMazeFile;

	String currMazeName;

	Position currPosition;
	Position endPosition;

	Button selectedCrossSection;

	public MyObservableGUI() {
		win = new MazeWindow("Maze Puzzle", 500, 300);
	}

	@Override
	public void start() {
		win.actualInitWidgets();
		initMazeWindowListeners();
		win.run();
	}

	@Override
	public void display(IDisplayable displayable) {
		if (displayable != null) {
			if (displayable instanceof IMazeDisplayable) {
				IMazeDisplayable mazeDisplayable = (IMazeDisplayable) displayable;
				win.setMazeData(mazeDisplayable.getMazeCrossSection());
			} else {
				String message = displayable.getMessage().toLowerCase();
				String reason = message.split(":")[0];
				switch (reason) {
				case "exception":
					MessageBoxCreator.createErrorMessageBox(win.shell, message);
					break;
				case "generate":
				case "load":
					currMazeName = message.split(":")[1].trim().split(" ")[1];
					setChanged();
					notifyObservers("get positions " + currMazeName);
					break;
				case "positions":
					String[] posValues = message.split(":")[1].trim().split(" ");
					currPosition = new Position(Integer.parseInt(posValues[0]), Integer.parseInt(posValues[1]),
							Integer.parseInt(posValues[2]));
					endPosition = new Position(Integer.parseInt(posValues[3]), Integer.parseInt(posValues[4]),
							Integer.parseInt(posValues[5]));
					win.setCurrentPositionText("( " + posValues[0] + ", " + posValues[1] + ", " + posValues[2] + ")");
					win.setGoalPositionText("( " + posValues[3] + ", " + posValues[4] + ", " + posValues[5] + ")");
					clickSelectedCrossSection();
				default:
					MessageBoxCreator.createNotificationMessageBox(win.shell, message);
					break;
				}
			}
		}
	}

	private void initMazeWindowListeners() {

		win.setShellCloseListener(new Listener() {
			@Override
			public void handleEvent(Event event) {
				int retVal;
				retVal = MessageBoxCreator.createMessageBox(win.shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO,
						"Confirmation", "Are you sure?");
				event.doit = retVal == SWT.YES;
				if (event.doit) {
					setChanged();
					notifyObservers("EXIT");
				}
			}
		});

		win.setGenerateSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeDialog dgd = new GenerateMazeDialog(win.shell);
				int retVal;
				retVal = dgd.open();
				if (retVal == SWT.OK) {
					setChanged();
					notifyObservers("generate 3d maze " + dgd.getMazeName() + " " + dgd.getHeight() + " "
							+ dgd.getWidth() + " " + dgd.getLength());
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setSolveSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				// TODO
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setHelpHintItemSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBoxCreator.createErrorMessageBox(win.shell, "Not yet implemented!");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setHelpAboutItemSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBoxCreator.createNotificationMessageBox(win.shell, "About",
						"Created by: Iddo Alexander\nCourse: Algorithmic Java Development\nLecturer: Eli K.");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setFilePropertyItemSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(win.shell, SWT.OPEN);
				fd.setText("open");
				fd.setFilterPath("");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				propertyFile = fd.open();
				if (propertyFile != null) {
					setChanged();
					notifyObservers("PROPERTY: " + propertyFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setFileSaveItemSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(win.shell, SWT.SAVE);
				fd.setText("save");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				saveMazeFile = fd.open();
				if (saveMazeFile != null) {
					File file = new File(saveMazeFile);
					String fileName = file.getName();
					String mazeName = fileName.split(".")[0];

					setChanged();
					notifyObservers("save maze " + mazeName + " " + saveMazeFile);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setFileLoadItemSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(win.shell, SWT.OPEN);
				fd.setText("load");
				fd.setFilterPath("");
				String[] filterExt = { "*.maz" };
				fd.setFilterExtensions(filterExt);
				loadMazeFile = fd.open();
				if (loadMazeFile != null) {
					File file = new File(loadMazeFile);
					String fileName = file.getName();
					String mazeName = fileName.split(".")[0];

					setChanged();
					notifyObservers("load maze " + loadMazeFile + " " + mazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setXSectionRadioSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				selectedCrossSection = win.xSectionButton;
				if (currMazeName != null) {
					setChanged();
					notifyObservers("display cross section by X " + currPosition.getLength() + " for " + currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setYSectionRadioSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				selectedCrossSection = win.ySectionButton;
				if (currMazeName != null) {
					setChanged();
					notifyObservers("display cross section by Y " + currPosition.getHeight() + " for " + currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setZSectionRadioSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				selectedCrossSection = win.zSectionButton;
				if (currMazeName != null) {
					setChanged();
					notifyObservers("display cross section by Z " + currPosition.getWidth() + " for " + currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		SelectionListener exitSelectionListener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				win.shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		};

		win.setExitSelectionListener(exitSelectionListener);

		win.setFileExitItemSelectionListener(exitSelectionListener);

		// Default select XSection:
		// win.xSectionButton.setSelection(true);
		selectedCrossSection = win.xSectionButton;
	}

	private void clickSelectedCrossSection() {
		selectedCrossSection.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				selectedCrossSection.setSelection(true);
				selectedCrossSection.notifyListeners(SWT.Selection, new Event());
			}
		});
	}
}
