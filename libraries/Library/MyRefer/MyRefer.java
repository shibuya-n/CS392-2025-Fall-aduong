package Library.MyRefer;

public class MyRefer<T> {
    T theValue;

    public MyRefer() {
	theValue = null;
    }
    public MyRefer(T value) {
	theValue = value;
    }

    public
    boolean nullq() {
	return (theValue == null);
    }

    public T get$raw() {
	return theValue ;
    }
    public T get$opt() {
	return theValue ;
    }
    public T get$exn() {
	if (!nullq()) {
	    return theValue;
	} else {
	    throw new MyReferNullExn();
	}
    }

    public T takeout$raw() {
	T value;
	value = theValue;
	theValue = null; return value;
    }

    public void discard$raw() {
	theValue = null; return /*void*/;
    }

    public void
	set$raw(T value) {
	theValue = value; return /*void*/;
    }

}
