
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

		LnList<T> sorted = new LnList<>();

		while (xs.consq1()) {
			T key = xs.hd1();
			xs = xs.tl1();

			sorted = insertIntoSorted(sorted, key);
		}

		return sorted;

	}

	public static <T extends Comparable<T>> LnList<T> insertIntoSorted(LnList<T> sorted, T key) {
		if (sorted.nilq1() || key.compareTo(sorted.hd1()) <= 0) {
			return new LnList<>(key, sorted);
		}

		LnList<T> result = new LnList<>();
		LnList<T> current = sorted;

		while (current.consq1() && (current.hd1().compareTo(key) < 0)) {
			result = new LnList<>(current.hd1(), result);
			current = current.tl1();
		}
		result = new LnList<>(key, result);

		while (current.consq1()) {
			result = new LnList<>(current.hd1(), result);
			current = current.tl1();
		}

		return result.reverse0();
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
