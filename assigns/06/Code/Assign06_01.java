import Library.LnStrm.*;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;
import Library.FnList.*;

// Given a (possibly infinite) linear stream (LnStrm) of ordered linear
// streams where the first elements of these ordered linear streams are
// also ordered (that is, the first element of the first stream is less
// than the first element of the second stream, which is less than the
// first element of the third stream, and so on, and so forth), you are
// asked to implement a static method to merge them into one single ordered
// linear stream.

public class Assign06_01 {
    //
    public static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
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

                if (node.consq()) { // Only keep non-empty streams
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
                    // This is the winner - advance it
                    if (node.tail != null) {
                        newStreams = new FnList<>(node.tail, newStreams);
                    }
                } else {
                    // Not the winner - reconstruct as stream with this node at front
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

    //
    public static void main(String[] args) {

    }
} // end of [public class Assign06_01{...}]
