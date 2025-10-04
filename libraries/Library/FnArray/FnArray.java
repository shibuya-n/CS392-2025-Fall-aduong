package Library.FnArray;

import Library.FnList.*;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import java.util.function.ToIntBiFunction;

public class FnArray<T> {
//
    T[] root;
//    
    public final
    FnArrayUtil U0 = new FnArrayUtil();
    public final
    FnArrayGUtil<T> GU = new FnArrayGUtil<T>();
//
    public FnArray(T[] xs) { root = xs; }
    public FnArray(FnList<T> xs) {
	int i = 0;
	int n = xs.length();
	root = (T[])(new Object[n]);
	while (!xs.nilq()) {
	    root[i] = xs.hd(); i++; xs = xs.tl();
	}
    }
//
    public T sub(int i) {
	return root[i];
    }
//
    public int length() {
	return root.length;
    }
//
    public void System$out$print() {
    	System.out.print("FnArray(");
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
    public void
	foritm(Consumer<? super T> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(root[i]);
	}
    }
    public void
	rforitm(Consumer<? super T> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(root[n-1-i]);
	}
    }
    public void
	iforitm(BiConsumer<Integer, ? super T> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(i, root[i]);
	}
    }
    public void
	irforitm(BiConsumer<Integer, ? super T> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(i, root[n-1-i]);
	}
    }
//
    public boolean
	forall(Predicate<? super T> pred) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    if (!pred.test(root[i])) return false;
	}
	return true;
    }
    public boolean
	rforall(Predicate<? super T> pred) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    if (!pred.test(root[n-1-i])) return false;
	}
	return true;
    }
    public boolean
	iforall(BiPredicate<Integer, ? super T> pred) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    if (!pred.test(i, root[i])) return false;
	}
	return true;
    }
    public boolean
	irforall(BiPredicate<Integer, ? super T> pred) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    if (!pred.test(i, root[n-1-i])) return false;
	}
	return true;
    }
//
    public FnList<T>
	listize() { return FnArrayUtil.listize(this); }
    public FnList<T>
	rlistize() { return FnArrayUtil.rlistize(this); }
//
} // end of [public class FnArray<T>{...}]
