import Library.LnStrm.*;
import Library.FnList.*;
import java.util.function.ToIntBiFunction;

// Given a (possibly infinite) linear stream (LnStrm) of ordered linear
// streams where the first elements of these ordered linear streams are
// also ordered, merge them into one single ordered linear stream.

public class Assign06_01 {

    public static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
        return new LnStrm<T>(() -> {
            LnStcn<LnStrm<T>> outer = fxss.eval0();

            if (outer.nilq()) {
                return new LnStcn<>(); // no more streams
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
    private static <T> LnStrm<LnStrm<T>> insertStream(LnStrm<T> s,
            LnStrm<LnStrm<T>> rest,
            ToIntBiFunction<T, T> cmpr) {
        return new LnStrm<LnStrm<T>>(() -> {
            LnStcn<T> sCons = s.eval0();

            if (sCons.nilq()) {
                // nothing to insert (stream s is empty)
                return rest.eval0();
            }

            T headS = sCons.hd();

            LnStcn<LnStrm<T>> restCons = rest.eval0();

            if (restCons.nilq()) {
                // no more streams; s goes at the end
                return new LnStcn<>(s, new LnStrm<>(() -> new LnStcn<>()));
            }

            LnStrm<T> r1 = restCons.hd();
            LnStcn<T> r1Cons = r1.eval0();

            if (r1Cons.nilq()) {
                // first rest stream is empty, skip it
                return insertStream(s, restCons.tl(), cmpr).eval0();
            }

            T headR1 = r1Cons.hd();

            // If s's head <= r1's head, s should come before r1
            if (cmpr.applyAsInt(headS, headR1) <= 0) {
                return new LnStcn<>(s, rest);
            } else {
                // otherwise keep r1 first and insert s further down
                LnStrm<LnStrm<T>> newTail = insertStream(s, restCons.tl(), cmpr);
                return new LnStcn<>(r1, newTail);
            }
        });
    }

    // Test code
    public static void main(String[] args) {
        // Test 1: merge [1,3,5,7] and [2,4,6,8]
        System.out.println("Test 1: Finite streams");
        LnStrm<Integer> stream1 = createStream(1, 3, 5, 7);
        LnStrm<Integer> stream2 = createStream(2, 4, 6, 8);

        LnStrm<LnStrm<Integer>> streamOfStreams = createStreamOfStreams(stream1, stream2);

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

        // Test 2: Test with infinite streams
        System.out.println("\nTest 2: Infinite streams");
        LnStrm<Integer> evens = createInfiniteStream(0, 2); // 0, 2, 4, 6, ...
        LnStrm<Integer> odds = createInfiniteStream(1, 2); // 1, 3, 5, 7, ...

        LnStrm<LnStrm<Integer>> infiniteStreamOfStreams = createStreamOfStreams(evens, odds);

        LnStrm<Integer> mergedInfinite = mergeLnStrm(infiniteStreamOfStreams, cmp);

        count[0] = 0;
        mergedInfinite.foritm0(x -> {
            if (count[0] < 20) {
                System.out.print(x + " ");
                count[0]++;
            }
        });
        System.out.println();

        // Test 3: Three streams
        System.out.println("\nTest 3: Three finite streams");
        LnStrm<Integer> s1 = createStream(1, 4, 7, 10);
        LnStrm<Integer> s2 = createStream(2, 5, 8, 11);
        LnStrm<Integer> s3 = createStream(3, 6, 9, 12);

        LnStrm<LnStrm<Integer>> threeStreams = createStreamOfStreams(s1, s2, s3);
        LnStrm<Integer> merged3 = mergeLnStrm(threeStreams, cmp);

        count[0] = 0;
        merged3.foritm0(x -> {
            if (count[0] < 20) {
                System.out.print(x + " ");
                count[0]++;
            }
        });
        System.out.println();
    }

    // Helper: create stream of streams from variable number of streams
    private static <T> LnStrm<LnStrm<T>> createStreamOfStreams(LnStrm<T>... streams) {
        return createStreamOfStreamsHelper(streams, 0);
    }

    private static <T> LnStrm<LnStrm<T>> createStreamOfStreamsHelper(LnStrm<T>[] streams, int index) {
        return new LnStrm<>(() -> {
            if (index >= streams.length) {
                return new LnStcn<>();
            }
            return new LnStcn<>(streams[index], createStreamOfStreamsHelper(streams, index + 1));
        });
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

    // Helper: create infinite arithmetic sequence
    private static LnStrm<Integer> createInfiniteStream(int start, int step) {
        return new LnStrm<>(() -> new LnStcn<>(start, createInfiniteStream(start + step, step)));
    }
}