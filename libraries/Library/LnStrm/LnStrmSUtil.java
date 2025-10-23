//
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
//
// HX-2025-10-23:
// typedef LnStrm<T> = Supplier<LnStcn<T>>
//
class LnStrmSUtil {

    public static<T>
	LnStcn<T> eval0(LnStrm<T> fxs) {
	return fxs.eval0();
    }

    public static<T>
	void foritm0
	(LnStrm<T> fxs, Consumer<? super T> work) {
	LnStcn<T> cxs = fxs.eval0();
	while (cxs.consq()) {
	    work.accept(cxs.head); cxs = cxs.tail.eval0();
	}
	return /*void*/;
    }
//
    public static<T,R>
	LnStrm<R> map0
	(LnStrm<T> fxs, Function<? super T, R> fopr) {
	return new LnStrm<R>(
	  () -> {
	      LnStcn<T> cxs = fxs.eval0();
	      if (cxs.nilq()) {
		  return new LnStcn<R>();
	      } else {
		  final T head = cxs.head;
		  final LnStrm<T> tail = cxs.tail;
		  return new LnStcn<R>(fopr.apply(head), map0(tail, fopr));
	      }
	  }
       );
    }
//
    public static<T>
	LnStrm<T> filter0
	(LnStrm<T> fxs, Predicate<? super T> pred) {
	return new LnStrm<T>(
	  () -> {
	      LnStcn<T> cxs = fxs.eval0();
	      while (cxs.consq()) {
		  final T hd = cxs.head;
		  final LnStrm<T> tl = cxs.tail;
		  if (pred.test(hd)) {
		      return new LnStcn<T>(hd, filter0(tl, pred));
		  } else {
		      cxs = cxs.tail.eval0();
		  }
	      }
	      return new LnStcn<T>(); // no satisfying elements found
	  }
       );
    }
//
} // end of [class LnStrmSUtil{...}]
