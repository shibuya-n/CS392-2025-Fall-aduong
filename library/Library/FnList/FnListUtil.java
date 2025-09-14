import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class FnListUtil {
//
    public static<T>
	void System$out$print(FnList<T> xs) {
    	System.out.print("FnList(");
	FnListUtil.iforitm
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
    public static<T>
	void foritm(FnList<T> xs, Consumer<? super T> action) {
	xs.foritm(action); return;
    }
    public static<T>
	void iforitm(FnList<T> xs, BiConsumer<Integer, ? super T> action) {
	xs.iforitm(action); return;
    }
    public static<T>
	boolean forall(FnList<T> xs, Predicate<? super T> pred) {
	return xs.forall(pred);
    }
    public static<T>
	boolean iforall(FnList<T> xs, BiPredicate<Integer, ? super T> pred) {
	return xs.iforall(pred);
    }
//
    public static<T>
	FnList<T> reverse(FnList<T> xs) {
	FnList<T> r0 = new FnListNil<T>();
	return FnListUtil.folditm
	    (xs, r0, (r1, x1) -> new FnListCons<T>(x1, r1));
    }
//
    public static<T>
	FnList<T> rappend(FnList<T> xs, FnList<T> ys) {
	return FnListUtil.folditm
	    (xs, ys, (r1, x1) -> new FnListCons<T>(x1, r1));
    }
//
    public static<T,R>
	R folditm(FnList<T> xs, R r0, BiFunction<R,T,R> fopr) {
	R res = r0;
	while (true) {
	    if (xs.nilq()) break;
	    res = fopr.apply(res, xs.hd());
	}
	return res;
    }
//
}
