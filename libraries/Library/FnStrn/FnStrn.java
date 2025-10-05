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
    public void
	foritm(Consumer<? super Character> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(root[i]);
	}
    }
    public void
	rforitm(Consumer<? super Character> action) {
	int n = root.length;
	for (int i = 0; i < n; i += 1) {
	    action.accept(root[n-1-i]);
	}
    }
//
} // end of [public class FnStrn{...}]
