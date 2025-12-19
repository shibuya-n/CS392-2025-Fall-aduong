package MySolution;

/*
// HX: 50 points for Final_04
// HX: This one tests your RBST implementation done in Quiz02_06.
// In Final_02, pg2701_word$count$listize2() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_04, you are asked to implement the same functionality
// with a different approach.
*/

import FnList.*;
import FnTuple.*;
import LnStrm.*;

import java.util.Random;
import java.util.function.ToIntBiFunction;

public class Final_04 {

	// Generic RBST-based associative map (modified from Quiz02_06)
	// This stores word -> count mappings
	static class RBSTMap<K> {
		Node root = null;
		Random rand = new Random();
		ToIntBiFunction<K, K> comparator;

		class Node {
			K key;
			int count; // Store count as integer, not FnList
			int size;
			Node lchild;
			Node rchild;

			Node(K key, int initialCount) {
				this.key = key;
				this.count = initialCount;
				this.size = 1;
				this.lchild = null;
				this.rchild = null;
			}
		}

		public RBSTMap(ToIntBiFunction<K, K> comparator) {
			this.comparator = comparator;
		}

		private void updateSize(Node n) {
			if (n == null)
				return;
			n.size = 1;
			if (n.lchild != null)
				n.size += n.lchild.size;
			if (n.rchild != null)
				n.size += n.rchild.size;
		}

		// Right rotation
		private Node rotateRight(Node n) {
			if (n == null || n.lchild == null)
				return n;
			Node l = n.lchild;
			n.lchild = l.rchild;
			l.rchild = n;
			updateSize(n);
			updateSize(l);
			return l;
		}

		// Left rotation
		private Node rotateLeft(Node n) {
			if (n == null || n.rchild == null)
				return n;
			Node r = n.rchild;
			n.rchild = r.lchild;
			r.lchild = n;
			updateSize(n);
			updateSize(r);
			return r;
		}

		// Insert or increment count
		public void insertOrIncrement(K key) {
			root = insertAtRoot(root, key);
		}

		private Node insertAtRoot(Node n, K key) {
			if (n == null) {
				return new Node(key, 1); // Initialize count to 1
			}

			int cmp = comparator.applyAsInt(key, n.key);

			if (cmp == 0) {
				// Key exists: increment count
				n.count++;
				return n;
			}

			// Randomized: insert at root with probability 1/(n.size+1)
			if (rand.nextInt(n.size + 1) == 0) {
				return insertAtRootHelper(n, key);
			}

			// Otherwise insert recursively
			if (cmp < 0) {
				n.lchild = insertAtRoot(n.lchild, key);
			} else {
				n.rchild = insertAtRoot(n.rchild, key);
			}

			updateSize(n);
			return n;
		}

		private Node insertAtRootHelper(Node n, K key) {
			int cmp = comparator.applyAsInt(key, n.key);

			if (cmp < 0) {
				n.lchild = insertAtRoot(n.lchild, key);
				updateSize(n);
				return rotateRight(n);
			} else {
				n.rchild = insertAtRoot(n.rchild, key);
				updateSize(n);
				return rotateLeft(n);
			}
		}

		// Convert tree to list of key-count pairs using in-order traversal
		public FnList<FnTupl2<K, Integer>> toList() {
			return inorderTraversal(root, FnListSUtil.nil());
		}

		private FnList<FnTupl2<K, Integer>> inorderTraversal(Node n, FnList<FnTupl2<K, Integer>> acc) {
			if (n == null)
				return acc;

			// Right subtree
			acc = inorderTraversal(n.rchild, acc);

			// Current node - use count field directly
			acc = FnListSUtil.cons(new FnTupl2<>(n.key, n.count), acc);

			// Left subtree
			acc = inorderTraversal(n.lchild, acc);

			return acc;
		}
	}

	// mergeSort from Assign05_01 - copied here as required
	private static <T> FnList<T> mergeSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		int n0 = xs.length();
		if (n0 <= 1) {
			return xs;
		} else {
			return mergeSort_split(xs, FnListSUtil.nil(), n0, 0, cmp);
		}
	}

	private static <T> FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1,
			ToIntBiFunction<T, T> cmp) {
		while (2 * n1 < n0) {
			ys = FnListSUtil.cons(xs.hd(), ys);
			xs = xs.tl();
			n1++;
		}

		FnList<T> leftSorted = mergeSort(reverse(ys), cmp);
		FnList<T> rightSorted = mergeSort(xs, cmp);
		return mergeSort_merge(leftSorted, rightSorted, cmp);
	}

	private static <T> FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys,
			ToIntBiFunction<T, T> cmp) {
		return mergeSort_merge_helper(xs, ys, FnListSUtil.nil(), cmp);
	}

	private static <T> FnList<T> mergeSort_merge_helper(FnList<T> xs, FnList<T> ys, FnList<T> zs,
			ToIntBiFunction<T, T> cmp) {
		while (xs.consq() && ys.consq()) {
			if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
				zs = FnListSUtil.cons(xs.hd(), zs);
				xs = xs.tl();
			} else {
				zs = FnListSUtil.cons(ys.hd(), zs);
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
		FnList<T> result = FnListSUtil.nil();
		while (xs.consq()) {
			result = FnListSUtil.cons(xs.hd(), result);
			xs = xs.tl();
		}
		return result;
	}

	private static <T> FnList<T> rappend(FnList<T> xs, FnList<T> ys) {
		while (xs.consq()) {
			ys = FnListSUtil.cons(xs.hd(), ys);
			xs = xs.tl();
		}
		return ys;
	}

	static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
		// HX-2025-12-15:
		// Your implementation must contain the following steps:
		// 1. Call pg2701_word$strmize() to get a stream of words
		// 2. Use the RBST from Quiz02_06, modified into a generic associative map
		// 3. Turn the RBST into a list WNS of word-count pairs
		// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS
		// 5. The sorted WNS is the return value

		// Step 1: Get stream of words
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

		// Step 2: Use RBST (modified from Quiz02_06) to count occurrences
		RBSTMap<FnList<Character>> wordMap = new RBSTMap<>(Final_04::compareWords);

		// Process each word in the stream
		LnStcn<FnList<Character>> cons = wordStream.eval0();
		while (cons.consq()) {
			FnList<Character> word = cons.hd();
			wordMap.insertOrIncrement(word); // Increment count directly
			cons = cons.tl().eval0();
		}

		// Step 3: Convert RBST to list of word-count pairs
		FnList<FnTupl2<FnList<Character>, Integer>> WNS = wordMap.toList();

		// Step 4: Sort using mergesort from Assign05_01 with the specified order
		WNS = mergeSort(WNS, Final_04::compareWordCountPairs);

		// Step 5: Return sorted WNS
		return WNS;
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
	private static int compareWordCountPairs(
			FnTupl2<FnList<Character>, Integer> p1,
			FnTupl2<FnList<Character>, Integer> p2) {
		int n1 = p1.sub1;
		int n2 = p2.sub1;

		// First compare by count (descending - higher counts first)
		if (n1 > n2)
			return -1;
		if (n1 < n2)
			return 1;

		// If counts equal, compare by word (ascending - lexicographic order)
		return compareWords(p1.sub0, p2.sub0);
	}

	public static void main(String[] args) {
		System.out.println("Starting word count with RBST...");

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize4();

		System.out.println("Printing first 100 word-count pairs:");

		int count = 0;
		FnList<FnTupl2<FnList<Character>, Integer>> current = wordCounts;

		while (count < 100 && current.consq()) {
			FnTupl2<FnList<Character>, Integer> pair = current.hd();
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
		while (word.consq()) {
			System.out.print(word.hd());
			word = word.tl();
		}
	}
}