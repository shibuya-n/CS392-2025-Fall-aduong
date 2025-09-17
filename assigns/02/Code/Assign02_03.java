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
				if (A[i] + A[j] == A[j + 1]) {
					return true;
				}

			}
		}

		return false;
	}

	public static void main(String[] argv) {
		// Please write some code here for testing solve_3sum

		Integer[] nums = { -1, 0, 1, 2, -1, 3 };

		System.out.println(solve_3sum(nums));
	}
}
