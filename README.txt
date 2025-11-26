****************
* Project: CircuitTracer
* Class: CS221
* Date: Dec 12, 2025
* Name: Broden
**************** 

OVERVIEW:

This program takes in a data file that is meant to represent the format of a CircuitBoard, which is then read. 
Once read, the program performs a BruteForce search algorithm which iteratively searches through possible paths and then
prints or displays the best found solutions. These paths are depicted by 'T's which are used to connect '1' and '2' on the board.


INCLUDED FILES:

 * CircuitBoard.java - File that is used to generate a CircuitBoard object from a data file.
 * CircuitTracerGUI.java - File used to output the results of CircuitTracer in a GUI format.
 * CircuitTracer.java - Driver file, assess provided args and depending on the args executes the brute force search using either a stack or queue. Output is given either through 
 console or GUI based on args.
 * InvalidFileFormatException.java - Custom exception used to validate file format.
 * OccupiedPositionException.java - Custom exception used when the program attempts to navigate to an occupied position.
 * Storage.java - Class that provides either a stack or queue
 * TraceState.java - Program that is used to trace each path as the program searches for solutions. 
 * README - this file


COMPILING AND RUNNING:

Once in the directory containing the file use the javac command to format the file:
javac CircuitTracer.java

Once compiled use the java command along with args to run the compiled class file:
java CircuitTracer <-StorageType> <-ProgramMode> <InputFileName>

Storage Type Options:
-s -- uses a stack, DFS
-q -- uses a queue, BFS"
Program Mode Options:
-c -- runs program in the console
-g -- runs program in a GUI

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The program is designed in three main sections. The CircuitBoard which reads in the file and formats it to be used by our algorithm, the CircuitTracer 
which takes our CircuitBoard and performs the search algorithm, and the CircuitTracerGUI which displays the algorithm in a GUI.

The bulk of the work is handled in CircuitTracer. CircuitTracer institutes a BruteForce search, a type of search that explores all possible solutions to determine the best solutions.
The main issue with this algorithm is that since it looks at all possible solutions as the depth of the search increases the time needed to run the program does as well (further discussion in analysis questions).
Once the alorithm is over the result is a list of TraceStates containing the best paths found. Best paths are those which are the ones with the least length, comparisons in the 
algorithm are used to ensure this.

How does the choice of Storage configuration (stack vs queue) affect the sequence in which paths are explored in the search algorithm? 

The storage configuration will affect the sequence in which paths are evaluated. In the case of the stack, the algorithm will evaluate the most recent
valide state found meaning that any valid states will be evaluated later after this valid state has been completely evaluated. If a queue is used it will evaluate each state
in the order they are added meaning that for example if state 1 is added and then state 2 is added the algorithm will evaluate state 1's next valid states first add them to the queue
and then evaluate state 2 without exhausting all state 1's future states. Simply put stacks go deep and queues go wide.

Is the total number of search states (possible paths) affected by the choice of stack or queue?

No, all possible paths will eventually be found, they will just be found at different times because of how each configuration behaves during a search.

Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?

Going as deep as possible first will likely result in finding a valid solution first because it tries to evaluate the most likely (deepest) state before going
to a less deep path, but this fails if the last valid states found all eventually result in an invalid solution as the time spent backtracking could add a lot more time finding
a valid solution.

Does using either of the storage structures guarantee that the first solution found will be a shortest path?

Queues evaluate the shallowest states first meaning it will find the shortest path first.

How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?

Queues evaluate states in the order they are found, aadding the next possible states to the queue as they go, these next possible states are held in the queue while other next possible
states from different branches are being looked at. This means it holds a lot of unexplored paths at any given time. Stacks on the other hand only hold the next possible states of one 
branch at any given time, meaning it uses less memory overall.

What is the Big-Oh runtime order for the search algorithm?

The runtime order is exponential. The deeper the search goes the more tasks there are to do for the algorithm for example at the second set of nodes we would expect about 3 possible states to have been created and 3 
possible states from each of those, this would result in a runtime similar to 3^2. The program would on average (sometimes there may only be less than three valid states but this is not the typical behavior expected) 
be expected to behave similarly so O(3^n).

What does the order reflect? (Maximum size of Storage? Number of board positions? Number of paths explored? Maximum path length? Something else?)

The order reflects the expected cost of running the program, this would be number of paths that are explored as the program searches all paths with each path growing 
exponentialy as the program runs.

What is 'n', the single primary input factor that increases the difficulty of the task?

n is equal to how deep the program has to go to get a solution. The deeper the algorithm searches the more branches from the previous states have to be made resulting in 
the exponential growth.

TESTING:
During development the provided tester was used to see where the program was falling short. Once the program passed all the tests expected output was looked at manually to 
ensure the proper output was occuring. Testing allowed for problems to be easily identifiable and by extension easy to fix. For example, all tests were passing except for 2,
it turned out that a part of my text input was causing the fail. By removing that section of text output I was able to pass the test.

DISCUSSION:

This assignment was fairly quick to get working. Once the file reader was instituted, the given sudo code was easy to understand and implement. Arguably the hardest part of this
assignment was the extra credit as Swing can tend to be finnicky to work with, but once it was complete it was very rewarding. This assignment overall was an interesting task and 
provided an opportunity to work with tools that haven't been handled in a while, making it an interesting and rewarding time.
 
EXTRA CREDIT:

This program implements a GUI element as described by the extra credit.