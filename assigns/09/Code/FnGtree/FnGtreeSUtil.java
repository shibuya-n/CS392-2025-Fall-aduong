package FnGtree;

import FnList.*;
import LnStrm.*;
import MyPQueue.*;

public class FnGtreeSUtil {

	// Small wrapper so we can use MyPQueueArray (which expects Comparable elements)
	private static class PQNode<T> implements Comparable<PQNode<T>> {
		final FnGtree<T> node;

		PQNode(FnGtree<T> node) {
			this.node = node;
		}

		@Override
		public int compareTo(PQNode<T> other) {
			int p1 = node.priority();
			int p2 = other.node.priority();
			return Integer.compare(p1, p2);
		}
	}

	//
	public static <T> LnStrm<T> PFirstEnumerate(FnGtree<T> root) {
		// Priority-first enumeration using a heap-based priority queue.
		// At each step, we:
		// - pop the node with the smallest priority()
		// - output its value()
		// - push all of its children() into the priority queue

		// You can choose a reasonable upper bound for the frontier size.
		// For most assignments, something like this is enough.
		final int CAPACITY = 1_000_000;

		MyPQueueArray<PQNode<T>> pq = new MyPQueueArray<>(CAPACITY);

		// Initialize frontier with the root
		pq.enque$raw(new PQNode<>(root));

		// Collect values in a functional list, in reverse order
		FnList<T> acc = FnListSUtil.nil();

		while (!pq.isEmpty()) {
			// Remove min-priority node
			PQNode<T> pqn = pq.deque$raw();
			FnGtree<T> cur = pqn.node;

			// Record its value
			acc = FnListSUtil.cons(cur.value(), acc);

			// Enqueue all children
			FnList<FnGtree<T>> kids = cur.children();
			FnList<FnGtree<T>> xs = kids;
			while (!xs.nilq()) {
				pq.enque$raw(new PQNode<>(xs.hd()));
				xs = xs.tl();
			}
		}

		// acc is in reverse order; fix it, then convert to a lazy stream
		FnList<T> result = FnListSUtil.reverse(acc);
		return FnListSUtil.strmize(result);
	}
	//
} // end of [public class FnGtreeSUtil{...}]
