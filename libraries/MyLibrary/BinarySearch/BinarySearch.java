package MyLibrary.BinarySearch;

public class BinarySearch<T extends Comparable<T>> {
    public BinarySearch() {

    }

    // arrays only
    public int indexOf(T[] A, T key) {
        int low = 0;
        int high = A.length - 1;

        while (low < high) {

            final int mid = low + (high - low) / 2;
            final int sign = key.compareTo(A[mid]);

            if (sign < 0) {
                high = mid - 1;
            } else if (sign > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

}
