How does the choice of Storage configuration (stack vs queue) affect the sequence in which paths are explored in the search algorithm? 
(This requires more than a "stacks are LIFOs and queues are FIFOs" answer.)

The storage configuration will affect the sequence in which paths are performed. In the case of the stack, the algorithm will evaluate the most recent
valide state found meaning that any valid states will be evaluated later after this valid state has been completely evaluated. If a queue is used it will evaluate each state
in the order they are added meaning that for example if state 1 is added and then state 2 is added the algorithm will evaluate state 1's next valid states first add them to the queue
and then evaluate state 2 without exhausting all state 1's future states. Simply put stacks go deep and queues go wide.

Is the total number of search states (possible paths) affected by the choice of stack or queue?

No all possible paths will eventually be found, they will just be found at different times because of how each configuration behaves during a search.

Is using one of the storage structures likely to find a solution in fewer steps than the other? Always?

I would argue going as deep as possible will likely result in finding a valid solution first because it tries to evaluate the most likely (deepest) state before going
to a less deep path, but this fails if the last valid states found all eventually result in an invalid solution as the time spent backtracking could add a lot more time finding
a valid solution.

Does using either of the storage structures guarantee that the first solution found will be a shortest path?

Queues evaluate the shallowest states first meaning it will find the shortest path first

How is memory use (the maximum number of states in Storage at one time) affected by the choice of underlying structure?

Queues evaluate states in the order they are found, aadding the next possible states to the queue as they go, these next possible states are held in the queue while other next possible
states from different branches are being looked at. This means it holds a lot of unexplored paths at any given time. Stacks on the other hand only hold the next possible states of one 
branch at any given time, meaning it uses less memory overall.

What is the Big-Oh runtime order for the search algorithm?

Best Case: O(1) where one of the first possible states is a solution
Worst Case: 

What does the order reflect? (Maximum size of Storage? Number of board positions? Number of paths explored? Maximum path length? Something else?)

What is 'n', the single primary input factor that increases the difficulty of the task?
