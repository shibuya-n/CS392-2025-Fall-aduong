
//
// HX: 50 points
//
import Library.LnList.*;
import java.util.Random;

// Please see Library/LnList for LnList.java
public class Quiz01_05 {
	private static Random rand = new Random();

	public static <T extends Comparable<T>> LnList<T> LnListQuickSort(LnList<T> xs) {
		// HX-2025-10-12:
		// Please implement quicksort on a linked list (LnList).
		// Note that you are not allowed to modify the definition
		// of the LnList class. You can only use the public methods
		// provided by the LnList class

		if (xs.nilq1() || xs.tl1().nilq1()) {
			return xs;
		}

		// Find length and choose random pivot
		int length = xs.length1();
		int pivotIndex = rand.nextInt(length);

		// Extract pivot node
		LnList<T> pivot;
		LnList<T> rest;

		if (pivotIndex == 0) {
			pivot = xs;
			rest = pivot.unlink();
		} else {
			// Navigate to node before pivot
			LnList<T> beforePivot = xs;
			for (int i = 0; i < pivotIndex - 1; i++) {
				beforePivot = beforePivot.tl1();
			}
			pivot = beforePivot.tl1();
			rest = pivot.unlink();
			beforePivot.link(rest);
			rest = xs;
		}

		T pivotValue = pivot.hd1();

		// Partition into less and greater (both start as null)
		LnList<T> less = null;
		LnList<T> greater = null;

		while (rest.consq1()) {
			LnList<T> current = rest;
			rest = current.unlink();

			if (current.hd1().compareTo(pivotValue) < 0) {
				// Add to less list
				if (less == null) {
					less = current;
				} else {
					current.link(less);
					less = current;
				}
			} else {
				// Add to greater list
				if (greater == null) {
					greater = current;
				} else {
					current.link(greater);
					greater = current;
				}
			}
		}

		// Recursively sort partitions
		if (less != null) {
			less = LnListQuickSort(less);
		}
		if (greater != null) {
			greater = LnListQuickSort(greater);
		}

		// Concatenate: less + pivot + greater
		if (less == null) {
			if (greater != null) {
				pivot.link(greater);
			}
			return pivot;
		} else {
			// Find end of less list
			LnList<T> lessTail = less;
			while (lessTail.tl1().consq1()) {
				lessTail = lessTail.tl1();
			}
			lessTail.link(pivot);
			if (greater != null) {
				pivot.link(greater);
			}
			return less;
		}
	}

	public static void main(String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for LnListQuickSort

		// Build test list manually
		LnList<Integer> test = new LnList<>();
		LnList<Integer> n1 = new LnList<>(5, test);
		LnList<Integer> n2 = new LnList<>(2, test);
		LnList<Integer> n3 = new LnList<>(8, test);
		LnList<Integer> n4 = new LnList<>(1, test);
		LnList<Integer> n5 = new LnList<>(9, test);
		LnList<Integer> n6 = new LnList<>(3, test);
		LnList<Integer> n7 = new LnList<>(7, test);

		n1.link(n2);
		n2.link(n3);
		n3.link(n4);
		n4.link(n5);
		n5.link(n6);
		n6.link(n7);

		System.out.print("Original: ");
		n1.System$out$print1();
		System.out.println();

		LnList<Integer> sorted = LnListQuickSort(n1);

		System.out.print("Sorted: ");
		sorted.System$out$print1();
		System.out.println();
	}
}