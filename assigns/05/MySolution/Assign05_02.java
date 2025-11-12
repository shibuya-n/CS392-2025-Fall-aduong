import Library.FnList.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign05_02 {

	public static <T extends Comparable<T>> FnList<T> insertSort(FnList<T> xs) {
		return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
	}

	// Fully iterative implementation of insertion sort
	public static <T> FnList<T> insertSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		FnList<T> sorted = new FnList<T>(); // Empty list to build sorted result

		// Process each element from the input list
		while (xs.consq()) {
			T current = xs.hd();
			sorted = insertIntoSorted(sorted, current, cmp);
			xs = xs.tl();
		}

		return sorted;
	}

	// Fully iterative helper method to insert an element into a sorted list
	private static <T> FnList<T> insertIntoSorted(FnList<T> sorted, T elem, ToIntBiFunction<T, T> cmp) {
		// Empty list - just return new list with element
		if (sorted.nilq()) {
			return new FnList<T>(elem, sorted);
		}

		// Element belongs at the beginning
		if (cmp.applyAsInt(elem, sorted.hd()) <= 0) {
			return new FnList<T>(elem, sorted);
		}

		// Build prefix of elements smaller than elem
		FnList<T> prefix = new FnList<T>();
		FnList<T> remaining = sorted;

		// Collect all elements that should come before elem
		while (remaining.consq() && cmp.applyAsInt(remaining.hd(), elem) < 0) {
			prefix = new FnList<T>(remaining.hd(), prefix);
			remaining = remaining.tl();
		}

		// Build result: reverse prefix + elem + remaining
		FnList<T> result = new FnList<T>(elem, remaining);
		while (prefix.consq()) {
			result = new FnList<T>(prefix.hd(), result);
			prefix = prefix.tl();
		}

		return result;
	}

	public static void main(String[] args) {
		// Create the test list: 1, 0, 3, 2, 5, 4, 7, 6, ..., 999999, 999998
		System.out.println("Creating test list of 1M numbers...");
		long startCreate = System.currentTimeMillis();

		FnList<Integer> testList = new FnList<Integer>();
		// Build list in reverse order (from end to beginning)
		for (int i = 1000000 - 1; i >= 0; i -= 2) {
			testList = new FnList<Integer>(i - 1, testList); // even number
			testList = new FnList<Integer>(i, testList); // odd number
		}

		long endCreate = System.currentTimeMillis();
		System.out.println("List created in " + (endCreate - startCreate) + " ms");

		// Verify the first few elements
		System.out.print("First 10 elements: ");
		FnList<Integer> temp = testList;
		for (int i = 0; i < 10 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		// Sort the list
		System.out.println("Sorting list...");
		long startSort = System.currentTimeMillis();
		FnList<Integer> sortedList = insertSort(testList);
		long endSort = System.currentTimeMillis();
		System.out.println("List sorted in " + (endSort - startSort) + " ms");

		// Verify the sorted list
		System.out.print("First 10 elements after sorting: ");
		temp = sortedList;
		for (int i = 0; i < 10 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		// Check if the list is properly sorted
		boolean isSorted = true;
		temp = sortedList;
		Integer prev = null;
		while (temp.consq()) {
			if (prev != null && prev > temp.hd()) {
				isSorted = false;
				break;
			}
			prev = temp.hd();
			temp = temp.tl();
		}

		System.out.println("Is sorted: " + isSorted);
		System.out.println("Total time: " + (endSort - startCreate) + " ms");
	}

} // end of [public class Assign05_02{...}]
