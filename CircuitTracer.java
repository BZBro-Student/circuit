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
		System.out.println("######################################################################################");
		System.out.println("To Run The Program Please Follow The Following Argument Structure:");
		System.out.println("<-StorageType> <-ProgramMode> <InputFileName>");
		System.out.println("Storage Type Options:\n-s -- uses a stack, DFS\n-q -- uses a queue, BFS");
		System.out.println("Program Mode Options:\n-c -- runs program in the console\n-g -- runs program in a GUI");
		System.out.println("Example:\njava CircuitTracer -s -c inputFile.dat");
		System.out.println("######################################################################################");
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
		if (args.length != 3) {
			printUsage();
			return; // exit the constructor immediately
		}
		// TODO: initialize the Storage to use either a stack or queue
		char argOne = args[0].charAt(1);
		System.out.println(argOne);
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

		String argThree = args[2];
		try {
			currBoard = new CircuitBoard(argThree);
		} catch (FileNotFoundException e) {
			System.out.println("Given file not found");
			printUsage();
			return;
		};

		char argTwo = args[1].charAt(1);
		System.out.println(argTwo);
		if (argTwo == 'c') {
			System.out.println("Input Board:");
			System.out.println(currBoard);
			System.out.println("######################");
			Point startPosition = currBoard.getStartingPoint();
			int startColumn = (int) startPosition.getX();
			int startRow = (int) startPosition.getY();
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

			while (!stateStore.isEmpty()){
				TraceState currState = stateStore.retrieve();
				if (currState.isSolution()){
					if (bestPaths.isEmpty() || currState.pathLength() == bestPaths.get(0).pathLength()) {
						bestPaths.add(currState);
					} else if ( currState.pathLength() < bestPaths.get(0).pathLength()){
						bestPaths.clear();
						bestPaths.add(currState);
					}
				} else {

				}
			}

			

		} else if (argTwo == 'g') {
			System.out.println("GUI not currently supported");
			return;
		} else {
			System.out.println("Invalid mode argument");
			printUsage();
			return;
		}

		// TODO: run the search for best paths
		// TODO: output results to console or GUI, according to specified choice
	}

} // class CircuitTracer
