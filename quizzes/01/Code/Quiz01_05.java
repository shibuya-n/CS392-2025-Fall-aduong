//
// HX: 50 points
//

import Library.FnA1sz.*;
import Library.LnList.*;

import java.util.function.ToIntBiFunction;
import java.util.random.*;

// Please see Library/LnList for LnList.java
public class Quiz01_05 {
	public static <T extends Comparable<T>> T partition(LnList<T> xs) {

		int rand = (int) (Math.random() * xs.length1());

		T pivot = null;

		LnList<T> temp = xs;

		for (int i = 0; i <= rand && temp.consq1(); i++) {
			if (i == rand) {
				pivot = temp.hd1();
			}

			temp = temp.tl1();

		}

		return pivot;

	}

	public static <T extends Comparable<T>> LnList<T> LnListQuickSort(LnList<T> xs) {
		return LnListQuickSort(xs, (x1, x2) -> x1.compareTo(x2));
	}

	public static <T extends Comparable<T>> LnList<T> LnListQuickSort(LnList<T> xs, ToIntBiFunction<T, T> cmp) {
		// HX-2025-10-12:
		// Please implement quicksort on a linked list (LnList).
		// Note that you are not allowed to modify the definition
		// of the LnList class. You can only use the public methods
		// provided by the LnList class

		LnList<T> smaller = new LnList<>();
		LnList<T> bigger = new LnList<>();

		if (xs.nilq1()) {
			return xs;
		} else {

			LnList<T> temp = xs;
			T pivot = partition(xs);
			System.out.println("Pivot: " + pivot);
			System.out.print("Original: ");
			while (temp.consq1()) {
				if (pivot.compareTo(temp.hd1()) > 0) {
					smaller = new LnList<>(temp.hd1(), smaller);
				} else if (pivot.compareTo(temp.hd1()) < 0) {
					bigger = new LnList<>(temp.hd1(), bigger);
				}

				temp = temp.tl1();
			}

			System.out.print("Smaller: ");
			smaller.System$out$print1();
			System.out.print(" Bigger: ");
			bigger.System$out$print1();
			System.out.println("\n");

			smaller = LnListQuickSort(smaller, cmp);
			bigger = LnListQuickSort(bigger, cmp);

			// Don't reverse! Build result properly:
			LnList<T> result = smaller;
			result.append1(new LnList<>(pivot, new LnList<>())); // Append pivot as single element
			result.append1(bigger);
			return result;
		}

	}

	public static void main(String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for LnListQuickSort
		LnList<Integer> random = new LnList<>(7,
				new LnList<>(3, new LnList<>(9, new LnList<>(1, new LnList<>(5, new LnList<>())))));
		LnList<Integer> sorted = LnListQuickSort(random);

		sorted.System$out$print1();

	}
}
