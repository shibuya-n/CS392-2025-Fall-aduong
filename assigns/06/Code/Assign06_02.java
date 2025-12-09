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

	// Merge streams using the function from Assign06_01
	private static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
		return new LnStrm<T>(() -> {
			LnStcn<LnStrm<T>> outerNode = fxss.eval0();

			if (outerNode.nilq()) {
				return new LnStcn<>();
			}

			FnList<LnStcn<T>> nonEmptyNodes = FnListSUtil.nil();
			LnStcn<LnStrm<T>> currentOuter = outerNode;

			while (currentOuter.consq()) {
				LnStrm<T> innerStream = currentOuter.hd();
				LnStcn<T> innerNode = innerStream.eval0();

				if (innerNode.consq()) {
					nonEmptyNodes = FnListSUtil.cons(innerNode, nonEmptyNodes);
				}

				currentOuter = currentOuter.tl().eval0();
			}

			if (nonEmptyNodes.nilq()) {
				return new LnStcn<>();
			}

			LnStcn<T> minNode = nonEmptyNodes.hd();
			T minValue = minNode.hd();
			FnList<LnStcn<T>> remaining = nonEmptyNodes.tl();

			while (remaining.consq()) {
				LnStcn<T> candidateNode = remaining.hd();
				T candidateValue = candidateNode.hd();

				if (cmpr.applyAsInt(candidateValue, minValue) < 0) {
					minValue = candidateValue;
					minNode = candidateNode;
				}

				remaining = remaining.tl();
			}

			FnList<LnStrm<T>> newStreams = FnListSUtil.nil();
			FnList<LnStcn<T>> allNodes = nonEmptyNodes;

			while (allNodes.consq()) {
				LnStcn<T> node = allNodes.hd();

				if (node == minNode) {
					LnStrm<T> advancedStream = node.tl();
					newStreams = FnListSUtil.cons(advancedStream, newStreams);
				} else {
					LnStrm<T> reconstructed = new LnStrm<>(() -> node);
					newStreams = FnListSUtil.cons(reconstructed, newStreams);
				}

				allNodes = allNodes.tl();
			}

			LnStrm<LnStrm<T>> newFxss = listToStreamOfStreams(newStreams);
			return new LnStcn<>(minValue, mergeLnStrm(newFxss, cmpr));
		});
	}

	private static <T> LnStrm<LnStrm<T>> listToStreamOfStreams(FnList<LnStrm<T>> list) {
		return new LnStrm<>(() -> {
			if (list.nilq()) {
				return new LnStcn<>();
			}
			return new LnStcn<>(list.hd(), listToStreamOfStreams(list.tl()));
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