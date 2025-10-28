//
import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Predicate;
//
class LnStrmTest {
    public static LnStrm<Integer> intFrom(int start) {
	return new LnStrm<Integer>(() -> new LnStcn<Integer>(start, intFrom(start+1)));
    }
    public static LnStrm<Integer> sieveMethod(LnStrm<Integer> fxs) {
	LnStcn<Integer> cxs = fxs.eval0();
	Integer head = cxs.head;
	LnStrm<Integer> tail = cxs.tail;
	return new LnStrm<Integer>
	    (() -> new LnStcn<Integer>(head, sieveMethod(tail.filter0((ix) -> ix.intValue() % head > 0))));
    }
    public static void main(String[] args) {
	LnStrm<Integer> intFrom2 = intFrom(2);
	LnStrm<Integer> thePrimes = sieveMethod(intFrom2);
	thePrimes.foritm0((px) -> System.out.println(px));
	LnStrm<Integer> theNaturals = intFrom(0);
	theNaturals.foritm0((nx) -> System.out.println(nx));
	LnStrmSUtil.map0(theNaturals, (nx) -> nx * nx).foritm0((nx) -> System.out.println(nx));
	return;
    }
}
