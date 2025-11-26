public class Quiz02_02 {
	public static <T extends Comparable<T>> void sort1000WithNoRecursion(T[] A) {
		// Use many passes to sort - each mega pass does multiple bubble passes
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
		megaPass(A);
	}

	private static <T extends Comparable<T>> void megaPass(T[] A) {
		// 100 bubble passes
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
		tenPasses(A);
	}

	private static <T extends Comparable<T>> void tenPasses(T[] A) {
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
		bubblePass(A);
	}

	private static <T extends Comparable<T>> void bubblePass(T[] A) {
		// One complete pass - swap all adjacent elements across entire array
		swapRange(A, 0, 100);
		swapRange(A, 100, 200);
		swapRange(A, 200, 300);
		swapRange(A, 300, 400);
		swapRange(A, 400, 500);
		swapRange(A, 500, 600);
		swapRange(A, 600, 700);
		swapRange(A, 700, 800);
		swapRange(A, 800, 900);
		swapRange(A, 900, 1000);
	}

	private static <T extends Comparable<T>> void swapRange(T[] A, int start, int end) {
		// Swap adjacent elements in range [start, end)
		swapChunk10(A, start);
		swapChunk10(A, start + 10);
		swapChunk10(A, start + 20);
		swapChunk10(A, start + 30);
		swapChunk10(A, start + 40);
		swapChunk10(A, start + 50);
		swapChunk10(A, start + 60);
		swapChunk10(A, start + 70);
		swapChunk10(A, start + 80);
		swapChunk10(A, start + 90);
	}

	private static <T extends Comparable<T>> void swapChunk10(T[] A, int start) {
		// Swap 10 consecutive adjacent pairs
		swap(A, start + 0, start + 1);
		swap(A, start + 1, start + 2);
		swap(A, start + 2, start + 3);
		swap(A, start + 3, start + 4);
		swap(A, start + 4, start + 5);
		swap(A, start + 5, start + 6);
		swap(A, start + 6, start + 7);
		swap(A, start + 7, start + 8);
		swap(A, start + 8, start + 9);
		swap(A, start + 9, start + 10);
	}

	private static <T extends Comparable<T>> void swap(T[] A, int i, int j) {
		if (j < A.length && A[i].compareTo(A[j]) > 0) {
			T temp = A[i];
			A[i] = A[j];
			A[j] = temp;
		}
	}

	public static <T extends Comparable<T>> Comparable[] sort10WithNoRecursion(
			T x0, T x1, T x2, T x3, T x4,
			T x5, T x6, T x7, T x8, T x9) {

		Comparable[] a = { x0, x1, x2, x3, x4, x5, x6, x7, x8, x9 };

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

	public static void main(String[] args) {
		// Test sort1000WithNoRecursion
		Integer[] test = new Integer[1000];
		for (int i = 0; i < 1000; i++) {
			test[i] = 1000 - i;
		}

		System.out.println("Before sorting: " + test[0] + " ... " + test[999]);
		sort1000WithNoRecursion(test);
		System.out.println("After sorting: " + test[0] + " ... " + test[999]);

		boolean sorted = true;
		for (int i = 0; i < 999; i++) {
			if (test[i] > test[i + 1]) {
				sorted = false;
				System.out.println("Error at index " + i + ": " + test[i] + " > " + test[i + 1]);
				break;
			}
		}

		System.out.println("Array sorted correctly: " + sorted);
	}
}