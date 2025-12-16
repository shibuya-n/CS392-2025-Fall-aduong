package MyPQueue;

import java.util.Comparator;

/**
 * Array-backed priority queue implemented as a binary min-heap.
 *
 * Smaller elements (by comparator or natural ordering) have higher priority.
 */
public class MyPQueueArray<T> extends MyPQueueBase<T> {

    /** Heap storage (valid elements are in indices [0, size)) */
    private final Object[] heap;

    /** Number of elements currently in the queue */
    private int size;

    /** Comparator used to order elements (null = natural ordering) */
    private final Comparator<? super T> comparator;

    /** Construct a priority queue with given capacity and natural ordering */
    public MyPQueueArray(int capacity) {
        this(capacity, null);
    }

    /** Construct a priority queue with given capacity and comparator */
    public MyPQueueArray(int capacity, Comparator<? super T> comparator) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity must be non-negative");
        }
        this.heap = new Object[capacity];
        this.size = 0;
        this.comparator = comparator;
    }

    /** Returns the number of elements in the queue */
    @Override
    public int size() {
        return size;
    }

    /** Returns true if the queue is full */
    @Override
    public boolean isFull() {
        return size == heap.length;
    }

    /** Returns the highest-priority element without removing it */
    @Override
    @SuppressWarnings("unchecked")
    public T top$raw() {
        return (T) heap[0];
    }

    /** Removes and returns the highest-priority element */
    @Override
    @SuppressWarnings("unchecked")
    public T deque$raw() {
        T result = (T) heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return result;
    }

    /** Inserts an element into the priority queue */
    @Override
    public void enque$raw(T item) {
        heap[size] = item;
        siftUp(size);
        size++;
    }

    // --------------------------------------------------
    // Heap helpers
    // --------------------------------------------------

    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        }
        return ((Comparable<? super T>) a).compareTo(b);
    }

    /** Restores heap order by moving element upward */
    @SuppressWarnings("unchecked")
    private void siftUp(int index) {
        int child = index;
        while (child > 0) {
            int parent = (child - 1) / 2;
            T childVal = (T) heap[child];
            T parentVal = (T) heap[parent];

            if (compare(childVal, parentVal) < 0) {
                heap[child] = parentVal;
                heap[parent] = childVal;
                child = parent;
            } else {
                break;
            }
        }
    }

    /** Restores heap order by moving element downward */
    @SuppressWarnings("unchecked")
    private void siftDown(int index) {
        int parent = index;

        while (true) {
            int left = 2 * parent + 1;
            int right = left + 1;
            if (left >= size) {
                return;
            }

            int smallest = left;
            if (right < size &&
                    compare((T) heap[right], (T) heap[left]) < 0) {
                smallest = right;
            }

            if (compare((T) heap[smallest], (T) heap[parent]) < 0) {
                Object tmp = heap[parent];
                heap[parent] = heap[smallest];
                heap[smallest] = tmp;
                parent = smallest;
            } else {
                return;
            }
        }
    }
}
