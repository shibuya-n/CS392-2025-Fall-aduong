//
// HX: 30 points
// This one may seem easy but can be time-consuming
// if you use a brute-force approach.
//

public class Quiz01_03 {

	public static <T extends Comparable<T>> Comparable[] sort10WithNoRecursion(
			T x0, T x1, T x2, T x3, T x4,
			T x5, T x6, T x7, T x8, T x9) { // i get a runtime error because i can't cast comparable to integer

		Comparable[] a = { x0, x1, x2, x3, x4, x5, x6, x7, x8, x9 };

		// HX-2025-10-12:
		// Given 10 arguments,
		// please return an array of size 10 containing the
		// 10 arguments sorted according to the order implemented by
		// compareTo on T.
		// HX: No arrays, lists, etc.
		// HX: No recursion is allowed for this one
		// HX: No loops (either while-loop or for-loop) is allowed.
		// HX: Yes, you can use functions (but not recursive functions)
		// HX: Please do not try to write a HUGH if-then-else mumble jumble!

		swap(a, 0, 1);
		swap(a, 2, 3);
		swap(a, 4, 5);
		swap(a, 6, 7);
		swap(a, 8, 9);
		swap(a, 0, 2);
		swap(a, 1, 3);
		swap(a, 4, 6);
		swap(a, 5, 7);
		swap(a, 0, 4);
		swap(a, 1, 5);
		swap(a, 2, 6);
		swap(a, 3, 7);
		swap(a, 0, 8);
		swap(a, 1, 9);
		swap(a, 2, 8);
		swap(a, 3, 9);
		swap(a, 4, 8);
		swap(a, 5, 9);
		swap(a, 1, 2);
		swap(a, 3, 4);
		swap(a, 5, 6);
		swap(a, 7, 8);
		swap(a, 0, 1);
		swap(a, 2, 3);
		swap(a, 4, 5);
		swap(a, 6, 7);
		swap(a, 8, 9);
		swap(a, 1, 2);
		swap(a, 3, 4);
		swap(a, 5, 6);
		swap(a, 7, 8);

		return a;
	}

	private static void swap(Comparable[] a, int i, int j) {
		if (a[i].compareTo(a[j]) > 0) {
			Comparable temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}

	public static void main(String[] args) {
		Comparable[] sorted = Quiz01_03.sort10WithNoRecursion(
				42, 5, 19, 99, 13, 7, 88, 25, 1, 50);

		for (Comparable x : sorted)
			System.out.print(x + " ");
	}
}
