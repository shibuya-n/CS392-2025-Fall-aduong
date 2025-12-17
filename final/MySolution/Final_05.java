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
		LnList<T> listNode; // The actual node from the original list
		int sourceIndex; // For stability: which sublist this came from

		ListNode(T val, LnList<T> node, int srcIdx) {
			value = val;
			listNode = node;
			sourceIndex = srcIdx;
		}
	}

	public static <T> LnList<T> LnList_n$way$merge(LnList<T>[] xss, ToIntBiFunction<T, T> cmp) {
		// HX: Given an array of (linear) lists (LnList), each of which is
		// ordered according to cmp, please implement a function to merge them
		// into one ordered (linear) list. Please note that you cannot create
		// new list nodes; you can only use exist nodes to form the returned
		// linear list. You are asked to use MyPQueueArray.java implemented in
		// Assigment#9 for finding the minimum of a collection of arguments.

		if (xss == null || xss.length == 0) {
			return new LnList<T>();
		}

		// Create comparator for ListNode that ensures stability
		// Break ties by sourceIndex (earlier sublists have priority)
		java.util.Comparator<ListNode<T>> nodeComparator = (n1, n2) -> {
			int cmpResult = cmp.applyAsInt(n1.value, n2.value);
			if (cmpResult != 0) {
				return cmpResult;
			}
			// Tie: use sourceIndex to maintain stability
			// Lower sourceIndex = earlier in original list = higher priority
			return Integer.compare(n1.sourceIndex, n2.sourceIndex);
		};

		// Create priority queue with capacity equal to number of lists
		MyPQueueArray<ListNode<T>> pq = new MyPQueueArray<>(xss.length, nodeComparator);

		// Initialize priority queue with head of each non-empty list
		for (int i = 0; i < xss.length; i++) {
			if (xss[i] != null && xss[i].consq1()) {
				T headVal = xss[i].hd1();
				pq.enque$exn(new ListNode<>(headVal, xss[i], i));
			}
		}

		if (pq.isEmpty()) {
			return new LnList<T>();
		}

		// Get the first node to be the head of result
		ListNode<T> firstNode = pq.deque$exn();
		LnList<T> resultHead = firstNode.listNode;
		LnList<T> tail = resultHead;
		int firstSrc = firstNode.sourceIndex;

		// Get next from first list and add to PQ if exists
		LnList<T> nextFromFirst = tail.unlink1();
		if (nextFromFirst != null && nextFromFirst.consq1()) {
			T nextVal = nextFromFirst.hd1();
			pq.enque$exn(new ListNode<>(nextVal, nextFromFirst, firstSrc));
		}

		// Process remaining nodes
		while (!pq.isEmpty()) {
			ListNode<T> minNode = pq.deque$exn();
			LnList<T> currentNode = minNode.listNode;
			int srcIdx = minNode.sourceIndex;

			// Unlink this node from its original chain and get next
			LnList<T> nextInOriginalList = currentNode.unlink1();

			// Append current node to tail
			tail.link1(currentNode);
			tail = currentNode;

			// If there was a next node in the original list, add it to PQ
			if (nextInOriginalList != null && nextInOriginalList.consq1()) {
				T nextVal = nextInOriginalList.hd1();
				pq.enque$exn(new ListNode<>(nextVal, nextInOriginalList, srcIdx));
			}
		}

		return resultHead;
	}

	public static <T> LnList<T> LnList_mergeSort$5way(LnList<T> xs, ToIntBiFunction<T, T> cmp) {
		// HX: Please use LnList_n$way$merge to implement 5-way mergesort
		// on a linear list. That is, split each list evenly into 5 sublists;
		// recursely sort the 5 sublist and then use LnList_n$way$merge to merge
		// them into one sorted list.
		// Please make sure that your implementation of LnList_mergeSort$5way
		// does stable sorting!

		if (xs == null || xs.nilq1()) {
			return new LnList<T>();
		}

		int len = xs.length1();
		if (len <= 1) {
			// Base case: return the single node as-is
			return xs;
		}

		// Split into 5 sublists by unlinking
		LnList<T>[] sublists = splitInto5WithUnlink(xs, len);

		// Recursively sort each sublist - stay in LnList domain
		@SuppressWarnings("unchecked")
		LnList<T>[] sortedLnLists = (LnList<T>[]) new LnList[5];
		for (int i = 0; i < 5; i++) {
			sortedLnLists[i] = LnList_mergeSort$5way(sublists[i], cmp);
		}

		// Merge using n-way merge (which reuses nodes)
		return LnList_n$way$merge(sortedLnLists, cmp);
	}

	// Helper: Split LnList into 5 roughly equal parts by unlinking
	@SuppressWarnings("unchecked")
	private static <T> LnList<T>[] splitInto5WithUnlink(LnList<T> xs, int len) {
		LnList<T>[] result = (LnList<T>[]) new LnList[5];

		// Calculate sizes for each part
		int baseSize = len / 5;
		int remainder = len % 5;

		int[] sizes = new int[5];
		for (int i = 0; i < 5; i++) {
			sizes[i] = baseSize + (i < remainder ? 1 : 0);
		}

		// Split the list by unlinking
		LnList<T> current = xs;
		for (int i = 0; i < 5; i++) {
			if (sizes[i] == 0) {
				result[i] = new LnList<T>();
			} else {
				result[i] = current;

				// Navigate to the last node of this sublist
				LnList<T> temp = current;
				for (int j = 1; j < sizes[i]; j++) {
					temp = temp.tl1();
				}

				// Unlink to create separate lists
				current = temp.unlink1();
			}
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

		LnList<Integer> sorted = LnList_mergeSort$5way(list, parityCmp);

		long endTime = System.currentTimeMillis();
		System.out.println("Sorting completed in " + (endTime - startTime) + " ms");

		// Verify results
		System.out.println("\nFirst 20 elements:");
		LnList<Integer> temp = sorted;
		for (int i = 0; i < 20 && temp.consq1(); i++) {
			System.out.print(temp.hd1() + " ");
			temp = temp.tl1();
		}
		System.out.println();

		System.out.println("\nLast 20 elements:");
		int len = sorted.length1();
		temp = sorted;
		for (int i = 0; i < len - 20 && temp.consq1(); i++) {
			temp = temp.tl1();
		}
		for (int i = 0; i < 20 && temp.consq1(); i++) {
			System.out.print(temp.hd1() + " ");
			temp = temp.tl1();
		}
		System.out.println();

		// Verify sorting correctness
		System.out.println("\nVerifying sort correctness...");
		temp = sorted;
		Integer prev = null;
		boolean correct = true;
		int count = 0;

		while (temp.consq1() && correct) {
			Integer curr = temp.hd1();
			if (prev != null) {
				if (parityCmp.applyAsInt(prev, curr) > 0) {
					System.out.println("ERROR at position " + count + ": " + prev + " > " + curr);
					correct = false;
				}
			}
			prev = curr;
			temp = temp.tl1();
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