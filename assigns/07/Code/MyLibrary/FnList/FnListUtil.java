package MyLibrary.FnList;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class FnListUtil {
	//
	public static <T> FnList<T> nil() {
		return new FnList<T>();
	}

	public static <T> FnList<T> cons(T x0, FnList<T> xs) {
		return new FnList<T>(x0, xs);
	}

	//
	public static <T> boolean nilq(FnList<T> xs) {
		return xs.nilq();
	}

	public static <T> boolean consq(FnList<T> xs) {
		return xs.consq();
	}

	//
	public static <T> void System$out$print(FnList<T> xs) {
		System.out.print("FnList(");
		FnListUtil.iforitm(xs,
				(i, itm) -> {
					if (i > 0) {
						System.out.print(",");
					}
					System.out.print(itm.toString());
				});
		System.out.print(")");
	}

	//
	public static FnList<Object> list_make_int0(int n0) {
		FnList<Object> xs = nil();
		for (int i0 = n0 - 1; i0 >= 0; i0 -= 1) {
			xs = cons(null, xs);
		}
		return xs;
	}

	public static FnList<Integer> list_make_int1(int n0) {
		FnList<Integer> xs = nil();
		for (int i0 = n0 - 1; i0 >= 0; i0 -= 1) {
			xs = cons(i0, xs);
		}
		return xs;
	}

	//
	public static FnList<Integer> list$int_rand$make(int n0) {
		Random rand = new Random();
		FnList<Integer> xs = nil();
		for (int i0 = 0; i0 < n0; i0 += 1) {
			xs = cons(rand.nextInt(), xs);
		}
		return xs;
	}

	//
	public static <T, R> FnList<R> map_list(FnList<T> xs, Function<? super T, R> fopr) {
		FnList<R> rs = nil();
		while (!nilq(xs)) {
			rs = cons(fopr.apply(xs.hd()), rs);
			xs = xs.tl();
		}
		return reverse(rs);
	}

	public static <T, R> FnList<R> imap_list(FnList<T> xs, BiFunction<Integer, ? super T, R> fopr) {
		int i0 = 0;
		FnList<R> rs = nil();
		while (!nilq(xs)) {
			rs = cons(fopr.apply(i0, xs.hd()), rs);
			i0 += 1;
			xs = xs.tl();
		}
		return reverse(rs);
	}

	//
	public static <T, R> FnList<R> rmap_list(FnList<T> xs, Function<? super T, R> fopr) {
		return map_list(reverse(xs), fopr);
	}

	public static <T, R> FnList<R> irmap_list(FnList<T> xs, BiFunction<Integer, ? super T, R> fopr) {
		return imap_list(reverse(xs), fopr);
	}

	//
	public static <T> void foritm(FnList<T> xs, Consumer<? super T> action) {
		xs.foritm(action);
		return;
	}

	public static <T> void rforitm(FnList<T> xs, Consumer<? super T> action) {
		reverse(xs).foritm(action);
		return;
	}

	public static <T> void iforitm(FnList<T> xs, BiConsumer<Integer, ? super T> action) {
		xs.iforitm(action);
		return;
	}

	public static <T> void irforitm(FnList<T> xs, BiConsumer<Integer, ? super T> action) {
		reverse(xs).iforitm(action);
		return;
	}

	//
	public static <T> boolean forall(FnList<T> xs, Predicate<? super T> pred) {
		return xs.forall(pred);
	}

	public static <T> boolean rforall(FnList<T> xs, Predicate<? super T> pred) {
		return reverse(xs).forall(pred);
	}

	public static <T> boolean iforall(FnList<T> xs, BiPredicate<Integer, ? super T> pred) {
		return xs.iforall(pred);
	}

	public static <T> boolean irforall(FnList<T> xs, BiPredicate<Integer, ? super T> pred) {
		return reverse(xs).iforall(pred);
	}

	//
	/*
	 * public static<T>
	 * FnList<T> reverse(FnList<T> xs) {
	 * FnList<T> ys;
	 * ys = nil();
	 * while (!nilq(xs)) {
	 * ys = cons(xs.hd(), ys); xs = xs.tl();
	 * }
	 * return ys;
	 * }
	 */
	public static <T> FnList<T> reverse(FnList<T> xs) {
		FnList<T> r0 = nil();
		return FnListUtil.folditm(xs, r0, (r1, x1) -> cons(x1, r1));
	}

	//
	public static <T> FnList<T> rappend(FnList<T> xs, FnList<T> ys) {
		return FnListUtil.folditm(xs, ys, (r1, x1) -> cons(x1, r1));
	}

	//
	public static <T, R> R folditm(FnList<T> xs, R r0, BiFunction<R, ? super T, R> fopr) {
		R rs = r0;
		while (!nilq(xs)) {
			rs = fopr.apply(rs, xs.hd());
			xs = xs.tl();
		}
		return rs;
	}

	//
	public static <T, R> R rfolditm(FnList<T> xs, R r0, BiFunction<? super T, R, R> fopr) {
		return FnListUtil.folditm(reverse(xs), r0, (x1, r1) -> fopr.apply(r1, x1));
	}

	//
	public static <T extends Comparable<T>> boolean orderedq(FnList<T> xs) {
		return orderedq(xs, (x1, x2) -> x1.compareTo(x2));
	}

	public static <T extends Comparable<T>> boolean orderedq(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		T x0, x1;
		if (nilq(xs))
			return true;
		x0 = xs.hd();
		xs = xs.tl();
		while (!nilq(xs)) {
			x1 = xs.hd();
			if (cmp.applyAsInt(x0, x1) > 0) {
				return false;
			} else {
				x0 = x1;
				xs = xs.tl();
			}
		}
		return true; // HX: [xs] is ordered
	}

	//
	public static <T extends Comparable<T>> FnList<T> insertSort(FnList<T> xs) {
		return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
	}

	// HX-2025-09-30:
	// Poor non-tail-recursive implementation
	// It is here for demonstration purpose only
	public static <T> FnList<T> insertSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		if (nilq(xs)) {
			return xs;
		} else {
			return insert(insertSort(xs.tl(), cmp), xs.hd(), cmp);
		}
	}

	private static <T> FnList<T> insert(FnList<T> xs, T x0, ToIntBiFunction<T, T> cmp) {
		if (nilq(xs)) {
			return cons(x0, xs);
		} else {
			final T hd = xs.hd();
			final int sgn = cmp.applyAsInt(x0, hd);
			if (sgn <= 0) { // HX: for stableness
				return cons(x0, xs); // [x0] is returned
			} else {
				return cons(hd, insert(xs.tl(), x0, cmp));
			}
		}
	}

	//
	public static <T extends Comparable<T>> FnList<T> mergeSort(FnList<T> xs) {
		return mergeSort(xs, (x1, x2) -> x1.compareTo(x2));
	}

	//
	public static <T> FnList<T> mergeSort(FnList<T> xs, ToIntBiFunction<T, T> cmp) {
		if (xs.length() <= 1) {
			return xs;
		} else {
			return mergeSort_split(xs, nil(), xs.length(), 0, cmp);
		}
	}

	private static <T> FnList<T> mergeSort_split(FnList<T> xs, FnList<T> ys, int n0, int n1,
			ToIntBiFunction<T, T> cmp) {
		if (2 * n1 < n0) {
			return mergeSort_split(xs.tl(), cons(xs.hd(), ys), n0, n1 + 1, cmp);
		} else {
			return mergeSort_merge(mergeSort(reverse(ys), cmp), mergeSort(xs, cmp), cmp);
		}
	}

	private static <T> FnList<T> mergeSort_merge(FnList<T> xs, FnList<T> ys, ToIntBiFunction<T, T> cmp) {
		return mergeSort_merge_helper(xs, ys, nil(), cmp);
	}

	private static <T> FnList<T> mergeSort_merge_helper(FnList<T> xs, FnList<T> ys, FnList<T> zs,
			ToIntBiFunction<T, T> cmp) {
		if (nilq(xs))
			return rappend(zs, ys);
		if (nilq(ys))
			return rappend(zs, xs);
		if (cmp.applyAsInt(xs.hd(), ys.hd()) <= 0) {
			return mergeSort_merge_helper(xs.tl(), ys, cons(xs.hd(), zs), cmp);
		} else {
			return mergeSort_merge_helper(xs, ys.tl(), cons(ys.hd(), zs), cmp);
		}
	}
	//
} // end of [public class FnListUtil{...}]