package Library.FnArray;

import Library.FnGseq.*;
import Library.FnList.*;

import java.util.function.Consumer;

public class FnArrayGUtil<X0>
    extends FnGseq<FnArray<X0>,X0> {
//
    @Override
    public int
	length(FnArray<X0> xs) {
	return xs.length(); // HX: O(1)
    }
//
    @Override
    public FnArray<X0>
	list$make(FnList<X0> xs) {
	return FnArrayUtil.list$make(xs);
    }
//
    @Override
    public void
	foritm(FnArray<X0> xs, Consumer<? super X0> work) {
	xs.foritm(work); return;
    }
//
    @Override
    public void
	rforitm(FnArray<X0> xs, Consumer<? super X0> work) {
	xs.rforitm(work); return;
    }
//
} // end of [public class FnArrayGUtil<X0>{...}]
