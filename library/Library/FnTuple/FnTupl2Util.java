public class FnTupl2Util {
    public static
	<T0 extends Comparable<T0>,
	 T1 extends Comparable<T1>>
	int compare(FnTupl2<T0,T1> tup1, FnTupl2<T0,T1> tup2) {
	int sgn;
	sgn = tup1.sub0.compareTo(tup2.sub0);
	if (sgn != 0)
	    return sgn;
	else {
	    return tup1.sub1.compareTo(tup2.sub1);
	} // end of [if]
    }
}
