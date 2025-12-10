import Library.LnStrm.*;
import Library.FnList.*;
import Library.FnGtree.*;

class UnsupportedOpr extends RuntimeException {
    String opr;

    public UnsupportedOpr(String opr) {
        this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";

    public abstract double eval();

    public abstract String toExprString();
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

    public String toExprString() {
        return String.valueOf(val);
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
        double v1 = arg1.eval();
        double v2 = arg2.eval();
        switch (opr) {
            case "+":
                return v1 + v2;
            case "-":
                return v1 - v2;
            case "*":
                return v1 * v2;
            case "/":
                return v1 / v2;
            default:
                throw new UnsupportedOpr(opr);
        }
    }

    public String toExprString() {
        return "(" + arg1.toExprString() + " " + opr + " " + arg2.toExprString() + ")";
    }
}

public class Assign07_02 {

    // GameState represents the current state in the game tree
    static class GameState {
        FnList<Term> terms;

        public GameState(FnList<Term> terms) {
            this.terms = terms;
        }

        public boolean isSolution() {
            if (terms.length() != 1) {
                return false;
            }
            double val = terms.hd().eval();
            return Math.abs(val - 24.0) < 1e-9;
        }

        public Term getSolution() {
            if (isSolution()) {
                return terms.hd();
            }
            return null;
        }
    }

    // Game tree node implementation
    static class GameTreeNode implements FnGtree<GameState> {
        private GameState state;
        private FnList<FnGtree<GameState>> childrenCache;

        public GameTreeNode(GameState state) {
            this.state = state;
            this.childrenCache = null;
        }

        @Override
        public GameState value() {
            return state;
        }

        @Override
        public FnList<FnGtree<GameState>> children() {
            if (childrenCache != null) {
                return childrenCache;
            }

            FnList<Term> terms = state.terms;

            // If only one term left, no more children
            if (terms.length() <= 1) {
                childrenCache = FnListSUtil.nil();
                return childrenCache;
            }

            // Generate all possible next states
            FnList<FnGtree<GameState>> children = FnListSUtil.nil();

            // Convert to array for easier indexing
            int n = terms.length();
            Term[] termsArray = new Term[n];
            int idx = 0;
            FnList<Term> temp = terms;
            while (temp.consq()) {
                termsArray[idx++] = temp.hd();
                temp = temp.tl();
            }

            // Try all pairs of terms
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    Term t1 = termsArray[i];
                    Term t2 = termsArray[j];

                    // Try all operations
                    String[] operations = { "+", "-", "*", "/" };

                    for (String op : operations) {
                        // Create new term with operation
                        Term newTerm1 = new TermOpr(op, t1, t2);

                        // Skip division by zero
                        if (op.equals("/") && Math.abs(t2.eval()) < 1e-9) {
                            continue;
                        }

                        // Create remaining terms list
                        FnList<Term> newTerms = FnListSUtil.nil();
                        for (int k = n - 1; k >= 0; k--) {
                            if (k != i && k != j) {
                                newTerms = FnListSUtil.cons(termsArray[k], newTerms);
                            }
                        }
                        newTerms = FnListSUtil.cons(newTerm1, newTerms);

                        GameState newState = new GameState(newTerms);
                        children = FnListSUtil.cons(new GameTreeNode(newState), children);

                        // Also try reverse order for non-commutative operations
                        if (op.equals("-") || op.equals("/")) {
                            Term newTerm2 = new TermOpr(op, t2, t1);

                            // Skip division by zero
                            if (op.equals("/") && Math.abs(t1.eval()) < 1e-9) {
                                continue;
                            }

                            FnList<Term> newTerms2 = FnListSUtil.nil();
                            for (int k = n - 1; k >= 0; k--) {
                                if (k != i && k != j) {
                                    newTerms2 = FnListSUtil.cons(termsArray[k], newTerms2);
                                }
                            }
                            newTerms2 = FnListSUtil.cons(newTerm2, newTerms2);

                            GameState newState2 = new GameState(newTerms2);
                            children = FnListSUtil.cons(new GameTreeNode(newState2), children);
                        }
                    }
                }
            }

            childrenCache = children;
            return childrenCache;
        }
    }

    public LnStrm<Term> GameOf24_bfs_solve(int n1, int n2, int n3, int n4) {
        // Create initial state with four integer terms
        FnList<Term> initialTerms = FnListSUtil.nil();
        initialTerms = FnListSUtil.cons(new TermInt(n4), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n3), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n2), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n1), initialTerms);

        GameState initialState = new GameState(initialTerms);
        GameTreeNode root = new GameTreeNode(initialState);

        // Use BFS to enumerate all states
        LnStrm<GameState> bfsStream = Assign07_01.BFirstEnumerate(root);

        // Filter to only solution states and map to Terms
        return LnStrmSUtil.map0(
                LnStrmSUtil.filter0(bfsStream, (state) -> state.isSolution()),
                (state) -> state.getSolution());
    }

    public LnStrm<Term> GameOf24_dfs_solve(int n1, int n2, int n3, int n4) {
        // Create initial state with four integer terms
        FnList<Term> initialTerms = FnListSUtil.nil();
        initialTerms = FnListSUtil.cons(new TermInt(n4), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n3), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n2), initialTerms);
        initialTerms = FnListSUtil.cons(new TermInt(n1), initialTerms);

        GameState initialState = new GameState(initialTerms);
        GameTreeNode root = new GameTreeNode(initialState);

        // Use DFS to enumerate all states
        LnStrm<GameState> dfsStream = Assign07_01.DFirstEnumerate(root);

        // Filter to only solution states and map to Terms
        return LnStrmSUtil.map0(
                LnStrmSUtil.filter0(dfsStream, (state) -> state.isSolution()),
                (state) -> state.getSolution());
    }

    // Testing code
    public static void main(String[] args) {
        Assign07_02 solver = new Assign07_02();

        System.out.println("========================================");
        System.out.println("Game of 24 Solver - BFS and DFS");
        System.out.println("========================================\n");

        // Test case 1: 3, 3, 8, 8
        System.out.println("Test Case 1: Numbers [3, 3, 8, 8]");
        System.out.println("------------------------------");

        System.out.println("\nBFS Solutions:");
        LnStrm<Term> bfsSolutions1 = solver.GameOf24_bfs_solve(3, 3, 8, 8);
        final int[] bfsCount1 = { 0 };
        bfsSolutions1.foritm0((term) -> {
            if (bfsCount1[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                bfsCount1[0]++;
            }
        });
        System.out.println("  ... (total solutions found: " + bfsCount1[0] + ")");

        System.out.println("\nDFS Solutions:");
        LnStrm<Term> dfsSolutions1 = solver.GameOf24_dfs_solve(3, 3, 8, 8);
        final int[] dfsCount1 = { 0 };
        dfsSolutions1.foritm0((term) -> {
            if (dfsCount1[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                dfsCount1[0]++;
            }
        });
        System.out.println("  ... (total solutions found: " + dfsCount1[0] + ")");

        // Test case 2: 4, 6, 6, 8
        System.out.println("\n\nTest Case 2: Numbers [4, 6, 6, 8]");
        System.out.println("------------------------------");

        System.out.println("\nBFS Solutions:");
        LnStrm<Term> bfsSolutions2 = solver.GameOf24_bfs_solve(4, 6, 6, 8);
        final int[] bfsCount2 = { 0 };
        bfsSolutions2.foritm0((term) -> {
            if (bfsCount2[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                bfsCount2[0]++;
            }
        });
        System.out.println("  ... (total solutions found: " + bfsCount2[0] + ")");

        System.out.println("\nDFS Solutions:");
        LnStrm<Term> dfsSolutions2 = solver.GameOf24_dfs_solve(4, 6, 6, 8);
        final int[] dfsCount2 = { 0 };
        dfsSolutions2.foritm0((term) -> {
            if (dfsCount2[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                dfsCount2[0]++;
            }
        });
        System.out.println("  ... (total solutions found: " + dfsCount2[0] + ")");

        // Test case 3: 1, 5, 5, 5
        System.out.println("\n\nTest Case 3: Numbers [1, 5, 5, 5]");
        System.out.println("------------------------------");

        System.out.println("\nBFS Solutions:");
        LnStrm<Term> bfsSolutions3 = solver.GameOf24_bfs_solve(1, 5, 5, 5);
        final int[] bfsCount3 = { 0 };
        bfsSolutions3.foritm0((term) -> {
            if (bfsCount3[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                bfsCount3[0]++;
            }
        });
        if (bfsCount3[0] == 0) {
            System.out.println("  No solutions found.");
        } else {
            System.out.println("  ... (total solutions found: " + bfsCount3[0] + ")");
        }

        System.out.println("\nDFS Solutions:");
        LnStrm<Term> dfsSolutions3 = solver.GameOf24_dfs_solve(1, 5, 5, 5);
        final int[] dfsCount3 = { 0 };
        dfsSolutions3.foritm0((term) -> {
            if (dfsCount3[0] < 3) {
                System.out.println("  " + term.toExprString() + " = " + term.eval());
                dfsCount3[0]++;
            }
        });
        if (dfsCount3[0] == 0) {
            System.out.println("  No solutions found.");
        } else {
            System.out.println("  ... (total solutions found: " + dfsCount3[0] + ")");
        }

        System.out.println("\n========================================");
        System.out.println("Testing Complete!");
        System.out.println("========================================");
    }
}