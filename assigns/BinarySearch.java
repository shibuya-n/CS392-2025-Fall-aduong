import java.util.Arrays;

public class BinarySearch {
    public BinarySearch() {

    }

    public int indexOf(int[] a, int key) {
        int min = 0;
        int max = a.length - 1;

        while (min <= max) {
            // key is in a[min..max] or not present

            final int mid = min + (max - min) / 2;

            if (key < a[mid]) {
                max = mid - 1;
            } else if (key > a[mid]) {
                min = mid + 1;

            } else {
                return mid;
            }
        }
    }
}