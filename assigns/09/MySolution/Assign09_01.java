import java.util.function.BiConsumer;
import java.util.function.Consumer;

import MyPQueue.MyPQueueBase;
import MyPQueue.MyPQueueEmptyExn;
import MyPQueue.MyPQueueFullExn;

public class Assign09_01<T extends Comparable<T>> extends MyPQueueBase<T> {
    // HX: There is NO Assign09_01

    private final Object[] heap; // underlying array
    private int size; // current number of elements

    // ----- constructors -----

    /**
     * Create a priority queue with the given capacity.
     */
    public Assign09_01(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.heap = new Object[capacity];
        this.size = 0;
    }

    // ----- basic info -----

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size >= heap.length;
    }

    // ----- helpers -----

    @SuppressWarnings("unchecked")
    private T elem(int i) {
        return (T) heap[i];
    }

    private void swap(int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    /**
     * Bubble an element up from index i to restore the heap property.
     */
    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2; // parent index
            if (elem(i).compareTo(elem(p)) >= 0) {
                break; // heap OK
            }
            swap(i, p);
            i = p;
        }
    }

    /**
     * Push an element down from index i to restore the heap property.
     */
    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && elem(left).compareTo(elem(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && elem(right).compareTo(elem(smallest)) < 0) {
                smallest = right;
            }
            if (smallest == i) {
                break; // already a heap
            }
            swap(i, smallest);
            i = smallest;
        }
    }

    // ----- core priority queue operations -----

    /**
     * Return the minimum element without removing it.
     * Precondition: !isEmpty()
     */
    @Override
    public T top$raw() {
        if (isEmpty()) {
            throw new MyPQueueEmptyExn();
        }
        return elem(0);
    }

    /**
     * Remove and return the minimum element.
     * Precondition: !isEmpty()
     */
    @Override
    public T deque$raw() {
        if (isEmpty()) {
            throw new MyPQueueEmptyExn();
        }
        T result = elem(0);
        size--;
        if (size > 0) {
            heap[0] = heap[size]; // move last element to root
            heap[size] = null; // avoid loitering
            siftDown(0); // restore heap property
        } else {
            heap[0] = null; // queue became empty
        }
        return result;
    }

    /**
     * Insert a new element.
     * Precondition: !isFull()
     */
    @Override
    public void enque$raw(T itm) {
        if (isFull()) {
            throw new MyPQueueFullExn();
        }
        heap[size] = itm;
        siftUp(size);
        size++;
    }

    // ----- optional visualization helpers (use the imports) -----

    /**
     * Traverse the heap in array (level-order) order and apply f to each item.
     * This corresponds to a level-order traversal of the implicit tree.
     */
    public void foreachLevel(Consumer<T> f) {
        for (int i = 0; i < size; i++) {
            f.accept(elem(i));
        }
    }

    /**
     * Apply g to each parent-child edge in the heap tree.
     * That is, for each edge parent -> child, call g.accept(parent, child).
     */
    public void foreachEdge(BiConsumer<T, T> g) {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < size) {
                g.accept(elem(i), elem(left));
            }
            if (right < size) {
                g.accept(elem(i), elem(right));
            }
        }
    }

    public static void main(String[] args) {
        Assign09_01<Integer> pq = new Assign09_01<>(10);

        int[] values = { 5, 1, 7, 3, 9, 2 };
        for (int v : values) {
            pq.enque$raw(v);
        }

        while (!pq.isEmpty()) {
            System.out.println(pq.deque$raw());
        }
    }
}
