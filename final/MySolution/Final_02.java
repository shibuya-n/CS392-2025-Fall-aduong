package MySolution;

/*
// HX: 50 points for Final_02
// HX: This one tests your quicksort and mergesort
// In Final_01, pg2701_word$strmize() is implemented
// that lists all the words in pg2701.txt. Here, you
// are asked to generate FnList of pairs; each pair consists
// of a word (FnList<Character>) and a count (Integer) such that
// the count is the number of occurrences of the word in pg2701.txt.
// Note that a lower case letter is considered the same as its
// corresponding upper case. For instance, "Whale" and "whale"
// are considered the same word.
*/

import FnList.*;
import FnTuple.*;
import LnStrm.*;

public class Final_02 {

	// arrayQuickSort from Assign06_03 - included here as required
	private static <T> void arrayQuickSort(T[] A, java.util.function.ToIntBiFunction<T, T> cmp) {
		if (A == null || A.length <= 1) {
			return;
		}
		quickSortHelper(A, 0, A.length - 1, cmp);
	}

	private static <T> void quickSortHelper(T[] A, int lo, int hi, java.util.function.ToIntBiFunction<T, T> cmp) {
		if (lo >= hi) {
			return;
		}

		// 3-way partition to handle duplicates efficiently
		int[] bounds = partition3Way(A, lo, hi, cmp);
		int lt = bounds[0];
		int gt = bounds[1];

		quickSortHelper(A, lo, lt - 1, cmp);
		quickSortHelper(A, gt + 1, hi, cmp);
	}

	private static <T> int[] partition3Way(T[] A, int lo, int hi, java.util.function.ToIntBiFunction<T, T> cmp) {
		int pivotIdx = medianOfThree(A, lo, hi, cmp);
		T pivot = A[pivotIdx];

		swap(A, lo, pivotIdx);

		int lt = lo;
		int i = lo + 1;
		int gt = hi;

		while (i <= gt) {
			int cmpResult = cmp.applyAsInt(A[i], pivot);

			if (cmpResult < 0) {
				swap(A, lt, i);
				lt++;
				i++;
			} else if (cmpResult > 0) {
				swap(A, i, gt);
				gt--;
			} else {
				i++;
			}
		}

		return new int[] { lt, gt };
	}

	private static <T> int medianOfThree(T[] A, int lo, int hi, java.util.function.ToIntBiFunction<T, T> cmp) {
		int mid = lo + (hi - lo) / 2;

		if (cmp.applyAsInt(A[mid], A[lo]) < 0) {
			swap(A, lo, mid);
		}
		if (cmp.applyAsInt(A[hi], A[lo]) < 0) {
			swap(A, lo, hi);
		}
		if (cmp.applyAsInt(A[hi], A[mid]) < 0) {
			swap(A, mid, hi);
		}

		return mid;
	}

	private static <T> void swap(T[] A, int i, int j) {
		T temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}

	// mergeSort from Assign05_01 - included here as required
	private static <T> FnList<T> mergeSort(FnList<T> xs, java.util.function.ToIntBiFunction<T, T> cmp) {
		int n0 = xs.length();
		if (n0 <= 1) {
			return xs;
		} else {
			return mergeSort_split(xs, new FnList<T>(), n0, 0, cmp);
		}
	}

	private static <T> FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1,
			java.util.function.ToIntBiFunction<T, T> cmp) {
		while (2 * n1 < n0) {
			ys = new FnList<T>(xs.hd(), ys);
			xs = xs.tl();
			n1++;
		}

		FnList<T> leftSorted = mergeSort(reverse(ys), cmp);
		FnList<T> rightSorted = mergeSort(xs, cmp);
		return mergeSort_merge(leftSorted, rightSorted, cmp);
	}

	private static <T> FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys,
			java.util.function.ToIntBiFunction<T, T> cmp) {
		return mergeSort_merge_helper(xs, ys, new FnList<T>(), cmp);
	}

	private static <T> FnList<T> mergeSort_merge_helper(FnList<T> xs, FnList<T> ys, FnList<T> zs,
			java.util.function.ToIntBiFunction<T, T> cmp) {
		while (xs.consq() && ys.consq()) {
			if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
				zs = new FnList<T>(xs.hd(), zs);
				xs = xs.tl();
			} else {
				zs = new FnList<T>(ys.hd(), zs);
				ys = ys.tl();
			}
		}

		if (xs.nilq())
			return rappend(zs, ys);
		if (ys.nilq())
			return rappend(zs, xs);

		return zs;
	}

	private static <T> FnList<T> reverse(FnList<T> xs) {
		FnList<T> result = new FnList<T>();
		while (xs.consq()) {
			result = new FnList<T>(xs.hd(), result);
			xs = xs.tl();
		}
		return result;
	}

	private static <T> FnList<T> rappend(FnList<T> xs, FnList<T> ys) {
		while (xs.consq()) {
			ys = new FnList<T>(xs.hd(), ys);
			xs = xs.tl();
		}
		return ys;
	}

	static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize2() {
		// HX-2025-12-15:
		// Your implementation must contain the following steps:
		// 1. Call pg2701_word$strmize() to get a stream of words
		// 2. Turn this stream into an array A1 of words (FnList<Character>[])
		// 3. Call the quicksort (arrayQuickSort) done in Assign06_03 to sort A1
		// 4. Use sorted A1 to generate a list L2 of word-count pairs
		// 5. Use the mergesort (mergeSort) in Assign05_01 to sort L2 using
		// the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
		// 6. The sorted L2 is the return value of pg2701_word$count$listize2()

		// Step 1: Get stream of words
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

		// Step 2: Turn stream into an array A1
		FnList<Character>[] A1 = streamToArray(wordStream);

		// Step 3: Sort A1 using quicksort (from Assign06_03)
		arrayQuickSort(A1, Final_02::compareWords);

		// Step 4: Generate list L2 of word-count pairs from sorted A1
		FnList<FnTupl2<FnList<Character>, Integer>> L2 = generateWordCounts(A1);

		// Step 5: Sort L2 using mergesort (from Assign05_01) with custom comparator
		L2 = mergeSort(L2, Final_02::compareWordCountPairs);

		// Step 6: Return sorted L2
		return L2;
	}

	// Convert stream to array
	@SuppressWarnings("unchecked")
	private static FnList<Character>[] streamToArray(LnStrm<FnList<Character>> stream) {
		// First, collect all words into a list to count them
		FnList<FnList<Character>> wordList = FnListSUtil.nil();
		int count = 0;

		LnStcn<FnList<Character>> cons = stream.eval0();
		while (cons.consq()) {
			wordList = FnListSUtil.cons(cons.hd(), wordList);
			count++;
			cons = cons.tl().eval0();
		}

		// Reverse to maintain original order
		wordList = FnListSUtil.reverse(wordList);

		// Create array and fill it
		FnList<Character>[] array = (FnList<Character>[]) new FnList[count];
		int i = 0;
		while (wordList.consq()) {
			array[i] = wordList.hd();
			wordList = wordList.tl();
			i++;
		}

		return array;
	}

	// Compare two words lexicographically
	private static int compareWords(FnList<Character> w1, FnList<Character> w2) {
		while (w1.consq() && w2.consq()) {
			char c1 = w1.hd();
			char c2 = w2.hd();
			if (c1 < c2)
				return -1;
			if (c1 > c2)
				return 1;
			w1 = w1.tl();
			w2 = w2.tl();
		}
		// If we get here, one or both lists are empty
		if (w1.nilq() && w2.nilq())
			return 0;
		if (w1.nilq())
			return -1; // w1 is shorter
		return 1; // w2 is shorter
	}

	// Generate word-count pairs from sorted array
	private static FnList<FnTupl2<FnList<Character>, Integer>> generateWordCounts(FnList<Character>[] sortedWords) {
		if (sortedWords.length == 0) {
			return FnListSUtil.nil();
		}

		FnList<FnTupl2<FnList<Character>, Integer>> result = FnListSUtil.nil();

		int i = 0;
		while (i < sortedWords.length) {
			FnList<Character> currentWord = sortedWords[i];
			int count = 1;
			i++;

			// Count consecutive identical words
			while (i < sortedWords.length && compareWords(currentWord, sortedWords[i]) == 0) {
				count++;
				i++;
			}

			// Add word-count pair to result
			FnTupl2<FnList<Character>, Integer> pair = new FnTupl2<>(currentWord, count);
			result = FnListSUtil.cons(pair, result);
		}

		// Reverse to maintain correct order
		return FnListSUtil.reverse(result);
	}

	// Compare word-count pairs: (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1
	// <= w2
	private static int compareWordCountPairs(FnTupl2<FnList<Character>, Integer> p1,
			FnTupl2<FnList<Character>, Integer> p2) {
		int n1 = p1.sub1;
		int n2 = p2.sub1;

		// First compare by count (descending - higher counts first)
		if (n1 > n2)
			return -1;
		if (n1 < n2)
			return 1;

		// If counts are equal, compare by word (ascending - lexicographic order)
		return compareWords(p1.sub0, p2.sub0);
	}

	public static void main(String[] args) {
		// HX-2025-12-16:
		// Please write minimal testing code for pg2701_word$count$listize2()
		// In particular, please print out the first 100 word-count pairs, where
		// each line should contain only one word-count pair.

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize2();

		int count = 0;
		FnList<FnTupl2<FnList<Character>, Integer>> current = wordCounts;

		while (count < 100 && current.consq()) {
			FnTupl2<FnList<Character>, Integer> pair = current.hd();

			// Print count and word
			System.out.print((count + 1) + ": ");
			printWord(pair.sub0);
			System.out.println(" -> " + pair.sub1);

			current = current.tl();
			count++;
		}

		System.out.println("\nTotal unique words: " + wordCounts.length());
		return /* void */;

	}

	// Helper to print a word (FnList<Character>)
	private static void printWord(FnList<Character> word) {
		word.foritm(ch -> System.out.print(ch));
	}
}