public MyRefer<T> {
    T theValue;

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
	    thrown new MyReferNullExn();
	}
    }

    public T takeout$raw() {
	T value;
	value = theValue;
	theValue = null; return value;
    }

    public void discard$raw() {
	theValue = null; return value;
    }

    public void
	set$raw(T value) {
	theValue = value; return /*void*/;
    }

}
