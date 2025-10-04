import Library.FnList.*;
import Library.FnArray.*;

public class FnArrayTest {
//
    public static Integer tally(int n) {
	return FnArrayUtil.folditm
	    (FnArrayUtil.int1$make(n), 0, (r, i) -> r + (i+1));
    }
    public static Integer factorial(int n) {
	return FnArrayUtil.folditm
	    (FnArrayUtil.int1$make(n), 1, (r, i) -> r * (i+1));
    }
//
    public static void main(String[] args) {
	FnArray<Integer> xs =
	    FnArrayUtil.int1$make(10);
	xs.System$out$print(); System.out.println();
	System.out.println
	    ("GU|xs| = " + xs.GU.length(xs));
	xs = FnArrayUtil.map_array(xs, (x0) -> x0 * x0);
	xs.System$out$print(); System.out.println();
	xs = FnArrayUtil.imap_array(xs, (i0, x0) -> 10 * (100-x0) + i0);
	xs.System$out$print(); System.out.println();
	FnArray<Integer> ys = xs.U0.mergeSort(xs);
	System.out.print("By mergeSort: "); ys.System$out$print(); System.out.println();
	FnArray<Integer> zs = ys.GU.mergeSort(ys, (x1, x2) -> x2.compareTo(x1));
	System.out.print("By mergeSort: "); zs.System$out$print(); System.out.println();
	System.out.println("tally(10) = " + tally(10).toString());
	System.out.println("factorial(10) = " + factorial(10).toString());
    }
//
} // end of [public class FnArrayTest{...}]
