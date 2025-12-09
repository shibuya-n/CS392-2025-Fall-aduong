import FnList.*;
import LnList.*;
import FnTuple.*;
import LnStrm.*;
import FnGtree.*;

public class Assign09_02 {
    // HX-2025-12-02:
    // Please use Warnsdorf's rule to
    // search for knight's tours on a chess board
    // of dimension (chessBoardSize x chessBoardSize)
    // Your search should be based on the PFirstEnumerate
    // (See Code/FnGtree/FnGtreeSUtil.java)

    /**
     * Internal tree node for the search space of knight's tours.
     * The value stored at each node is a functional list [path] of positions,
     * where the head of the list is the current position of the knight and
     * the tail are the previously visited squares.
     */
    private static class KnightNode
            implements FnGtree<FnList<FnTupl2<Integer, Integer>>> {

        private final FnList<FnTupl2<Integer, Integer>> path;
        private final int boardSize;

        // Knight move offsets (row, col)
        private static final int[] DR = { 2, 1, -1, -2, -2, -1, 1, 2 };
        private static final int[] DC = { 1, 2, 2, 1, -1, -2, -2, -1 };

        KnightNode(FnList<FnTupl2<Integer, Integer>> path, int boardSize) {
            this.path = path;
            this.boardSize = boardSize;
        }

        @Override
        public FnList<FnTupl2<Integer, Integer>> value() {
            return path;
        }

        /**
         * Priority for priority-first search.
         *
         * We implement Warnsdorf's rule by using, as priority, the number
         * of onward moves available from the current square:
         * fewer onward moves = smaller priority value = explored earlier.
         */
        @Override
        public int priority() {
            // Path should never be empty in our construction, but guard anyway.
            if (path.nilq()) {
                return 0;
            }
            FnTupl2<Integer, Integer> pos = path.hd();
            int r = pos.sub0;
            int c = pos.sub1;
            return onwardDegree(r, c);
        }

        /**
         * Children are obtained by trying all legal knight moves from the
         * current square to unvisited squares, producing extended paths.
         */
        @Override
        public FnList<FnGtree<FnList<FnTupl2<Integer, Integer>>>> children() {
            FnList<FnGtree<FnList<FnTupl2<Integer, Integer>>>> res = FnListSUtil.nil();

            if (path.nilq()) {
                return res;
            }

            FnTupl2<Integer, Integer> pos = path.hd();
            int r = pos.sub0;
            int c = pos.sub1;

            for (int k = 0; k < 8; k += 1) {
                int nr = r + DR[k];
                int nc = c + DC[k];
                if (inside(nr, nc) && !visited(nr, nc)) {
                    FnTupl2<Integer, Integer> nextPos = new FnTupl2<Integer, Integer>(nr, nc);
                    FnList<FnTupl2<Integer, Integer>> newPath = FnListSUtil.cons(nextPos, path);
                    KnightNode child = new KnightNode(newPath, boardSize);
                    res = FnListSUtil.cons(child, res);
                }
            }
            // The order of [res] is not important; PFirstEnumerate
            // will schedule nodes globally according to priority().
            return res;
        }

        // Is (r, c) inside the board?
        private boolean inside(int r, int c) {
            return (0 <= r && r < boardSize && 0 <= c && c < boardSize);
        }

        // Has (r, c) already been visited in this path?
        private boolean visited(int r, int c) {
            FnList<FnTupl2<Integer, Integer>> xs = path;
            while (!xs.nilq()) {
                FnTupl2<Integer, Integer> p = xs.hd();
                if (p.sub0 == r && p.sub1 == c) {
                    return true;
                }
                xs = xs.tl();
            }
            return false;
        }

        // Number of legal, unvisited knight moves from (r, c)
        private int onwardDegree(int r, int c) {
            int cnt = 0;
            for (int k = 0; k < 8; k += 1) {
                int nr = r + DR[k];
                int nc = c + DC[k];
                if (inside(nr, nc) && !visited(nr, nc)) {
                    cnt += 1;
                }
            }
            return cnt;
        }
    }

    public static LnStrm<FnList<FnTupl2<Integer, Integer>>> genKnightsTours(int chessBoardSize) {
        if (chessBoardSize <= 0) {
            // empty stream if board size is invalid
            return new LnStrm<FnList<FnTupl2<Integer, Integer>>>();
        }

        // Start the tour at (0,0). Any starting square would work; Warnsdorf's
        // heuristic is applied from this root onward.
        FnTupl2<Integer, Integer> startPos = new FnTupl2<Integer, Integer>(0, 0);
        FnList<FnTupl2<Integer, Integer>> startPath = FnListSUtil.cons(startPos, FnListSUtil.nil());

        KnightNode root = new KnightNode(startPath, chessBoardSize);

        // Priority-first enumeration of all partial paths according to
        // priority() = Warnsdorf degree. This is defined in FnGtreeSUtil.
        LnStrm<FnList<FnTupl2<Integer, Integer>>> allPaths = FnGtreeSUtil.PFirstEnumerate(root);

        final int fullLen = chessBoardSize * chessBoardSize;

        // Keep only full tours (paths that visit every square exactly once).
        return allPaths.filter0(path -> path.length() == fullLen);
    }

    // Please write minimal testing code for [genKnightsTours]
    public static void main(String[] args) {
        int n = 5; // try a smaller board first; 8x8 may take longer

        System.out.println("Searching for a knight's tour on a "
                + n + "x" + n + " board...");

        LnStrm<FnList<FnTupl2<Integer, Integer>>> tours = genKnightsTours(n);

        // Extract the first tour from the lazy stream, if any
        LnStcn<FnList<FnTupl2<Integer, Integer>>> cell = tours.eval0();
        if (!cell.consq()) {
            System.out.println("No tour found.");
            return;
        }

        FnList<FnTupl2<Integer, Integer>> tour = cell.hd();
        System.out.println("Found a tour of length " + tour.length() + ":");

        // Our representation stores the most recent position at the head
        // of the list, so reverse it for natural order.
        FnList<FnTupl2<Integer, Integer>> inOrder = tour.reverse();
        inOrder.foritm(pos -> System.out.println("(" + pos.sub0 + "," + pos.sub1 + ")"));
    }
}
