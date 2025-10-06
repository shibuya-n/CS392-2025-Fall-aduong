package Library.LnList;

import Library.FnList.*;
import Library.FnA1sz.*;
import Library.MyRefer.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class LnListSUtil {
//
    public static<T>
	LnList<T> nil() {
	return new LnList<T>();
    }
    public static<T>
	LnList<T>
	cons(T x0, LnList<T> xs) {
	return new LnList<T>(x0, xs);
    }
//
    public static<T>
	boolean nilq(LnList<T> xs) {
	return xs.nilq();
    }
    public static<T>
	boolean consq(LnList<T> xs) {
	return xs.consq();
    }
//
} // end of [public class LnListSUtil{...}]
