package MyFinalLib.MyDeque;

import FnList.*;
import LnStrm.*;
import MyDeque.*;
import FnGtree.*;

import java.util.function.Consumer;

public class DequeEnumerate {

    // Breadth-First Enumeration using MyDequeList as a Queue
    public static <T> LnStrm<T> BFirstEnumerate(FnGtree<T> root) {
        MyDequeList<FnGtree<T>> queue = new MyDequeList<FnGtree<T>>();
        queue.renque$exn(root); // Enqueue at rear
        return BFirstEnumerate_helper(queue);
    }

    private static <T> LnStrm<T> BFirstEnumerate_helper(MyDequeList<FnGtree<T>> queue) {
        return new LnStrm<T>(
                () -> {
                    if (queue.isEmpty()) {
                        return new LnStcn<T>();
                    } else {
                        FnGtree<T> node = queue.fdeque$raw(); // Dequeue from front
                        node.children().foritm((tx) -> queue.renque$exn(tx)); // Enqueue children at rear
                        return new LnStcn<T>(node.value(), BFirstEnumerate_helper(queue));
                    }
                });
    }

    // Depth-First Enumeration using MyDequeList as a Stack
    public static <T> LnStrm<T> DFirstEnumerate(FnGtree<T> root) {
        MyDequeList<FnGtree<T>> stack = new MyDequeList<FnGtree<T>>();
        stack.fenque$exn(root); // Push at front
        return DFirstEnumerate_helper(stack);
    }

    private static <T> LnStrm<T> DFirstEnumerate_helper(MyDequeList<FnGtree<T>> stack) {
        return new LnStrm<T>(
                () -> {
                    if (stack.isEmpty()) {
                        return new LnStcn<T>();
                    } else {
                        FnGtree<T> node = stack.fdeque$raw(); // Pop from front
                        node.children().rforitm((tx) -> stack.fenque$exn(tx)); // Push children at front
                        return new LnStcn<T>(node.value(), DFirstEnumerate_helper(stack));
                    }
                });
    }

} // end of [public class Assign07_01{...}]