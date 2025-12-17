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
import Sort.MergeSort;

import java.util.Random;
import java.util.function.ToIntBiFunction;

public class Final_04 {

	// Generic RBST-based associative map
	static class RBSTMap<K, V> {
		Node root = null;
		Random rand = new Random();
		ToIntBiFunction<K, K> comparator;

		class Node {
			K key;
			FnList<V> values; // List of values for LIFO behavior
			int size;
			Node lchild;
			Node rchild;

			Node(K key, V value) {
				this.key = key;
				this.values = new FnList<>(value, new FnList<>());
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

		// Randomized insertion at root with probability 1/(n.size+1)
		public void insert(K key, V value) {
			root = insertAtRoot(root, key, value);
		}

		private Node insertAtRoot(Node n, K key, V value) {
			if (n == null) {
				return new Node(key, value);
			}

			int cmp = comparator.applyAsInt(key, n.key);

			if (cmp == 0) {
				// Key exists: prepend value (LIFO)
				n.values = new FnList<>(value, n.values);
				return n;
			}

			// Randomized: insert at root with probability 1/(n.size+1)
			if (rand.nextInt(n.size + 1) == 0) {
				return insertAtRootHelper(n, key, value);
			}

			// Otherwise insert recursively
			if (cmp < 0) {
				n.lchild = insertAtRoot(n.lchild, key, value);
			} else {
				n.rchild = insertAtRoot(n.rchild, key, value);
			}

			updateSize(n);
			return n;
		}

		private Node insertAtRootHelper(Node n, K key, V value) {
			int cmp = comparator.applyAsInt(key, n.key);

			if (cmp < 0) {
				n.lchild = insertAtRoot(n.lchild, key, value);
				updateSize(n);
				return rotateRight(n);
			} else {
				n.rchild = insertAtRoot(n.rchild, key, value);
				updateSize(n);
				return rotateLeft(n);
			}
		}

		// Convert tree to list of key-count pairs using in-order traversal
		public FnList<FnTupl2<K, Integer>> toList() {
			return inorderTraversal(root, new FnList<>());
		}

		private FnList<FnTupl2<K, Integer>> inorderTraversal(Node n, FnList<FnTupl2<K, Integer>> acc) {
			if (n == null)
				return acc;

			// Right subtree
			acc = inorderTraversal(n.rchild, acc);

			// Current node
			int count = n.values.length();
			acc = new FnList<>(new FnTupl2<>(n.key, count), acc);

			// Left subtree
			acc = inorderTraversal(n.lchild, acc);

			return acc;
		}
	}

	static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
		// Step 1: Get stream of words
		LnStrm<FnList<Character>> wordStream = Final_01.pg2701_word$strmize();

		// Step 2: Use RBST to count occurrences
		RBSTMap<FnList<Character>, Object> wordMap = new RBSTMap<>(Final_04::compareWords);

		// Process each word
		LnStcn<FnList<Character>> cons = wordStream.eval0();
		while (cons.consq()) {
			FnList<Character> word = cons.hd();
			wordMap.insert(word, null); // Use null as dummy value
			cons = cons.tl().eval0();
		}

		// Step 3: Convert RBST to list of word-count pairs
		FnList<FnTupl2<FnList<Character>, Integer>> WNS = wordMap.toList();

		// Step 4: Sort using mergesort from Sort library
		WNS = MergeSort.mergeSort(WNS, Final_04::compareWordCountPairs);

		// Step 5: Return sorted list
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

		// First compare by count (descending)
		if (n1 > n2)
			return -1;
		if (n1 < n2)
			return 1;

		// If counts equal, compare by word (ascending)
		return compareWords(p1.sub0, p2.sub0);
	}

	public static void main(String[] args) {
		System.out.println("Starting word count...");

		FnList<FnTupl2<FnList<Character>, Integer>> wordCounts = pg2701_word$count$listize4();

		System.out.println("Printing first 100 word-count pairs:");

		int count = 0;
		FnList<FnTupl2<FnList<Character>, Integer>> current = wordCounts;

		while (count < 100 && current.consq()) {
			FnTupl2<FnList<Character>, Integer> pair = current.hd();
			printWord(pair.sub0);
			System.out.print(" ");
			System.out.println(pair.sub1);
			current = current.tl();
			count++;
		}

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