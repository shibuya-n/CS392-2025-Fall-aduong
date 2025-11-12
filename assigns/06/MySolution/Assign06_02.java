import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.FnList.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign06_02 {

	// Wrapper class to hold pair data with accessible fields
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

	// Helper: Generate pairs (1,y), (2,y), ..., (y,y) for fixed y
	private static LnStrm<PairWithSum> pairsWithFixedY(int y) {
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

	// Helper: Generate infinite stream of streams, one for each y
	private static LnStrm<LnStrm<PairWithSum>> allPairStreams() {
		return allPairStreamsHelper(1);
	}

	private static LnStrm<LnStrm<PairWithSum>> allPairStreamsHelper(int y) {
		return new LnStrm<>(() -> {
			LnStrm<PairWithSum> pairsForY = pairsWithFixedY(y);
			return new LnStcn<>(pairsForY, allPairStreamsHelper(y + 1));
		});
	}

	// Copy mergeLnStrm from Assign06_01
	private static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
		return new LnStrm<T>(() -> {
			FnList<LnStrm<T>> holder = new FnList<>();
			LnStcn<LnStrm<T>> outerNode = fxss.eval0();

			if (outerNode.nilq()) {
				return new LnStcn<>();
			}

			while (outerNode.consq()) {
				LnStrm<T> innerStream = outerNode.head;
				holder = new FnList<>(innerStream, holder);
				outerNode = outerNode.tail.eval0();
			}

			FnList<LnStcn<T>> evaluatedNodes = new FnList<>();
			FnList<LnStrm<T>> currentList = holder;

			while (currentList.consq()) {
				LnStrm<T> stream = currentList.hd();
				LnStcn<T> node = stream.eval0();

				if (node.consq()) {
					evaluatedNodes = new FnList<>(node, evaluatedNodes);
				}

				currentList = currentList.tl();
			}

			if (evaluatedNodes.nilq()) {
				return new LnStcn<>();
			}

			LnStcn<T> minNode = evaluatedNodes.hd();
			T minHead = minNode.head;
			FnList<LnStcn<T>> remaining = evaluatedNodes.tl();

			while (remaining.consq()) {
				LnStcn<T> currentNode = remaining.hd();
				T currentHead = currentNode.head;

				if (cmpr.applyAsInt(currentHead, minHead) < 0) {
					minHead = currentHead;
					minNode = currentNode;
				}

				remaining = remaining.tl();
			}

			FnList<LnStrm<T>> newStreams = new FnList<>();
			FnList<LnStcn<T>> nodeList = evaluatedNodes;

			while (nodeList.consq()) {
				LnStcn<T> node = nodeList.hd();

				if (node == minNode) {
					if (node.tail != null) {
						newStreams = new FnList<>(node.tail, newStreams);
					}
				} else {
					LnStrm<T> reconstructed = new LnStrm<>(() -> node);
					newStreams = new FnList<>(reconstructed, newStreams);
				}

				nodeList = nodeList.tl();
			}

			LnStrm<LnStrm<T>> newFxss = fnListToLnStrm(newStreams);
			return new LnStcn<>(minHead, mergeLnStrm(newFxss, cmpr));
		});
	}

	private static <T> LnStrm<LnStrm<T>> fnListToLnStrm(FnList<LnStrm<T>> list) {
		return new LnStrm<>(() -> {
			if (list.nilq()) {
				return new LnStcn<>();
			}
			return new LnStcn<>(list.hd(), fnListToLnStrm(list.tl()));
		});
	}

	public static LnStrm<FnTupl2<Integer, Integer>> cubeSumOrderedIntegerPairs() {
		// Comparator that compares the cube sums
		ToIntBiFunction<PairWithSum, PairWithSum> comparator = (p1, p2) -> Long.compare(p1.sum, p2.sum);

		// Merge using the comparator
		LnStrm<PairWithSum> merged = mergeLnStrm(allPairStreams(), comparator);

		// Convert PairWithSum to FnTupl2
		return convertToTupl2(merged);
	}

	private static LnStrm<FnTupl2<Integer, Integer>> convertToTupl2(LnStrm<PairWithSum> stream) {
		return new LnStrm<>(() -> {
			LnStcn<PairWithSum> node = stream.eval0();
			if (node.nilq()) {
				return new LnStcn<>();
			}
			FnTupl2<Integer, Integer> pair = new FnTupl2<>(node.head.x, node.head.y);
			return new LnStcn<>(pair, convertToTupl2(node.tail));
		});
	}

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

			return findRamanujanHelper(node.head, node.tail);
		});
	}

	private static LnStcn<Integer> findRamanujanHelper(
			PairWithSum prev,
			LnStrm<PairWithSum> rest) {

		LnStcn<PairWithSum> node = rest.eval0();

		if (node.nilq()) {
			return new LnStcn<>();
		}

		PairWithSum curr = node.head;

		if (prev.sum == curr.sum) {
			// Found a Ramanujan number!
			int ramanujan = (int) prev.sum;
			return new LnStcn<>(ramanujan, findRamanujan(node.tail));
		} else {
			// Keep searching
			return findRamanujanHelper(curr, node.tail);
		}
	}

	public static void main(String[] args) {
		System.out.println("=== Testing Cube Sum Ordered Pairs ===");
		System.out.println("First 20 pairs:");

		LnStrm<FnTupl2<Integer, Integer>> pairs = cubeSumOrderedIntegerPairs();
		final int[] count = { 0 };

		pairs.foritm0(pair -> {
			if (count[0] < 20) {
				pair.System$out$print();
				System.out.println();
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

} // end of [public class Assign06_02{...}]