package Library.FnStrn;

import Library.FnList.*;
import Library.FnArray.*;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;
import java.util.function.ToIntBiFunction;

public class FnStrn {
//
    char[] root;
//    
    public final
    FnStrnUtil U0 = new FnStrnUtil();
    public final
    FnStrnGUtil GU = new FnStrnGUtil();
//
    public FnStrn(char[] xs) {
	root = xs;
    }
    public FnStrn(String xs) {
	int n = xs.length();
	root = new char[n];
	for (int i = 0; i < n; i += 1) {
	    root[i] = xs.charAt(i);
	}
    }
    public FnStrn(FnList<Character> xs) {
	int n = xs.length();
	root = new char[n];
	xs.iforitm((Integer i0, Character x0) -> root[i0] = x0);
    }
//
    public char sub(int i) {
	return root[i];
    }
//
    public int length() {
	return root.length;
    }
//
    public void System$out$print() {
    	System.out.print("FnStrn(");
	this.foritm
	(
          (itm) ->
	  {
	      System.out.print(itm.toString());
	  }
	);
	System.out.print(")");
    }
//
    public FnStrn
	append(FnStrn xs) {
	return FnStrnUtil.append(this, xs);
    }
//
    public void
	foritm(Consumer<? super Character> work) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    work.accept(root[i]);
	}
    }
    public void
	rforitm(Consumer<? super Character> work) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    work.accept(root[n-1-i]);
	}
    }
    public void
	iforitm(BiConsumer<Integer, ? super Character> work) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    work.accept(i, root[i]);
	}
    }
    public void
	irforitm(BiConsumer<Integer, ? super Character> work) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    work.accept(i, root[n-1-i]);
	}
    }
//
    public FnStrn
	mergeSort() {
	return mergeSort((c1, c2) -> c1.compareTo(c2));
    }
    public FnStrn
	insertSort() {
	return insertSort((c1, c2) -> c1.compareTo(c2));
    }
    public FnStrn
	mergeSort
	(ToIntBiFunction<Character,Character> cmp) { return this.GU.mergeSort(this, cmp); }
    public FnStrn
	insertSort
	(ToIntBiFunction<Character,Character> cmp) { return this.GU.insertSort(this, cmp); }
//
} // end of [public class FnStrn{...}]
