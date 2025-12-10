import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.FnList.*;
import java.util.function.ToIntBiFunction;

public class Assign06_02 {

	// Helper class to hold pair with precomputed sum
	private static class PairWithSum {
		final int x;
		final int y;
		final long sum;

		PairWithSum(int x, int y) {
			this.x = x;
			this.y = y;
			this.sum = (long) x * x * x + (long) y * y * y;
		}
	}

	// Generate stream of pairs (1,y), (2,y), ..., (y,y) for fixed y
	private static LnStrm<PairWithSum> pairsForY(int y) {
		return pairsHelper(1, y);
	}

	private static LnStrm<PairWithSum> pairsHelper(int x, int y) {
		return new LnStrm<>(() -> {
			if (x > y) {
				return new LnStcn<>();
			}
			PairWithSum pair = new PairWithSum(x, y);
			return new LnStcn<>(pair, pairsHelper(x + 1, y));
		});
	}

	// Generate infinite stream of streams, one for each y
	private static LnStrm<LnStrm<PairWithSum>> allPairStreams() {
		return allPairStreamsHelper(1);
	}

	private static LnStrm<LnStrm<PairWithSum>> allPairStreamsHelper(int y) {
		return new LnStrm<>(() -> {
			LnStrm<PairWithSum> stream = pairsForY(y);
			return new LnStcn<>(stream, allPairStreamsHelper(y + 1));
		});
	}

	// Merge streams using the corrected lazy approach
	private static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
		return new LnStrm<T>(() -> {
			LnStcn<LnStrm<T>> outer = fxss.eval0();

			if (outer.nilq()) {
				return new LnStcn<>();
			}

			LnStrm<T> firstStream = outer.hd();
			LnStcn<T> firstCons = firstStream.eval0();

			if (firstCons.nilq()) {
				// first inner stream is empty, skip it
				return mergeLnStrm(outer.tl(), cmpr).eval0();
			}

			// The minimum is always the head of the first stream (by invariant)
			T head = firstCons.hd();
			LnStrm<T> tailStream = firstCons.tl();

			// Insert the advanced stream back into the rest, preserving sorted heads
			LnStrm<LnStrm<T>> rest = outer.tl();
			LnStrm<LnStrm<T>> newFxss = insertStream(tailStream, rest, cmpr);

			return new LnStcn<>(head, mergeLnStrm(newFxss, cmpr));
		});
	}

	// Helper: reinsert a stream into a stream-of-streams while preserving head
	// order
	private static <T> LnStrm<LnStrm<T>> insertStream(LnStrm<T> streamToInsert,
			LnStrm<LnStrm<T>> restStreams,
			ToIntBiFunction<T, T> cmpr) {
		return new LnStrm<LnStrm<T>>(() -> {
			// Evaluate streamToInsert once and save the result
			LnStcn<T> insertCons = streamToInsert.eval0();

			if (insertCons.nilq()) {
				// nothing to insert (stream is empty)
				return restStreams.eval0();
			}

			T insertHead = insertCons.hd();
			LnStrm<T> insertTail = insertCons.tl();

			// Evaluate restStreams once
			LnStcn<LnStrm<T>> restCons = restStreams.eval0();

			if (restCons.nilq()) {
				// no more streams; reconstructed stream goes at the end
				LnStrm<T> reconstructed = new LnStrm<>(() -> insertCons);
				return new LnStcn<>(reconstructed, new LnStrm<>(() -> new LnStcn<>()));
			}

			LnStrm<T> firstRestStream = restCons.hd();
			LnStcn<T> firstRestCons = firstRestStream.eval0();

			if (firstRestCons.nilq()) {
				// first rest stream is empty, skip it
				LnStrm<T> reconstructed = new LnStrm<>(() -> insertCons);
				return insertStream(reconstructed, restCons.tl(), cmpr).eval0();
			}

			T firstRestHead = firstRestCons.hd();

			// If insertHead <= firstRestHead, insert before
			if (cmpr.applyAsInt(insertHead, firstRestHead) <= 0) {
				// Reconstruct both streams since we evaluated them
				LnStrm<T> reconstructedInsert = new LnStrm<>(() -> insertCons);
				LnStrm<T> reconstructedFirst = new LnStrm<>(() -> firstRestCons);
				LnStrm<LnStrm<T>> reconstructedRest = new LnStrm<>(
						() -> new LnStcn<>(reconstructedFirst, restCons.tl()));
				return new LnStcn<>(reconstructedInsert, reconstructedRest);
			} else {
				// Keep first rest stream, insert further down
				LnStrm<T> reconstructedInsert = new LnStrm<>(() -> insertCons);
				LnStrm<T> reconstructedFirst = new LnStrm<>(() -> firstRestCons);
				LnStrm<LnStrm<T>> newTail = insertStream(reconstructedInsert, restCons.tl(), cmpr);
				return new LnStcn<>(reconstructedFirst, newTail);
			}
		});
	}

	// Generate stream of integer pairs ordered by cube sum
	public static LnStrm<FnTupl2<Integer, Integer>> cubeSumOrderedIntegerPairs() {
		ToIntBiFunction<PairWithSum, PairWithSum> comparator = (p1, p2) -> Long.compare(p1.sum, p2.sum);

		LnStrm<PairWithSum> merged = mergeLnStrm(allPairStreams(), comparator);

		return convertToTuples(merged);
	}

	private static LnStrm<FnTupl2<Integer, Integer>> convertToTuples(LnStrm<PairWithSum> stream) {
		return new LnStrm<>(() -> {
			LnStcn<PairWithSum> node = stream.eval0();
			if (node.nilq()) {
				return new LnStcn<>();
			}
			PairWithSum pair = node.hd();
			FnTupl2<Integer, Integer> tuple = new FnTupl2<>(pair.x, pair.y);
			return new LnStcn<>(tuple, convertToTuples(node.tl()));
		});
	}

	// Generate stream of Ramanujan numbers
	public static LnStrm<Integer> ramanujanNumbers() {
		ToIntBiFunction<PairWithSum, PairWithSum> comparator = (p1, p2) -> Long.compare(p1.sum, p2.sum);

		LnStrm<PairWithSum> merged = mergeLnStrm(allPairStreams(), comparator);

		return findRamanujan(merged);
	}

	private static LnStrm<Integer> findRamanujan(LnStrm<PairWithSum> pairs) {
		return new LnStrm<>(() -> {
			LnStcn<PairWithSum> node = pairs.eval0();

			if (node.nilq()) {
				return new LnStcn<>();
			}

			return findRamanujanHelper(node.hd(), node.tl());
		});
	}

	private static LnStcn<Integer> findRamanujanHelper(PairWithSum prev, LnStrm<PairWithSum> rest) {
		LnStcn<PairWithSum> node = rest.eval0();

		if (node.nilq()) {
			return new LnStcn<>();
		}

		PairWithSum curr = node.hd();

		if (prev.sum == curr.sum) {
			// Found a Ramanujan number!
			int ramanujan = (int) prev.sum;
			return new LnStcn<>(ramanujan, findRamanujan(node.tl()));
		} else {
			// Keep searching
			return findRamanujanHelper(curr, node.tl());
		}
	}

	public static void main(String[] args) {
		System.out.println("=== Testing Cube Sum Ordered Pairs ===");
		System.out.println("First 20 pairs:");

		LnStrm<FnTupl2<Integer, Integer>> pairs = cubeSumOrderedIntegerPairs();
		final int[] count = { 0 };

		pairs.foritm0(pair -> {
			if (count[0] < 20) {
				int x = pair.sub0;
				int y = pair.sub1;
				long sum = (long) x * x * x + (long) y * y * y;
				System.out.println("(" + x + "," + y + ") -> " + sum);
				count[0]++;
			}
		});

		System.out.println("\n=== First 10 Ramanujan Numbers ===");
		LnStrm<Integer> ramanujan = ramanujanNumbers();
		count[0] = 0;

		ramanujan.foritm0(num -> {
			if (count[0] < 10) {
				System.out.println("Ramanujan #" + (count[0] + 1) + ": " + num);
				count[0]++;
			}
		});

		System.out.println("\nNote: 1729 (the famous Hardy-Ramanujan number) should be first!");
		System.out.println("1729 = 1³ + 12³ = 9³ + 10³");
	}
}