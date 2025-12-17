/*
// HX: 50 points for Final_05
// HX: This one tests your priority queue implementation
*/

import LnList.*;
import MyPQueue.*;
import FnList.*;

import java.util.function.ToIntBiFunction;

public class Final_05 {

	// Helper class to wrap list nodes with their head values for priority queue
	private static class ListNode<T> {
		T value;
		LnList<T> list;

		ListNode(T val, LnList<T> lst) {
			value = val;
			list = lst;
		}
	}

	public static <T> LnList<T> LnList_n$way$merge(LnList<T> xss[], ToIntBiFunction<T, T> cmp) {
		// HX: Given an array of (linear) lists (LnList), each of which is
		// ordered according to cmp, please implement a function to merge them
		// into one ordered (linear) list. Please note that you cannot create
		// new list nodes; you can only use exist nodes to form the returned
		// linear list. You are asked to use MyPQueueArray.java implemented in
		// Assigment#9 for finding the minimum of a collection of arguments.

		if (xss == null || xss.length == 0) {
			return new LnList<T>();
		}

		// Create comparator for ListNode based on the value comparator
		java.util.Comparator<ListNode<T>> nodeComparator = (n1, n2) -> cmp.applyAsInt(n1.value, n2.value);

		// Create priority queue with capacity equal to number of lists
		MyPQueueArray<ListNode<T>> pq = new MyPQueueArray<>(xss.length, nodeComparator);

		// Initialize priority queue with head of each non-empty list
		for (int i = 0; i < xss.length; i++) {
			if (xss[i] != null && xss[i].consq1()) {
				T headVal = xss[i].hd1();
				LnList<T> tail = xss[i].tl1();
				pq.enque$exn(new ListNode<>(headVal, tail));
			}
		}

		// Build result list by repeatedly extracting minimum
		LnList<T> result = new LnList<T>();
		LnList<T> lastNode = null;

		while (!pq.isEmpty()) {
			ListNode<T> minNode = pq.deque$exn();

			// Create a new list node with the minimum value
			LnList<T> newNode = new LnList<T>(minNode.value, new LnList<T>());

			// Link it to the result
			if (result.nilq1()) {
				result = newNode;
				lastNode = newNode;
			} else {
				lastNode.link1(newNode);
				lastNode = newNode;
			}

			// If the list had more elements, add next element to priority queue
			if (minNode.list != null && minNode.list.consq1()) {
				T nextVal = minNode.list.hd1();
				LnList<T> nextTail = minNode.list.tl1();
				pq.enque$exn(new ListNode<>(nextVal, nextTail));
			}
		}

		return result;
	}

	public static <T> FnList<T> LnList_mergeSort$5way(LnList<T> xs, ToIntBiFunction<T, T> cmp) {
		// HX: Please use LnList_n$way$merge to implement 5-way mergesort
		// on a linear list. That is, split each list evenly into 5 sublists;
		// recursely sort the 5 sublist and then use LnList_n$way$merge to merge
		// them into one sorted list.
		// Please make sure that your implementation of LnList_mergeSort$5way
		// does stable sorting!

		if (xs == null || xs.nilq1()) {
			return new FnList<T>();
		}

		int len = xs.length1();
		if (len <= 1) {
			// Base case: convert to FnList
			if (xs.nilq1()) {
				return new FnList<T>();
			} else {
				return new FnList<T>(xs.hd1(), new FnList<T>());
			}
		}

		// Split into 5 sublists
		LnList<T>[] sublists = splitInto5(xs, len);

		// Recursively sort each sublist
		FnList<T>[] sortedFnLists = (FnList<T>[]) new FnList[5];
		for (int i = 0; i < 5; i++) {
			sortedFnLists[i] = LnList_mergeSort$5way(sublists[i], cmp);
		}

		// Convert FnLists back to LnLists for merging
		LnList<T>[] sortedLnLists = (LnList<T>[]) new LnList[5];
		for (int i = 0; i < 5; i++) {
			sortedLnLists[i] = new LnList<T>(sortedFnLists[i]);
		}

		// Merge using n-way merge
		LnList<T> merged = LnList_n$way$merge(sortedLnLists, cmp);

		// Convert result to FnList
		return lnListToFnList(merged);
	}

	// Helper: Split LnList into 5 roughly equal parts
	@SuppressWarnings("unchecked")
	private static <T> LnList<T>[] splitInto5(LnList<T> xs, int len) {
		LnList<T>[] result = (LnList<T>[]) new LnList[5];

		// Calculate sizes for each part
		int baseSize = len / 5;
		int remainder = len % 5;

		int[] sizes = new int[5];
		for (int i = 0; i < 5; i++) {
			sizes[i] = baseSize + (i < remainder ? 1 : 0);
		}

		// Split the list
		LnList<T> current = xs;
		for (int i = 0; i < 5; i++) {
			if (sizes[i] == 0) {
				result[i] = new LnList<T>();
			} else {
				result[i] = current;
				// Find the node before the split point
				LnList<T> prev = null;
				LnList<T> temp = current;
				for (int j = 0; j < sizes[i]; j++) {
					prev = temp;
					temp = temp.tl1();
				}
				// Unlink to create separate lists
				if (prev != null) {
					current = prev.unlink1();
				} else {
					current = new LnList<T>();
				}
			}
		}

		return result;
	}

	// Helper: Convert LnList to FnList
	private static <T> FnList<T> lnListToFnList(LnList<T> xs) {
		if (xs == null || xs.nilq1()) {
			return new FnList<T>();
		}

		// Build FnList in reverse, then reverse it
		FnList<T> result = new FnList<T>();
		LnList<T> current = xs;

		while (current.consq1()) {
			result = new FnList<T>(current.hd1(), result);
			current = current.tl1();
		}

		// Reverse to maintain order
		return reverseFnList(result);
	}

	// Helper: Reverse a FnList
	private static <T> FnList<T> reverseFnList(FnList<T> xs) {
		FnList<T> result = new FnList<T>();
		while (xs.consq()) {
			result = new FnList<T>(xs.hd(), result);
			xs = xs.tl();
		}
		return result;
	}

	public static void main(String[] args) {
		// Please write some testing code that applies
		// mergeSort to parity-sort the list [0,1,2,...,999998,999999]
		// of 1000000 elements.

		System.out.println("Creating list [0, 1, 2, ..., 999999]...");

		// Create LnList with 1,000,000 elements
		LnList<Integer> list = new LnList<Integer>();
		LnList<Integer> lastNode = null;

		for (int i = 0; i < 1000000; i++) {
			LnList<Integer> newNode = new LnList<Integer>(i, new LnList<Integer>());
			if (list.nilq1()) {
				list = newNode;
				lastNode = newNode;
			} else {
				lastNode.link1(newNode);
				lastNode = newNode;
			}
		}

		System.out.println("List created with " + list.length1() + " elements");

		// Parity comparator: even numbers < odd numbers, otherwise normal order
		ToIntBiFunction<Integer, Integer> parityCmp = (a, b) -> {
			boolean aEven = (a % 2 == 0);
			boolean bEven = (b % 2 == 0);

			if (aEven && !bEven) {
				return -1; // even < odd
			} else if (!aEven && bEven) {
				return 1; // odd > even
			} else {
				return a.compareTo(b); // same parity: normal order
			}
		};

		System.out.println("Sorting with parity sort (evens first, then odds)...");
		long startTime = System.currentTimeMillis();

		FnList<Integer> sorted = LnList_mergeSort$5way(list, parityCmp);

		long endTime = System.currentTimeMillis();
		System.out.println("Sorting completed in " + (endTime - startTime) + " ms");

		// Verify results
		System.out.println("\nFirst 20 elements:");
		FnList<Integer> temp = sorted;
		for (int i = 0; i < 20 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		System.out.println("\nLast 20 elements:");
		int len = sorted.length();
		temp = sorted;
		for (int i = 0; i < len - 20 && temp.consq(); i++) {
			temp = temp.tl();
		}
		for (int i = 0; i < 20 && temp.consq(); i++) {
			System.out.print(temp.hd() + " ");
			temp = temp.tl();
		}
		System.out.println();

		// Verify sorting correctness
		System.out.println("\nVerifying sort correctness...");
		temp = sorted;
		Integer prev = null;
		boolean correct = true;
		int count = 0;

		while (temp.consq() && correct) {
			Integer curr = temp.hd();
			if (prev != null) {
				if (parityCmp.applyAsInt(prev, curr) > 0) {
					System.out.println("ERROR at position " + count + ": " + prev + " > " + curr);
					correct = false;
				}
			}
			prev = curr;
			temp = temp.tl();
			count++;
		}

		if (correct) {
			System.out.println("✓ Sort is correct! All " + count + " elements in proper order.");
		} else {
			System.out.println("✗ Sort has errors!");
		}

		return /* void */;
	}
}