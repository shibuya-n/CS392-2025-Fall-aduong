package MyLibrary.MyQueue;

import Library.FnList.*;
import Library.MyRefer.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public abstract class MyQueueBase<T> implements MyQueue<T> {
    //
    public boolean isEmpty() {
        return (size() <= 0);
    }

    //
    public T top$opt() {
        return (isEmpty() ? null : top$raw());
    }

    public T top$exn() throws MyQueueEmptyExn {
        T top = top$opt();
        if (top != null)
            return top;
        else
            throw new MyQueueEmptyExn();
    }

    //
    public T deque$opt() {
        return (isEmpty() ? null : deque$raw());
    }

    public T deque$exn() throws MyQueueEmptyExn {
        T deque = deque$opt();
        if (deque != null)
            return deque;
        else
            throw new MyQueueEmptyExn();
    }

    //
    public boolean enque$opt(T itm) {
        if (isFull()) {
            return false;
        } else {
            enque$raw(itm);
            return true;
        }
    }

    public void enque$exn(T itm) throws MyQueueFullExn {
        boolean res = enque$opt(itm);
        if (!res)
            throw new MyQueueFullExn();
        else
            return;
    }

    //
    public void System$out$print() {
        System.out.print("MyQueue(");
        this.iforitm(
                (i, itm) -> {
                    if (i > 0) {
                        System.out.print(",");
                    }
                    System.out.print(itm.toString());
                });
        System.out.print(")");
    }

    //
    public void rforitm(Consumer<? super T> action) {
        final FnList<T> itms[] = (FnList<T>[]) new Object[1];
        itms[0] = new FnList<T>();
        foritm(itm -> itms[0] = new FnList(itm, itms[0]));
        (itms[0]).foritm(action);
    }

    //
    public void irforitm(BiConsumer<Integer, ? super T> action) {
        final FnList<T> itms[] = (FnList<T>[]) new Object[1];
        itms[0] = new FnList<T>();
        foritm(itm -> itms[0] = new FnList(itm, itms[0]));
        (itms[0]).iforitm(action);
    }
    //
} // end of [public abstract class MyQueueBase<T>{...}]
