
//
// HX: 40 points
//
import Library.FnA1sz.FnA1sz;
import Library.LnList.*;

// Please see Library/LnList for LnList.java
public class Quiz01_04 {
	public static <T extends Comparable<T>> LnList<T> LnListInsertSort(LnList<T> xs) {
		// HX-2025-10-12:
		// Please implement (stable) insert sort on a
		// linked list (LnList).
		// Note that you are not allowed to modify the definition
		// of the LnList class. You can only use the public methods
		// provided by the LnList class

		if (xs.nilq1()) {
			return xs;
		}

		LnList<T> sorted = xs;
		LnList<T> unsorted = sorted.unlink();

		while (unsorted.consq1()) {
			LnList<T> current = unsorted;
			unsorted = current.unlink();

			// insert current node into sorted list
			if (current.hd1().compareTo(sorted.hd1()) <= 0) {
				current.link(sorted);
				sorted = current;
			} else {
				LnList<T> prev = sorted;
				while (prev.tl1().consq1() && prev.tl1().hd1().compareTo(current.hd1()) < 0) {
					prev = prev.tl1();
				}

				LnList<T> after = prev.unlink();
				prev.link(current);
				current.link(after);

			}

		}

		return sorted;

	}

	public static void main(String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for LnListInsertSort
		Integer[] input = { 5, 2, 3, 1, 6, 8 };
		FnA1sz<Integer> temp = new FnA1sz<>(input);
		LnList<Integer> test = new LnList<>(temp);

		LnList<Integer> toReturn = LnListInsertSort(test);
		toReturn.System$out$print1();

	}
}
