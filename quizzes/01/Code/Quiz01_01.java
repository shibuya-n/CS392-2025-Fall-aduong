
//
// HX: 20 points
//
import Library.FnA1sz.*;

// Please see Library/FnA1sz for FnA1sz.java
public class Quiz01_01 {
	public static <T extends Comparable<T>> int FnA1szBinarySearch(FnA1sz<T> A, T key) {
		// HX-2025-10-12:
		// Please implement binary search on a sorted functional array (FnA1sz)
		// that returns the largest index i such that key >= A[i] if such i exists,
		// or the method returns -1. The comparison function should be the compareTo
		// method implemented by the class T.
		int low = 0;
		int high = A.length() - 1;
		int best = -1;
		while (low <= high) {
			int mid = low + (high - low) / 2;
			if (A.getAt(mid).compareTo(key) == 0 || A.getAt(mid).compareTo(key) < 0) {
				low = mid + 1;
				best = mid;
			} else if (A.getAt(mid).compareTo(key) > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}

		return best;

	}

	public static void main(String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for FnA1szBinarySearch
		Integer[] input = { 1, 3, 5, 7, 9, 9 };
		FnA1sz<Integer> test = new FnA1sz(input);

		System.out.println(FnA1szBinarySearch(test, 10));
		System.out.println(FnA1szBinarySearch(test, 3));
		System.out.println(FnA1szBinarySearch(test, 7));
		System.out.println(FnA1szBinarySearch(test, 8));
		System.out.println(FnA1szBinarySearch(test, 0));
		System.out.println(FnA1szBinarySearch(test, 9));

	}
}
