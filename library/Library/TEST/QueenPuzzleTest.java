public class QueenPuzzleTest {
//
    static int nsol = 0;
//
    public static void
	row$print(int c0) {
	for (int c1 = 0; c1 < Board.N; c1 += 1)
	{
	    System.out.print(c0 != c1 ? ". " : "Q ");
	}
	System.out.println();
    }
    public static void
	board$print(FnList<Integer> cs) {
	FnListUtil.rforitm(cs, (col) -> row$print(col));
	System.out.println();
    }
//
    public static void main(String[] args) {
	Board bd0 =
	    new Board(new FnListNil<Integer>());
	FnGtreeUtil.DepthFirstSearch(
	     bd0,
	     (cs) -> {
		 if (cs.length() == Board.N) {
		     nsol += 1;
		     System.out.println("Solution(" + nsol + "):");
		     board$print(cs);
		 }
	     }
        );
    }
//
} // end of [public class QueenPuzzleTest{...}]
