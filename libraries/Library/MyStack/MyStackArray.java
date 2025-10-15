package Library.MyStack;

import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyStackArray<T> extends MyStackBase<T> {
    // array extends from the stack base, provides the way you store elements.
    // the functions of the stack were taken from the base class
    int nitm = -1;
    T[] itms = null;

    public MyStackArray(int cap) {
        assert (cap >= 1); // assert returns a runtime error if something goes wrong -> use a lot
        nitm = 0;
        itms = (T[]) new Object[cap];
    }

    @Override
    public int size() {
        return nitm;
    }

    @Override
    public boolean isFull() {
        return (nitm >= itms.length);
    }

    @Override
    public T top$raw() {
        return itms[nitm - 1];
    }

    @Override
    public T pop$raw() {
        nitm -= 1;
        return itms[nitm];
    }

    @Override
    public void push$raw(T itm) {
        itms[nitm] = itm;
        nitm += 1;
        return;
    }

    public void foritm(Consumer<? super T> work) {
        int m = nitm - 1;
        for (int i = 0; i < nitm; i += 1)
            work.accept(itms[m - i]);
    }

    @Override
    public void iforitm(BiConsumer<Integer, ? super T> work) {
        int m = nitm - 1;
        for (int i = 0; i < nitm; i += 1)
            work.accept(i, itms[m - i]);
    }

    @Override
    public void rforitm(Consumer<? super T> work) {
        for (int i = 0; i < nitm; i += 1)
            work.accept(itms[i]);
    }

    @Override
    public void irforitm(BiConsumer<Integer, ? super T> work) {
        for (int i = 0; i < nitm; i += 1)
            work.accept(i, itms[i]);
    }

}
