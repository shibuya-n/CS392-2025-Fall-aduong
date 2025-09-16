import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class MyQueueArray<T> extends MyQueueBase<T> {

    int nitm = -1;
    int frst = -1;
    int last = -1;
    T[] itms = null;

    public
    MyQueueArray(int cap)
    {
	assert (cap >= 2);
	nitm = 0;
	frst = 0; last = 0;
	itms = (T[]) new Object[cap];
    }
    
    public int size() {
	return nitm;
    }

    public boolean isFull() {
	return (nitm >= itms.length);
    }

    public T top$raw() {
	return itms[frst];
    }

    public T deque$raw() {
	T itm = itms[frst];
	nitm -= 1;
	frst += 1;
	if (frst >= itms.length) frst = 0;
	return itm;
    }

    public void enque$raw(T itm) {
	itms[last] = itm;
	nitm += 1;
	last += 1;
	if (last >= itms.length) last = 0;
	return /*void*/ ;
    }

    public void
	foritm(Consumer<? super T> action) {
	int m = nitm - 1;
	int n = itms.length;
	for (int i = 0; i < nitm; i += 1) {
	    action.accept(itms[(frst+i)%n]);
	}
	return /*void*/ ;
    }

    public void
	iforitm(BiConsumer<Integer, ? super T> action) {
	int m = nitm - 1;
	int n = itms.length;
	for (int i = 0; i < nitm; i += 1) {
	    action.accept(i, itms[(frst+i)%n]);
	}
	return /*void*/ ;
    }
}
