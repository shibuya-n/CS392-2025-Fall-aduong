package MyLibrary.MergeSort;

public class MergeSort {

    public static <T extends Comparable<T>> void mergeSort(T[] array, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(array, l, m);
            mergeSort(array, m + 1, r);

            merge(array, l, m, r);
        }
    }

    public static <T extends Comparable<T>> void merge(T[] array, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        T[] L = (T[]) new Comparable[n1];
        T[] R = (T[]) new Comparable[n2];

        for (int i = 0; i < n1; i++)
            L[i] = array[l + i];

        for (int j = 0; j < n2; j++)
            R[j] = array[m + 1 + j];

        int i = 0, j = 0, k = l;

        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }
}
