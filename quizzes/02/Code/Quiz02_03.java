
//
// HX-2025-11-20: 50 points
// Some "hard" Sudoku puzzles can be
// found here: https://sudoku.com/hard/.
// This question is similar to Assign07_02.
// You are asked to use DFirstEnumerate and BFirstEnumerate
// in FnGtree to solve Sudoku puzzles. Your solution
// should be able to solve "hard" Sudoku puzzles effectively.
//
import Library.FnList.*;
import Library.LnStrm.*;
import Library.FnGtree.*;

public class Quiz02_03 {

    // ========================
    // Inner class: Sudoku
    // ========================
    public static class Sudoku {
        public final int[][] board; // 0 = empty

        public Sudoku(int[][] b) {
            board = new int[9][9];
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    board[r][c] = b[r][c];
                }
            }
        }

        public Sudoku copySet(int r, int c, int v) {
            int[][] b2 = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    b2[i][j] = board[i][j];
                }
            }
            b2[r][c] = v;
            return new Sudoku(b2);
        }

        // true if there is no 0 and board is valid
        public boolean solved() {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (board[r][c] == 0)
                        return false;
                }
            }
            return valid();
        }

        // check rows, cols, boxes (ignoring zeros)
        public boolean valid() {
            // rows + cols
            for (int i = 0; i < 9; i++) {
                boolean[] row = new boolean[10];
                boolean[] col = new boolean[10];
                for (int j = 0; j < 9; j++) {
                    int rv = board[i][j];
                    int cv = board[j][i];
                    if (rv != 0) {
                        if (row[rv])
                            return false;
                        row[rv] = true;
                    }
                    if (cv != 0) {
                        if (col[cv])
                            return false;
                        col[cv] = true;
                    }
                }
            }
            // 3x3 boxes
            for (int br = 0; br < 9; br += 3) {
                for (int bc = 0; bc < 9; bc += 3) {
                    boolean[] seen = new boolean[10];
                    for (int r = br; r < br + 3; r++) {
                        for (int c = bc; c < bc + 3; c++) {
                            int v = board[r][c];
                            if (v != 0) {
                                if (seen[v])
                                    return false;
                                seen[v] = true;
                            }
                        }
                    }
                }
            }
            return true;
        }

        // first (row, col) such that board[row][col] == 0, or null
        public int[] nextEmpty() {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (board[r][c] == 0) {
                        return new int[] { r, c };
                    }
                }
            }
            return null;
        }

        public boolean validMove(int r, int c, int v) {
            if (board[r][c] != 0)
                return false;

            // row and column
            for (int i = 0; i < 9; i++) {
                if (board[r][i] == v)
                    return false;
                if (board[i][c] == v)
                    return false;
            }

            // 3x3 box
            int br = (r / 3) * 3;
            int bc = (c / 3) * 3;
            for (int i = br; i < br + 3; i++) {
                for (int j = bc; j < bc + 3; j++) {
                    if (board[i][j] == v)
                        return false;
                }
            }

            return true;
        }

        @Override
        public String toString() {
            String sb = "";
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    sb = "" + (board[r][c] == 0 ? ". " : (board[r][c] + " "));
                }
                sb = "" + ("\n");
            }
            return sb.toString();
        }
    }

    // ============================
    // Inner class: SudokuNode
    // ============================
    public static class SudokuNode implements FnGtree<Sudoku> {
        Sudoku state;

        public SudokuNode(Sudoku s) {
            state = s;
        }

        @Override
        public Sudoku value() {
            return state;
        }

        @Override
        public FnList<FnGtree<Sudoku>> children() {
            // prune invalid boards
            if (!state.valid()) {
                return new FnList<FnGtree<Sudoku>>();
            }

            // solved → no children
            if (state.solved()) {
                return new FnList<FnGtree<Sudoku>>();
            }

            int[] pos = state.nextEmpty();
            if (pos == null) {
                return new FnList<FnGtree<Sudoku>>();
            }

            int r = pos[0];
            int c = pos[1];

            FnList<FnGtree<Sudoku>> kids = new FnList<FnGtree<Sudoku>>();

            // create children for all valid moves 1..9
            for (int v = 9; v >= 1; v--) {
                if (state.validMove(r, c, v)) {
                    Sudoku child = state.copySet(r, c, v);
                    kids = new FnList<FnGtree<Sudoku>>(new SudokuNode(child), kids);
                }
            }
            return kids;
        }
    }

    // ============================
    // DFS / BFS solvers
    // ============================
    public LnStrm<Sudoku> Soduku_dfs_solve(Sudoku puzzle) {
        return FnGtreeSUtil.DFirstEnumerate(new SudokuNode(puzzle))
                .filter0(s -> s.solved());
    }

    public LnStrm<Sudoku> Soduku_bfs_solve(Sudoku puzzle) {
        return FnGtreeSUtil.BFirstEnumerate(new SudokuNode(puzzle))
                .filter0(s -> s.solved());
    }

    // original is the starting grid with 0 = blank
    public static boolean respectsClues(int[][] original, Sudoku sol) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (original[r][c] != 0) {
                    if (sol.board[r][c] != original[r][c]) {
                        return false; // solver changed a given clue
                    }
                }
            }
        }
        return true;
    }

    // ============================
    // Minimal testing in main
    // ============================
    public static void main(String[] args) {

        int[][] example = {
                { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
                { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
                { 0, 9, 8, 0, 0, 0, 0, 6, 0 },

                { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
                { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
                { 7, 0, 0, 0, 2, 0, 0, 0, 6 },

                { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
                { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
                { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
        };

        Sudoku puzzle = new Sudoku(example);
        Quiz02_03 q = new Quiz02_03();

        System.out.println("Original puzzle:");
        System.out.println(puzzle);

        System.out.println("DFS solutions (if any):");
        LnStrm<Sudoku> dfs = q.Soduku_dfs_solve(puzzle);
        dfs.foritm0(s -> {
            System.out.println(s);

            boolean okSolved = s.solved(); // full & Sudoku-valid
            boolean okClues = respectsClues(example, s); // original clues preserved

            System.out.println("Solved()?      " + okSolved);
            System.out.println("Respects clues? " + okClues);

            if (okSolved && okClues) {
                System.out.println("DFS solution is VALID ");
            } else {
                System.out.println("DFS solution is INVALID ");
            }
        });

        System.out.println("BFS solutions (if any):");
        LnStrm<Sudoku> bfs = q.Soduku_bfs_solve(puzzle);
        bfs.foritm0(s -> {
            System.out.println(s);

            boolean okSolved = s.solved(); // full & Sudoku-valid
            boolean okClues = respectsClues(example, s); // original clues preserved

            System.out.println("Solved()?      " + okSolved);
            System.out.println("Respects clues? " + okClues);

            if (okSolved && okClues) {
                System.out.println("DFS solution is VALID ");
            } else {
                System.out.println("DFS solution is INVALID ");
            }
        });
    }
}
