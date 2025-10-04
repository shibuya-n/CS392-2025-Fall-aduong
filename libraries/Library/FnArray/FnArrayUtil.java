package Library.FnArray;

import Library.FnList.*;

import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import java.util.function.ToIntBiFunction;

public class FnArrayUtil {
//
    public static<T>
	FnArray<T>
	list$make(FnList<T> xs) {
	return new FnArray<T>(xs);
    }
    public static<T>
	FnArray<Integer>
	int1$make(int n0) {
	return
	list$make(FnListUtil.int1$make(n0));
    }
//
    public static<T>
	void System$out$print(FnArray<T> xs) {
    	System.out.print("FnArray(");
	FnArrayUtil.iforitm
        ( xs,
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
    public static<T,R>
	FnArray<R> map_array
	(FnArray<T> xs, Function<? super T, R> fopr) {
	int n = xs.length();
	R[] res = (R[])(new Object[n]);
	for (int i = 0; i < n; i += 1) {
	    res[i] = fopr.apply(xs.sub(i));
	}
	return new FnArray<R>(res);
    }
    public static<T,R>
	FnArray<R> rmap_array
	(FnArray<T> xs, Function<? super T, R> fopr) {
	int n = xs.length();
	R[] res = (R[])(new Object[n]);
	for (int i = 0; i < n; i += 1) {
	    res[i] = fopr.apply(xs.sub(n-1-i));
	}
	return new FnArray<R>(res);
    }
    public static<T,R>
	FnArray<R> imap_array
	(FnArray<T> xs, BiFunction<Integer, ? super T, R> fopr) {
	int n = xs.length();
	R[] res = (R[])(new Object[n]);
	for (int i = 0; i < n; i += 1) {
	    res[i] = fopr.apply(i, xs.sub(i));
	}
	return new FnArray<R>(res);
    }
    public static<T,R>
	FnArray<R> irmap_array
	(FnArray<T> xs, BiFunction<Integer, ? super T, R> fopr) {
	int n = xs.length();
	R[] res = (R[])(new Object[n]);
	for (int i = 0; i < n; i += 1) {
	    res[i] = fopr.apply(i, xs.sub(n-1-i));
	}
	return new FnArray<R>(res);
    }
//
    public static<T>
	void foritm
	(FnArray<T> xs, Consumer<? super T> action) {
	xs.foritm(action); return;
    }
    public static<T>
	void rforitm
	(FnArray<T> xs, Consumer<? super T> action) {
	xs.rforitm(action); return;
    }
    public static<T>
	void iforitm
	(FnArray<T> xs, BiConsumer<Integer, ? super T> action) {
	xs.iforitm(action); return;
    }
    public static<T>
	void irforitm
	(FnArray<T> xs, BiConsumer<Integer, ? super T> action) {
	xs.iforitm(action); return;
    }
//
    public static<T,R>
	R folditm
	(FnArray<T> xs, R r0, BiFunction<R, ? super T, R> fopr) {
	R res = r0;
	int n = xs.length();
	for (int i = 0; i < n; i += 1) {
	    res = fopr.apply(res, xs.sub(i));
	}
	return res;
    }
//
    public static<T,R>
	R rfolditm
	(FnArray<T> xs, R r0, BiFunction<? super T, R, R> fopr) {
	R res = r0;
	int n = xs.length();
	for (int i = 0; i < n; i += 1) {
	    res = fopr.apply(xs.sub(n-1-i), res);
	}
	return res;
    }
//
    public static<T>
	FnList<T> listize(FnArray<T> xs) {
	return xs.listize();
    }
    public static<T>
	FnList<T> rlistize(FnArray<T> xs) {
	return xs.rlistize();
    }
//
    public static<T>
	FnArray<T> toArray(FnArray<T> xs) {
	return xs; // HX: This is just a no-op!
    }
//
    public static<T> FnArray<T>
	mergeSort
	(FnArray<T> xs, ToIntBiFunction<T,T> cmp) {
	return list$make(mergeSort_list(xs, cmp));
    }
    public static<T> FnList<T>
	mergeSort_list
	(FnArray<T> xs, ToIntBiFunction<T,T> cmp) {
	return FnListUtil.mergeSort(listize(xs), cmp);
    }
    public static<T> FnArray<T>
	mergeSort_array
	(FnArray<T> xs, ToIntBiFunction<T,T> cmp) {
	return FnArrayUtil.mergeSort(toArray(xs), cmp);
    }
//
    public static
	<T extends Comparable<T>>
	FnArray<T> mergeSort(FnArray<T> xs) {
	return mergeSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
    public static
	<T extends Comparable<T>>
	FnList<T> mergeSort_list(FnArray<T> xs) {
	return mergeSort_list(xs, (x1, x2) -> x1.compareTo(x2));
    }
    public static
	<T extends Comparable<T>>
	FnArray<T> mergeSort_array(FnArray<T> xs) {
	return mergeSort_array(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
} // end of [public class FnArrayUtil{...}]
