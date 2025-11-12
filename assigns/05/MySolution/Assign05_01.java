import Library.FnList.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign05_01 {

	public static <T extends Comparable<T>> FnList<T> mergeSort(FnList<T> xs) {
		return mergeSort(xs, (x1, x2) -> x1.compareTo(x2));
	}

	public static <T> FnList<T> mergeSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		int n0 = xs.length();
		// Base case: lists of length 0 or 1 are already sorted
		if (n0 <= 1) {
			return xs;
		} else {
			// Split the list and recursively sort both halves
			return mergeSort_split(xs, new FnList<T>(), n0, 0, cmp);
		}
	}

	// Split the list into two halves (iterative version)
	private static <T> FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1,
			ToIntBiFunction<T, T> cmp) {
		// Iteratively move elements from xs to ys until we reach the midpoint
		while (2 * n1 < n0) {
			ys = new FnList<T>(xs.hd(), ys);
			xs = xs.tl();
			n1++;
		}

		// We've split the list in half
		// Sort both halves and merge them
		FnList<T> leftSorted = mergeSort(reverse(ys), cmp);
		FnList<T> rightSorted = mergeSort(xs, cmp);
		return mergeSort_merge(leftSorted, rightSorted, cmp);
	}

	// Merge two sorted lists
	private static <T> FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T, T> cmp) {
		return mergeSort_merge_helper(xs, ys, new FnList<T>(), cmp);
	}

	// Helper function for merging with accumulator (iterative version)
	private static <T> FnList<T> mergeSort_merge_helper(FnList<T> xs, FnList<T> ys, FnList<T> zs,
			ToIntBiFunction<T, T> cmp) {
		// Iteratively merge elements
		while (xs.consq() && ys.consq()) {
			if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
				zs = new FnList<T>(xs.hd(), zs);
				xs = xs.tl();
			} else {
				zs = new FnList<T>(ys.hd(), zs);
				ys = ys.tl();
			}
		}

		// Append remaining elements
		if (xs.nilq())
			return rappend(zs, ys);
		if (ys.nilq())
			return rappend(zs, xs);

		return zs; // Should never reach here
	}

	// Helper function to reverse a list
	private static <T> FnList<T> reverse(FnList<T> xs) {
		FnList<T> result = new FnList<T>();
		while (xs.consq()) {
			result = new FnList<T>(xs.hd(), result);
			xs = xs.tl();
		}
		return result;
	}

	// Helper function to reverse-append (prepend all elements of xs to ys in
	// reverse)
	private static <T> FnList<T> rappend(FnList<T> xs, FnList<T> ys) {
		while (xs.consq()) {
			ys = new FnList<T>(xs.hd(), ys);
			xs = xs.tl();
		}
		return ys;
	}

	public static void main(String[] args) {
		Random rand = new Random();

		// Generate a random list of 1,000,000 integers
		System.out.println("Generating random list of 1,000,000 integers...");
		long startGenerate = System.currentTimeMillis();

		FnList<Integer> randomList = new FnList<Integer>();
		for (int i = 0; i < 1000000; i++) {
			randomList = new FnList<Integer>(rand.nextInt(), randomList);
		}

		long endGenerate = System.currentTimeMillis();
		System.out.println("List generated in " + (endGenerate - startGenerate) + " ms");

		// Display first 10 elements before sorting
		System.out.print("First 10 elements (before): ");
		FnList<Integer> temp = randomList;
		for (int i = 0; i < 10 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		// Sort the list using mergeSort
		System.out.println("Sorting list using mergeSort...");
		long startSort = System.currentTimeMillis();
		FnList<Integer> sortedList = mergeSort(randomList);
		long endSort = System.currentTimeMillis();
		System.out.println("List sorted in " + (endSort - startSort) + " ms");

		// Display first 10 elements after sorting
		System.out.print("First 10 elements (after):  ");
		temp = sortedList;
		for (int i = 0; i < 10 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		// Verify the list is sorted
		System.out.println("Verifying sort correctness...");
		boolean isSorted = true;
		temp = sortedList;
		Integer prev = null;
		int count = 0;
		while (temp.consq()) {
			if (prev != null && prev.compareTo(temp.hd()) > 0) {
				isSorted = false;
				System.out.println("Error at position " + count + ": " + prev + " > " + temp.hd());
				break;
			}
			prev = temp.hd();
			temp = temp.tl();
			count++;
		}

		System.out.println("Is sorted: " + isSorted);
		System.out.println("List length: " + sortedList.length());
		System.out.println("Total time: " + (endSort - startGenerate) + " ms");
	}

} // end of [public class Assign05_01{...}]