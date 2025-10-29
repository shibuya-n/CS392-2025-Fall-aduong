//
// HX: 50 points
//

import Library.FnList.*;
import Library.FnList.FnList;

import java.util.function.ToIntBiFunction;

abstract public class Quiz01_06 {

	// Wrapper class to make sorting stable by tracking original index
	private static class IndexedItem<T> {
		T value;
		int index;

		IndexedItem(T value, int index) {
			this.value = value;
			this.index = index;
		}
	}

	public static <T> FnList<T> someSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		// Using quickSort as the unstable sorting method
		return FnListSUtil.quickSort(xs, cmp);
	}

	public static <T extends Comparable<T>> FnList<T> someStableSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {

		FnList<IndexedItem<T>> indexed = FnListSUtil.imap_list(
				xs,
				(i, item) -> new IndexedItem<T>(item, i));

		ToIntBiFunction<IndexedItem<T>, IndexedItem<T>> stableCmp = (item1, item2) -> {
			int result = cmp.applyAsInt(item1.value, item2.value);
			if (result != 0) {
				return result;
			} else {

				return Integer.compare(item1.index, item2.index);
			}
		};

		FnList<IndexedItem<T>> sorted = someSort(indexed, stableCmp);

		return FnListSUtil.map_list(sorted, item -> item.value);
	}

	public static void main(String[] args) {

		FnList<Integer> list = FnListSUtil.int1$make(1000000);

		ToIntBiFunction<Integer, Integer> parityCmp = (x, y) -> {
			boolean xEven = (x % 2 == 0);
			boolean yEven = (y % 2 == 0);

			if (xEven && !yEven) {
				return -1; // even comes before odd
			} else if (!xEven && yEven) {
				return 1; // odd comes after even
			} else {
				return 0; // same parity - let stability determine order
			}
		};
		FnList<Integer> sorted = someStableSort(list, parityCmp);

		sorted.System$out$print();

	}
}

//////////////////////////////////////////////////////////////////////// .
//
// HX-2025-10-15:
// Please find a way to test someStableSort by
// implementing someSort as quickSort on FnList
// and then use someStableSort to parity-sort the following
// list of 1M integers:
// 0, 1, 2, 3, 4, ..., 999999
// That is, the result of parity-sorting should be:
// 0, 2, ..., 999998, 1, 3, ..., 999999
//
// Note that you should be able to call the quickSort method
// in Library/FnList/FnListSUtil.java; should not do another
// implementation of quickSort in your testing code.
//////////////////////////////////////////////////////////////////////// .