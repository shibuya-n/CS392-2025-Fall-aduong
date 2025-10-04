import Library.FnList.*;
    
public class FnListTest {
//
    static int abs(int x) {
	return (x >= 0 ? x : -x);
    }
//
    public static Integer tally(int n) {
	return FnListUtil.folditm
	    (FnListUtil.int1$make(n), 0, (r, i) -> r + (i+1));
    }
    public static Integer factorial(int n) {
	return FnListUtil.folditm
	    (FnListUtil.int1$make(n), 1, (r, i) -> r * (i+1));
    }
//
    public static void main(String[] args) {
	FnList<Integer> xs =
	    FnListUtil.int1$make(10);
	System.out.println
	    ("GU|xs| = " + xs.GU.length(xs));
	xs.System$out$print(); System.out.println();
	xs = FnListUtil.map_list(xs, (x0) -> 10-x0);
	xs.System$out$print(); System.out.println();
	xs = FnListUtil.map_list(xs, (x0) -> x0 * x0);
	xs.System$out$print(); System.out.println();
	xs = FnListUtil.imap_list(xs, (i0, x0) -> 10 * x0 + i0);
	xs.System$out$print(); System.out.println();
//
	xs = FnListUtil.insertSort(xs);
	xs.System$out$print(); System.out.println();
	xs = xs.insertSort((x1, x2) -> x2.compareTo(x1));
	xs.System$out$print(); System.out.println();
//
	xs = FnListUtil.rand$int$make(10);
	xs = FnListUtil.map_list(xs, (x0) -> x0 % 100);
	xs.System$out$print(); System.out.println();
	xs = FnListUtil.insertSort(xs);
	xs.System$out$print(); System.out.println();
	xs = xs.mergeSort((x1, x2) -> x2.compareTo(x1));
	System.out.print("By mergeSort: "); xs.System$out$print(); System.out.println();
//
	System.out.println("tally(10) = " + tally(10).toString());
	System.out.println("factorial(10) = " + factorial(10).toString());
//
    }
//
} // end of [public class FnListTest{...}]
