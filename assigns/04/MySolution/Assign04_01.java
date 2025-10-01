
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import Library.MyQueue.*;
import Library.FnList.*;

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

  public Assign04_01() {
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

  public void foritm(Consumer<? super T> action) {
    FnList<T> xs = frnt;

    if (xs.nilq()) {
      xs = rear.reverse();
      rear = new FnList<>();
    }

    while (true) {
      if (xs.nilq()) {
        break;
      }
      action.accept(xs.hd());
      xs = xs.tl();
    }
  }

  public void iforitm(BiConsumer<Integer, ? super T> action) {

    FnList<T> xs = frnt;

    if (xs.nilq()) {
      xs = rear.reverse();
      rear = new FnList<T>();
    }

    int i = 0;

    while (true) {
      if (xs.nilq()) {
        break;
      }
      action.accept(i, xs.hd());
      xs = xs.tl();
      i += 1;

    }

  }

  public void rforitm(Consumer<? super T> action) {
    FnList<T> xs = rear;

    while (!xs.nilq()) {
      action.accept(xs.hd());
      xs = xs.tl();

    }

  }

  public void irforitm(BiConsumer<Integer, ? super T> action) {
    FnList<T> xs = rear;

    int i = 0;

    while (!xs.nilq()) {
      action.accept(i, xs.hd());
      xs = xs.tl();

      i++;
    }

  }

  public static void main(String[] args) {
    Assign04_01<String> test = new Assign04_01<String>();

    test.enque$exn("test1");
    test.enque$exn("test2");
    test.enque$exn("test3");

    test.foritm(x -> System.out.println(x));

    test.enque$exn("test1");
    test.enque$exn("test2");
    test.enque$exn("test3");
    test.iforitm((i, x) -> System.out.println("Number: " + i + " " + x));

    test.enque$exn("test1");
    test.enque$exn("test2");
    test.enque$exn("test3");

    test.rforitm(x -> System.out.println(x));

    test.irforitm((i, x) -> System.out.println("Number: " + i + "" + x));
  }

} // end of [public class Assign04_01<T> extends MyQueueBase<T>{...}]
