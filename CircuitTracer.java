import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {

	/**
	 * Launch the program.
	 * 
	 * @param args three required arguments:
	 *             first arg: -s for stack or -q for queue
	 *             second arg: -c for console output or -g for GUI output
	 *             third arg: input file name
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); // create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		// System.out.println("######################################################################################");
		System.out.println("To Run The Program Please Follow The Following Argument Structure:");
		System.out.println("<-StorageType> <-ProgramMode> <InputFileName>");
		System.out.println("Storage Type Options:\n-s -- uses a stack, DFS\n-q -- uses a queue, BFS");
		System.out.println("Program Mode Options:\n-c -- runs program in the console\n-g -- runs program in a GUI");
		System.out.println("Example:\njava CircuitTracer -s -c inputFile.dat");
		// System.out.println("######################################################################################");
	}

	/**
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		CircuitBoard currBoard;
		Storage<TraceState> stateStore;
		ArrayList<TraceState> bestPaths;
		try {
			if (args.length != 3) {
				printUsage();
				return; // exit the constructor immediately
			}
			// Storage Selection
			char argOne = args[0].charAt(1);
			if (argOne == 's') {
				stateStore = Storage.getStackInstance();
				bestPaths = new ArrayList<TraceState>();
			} else if (argOne == 'q') {
				stateStore = Storage.getQueueInstance();
				bestPaths = new ArrayList<TraceState>();
			} else {
				System.out.println("Invalid storage argument");
				printUsage();
				return;
			}
			// File Reader
			String argThree = args[2];
			currBoard = new CircuitBoard(argThree);
			// Read File
			Point startPosition = currBoard.getStartingPoint();
			int startColumn = (int) startPosition.getY();
			int startRow = (int) startPosition.getX();
			if (currBoard.isOpen(startRow + 1, startColumn)) {
				stateStore.store(new TraceState(currBoard, startRow + 1, startColumn));
			}
			if (currBoard.isOpen(startRow - 1, startColumn)) {
				stateStore.store(new TraceState(currBoard, startRow - 1, startColumn));
			}
			if (currBoard.isOpen(startRow, startColumn - 1)) {
				stateStore.store(new TraceState(currBoard, startRow, startColumn - 1));
			}
			if (currBoard.isOpen(startRow, startColumn + 1)) {
				stateStore.store(new TraceState(currBoard, startRow, startColumn + 1));
			}
			while (!stateStore.isEmpty()) {
				TraceState currState = stateStore.retrieve();
				if (currState.isSolution()) {
					if (bestPaths.isEmpty() || currState.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(currState);
					} else if (currState.pathLength() < bestPaths.get(0).pathLength()) {
						bestPaths.clear();
						bestPaths.add(currState);
					}
				} else {
					int currStateRow = currState.getRow();
					int currStateCol = currState.getCol();
					if (currState.isOpen(currStateRow + 1, currStateCol)) {
						stateStore.store(new TraceState(currState, currStateRow + 1, currStateCol));
					}
					if (currState.isOpen(currStateRow - 1, currStateCol)) {
						stateStore.store(new TraceState(currState, currStateRow - 1, currStateCol));
					}
					if (currState.isOpen(currStateRow, currStateCol + 1)) {
						stateStore.store(new TraceState(currState, currStateRow, currStateCol + 1));
					}
					if (currState.isOpen(currStateRow, currStateCol - 1)) {
						stateStore.store(new TraceState(currState, currStateRow, currStateCol - 1));
					}

				}
			}
			// Graphics Mode Selection
			char argTwo = args[1].charAt(1);
			if (argTwo == 'c') {
				for (TraceState state : bestPaths) {
					System.out.println(state.getBoard().toString());
				}
			} else if (argTwo == 'g') {
				// Creates GUI object which opens a frame automatically
				new CircuitTracerGUI(currBoard, bestPaths);
			} else {
				System.out.println("Invalid mode argument");
				printUsage();
			}
		} catch (InvalidFileFormatException e) {
			System.out.println(e);
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
} // class CircuitTracer
