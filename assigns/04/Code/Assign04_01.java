
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import libraries.Library.MyQueueBase;

public class Assign04_01<T> extends MyQueueBase<T> {
  //
  /*
   * HX-2025-09-24:
   * Please first copy your implementation of Assign03_03
   * to this class.
   */

  /*
   * The following four higher-order methods are declared
   * in MyQueueBase<T>:
   * 
   * public void foritm(Consumer<? super T> action);
   * public void iforitm(BiConsumer<Integer, ? super T> action);
   * public rforitm(Consumer<? super T> action);
   * public irforitm(BiConsumer<Integer, ? super T> action);
   * 
   * Please implement them for your two list based queue.
   */
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

} // end of [public class Assign04_01<T> extends MyQueueBase<T>{...}]
