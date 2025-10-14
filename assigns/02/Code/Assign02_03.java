import java.util.Arrays;

public class Assign02_03 {
	public static boolean solve_3sum(Integer[] A) {
		// Please give a soft qudratic time implementation
		// that solves the 3-sum problem. The function call
		// solve_3sum(A) returns true if and only if there exist
		// indices i, j, and k satisfying A[i]+A[j] = A[k].
		// Why is your implementation soft O(n^2)?
		Arrays.sort(A); // sorting adds a log factor to O(n^2), making it soft 0(n^2)

		for (int i = 0; i < A.length; i++) {
			for (int j = i + 1; j < A.length - 1; j++) {
				int sum = A[i] + A[j];

				return binarySearch(A, sum);

			}
		}

		return false;
	}

	private static boolean binarySearch(Integer[] A, int target) {
		int left = 0;
		int right = A.length - 1;

		while (left <= right) {
			int mid = (left + right) / 2;
			if (mid == target) {
				return true;
			} else if (mid < target) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return false;
	}

	public static void main(String[] argv) {
		// Please write some code here for testing solve_3sum

		Integer[] A = { 2, 4, 1, 6, 5 };

		System.out.println(solve_3sum(A));
	}
}
