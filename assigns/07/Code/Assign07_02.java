import Library.LnStrm.*;

class UnsupportedOpr extends RuntimeException {
    String opr;

    public UnsupportedOpr(String opr) {
        this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";

    public abstract double eval();
    // eval() returns the value of the term
}

class TermInt extends Term {
    public int val;

    public TermInt(int val) {
        this.tag = "TermInt";
        this.val = val;
    }

    public double eval() {
        return val;
    }

    public String toString() {
        return "" + val;
    }
}

class TermOpr extends Term {
    public String opr;
    public Term arg1, arg2;

    public TermOpr(String opr0, Term arg1, Term arg2) {
        this.tag = "TermOpr";
        this.opr = opr0;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    public double eval() {
        switch (opr) {
            case "+":
                return arg1.eval() + arg2.eval();
            case "-":
                return arg1.eval() - arg2.eval();
            case "*":
                return arg1.eval() * arg2.eval();
            case "/":
                double divisor = arg2.eval();
                if (Math.abs(divisor) < 1e-10)
                    return Double.NaN;
                return arg1.eval() / divisor;
        }
        throw new UnsupportedOpr(opr);
    }

    public String toString() {
        return "(" + arg1.toString() + " " + opr + " " + arg2.toString() + ")";
    }
}

// Simple list to hold terms - no external imports needed
class TermList {
    Term head;
    TermList tail;

    TermList(Term head, TermList tail) {
        this.head = head;
        this.tail = tail;
    }

    boolean isEmpty() {
        return head == null;
    }

    int length() {
        int count = 0;
        TermList current = this;
        while (current != null && current.head != null) {
            count++;
            current = current.tail;
        }
        return count;
    }

    Term get(int index) {
        TermList current = this;
        for (int i = 0; i < index && current != null; i++) {
            current = current.tail;
        }
        return (current != null) ? current.head : null;
    }
}

// Simple queue for BFS - no external imports
class SimpleQueue {
    private Node front, rear;

    private class Node {
        TermList data;
        Node next;

        Node(TermList data) {
            this.data = data;
        }
    }

    void enqueue(TermList item) {
        Node newNode = new Node(item);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    TermList dequeue() {
        if (front == null)
            return null;
        TermList data = front.data;
        front = front.next;
        if (front == null)
            rear = null;
        return data;
    }

    boolean isEmpty() {
        return front == null;
    }
}

// Simple stack for DFS - no external imports
class SimpleStack {
    private Node top;

    private class Node {
        TermList data;
        Node next;

        Node(TermList data) {
            this.data = data;
        }
    }

    void push(TermList item) {
        Node newNode = new Node(item);
        newNode.next = top;
        top = newNode;
    }

    TermList pop() {
        if (top == null)
            return null;
        TermList data = top.data;
        top = top.next;
        return data;
    }

    boolean isEmpty() {
        return top == null;
    }
}

public class Assign07_02 {

    // Helper to create a list with removed indices and new term
    private TermList removeAndAdd(TermList list, int idx1, int idx2, Term newTerm) {
        TermList result = new TermList(newTerm, null);
        TermList resultTail = result;

        int index = 0;
        TermList current = list;
        while (current != null && current.head != null) {
            if (index != idx1 && index != idx2) {
                TermList newNode = new TermList(current.head, null);
                resultTail.tail = newNode;
                resultTail = newNode;
            }
            index++;
            current = current.tail;
        }

        return result;
    }

    // Generate all children states from a given state
    private SimpleQueue generateChildren(TermList state, boolean useBFS) {
        SimpleQueue children = new SimpleQueue();
        int len = state.length();

        if (len <= 1)
            return children;

        // Try all pairs
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                Term t1 = state.get(i);
                Term t2 = state.get(j);

                // Try all operators
                String[] ops = { "+", "-", "*", "/" };
                for (String op : ops) {
                    Term newTerm = new TermOpr(op, t1, t2);
                    TermList newState = removeAndAdd(state, i, j, newTerm);
                    children.enqueue(newState);
                }

                // Try reverse for non-commutative
                Term newTermSub = new TermOpr("-", t2, t1);
                children.enqueue(removeAndAdd(state, i, j, newTermSub));

                Term newTermDiv = new TermOpr("/", t2, t1);
                children.enqueue(removeAndAdd(state, i, j, newTermDiv));
            }
        }

        return children;
    }

    public LnStrm<Term> GameOf24_bfs_solve(int n1, int n2, int n3, int n4) {
        // Create initial state
        TermList initial = new TermList(new TermInt(n1),
                new TermList(new TermInt(n2),
                        new TermList(new TermInt(n3),
                                new TermList(new TermInt(n4), null))));

        SimpleQueue queue = new SimpleQueue();
        queue.enqueue(initial);

        return bfsHelper(queue);
    }

    private LnStrm<Term> bfsHelper(SimpleQueue queue) {
        return new LnStrm<Term>(() -> {
            while (!queue.isEmpty()) {
                TermList state = queue.dequeue();

                // Check if solution
                if (state.length() == 1) {
                    Term term = state.head;
                    double val = term.eval();
                    if (!Double.isNaN(val) && Math.abs(val - 24.0) < 1e-9) {
                        return new LnStcn<Term>(term, bfsHelper(queue));
                    }
                } else {
                    // Generate children and add to queue
                    SimpleQueue children = generateChildren(state, true);
                    while (!children.isEmpty()) {
                        queue.enqueue(children.dequeue());
                    }
                }
            }
            return new LnStcn<Term>(); // Empty
        });
    }

    public LnStrm<Term> GameOf24_dfs_solve(int n1, int n2, int n3, int n4) {
        // Create initial state
        TermList initial = new TermList(new TermInt(n1),
                new TermList(new TermInt(n2),
                        new TermList(new TermInt(n3),
                                new TermList(new TermInt(n4), null))));

        SimpleStack stack = new SimpleStack();
        stack.push(initial);

        return dfsHelper(stack);
    }

    private LnStrm<Term> dfsHelper(SimpleStack stack) {
        return new LnStrm<Term>(() -> {
            while (!stack.isEmpty()) {
                TermList state = stack.pop();

                // Check if solution
                if (state.length() == 1) {
                    Term term = state.head;
                    double val = term.eval();
                    if (!Double.isNaN(val) && Math.abs(val - 24.0) < 1e-9) {
                        return new LnStcn<Term>(term, dfsHelper(stack));
                    }
                } else {
                    // Generate children and add to stack (reverse order for DFS)
                    SimpleQueue children = generateChildren(state, false);
                    SimpleStack tempStack = new SimpleStack();
                    while (!children.isEmpty()) {
                        tempStack.push(children.dequeue());
                    }
                    while (!tempStack.isEmpty()) {
                        stack.push(tempStack.pop());
                    }
                }
            }
            return new LnStcn<Term>();
        });
    }

    public static void main(String[] args) {
        Assign07_02 solver = new Assign07_02();

        System.out.println("=== Game of 24 Solver ===\n");

        // Test BFS
        System.out.println("BFS Solutions for (3, 3, 8, 8) - first 3:");
        LnStrm<Term> bfsSolutions = solver.GameOf24_bfs_solve(3, 3, 8, 8);
        printFirstN(bfsSolutions, 3);

        System.out.println("\nDFS Solutions for (3, 3, 8, 8) - first 3:");
        LnStrm<Term> dfsSolutions = solver.GameOf24_dfs_solve(3, 3, 8, 8);
        printFirstN(dfsSolutions, 3);

        System.out.println("\nBFS Solutions for (4, 4, 7, 7) - first 3:");
        LnStrm<Term> bfsSolutions2 = solver.GameOf24_bfs_solve(4, 4, 7, 7);
        printFirstN(bfsSolutions2, 3);

        System.out.println("\n=== Testing Complete ===");
    }

    private static void printFirstN(LnStrm<Term> stream, int n) {
        int count = 0;
        LnStcn<Term> current = stream.eval0();

        while (current.consq() && count < n) {
            Term term = current.head;
            System.out.println("  " + term.toString() + " = " + term.eval());
            count++;
            current = current.tail.eval0();
        }

        if (count == 0) {
            System.out.println("  (No solutions found)");
        }
    }

} // end of [public class Assign07_02{...}]