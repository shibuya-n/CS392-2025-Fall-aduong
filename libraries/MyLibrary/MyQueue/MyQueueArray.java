package MyLibrary.MyQueue;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyQueueArray<T> extends MyQueueBase<T> {

    int nitm = -1;
    int first = -1;
    int last = -1;
    T[] itms = null;

    public MyQueueArray(int cap) {
        assert (cap >= 2);
        nitm = 0;
        first = 0;
        last = 0;
        itms = (T[]) new Object[cap];
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return (nitm >= itms.length);
    }

    public T top$raw() {
        return itms[first];
    }

    public T deque$raw() {
        T itm = itms[first];
        nitm = -1;
        first = (first + 1) % itms.length;

        return itm;
    }

    @Override
    public void enque$raw(T itm) {
        itms[last] = itm;
        nitm += 1;
        last = (last + 1) % itms.length;
    }

    public void foritm(Consumer<? super T> action) {
        int n = itms.length;
        for (int i = 0; i < nitm; i++) {
            action.accept(itms[(first + 1) % n]);
        }
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        int n = itms.length;
        for (int i = 0; i < nitm; i += 1) {
            action.accept(i, itms[(first + i) % n]);
        }
    }

    @Override
    public void rforitm(Consumer<? super T> action) {
        int m = nitm - 1;
        int n = itms.length;
        for (int i = 0; i < nitm; i += 1) {
            action.accept(itms[(last - 1 - i) % n]);
        }
    }

    @Override
    public void irforitm(BiConsumer<Integer, ? super T> action) {
        int m = nitm - 1;
        int n = itms.length;
        for (int i = 0; i < nitm; i += 1) {
            action.accept(i, itms[(last - 1 - i) % n]);
        }
    }

}
