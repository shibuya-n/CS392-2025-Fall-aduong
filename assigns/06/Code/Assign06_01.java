import Library.LnStrm.*;
import Library.FnList.*;
import java.util.function.ToIntBiFunction;

// Given a (possibly infinite) linear stream (LnStrm) of ordered linear
// streams where the first elements of these ordered linear streams are
// also ordered, merge them into one single ordered linear stream.

public class Assign06_01 {

    public static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
        return new LnStrm<T>(() -> {
            // Evaluate the outer stream to get the first node
            LnStcn<LnStrm<T>> outerNode = fxss.eval0();

            // If no streams, return empty
            if (outerNode.nilq()) {
                return new LnStcn<>();
            }

            // Collect all non-empty streams into a list
            FnList<LnStcn<T>> nonEmptyNodes = FnListSUtil.nil();
            LnStcn<LnStrm<T>> currentOuter = outerNode;

            while (currentOuter.consq()) {
                LnStrm<T> innerStream = currentOuter.hd();
                LnStcn<T> innerNode = innerStream.eval0();

                // Only keep non-empty streams
                if (innerNode.consq()) {
                    nonEmptyNodes = FnListSUtil.cons(innerNode, nonEmptyNodes);
                }

                currentOuter = currentOuter.tl().eval0();
            }

            // If all streams were empty, return empty
            if (nonEmptyNodes.nilq()) {
                return new LnStcn<>();
            }

            // Find the minimum element among all stream heads
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

            // Build the new stream of streams (advance the winner, keep others)
            FnList<LnStrm<T>> newStreams = FnListSUtil.nil();
            FnList<LnStcn<T>> allNodes = nonEmptyNodes;

            while (allNodes.consq()) {
                LnStcn<T> node = allNodes.hd();

                if (node == minNode) {
                    // This stream had the minimum - advance it
                    LnStrm<T> advancedStream = node.tl();
                    newStreams = FnListSUtil.cons(advancedStream, newStreams);
                } else {
                    // This stream didn't have minimum - reconstruct it with current node
                    LnStrm<T> reconstructed = new LnStrm<>(() -> node);
                    newStreams = FnListSUtil.cons(reconstructed, newStreams);
                }

                allNodes = allNodes.tl();
            }

            // Convert the list of streams back to a stream of streams
            LnStrm<LnStrm<T>> newFxss = listToStreamOfStreams(newStreams);

            // Return the minimum value with the rest merged recursively
            return new LnStcn<>(minValue, mergeLnStrm(newFxss, cmpr));
        });
    }

    // Helper to convert FnList<LnStrm<T>> to LnStrm<LnStrm<T>>
    private static <T> LnStrm<LnStrm<T>> listToStreamOfStreams(FnList<LnStrm<T>> list) {
        return new LnStrm<>(() -> {
            if (list.nilq()) {
                return new LnStcn<>();
            }
            return new LnStcn<>(list.hd(), listToStreamOfStreams(list.tl()));
        });
    }

    // Test code
    public static void main(String[] args) {
        // Create test: merge [1,3,5,7] and [2,4,6,8]
        LnStrm<Integer> stream1 = createStream(1, 3, 5, 7);
        LnStrm<Integer> stream2 = createStream(2, 4, 6, 8);

        LnStrm<LnStrm<Integer>> streamOfStreams = new LnStrm<>(
                () -> new LnStcn<>(stream1, new LnStrm<>(() -> new LnStcn<>(stream2, new LnStrm<>()))));

        ToIntBiFunction<Integer, Integer> cmp = (a, b) -> a.compareTo(b);
        LnStrm<Integer> merged = mergeLnStrm(streamOfStreams, cmp);

        System.out.println("Merged stream:");
        final int[] count = { 0 };
        merged.foritm0(x -> {
            if (count[0] < 20) {
                System.out.print(x + " ");
                count[0]++;
            }
        });
        System.out.println();
    }

    private static LnStrm<Integer> createStream(int... values) {
        return createStreamHelper(values, 0);
    }

    private static LnStrm<Integer> createStreamHelper(int[] values, int index) {
        return new LnStrm<>(() -> {
            if (index >= values.length) {
                return new LnStcn<>();
            }
            return new LnStcn<>(values[index], createStreamHelper(values, index + 1));
        });
    }
}