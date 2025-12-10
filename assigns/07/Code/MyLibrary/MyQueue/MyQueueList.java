package MyLibrary.MyQueue;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyQueueList<T> extends MyQueueBase<T> {

    int nitm = -1;
    Node first = null;
    Node last = null;

    private class Node {
        private T item;
        private Node next;

        private Node(T itm, Node nxt) {
            item = itm;
            next = nxt;
        }

    }

    public MyQueueList() {
        nitm = 0;
        first = null;
        last = null;
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return false;
    }

    public T top$raw() {
        return first.item;
    }

    public T deque$raw() {
        T itm = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        nitm -= 1;
        return itm;
    }

    public void enque$raw(T itm) {
        if (last == null) {
            last = new Node(itm, null);
        } else {
            last.next = new Node(itm, null);
            last = last.next;
        }
        nitm += 1;
    }

    public void foritm(Consumer<? super T> action) {
        Node xs = first;
        while (xs != null) {
            action.accept(xs.item);
            xs = xs.next;
        }
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        int i = 0;
        Node xs = first;
        while (xs != null) {
            action.accept(i, xs.item);
            i += 1;
            xs = xs.next;
        }
    }
}
