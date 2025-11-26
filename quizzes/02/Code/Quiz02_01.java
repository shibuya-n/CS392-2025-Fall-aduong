
//
// HX-2025-11-19: 50 points
//
// This question tests your understanding
// of recursion and time analysis involving
// recursion.
// Given a sequence xs, a subsequence of xs
// can be represented as a list of integers
// (representing indices). For instance, given
// xs = "Hello", (0, 2, 4) refers to the subeqence
// "Hlo" (since xs[0] = 'H', xs[2] = 'l', and
// xs[4] = 'o'); (0, 3, 4) also refers to "Hlo".
// The subsequece (0, 2, 4) is to the left of
// the subsequece (0, 3, 4) as (0, 2, 4) is less
// than (0, 3, 4) according to the lexicographic
// ordering.
//
// Here you are asked to implement a function that
// finds the longest leftmost ascending subsequence
// of a given sequence.
// For instance, suppose xs = [1,2,1,2,3,1,2,3,4],
// the longest leftmost ascending subsequence of xs
// is represented by (0, 1, 3, 4, 7, 8) (which refers
// to [1,2,2,3,3,4] in xs).
//
// In order to receive 50 points, your implementation
// should be quadratic time, that is, O(n^2) time and
// you MUST give a brief explanation as to why it is so.
// Otherwise, a working solution receives at most 60%, that
// is, 30 points out of 50 points.
//
import Library.FnList.*;
// Please see Library/FnList for FnList.java
import Library.FnA1sz.*;

// Please see Library/FnA1sz for FnA1sz.java
public class Quiz02_01 {
	public static <T extends Comparable<T>> FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
		// HX-2025-11-19:
		// This method finds the leftmost longest ascending subsequence
		// of xs. Note that the returned list consists of the indices of
		// the elements of the subsequence.

		int[] length = new int[xs.length()];
		int[] parent = new int[xs.length()];

		xs.iforitm((i, val) -> {
			length[i] = 1;
			parent[i] = -1;
		});

		for (int i = 1; i < xs.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (xs.getAt(j).compareTo(xs.getAt(i)) <= 0) {
					if (length[j] + 1 > length[i]) {
						if (length[i] + 1 > length[i]) {
							length[i] = length[j] + 1;
							parent[i] = j;

						}
					}
				}
			}
		}

		int maxLen = 0;
		int endIdx = 0;

		for (int i = 0; i < xs.length(); i++) {
			if (length[i] > maxLen) {
				maxLen = length[i];
				endIdx = i;
			}
		}

		FnList<Integer> toReturn = new FnList<>();
		int curr = endIdx;

		while (curr != -1) {
			toReturn = new FnList<>(curr, toReturn);
			curr = parent[curr];
		}

		return toReturn;
	}

	public static void main(String[] args) {
		// HX-2025-11-19:
		// Please write minimal testing code for FnA1szLongestMonoSubsequence

		Integer[] testArr = { 1, 2, 1, 2, 3, 1, 2, 3, 4 };
		FnA1sz<Integer> xs1 = new FnA1sz<>(testArr);
		System.out.print("input: ");
		xs1.System$out$print();

		FnList<Integer> result = FnA1szLongestMonoSubsequence(xs1);
		System.out.print("result: ");
		result.System$out$print();
	}
}
