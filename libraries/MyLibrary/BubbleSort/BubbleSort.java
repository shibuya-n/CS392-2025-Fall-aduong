package MyLibrary.BubbleSort;

public class BubbleSort<T extends Comparable<T>> {

    public static <T extends Comparable<T>> void sort_generics(T[] a) {

        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j].compareTo(a[j + 1]) > 0) {

                }
            }
        }
    }

}
