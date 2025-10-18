import Library.FnList.*;
import Library.LnList.*;
import java.util.function.Consumer;

//
// HX: 30 points
// This one tests your understanding of higher-order
// methods. Trying to construct a consumer of consumers
// (of the type Consumer<Consumer<Character>>) can help
// you understand the meaning of this one.
//
public class Quiz01_02 {

	private static class Holder {
		FnList<Character> result = new FnList<>();

	}

	public static FnList<Character> thirdOrderFun(Consumer<Consumer<Character>> ffcs) {
		// HX: Given a consumer of consumers of characters,
		// thirdOrderFun returns a string cs.
		// Given fcs = (ch) -> System.out.print(ch),
		// which is of the type Consumer<Character>,
		// ffcs.accept(fcs) and cs.foritm(fcs) should behave
		// the same.

		Holder temp = new Holder();

		Consumer<Character> cs = c -> {

			temp.result = new FnList<>(c, temp.result);

		};

		ffcs.accept(cs);

		return temp.result.reverse();

	}

	public static void main(String[] args) {
		// HX-2025-10-12:
		// Please write minimal testing code for thirdOrderFun.

		Consumer<Consumer<Character>> ffcs = x -> {
			x.accept('a');
			x.accept('b');
			x.accept('c');

		};

		FnList<Character> result = thirdOrderFun(ffcs);

		result.System$out$print();

	}
}
