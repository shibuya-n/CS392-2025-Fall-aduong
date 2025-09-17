import java.util.Arrays;

public class Assign02_02 {
    /*
     * HX-2025-02-13: 10 points
     * Recursion is a fundamental concept in programming.
     * However, the support for recursion in Java is very limited.
     * Nontheless, we will be making extensive use of recursion in
     * this class.
     */

    /*
     * // This is a so-called iterative implementation:
     * public static <T extends Comparable<T> > int indexOf(T[] a, T key) {
     * int lo = 0;
     * int hi = a.length - 1;
     * while (lo <= hi) {
     * // Key is in a[lo..hi] or not present.
     * final int mid = lo + (hi - lo) / 2;
     * final int sign = key.compareTo(a[mid]);
     * if (sign < 0) hi = mid - 1;
     * else if (sign > 0) lo = mid + 1;
     * else return mid;
     * }
     * return -1;
     * }
     */
    public static <T extends Comparable<T>> int indexOf(T[] a, T key) {
        return indexOf(a, key, 0, a.length - 1);
    }

    public static <T extends Comparable<T>> int indexOf(T[] a, T key, int lo, int hi) {
        // Please give a recursive implementation of 'indexOf' that is
        // equivalent to the above one

        if (lo > hi) {
            return -1;
        }
        int mid = lo + (hi - lo) / 2;
        int sign = key.compareTo(a[mid]);
        if (sign < 0) {

            return indexOf(a, key, lo, mid - 1);
        } else if (sign > 0) {

            return indexOf(a, key, mid + 1, hi);
        } else {
            return mid;
        }

    }

    public static void main(String[] argv) {
        // Please write some testing code for your implementation of 'indexOf'

        Integer[] temp = { 1, 2, 3, 4, 5, 6, 7 };
        Integer key = 6;

        System.out.println(Assign02_02.indexOf(temp, key));

    }
}
