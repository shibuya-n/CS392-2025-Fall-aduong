public class FnListCons<T> extends FnList<T> {
    private T x0;
    private FnList<T> xs;
    
    FnListCons(T x0, FnList<T> xs) {
	this.ctag = 1;
	this.x0 = x0;
	this.xs = xs;
    }

    public T hd() {
	return x0;
    }
    public FnList<T> tl() {
	return xs;
    }
}
