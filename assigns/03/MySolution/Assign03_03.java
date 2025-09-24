public class Assign03_03<T> extends MyQueueBase<T> {

    int nitm = -1;
    FnList<T> frnt = null;
    FnList<T> rear = null;

    public Assign03_03() {
        nitm = 0;
        frnt = new FnList<T>();
        rear = new FnList<T>();
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return false;
    }

    public T top$raw() {
        // HX: Please implement
        if (frnt.nilq()) {
            frnt = rear.reverse();
            rear = new FnList<T>();
        }
        return frnt.hd();
    }

    public T deque$raw() {
        // HX: Please implement
        if (frnt.nilq()) {
            frnt = rear.reverse();
            rear = new FnList<T>();

        }
        T x = frnt.hd();
        frnt = frnt.tl();
        nitm--;
        return x;

    }

    public void enque$raw(T itm) {
        // HX: Please implement
        rear = new FnList<>(itm, rear);

        nitm++;
    }

}
