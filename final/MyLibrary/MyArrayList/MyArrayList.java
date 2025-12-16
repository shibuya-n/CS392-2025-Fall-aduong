package MyArrayList;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Function;

/**
 * Simple dynamic array (ArrayList-like) implementation
 */
public class MyArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] data;
    private int size;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        data = new Object[initialCapacity];
        size = 0;
    }

    // Basic accessors
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int capacity() {
        return data.length;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value;
    }

    // Adding elements
    public void add(T value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = value;
        size++;
    }

    public void addAll(MyArrayList<? extends T> other) {
        ensureCapacity(size + other.size);
        for (int i = 0; i < other.size; i++) {
            data[size++] = other.data[i];
        }
    }

    // Removing elements
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T oldValue = (T) data[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null; // Clear reference

        return oldValue;
    }

    public boolean remove(T value) {
        int index = indexOf(value);
        if (index >= 0) {
            remove(index);
            return true;
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    // Search
    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == null ? value == null : data[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(T value) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i] == null ? value == null : data[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    // Iteration
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            action.accept(element);
        }
    }

    public void forEachIndexed(BiConsumer<Integer, ? super T> action) {
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            action.accept(i, element);
        }
    }

    // Filtering
    public boolean all(Predicate<? super T> predicate) {
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            if (!predicate.test(element)) {
                return false;
            }
        }
        return true;
    }

    public boolean any(Predicate<? super T> predicate) {
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            if (predicate.test(element)) {
                return true;
            }
        }
        return false;
    }

    public MyArrayList<T> filter(Predicate<? super T> predicate) {
        MyArrayList<T> result = new MyArrayList<>();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            if (predicate.test(element)) {
                result.add(element);
            }
        }
        return result;
    }

    // Transformation
    public <R> MyArrayList<R> map(Function<? super T, ? extends R> mapper) {
        MyArrayList<R> result = new MyArrayList<>(size);
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            result.add(mapper.apply(element));
        }
        return result;
    }

    // Sublist
    public MyArrayList<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("Invalid range");
        }
        MyArrayList<T> result = new MyArrayList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            @SuppressWarnings("unchecked")
            T element = (T) data[i];
            result.add(element);
        }
        return result;
    }

    // Capacity management
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * 2, minCapacity);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    public void trimToSize() {
        if (size < data.length) {
            Object[] newData = new Object[size];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    // Utility
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        System.arraycopy(data, 0, result, 0, size);
        return result;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    @Override
    public String toString() {
        if (size == 0)
            return "[]";

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}