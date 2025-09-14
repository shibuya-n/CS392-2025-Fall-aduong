import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public abstract class FnList<T> {

    int ctag = -1;

    public boolean nilq() {
	return (ctag == 0);
    }
    public boolean consq() {
	return (ctag == 1);
    }

    public T hd() {
	return null;
    }
    public FnList<T> tl() {
	return null;
    }
//
    public FnList<T> reverse() {
	return FnListUtil.reverse(this);
    }
    public FnList<T>
	rappend(FnList<T> ys) {
	return FnListUtil.rappend(this, ys);
    }
//
    public void System$out$print() {
    	System.out.print("FnList(");
	this.iforitm
	(
          (i, itm) ->
	  {
	      if (i > 0) {
		  System.out.print(",");
	      }
	      System.out.print(itm.toString());
	  }
	);
	System.out.print(")");
    }
//
    void foritm(Consumer<? super T> action) {
	FnList<T> xs = this;
	while (true) {
	    if (xs.nilq()) break;
	    action.accept(xs.hd());
	    xs = xs.tl();
	}
    }
    void iforitm(BiConsumer<Integer, ? super T> action) {
	int i0 = 0;
	FnList<T> xs = this;
	while (true) {
	    if (xs.nilq()) break;
	    action.accept(i0, xs.hd());
	    i0 += 1; xs = xs.tl();
	}
    }
//
    boolean forall(Predicate<? super T> pred) {
	FnList<T> xs = this;
	while (true) {
	    if (xs.nilq()) break;
	    if (!pred.test(xs.hd())) return false;
	    xs = xs.tl();
	}
	return true;
    }
    boolean iforall(BiPredicate<Integer, ? super T> pred) {
	int i0 = 0;
	FnList<T> xs = this;
	while (true) {
	    if (xs.nilq()) break;
	    if (!pred.test(i0, xs.hd())) return false;
	    i0 += 1; xs = xs.tl();
	}
	return true;
    }
}
