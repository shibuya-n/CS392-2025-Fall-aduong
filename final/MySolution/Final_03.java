/*
// HX: 50 points for Final_03
// HX: This one tests your hash map implementation
// In Final_02, pg2701_word$count$listize2() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_03, you are asked to implement the same functionality
// with a different approach.
*/

import FnList.*;
import FnTuple.*;
import LnStrm.*;
import MyMap00.*;

public class Final_03 {

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

	static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize3() {
		// HX-2025-12-15:
		// Your implementation must contain the following steps:
		// 1. Call pg2701_word$strmize() to get a stream of words
		// 2. Then use the hash map implemented in Assign08_02 (open addressing)
		// to count the number of occurrences of each word in the stream of words
		// 3. Then figure out a way to turn the hash map into a list WNS (FnList) of
		// word-count pairs
		// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
		// the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
		// 5. The sorted WNS is the return value of pg2701_word$count$listize3()

		// Step 1: Get stream of words
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

		// Step 2: Use hash map (open addressing) to count word occurrences
		MyMapOpenAddressing<Integer> wordMap = new MyMapOpenAddressing<>(10000);

		// Process each word in the stream
		LnStcn<FnList<Character>> cons = wordStream.eval0();
		while (cons.consq()) {
			FnList<Character> word = cons.hd();
			String wordKey = wordToString(word);

			// Get current count or 0 if not present
			FnList<Integer> counts = wordMap.search$opt(wordKey);
			int currentCount = (counts != null && counts.consq()) ? counts.hd() : 0;

			// Increment count
			wordMap.insert$raw(wordKey, currentCount + 1);

			cons = cons.tl().eval0();
		}

		// Step 3: Turn hash map into a list WNS of word-count pairs
		FnList<FnTupl2<FnList<Character>, Integer>> WNS = FnListSUtil.nil();

		// Actually, we need to iterate through unique keys, so let's use a different
		// approach
		// Use strmize to get all key-value pairs, then process them
		LnStrm<FnTupl2<String, FnList<Integer>>> mapStream = wordMap.strmize();
		LnStcn<FnTupl2<String, FnList<Integer>>> mapCons = mapStream.eval0();

		while (mapCons.consq()) {
			FnTupl2<String, FnList<Integer>> entry = mapCons.hd();
			String wordKey = entry.sub0;
			FnList<Integer> counts = entry.sub1;

			// Get the most recent count (head of the list)
			if (counts.consq()) {
				int count = counts.hd();
				FnList<Character> word = stringToWord(wordKey);
				FnTupl2<FnList<Character>, Integer> pair = new FnTupl2<>(word, count);
				WNS = FnListSUtil.cons(pair, WNS);
			}

			mapCons = mapCons.tl().eval0();
		}

		// Step 4: Sort WNS using mergesort with the specified order
		WNS = mergeSort(WNS, Final_03::compareWordCountPairs);

		// Step 5: Return sorted WNS
		return WNS;
	}

	// Helper: Convert FnList<Character> to String for use as hash map key
	private static String wordToString(FnList<Character> word) {
		StringBuilder sb = new StringBuilder();
		FnList<Character> current = word;
		while (current.consq()) {
			sb.append(current.hd());
			current = current.tl();
		}
		return sb.toString();
	}

	// Helper: Convert String back to FnList<Character>
	private static FnList<Character> stringToWord(String str) {
		FnList<Character> word = FnListSUtil.nil();
		for (int i = str.length() - 1; i >= 0; i--) {
			word = FnListSUtil.cons(str.charAt(i), word);
		}
		return word;
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
		if (w1.nilq() && w2.nilq())
			return 0;
		if (w1.nilq())
			return -1;
		return 1;
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
		// Please write minimal testing code for pg2701_word$count$listize3()
		// In particular, please print out the first 100 word-count pairs, where
		// each line should contain only one word-count pair.

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize3();

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

		return /* void */;
	}

	// Helper to print a word (FnList<Character>)
	private static void printWord(FnList<Character> word) {
		word.foritm(ch -> System.out.print(ch));
	}
}