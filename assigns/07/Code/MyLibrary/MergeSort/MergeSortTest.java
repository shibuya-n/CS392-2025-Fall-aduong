package MyLibrary.MergeSort;

public class MergeSortTest {

    public static void main(String[] args) {
        Integer[] test = new Integer[10];

        // Generate random integers
        for (int i = 0; i < test.length; i++) {
            int temp = (int) (Math.random() * 100) + 1;
            test[i] = temp;
            System.out.print(temp + " ");
        }
        System.out.println();

        // Call merge sort directly
        MergeSort.mergeSort(test, 0, test.length - 1);

        // Print sorted array
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i] + " ");
        }
    }
}
