## Assign07_01

BFirstEnumerate

- changed queue methods to dequeue methods (ex. enqueue() to renque() and dequeue() to fdeque())

DFirstEnumerate

- changed stack methods to dequeue methods (ex. push() to fenque() and pop() to fdeque())
- changed from foritm() to rforitm() to get the correct order
  -- foritm() visits children in reverse order

## Assign07_02

- this problem can be approached as a tree search problem:
  -- we can look at these through states: a list of terms that can still be combined
  -- the initial state is four integer terms representing the input numbers
  -- the goal state is a single term that evaluates to 24
  -- to find the goal state, we can transition through states:
  --- select any two terms, apply an arithmetic operator, and replace the two terms with the result

- the tree structure is also important:
  -- root: initial state with 4 terms
  -- children of a node: all possible states created by combining the two terms from the current state
  -- leaves: staets with only one term (these carry the potential solutions)

- operations at each node:
  -- for a state with N terms:
  1. Choose 2 terms from the N available terms
  2. for each pair, try all 4 operators (+,-,\*,/)
  3. for non-commutative operators(-,/) also try reverse order
  4. create new state with (N-1) terms

DFS Approach

- strategy:
  -- explore one branch deeply before backtracking
  -- use a stack structure for (LIFO - last in first out)

  1. start with root state
  2. generate all possible next state by combining pairs
  3. push children onto stack
  4. pop from stack and repeat until finding a solution
  5. backtrack when a path is exhausted

  -- use DFirstEnumerate which employs the stack structure

BFS Approach

- strategy:
  -- explore all states at current depth before going deeper
  -- use a queue structure (FIFO - first in first out)
  -- find solutions in order of simplicity

  1. start with root state
  2. process all states at depth 1 (combining 4 numbers into 3)
  3. process all states at depth 2 (3 numbers into 2)
  4. process states at depth 3 (2 numbers into 1)
  5. check each final state to see if it equals 24

  -- use BFirstEnumerate to employ a queue structure

- finally, find states that have exactly one term remaining and evaluate that term
- check if the result equals 24 and return all valid solutions as a lazy stream
