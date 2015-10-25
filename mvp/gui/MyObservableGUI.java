package gui;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;

import algorithms.mazeGenerators.Position;
import search.Solution;
import search.State;
import view.IDisplayable;
import view.IMazeDisplayable;
import view.ISolutionDisplayable;
import view.MVPView;

public class MyObservableGUI extends MVPView {

	MazeWindow win;

	String propertyFile;
	String saveMazeFile;
	String loadMazeFile;

	String currMazeName;

	Position startPosition;
	Position currPosition;
	Position endPosition;

	Button selectedCrossSection;

	boolean isMazeSolved = false;
	Image victoryImage;

	Timer timer;
	TimerTask task;

	public MyObservableGUI() {
		win = new MazeWindow("Maze Puzzle", 500, 300);
	}

	@Override
	public void start() {
		win.actualInitWidgets();
		initMazeWindowListeners();
		win.run();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void display(IDisplayable displayable) {
		if (displayable != null) {
			if (displayable instanceof IMazeDisplayable) {
				IMazeDisplayable mazeDisplayable = (IMazeDisplayable) displayable;
				win.setMazeData(mazeDisplayable.getMazeCrossSection());
			} else if (displayable instanceof ISolutionDisplayable<?>) {
				ISolutionDisplayable<Position> positionSolution = (ISolutionDisplayable<Position>) displayable;
				Solution<Position> solution = positionSolution.getSolution();
				solveMaze(solution);
			} else {
				String message = displayable.getMessage().toLowerCase();
				String[] splitMessage = message.split(":");
				String reason = splitMessage[0];
				String notification = "";
				if (splitMessage.length > 1) {
					notification = splitMessage[1].trim();
				}

				switch (reason) {
				case "exception":
					MessageBoxCreator.createErrorMessageBox(win.shell, notification);
					break;
				case "generate":
				case "load":
					currMazeName = notification.split(" ")[1].split("'")[1];
					setChanged();
					notifyObservers("get positions " + currMazeName);
					isMazeSolved = false;
					break;
				case "positions":
					String[] posValues = notification.split(" ");
					int startHeight = Integer.parseInt(posValues[0]);
					int startWidth = Integer.parseInt(posValues[1]);
					int startLength = Integer.parseInt(posValues[2]);
					startPosition = new Position(startHeight, startWidth, startLength);
					int goalHeight = Integer.parseInt(posValues[3]);
					int goalWidth = Integer.parseInt(posValues[4]);
					int goalLength = Integer.parseInt(posValues[5]);
					endPosition = new Position(goalHeight, goalWidth, goalLength);
					win.setGoalPositionText("( " + goalLength + ", " + goalHeight + ", " + goalWidth + ")");
				case "move":
					posValues = notification.split(" ");
					int currHeight = Integer.parseInt(posValues[0]);
					int currWidth = Integer.parseInt(posValues[1]);
					int currLength = Integer.parseInt(posValues[2]);
					currPosition = new Position(currHeight, currWidth, currLength);
					win.setCurrentPositionText("( " + currLength + ", " + currHeight + ", " + currWidth + ")");
					clickSelectedCrossSection();
					checkVictory();
					break;
				case "solve":
					if (currPosition.equals(startPosition)) {
						currMazeName = notification.split(" ")[1].split("'")[1];
						setChanged();
						notifyObservers("display solution " + currMazeName);
					} else {
						MessageBoxCreator.createErrorMessageBox(win.shell, "Current position must be the starting position.");
					}
					break;
				case "set_algorithm":
					setAlgorithm(notification);
					break;
				default:
					MessageBoxCreator.createNotificationMessageBox(win.shell, message);
					break;
				}
			}
		}
	}

	private void solveMaze(Solution<Position> solution) {
		LinkedList<Integer> eventList = getKeyMoveList(solution.getSolutionList());
		timer = new Timer();
		task = new TimerTask() {
			boolean allKeysClicked = false;

			@Override
			public void run() {
				if (eventList.isEmpty()) {
					allKeysClicked = true;
				} else {
					simulateKeyPress(eventList.pop());
				}

				if (allKeysClicked) {
					closeTimerTask();
				}
			}
		};

		timer.scheduleAtFixedRate(task, 0, 500);
	}

	protected void closeTimerTask() {
		task.cancel();
		timer.cancel();
	}

	private LinkedList<Integer> getKeyMoveList(LinkedList<State<Position>> solutionList) {
		LinkedList<State<Position>> copySolutionList = new LinkedList<>();
		copySolutionList.addAll(solutionList);
		Collections.reverse(copySolutionList);
		LinkedList<Integer> eventList = new LinkedList<>();
		Position last = copySolutionList.pop().getState();
		int swtMove;

		for (State<Position> state : copySolutionList) {
			swtMove = getEventDiff(last, state.getState());
			eventList.add(swtMove);
			last = state.getState();
		}

		return eventList;
	}

	private Integer getEventDiff(Position last, Position curr) {
		Integer retVal = 0;

		if (last.getHeight() != curr.getHeight()) {
			retVal = last.getHeight() > curr.getHeight() ? SWT.PAGE_DOWN : SWT.PAGE_UP;
		} else if (last.getWidth() != curr.getWidth()) {
			retVal = last.getWidth() > curr.getWidth() ? SWT.ARROW_LEFT : SWT.ARROW_RIGHT;
		} else if (last.getLength() != curr.getLength()) {
			retVal = last.getLength() > curr.getLength() ? SWT.ARROW_UP : SWT.ARROW_DOWN;
		}

		return retVal;
	}

	private void checkVictory() {
		if (currPosition.equals(endPosition)) {
			// TODO
			MessageBoxCreator.createMessageBox(win.shell, SWT.HOME, "Victory!", "Good job!\nMaze completed");
			isMazeSolved = true;
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
				if (currMazeName != null && currAlgorithm != null) {
					setChanged();
					notifyObservers("solve " + currMazeName + " " + currAlgorithm);
				}
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
					String path = file.getParentFile().getAbsolutePath();
					String fileName = path + File.separator + currMazeName + ".maz";

					setChanged();
					notifyObservers("save maze " + currMazeName + " " + fileName);
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
					String mazeName = fileName;
					int pos = fileName.lastIndexOf(".");
					if (pos > 0) {
						mazeName = fileName.substring(0, pos);
					}
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
					win.setCharacterPosition(currPosition.getHeight(), currPosition.getWidth());
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
					win.setCharacterPosition(currPosition.getLength(), currPosition.getWidth());
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
					win.setCharacterPosition(currPosition.getLength(), currPosition.getHeight());
					setChanged();
					notifyObservers("display cross section by Z " + currPosition.getWidth() + " for " + currMazeName);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		win.setMazeDisplayerKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				String direction = "";
				switch (e.keyCode) {
				case SWT.ARROW_UP:
					direction = "backward";
					break;
				case SWT.ARROW_DOWN:
					direction = "forward";
					break;
				case SWT.ARROW_LEFT:
					direction = "left";
					break;
				case SWT.ARROW_RIGHT:
					direction = "right";
					break;
				case SWT.PAGE_UP:
					direction = "up";
					break;
				case SWT.PAGE_DOWN:
					direction = "down";
					break;
				default:
					break;
				}

				if (direction != "" && currMazeName != null && currPosition != null && !isMazeSolved) {
					setChanged();
					notifyObservers("move direction " + direction + " " + currMazeName + " " + currPosition.getHeight()
							+ " " + currPosition.getWidth() + " " + currPosition.getLength());
				}
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

		// Default select YSection:
		selectedCrossSection = win.ySectionButton;
		clickSelectedCrossSection();
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

	private void simulateKeyPress(int keyCode) {
		win.maze.getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				Event e = new Event();
				e.keyCode = keyCode;
				win.maze.notifyListeners(SWT.KeyDown, e);
			}
		});
	}
}
