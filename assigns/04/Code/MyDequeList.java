
import java.util.function.Consumer;

import java.util.function.BiConsumer;

public class MyDequeList<T> implements MyDeque<T> {

    int nitm = -1;
    Node frst = null;
    Node last = null;

    private class Node {
        private T item;
        private Node next;

        private Node prev;

        private Node(T itm) {
            item = itm;
            next = null;
            prev = null;
        }
    }

    public MyDequeList() {
        nitm = 0;
        frst = null;
        last = null;
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return (nitm == 0);
    }

    public T fpeek$raw() {
        return frst.item;
    }

    public T fpeek$opt() {

        if (frst == null) {
            return null;
        } else {
            return frst.item;
        }

    }

    public T fpeek$exn() throws MyDequeEmptyExn {
        T toReturn = fpeek$opt();
        if (toReturn == null) {
            throw new MyDequeEmptyExn();
        } else {
            return toReturn;
        }

    }

    public T rpeek$raw() {
        return last.item;
    }

    public T rpeek$opt() {
        if (last == null) {
            return null;
        } else {
            return rpeek$raw();
        }
    }

    public T rpeek$exn() throws MyDequeEmptyExn {
        T toReturn = rpeek$opt();

        if (toReturn == null) {
            throw new MyDequeEmptyExn();
        }

        else {
            return toReturn;
        }
    }

    public void fenque$raw(T itm) {
        Node toInsert = new Node(itm);
        if (frst == null) {
            frst = toInsert;
            last = toInsert;
        } else {
            // link new noe to the front
            toInsert.next = frst;
            frst.prev = toInsert;

            // make the first node the new node
            frst = toInsert;
        }

        nitm++;
    }

    public boolean fenque$opt(T itm) {
        if (!isFull()) {
            fenque$raw(itm);
            return true;
        } else {
            return false;
        }
    }

    public void fenque$exn(T itm) throws MyQueueFullExn {
        if (!fenque$opt(itm)) {
            throw new MyQueueFullExn();
        }
    }

    public void renque$raw(T itm) {
        Node toInsert = new Node(itm);
        if (last == null) {
            last = toInsert;
            frst = toInsert;
        } else {
            toInsert.prev = last;
            last.next = toInsert;

            last = toInsert;
        }

        nitm++;
    }

    public boolean renque$opt(T itm) {
        if (!isFull()) {
            renque$raw(itm);
            return true;
        } else {
            return false;
        }
    }

    public void renque$exn(T itm) throws MyDequeFullExn {
        if (!renque$opt(itm)) {
            throw new MyDequeFullExn();
        }
    }

    public T fdeque$raw() {
        if (frst == null) {
            return null;
        } else {
            Node toReturn = frst;
            frst = frst.next;
            if (frst == null) {
                last = null;
            } else {
                frst.prev = null; // get rid of connection to old first node
            }
            nitm--;
            return toReturn.item;

        }
    }

    public T fdeque$opt() {
        return fdeque$raw();
    }

    public T fdeque$exn() throws MyDequeEmptyExn {
        T toReturn = fdeque$opt();
        if (toReturn == null) {
            throw new MyDequeEmptyExn();
        } else {
            return toReturn;
        }
    }

    public T rdeque$raw() {
        if (last == null) {
            return null;
        } else {
            Node toReturn = last;
            last = last.prev;

            if (last == null) {
                frst = null;
            } else {
                last.next = null;
            }

            nitm--;
            return toReturn.item;

        }
    }

    public T rdeque$opt() {
        return rdeque$raw();
    }

    public T rdeque$exn() throws MyQueueEmptyExn {
        T toReturn = rdeque$opt();
        if (toReturn == null) {
            throw new MyQueueEmptyExn();
        } else {
            return toReturn;
        }
    }

    public void foritm(Consumer<? super T> action) {
        Node curr = frst;
        while (curr != null) {
            action.accept(curr.item);

            curr = frst.next;

        }
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        int i = 0;
        Node curr = frst;
        while (curr != null) {
            action.accept(i, curr.item);

            curr = curr.next;
            i++;
        }
    }

    public void rforitm(Consumer<? super T> action) {
        Node curr = last;
        while (curr != null) {
            action.accept(curr.item);
            curr = curr.prev;
        }
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
        int i = 0;
        Node curr = last;
        while (curr != null) {
            action.accept(i, curr.item);
            curr = curr.prev;
            i++;

        }
    }

    public void System$out$print() {
        System.out.print("My Deque: (");
        this.iforitm(
                (i, x) -> {

                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(x);

                });
        System.out.print(")");
    }

}
