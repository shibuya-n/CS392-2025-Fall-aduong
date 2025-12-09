import Library.FnList.*;
import Library.LnList.*;
import Library.FnTuple.*;
import Library.LnStrm.*;

public class Assign09_02 {

    // BoardState for knight's tour problem using priority-first search
    private static class BoardState implements Comparable<BoardState> {
        private final int n;
        private final FnList<FnTupl2<Integer, Integer>> positions;
        private final boolean[][] used;
        private final int heuristic;
        private final int length;

        // Create independent copy of boolean array
        private static boolean[][] copyArray(boolean[][] arr, int size) {
            boolean[][] result = new boolean[size][size];
            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {
                    result[r][c] = arr[r][c];
                }
            }
            return result;
        }

        public BoardState(int boardSize) {
            this.n = boardSize;
            this.positions = FnListSUtil.nil();
            this.used = new boolean[boardSize][boardSize];
            this.heuristic = 0;
            this.length = 0;
        }

        public BoardState(int boardSize, FnList<FnTupl2<Integer, Integer>> pos,
                boolean[][] usedSquares, int len) {
            this.n = boardSize;
            this.positions = pos;
            this.used = copyArray(usedSquares, boardSize);
            this.length = len;

            // Compute Warnsdorf heuristic value
            if (pos.nilq()) {
                this.heuristic = 0;
            } else {
                FnTupl2<Integer, Integer> curr = pos.hd();
                this.heuristic = availableMoves(curr.sub0, curr.sub1);
            }
        }

        public FnList<FnTupl2<Integer, Integer>> value() {
            return positions;
        }

        public int getLength() {
            return length;
        }

        public int priority() {
            return heuristic;
        }

        public FnList<BoardState> children() {
            // Complete tour has no children
            if (length >= n * n) {
                return FnListSUtil.nil();
            }

            // Initial state - start at origin
            if (positions.nilq()) {
                FnTupl2<Integer, Integer> origin = new FnTupl2<>(0, 0);
                boolean[][] nextUsed = copyArray(used, n);
                nextUsed[0][0] = true;
                FnList<FnTupl2<Integer, Integer>> nextPos = FnListSUtil.cons(origin, positions);
                return FnListSUtil.sing(new BoardState(n, nextPos, nextUsed, 1));
            }

            // Get knight's current location
            FnTupl2<Integer, Integer> curr = positions.hd();
            int row = curr.sub0;
            int col = curr.sub1;

            // Find valid moves with their accessibility counts
            FnList<FnTupl2<Integer, FnTupl2<Integer, Integer>>> candidates = FnListSUtil.nil();

            // Knight move offsets
            int[][] offsets = {
                    { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
                    { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
            };

            for (int[] offset : offsets) {
                int r = row + offset[0];
                int c = col + offset[1];

                if (inBounds(r, c) && !used[r][c]) {
                    FnTupl2<Integer, Integer> square = new FnTupl2<>(r, c);
                    int access = availableMoves(r, c);
                    candidates = FnListSUtil.cons(
                            new FnTupl2<>(access, square),
                            candidates);
                }
            }

            // Sort by accessibility (Warnsdorf's heuristic)
            candidates = FnListSUtil.insertSort(
                    candidates,
                    (a, b) -> a.sub0.compareTo(b.sub0));

            // Build child states
            FnList<BoardState> result = FnListSUtil.nil();
            while (!candidates.nilq()) {
                FnTupl2<Integer, FnTupl2<Integer, Integer>> candidate = candidates.hd();
                FnTupl2<Integer, Integer> square = candidate.sub1;

                boolean[][] nextUsed = copyArray(used, n);
                nextUsed[square.sub0][square.sub1] = true;
                FnList<FnTupl2<Integer, Integer>> nextPos = FnListSUtil.cons(square, positions);
                result = FnListSUtil.cons(
                        new BoardState(n, nextPos, nextUsed, length + 1),
                        result);

                candidates = candidates.tl();
            }

            return result;
        }

        private boolean inBounds(int r, int c) {
            return r >= 0 && r < n && c >= 0 && c < n;
        }

        private int availableMoves(int r, int c) {
            int moves = 0;
            int[][] offsets = {
                    { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
                    { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
            };

            for (int[] offset : offsets) {
                int nr = r + offset[0];
                int nc = c + offset[1];
                if (inBounds(nr, nc) && !used[nr][nc]) {
                    moves++;
                }
            }
            return moves;
        }

        @Override
        public int compareTo(BoardState other) {
            // Smaller heuristic means higher priority
            return Integer.compare(this.heuristic, other.heuristic);
        }
    }

    // Priority queue using binary heap
    private static class MyPQueueArray<T extends Comparable<T>> {
        private T[] data;
        private int count;

        public MyPQueueArray(int capacity) {
            data = (T[]) new Comparable[capacity + 1];
            count = 0;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return count >= data.length - 1;
        }

        public void enque(T item) {
            if (isFull()) {
                expand(2 * data.length);
            }
            data[++count] = item;
            bubbleUp(count);
        }

        public T deque() {
            if (isEmpty())
                return null;
            T result = data[1];
            data[1] = data[count];
            data[count] = null;
            count--;
            if (count > 0) {
                bubbleDown(1);
            }
            return result;
        }

        private void bubbleUp(int idx) {
            while (idx > 1 && isGreater(idx / 2, idx)) {
                exchange(idx, idx / 2);
                idx = idx / 2;
            }
        }

        private void bubbleDown(int idx) {
            while (2 * idx <= count) {
                int child = 2 * idx;
                if (child < count && isGreater(child, child + 1))
                    child++;
                if (!isGreater(idx, child))
                    break;
                exchange(idx, child);
                idx = child;
            }
        }

        private boolean isGreater(int i, int j) {
            return data[i].compareTo(data[j]) > 0;
        }

        private void exchange(int i, int j) {
            T tmp = data[i];
            data[i] = data[j];
            data[j] = tmp;
        }

        private void expand(int capacity) {
            T[] newData = (T[]) new Comparable[capacity];
            for (int i = 1; i <= count; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    // Priority-first enumeration following FnGtreeSUtil pattern
    private static LnStrm<FnList<FnTupl2<Integer, Integer>>> PFirstEnumerate(BoardState root, int target) {
        MyPQueueArray<BoardState> pq = new MyPQueueArray<>(100000);
        pq.enque(root);
        return PFirstEnumerate_helper(pq, target);
    }

    private static LnStrm<FnList<FnTupl2<Integer, Integer>>> PFirstEnumerate_helper(MyPQueueArray<BoardState> pq,
            int target) {
        return new LnStrm<>(
                () -> {
                    while (!pq.isEmpty()) {
                        BoardState state = pq.deque();

                        if (state == null)
                            break;

                        // Found complete tour
                        if (state.getLength() == target * target) {
                            FnList<FnTupl2<Integer, Integer>> solution = FnListSUtil.reverse(state.value());
                            return new LnStcn<>(solution, PFirstEnumerate_helper(pq, target));
                        }

                        // Enqueue all children
                        FnList<BoardState> kids = state.children();
                        kids.foritm((child) -> pq.enque(child));
                    }

                    // No more solutions
                    return new LnStcn<>();
                });
    }

    // HX-2025-12-02:
    // Please use Warnsdorf's rule to
    // search for knight's tours on a chess board
    // of dimension (chessBoardSize x chessBoardSize)
    // Your search should be based on the PFirstEnumerate
    public static LnStrm<FnList<FnTupl2<Integer, Integer>>> genKnightsTours(int chessBoardSize) {
        BoardState start = new BoardState(chessBoardSize);
        return PFirstEnumerate(start, chessBoardSize);
    }

    // Please write minimal testing code for [genKnightsTours]
    public static void main(String[] args) {
        System.out.println("Knight's tour search using priority-first enumeration");
        System.out.println("Applying Warnsdorf's heuristic");
        System.out.println();

        // Test 5x5 board
        System.out.println("Testing 5x5 board:");
        LnStrm<FnList<FnTupl2<Integer, Integer>>> solutions = genKnightsTours(5);

        final int[] total = { 0 };
        final int display = 10;

        solutions.foritm0((tour) -> {
            total[0]++;
            if (total[0] <= display) {
                System.out.println("Solution #" + total[0] + ":");
                displayTour(tour, 5);
                System.out.println();
            }

            if (total[0] == display) {
                System.out.println("(Displaying first " + display + " solutions...)");
            }
        });

        if (total[0] == 0) {
            System.out.println("No solutions found.");
        } else {
            System.out.println("Total solutions: " + total[0]);
        }
    }

    private static void displayTour(FnList<FnTupl2<Integer, Integer>> tour, int size) {
        int[][] board = new int[size][size];
        final int[] step = { tour.length() };

        tour.foritm((pos) -> {
            board[pos.sub0][pos.sub1] = step[0];
            step[0]--;
        });

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.printf("%3d ", board[i][j]);
            }
            System.out.println();
        }
    }
}