package Sort;

import LnStrm.*;
import FnTuple.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class ArrayQuickSort {

    public static <T> void arrayQuickSort(T[] A, ToIntBiFunction<T, T> cmp) {
        if (A == null || A.length <= 1) {
            return;
        }
        quickSortHelper(A, 0, A.length - 1, cmp);
    }

    private static <T> void quickSortHelper(T[] A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        if (lo >= hi) {
            return;
        }

        // 3-way partition to handle duplicates efficiently
        int[] bounds = partition3Way(A, lo, hi, cmp);
        int lt = bounds[0]; // Everything < pivot is in [lo, lt-1]
        int gt = bounds[1]; // Everything > pivot is in [gt+1, hi]
        // Everything == pivot is in [lt, gt]

        quickSortHelper(A, lo, lt - 1, cmp);
        quickSortHelper(A, gt + 1, hi, cmp);
    }

    // 3-way partitioning (Dijkstra's Dutch National Flag algorithm)
    // Returns [lt, gt] where:
    // A[lo..lt-1] < pivot
    // A[lt..gt] == pivot
    // A[gt+1..hi] > pivot
    private static <T> int[] partition3Way(T[] A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        // Choose pivot (median-of-three for better performance)
        int pivotIdx = medianOfThree(A, lo, hi, cmp);
        T pivot = A[pivotIdx];

        // Move pivot to start
        swap(A, lo, pivotIdx);

        int lt = lo; // Next position for elements < pivot
        int i = lo + 1; // Current element being examined
        int gt = hi; // Next position for elements > pivot

        while (i <= gt) {
            int cmpResult = cmp.applyAsInt(A[i], pivot);

            if (cmpResult < 0) {
                // A[i] < pivot: swap with lt and move both forward
                swap(A, lt, i);
                lt++;
                i++;
            } else if (cmpResult > 0) {
                // A[i] > pivot: swap with gt and move gt back
                swap(A, i, gt);
                gt--;
                // Don't increment i (need to examine swapped element)
            } else {
                // A[i] == pivot: just move forward
                i++;
            }
        }

        return new int[] { lt, gt };
    }

    // Median-of-three pivot selection to avoid worst-case on sorted arrays
    private static <T> int medianOfThree(T[] A, int lo, int hi, ToIntBiFunction<T, T> cmp) {
        int mid = lo + (hi - lo) / 2;

        // Sort lo, mid, hi
        if (cmp.applyAsInt(A[mid], A[lo]) < 0) {
            swap(A, lo, mid);
        }
        if (cmp.applyAsInt(A[hi], A[lo]) < 0) {
            swap(A, lo, hi);
        }
        if (cmp.applyAsInt(A[hi], A[mid]) < 0) {
            swap(A, mid, hi);
        }

        return mid;
    }

    private static <T> void swap(T[] A, int i, int j) {
        T temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private static <T> boolean isSorted(T[] arr, ToIntBiFunction<T, T> cmp) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (cmp.applyAsInt(arr[i], arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    private static <T> void printArray(T[] arr, String label) {
        System.out.print(label + ": [");
        int limit = Math.min(arr.length, 15);
        for (int i = 0; i < limit; i++) {
            System.out.print(arr[i]);
            if (i < limit - 1) {
                System.out.print(", ");
            }
        }
        if (arr.length > limit) {
            System.out.print("... (" + arr.length + " total)");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        ToIntBiFunction<Integer, Integer> intCmp = (a, b) -> a - b;

        // Test 1: Basic small array

        System.out.println("Test 1: Basic small array");
        Integer[] arr1 = { 5, 2, 8, 1, 9, 3, 7, 4, 6 };
        printArray(arr1, "Before");
        arrayQuickSort(arr1, intCmp);
        printArray(arr1, "After ");
        boolean pass1 = isSorted(arr1, intCmp);
        System.out.println("Result: " + (pass1 ? "PASS ✓" : "FAIL ✗"));

        System.out.println();

    }

} // end of [public class Assign06_03{...}]