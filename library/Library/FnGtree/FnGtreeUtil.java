import java.util.function.Consumer;

public class FnGtreeUtil {
//
    public static<T>
	void DepthFirstSearch
	(FnGtree<T> root, Consumer<? super T> action) {
	FnGtree<T> tree;
	MyStackList<FnGtree<T>> stack = new MyStackList<FnGtree<T>>();
	stack.push$exn(root);
	while (true) {
	    if (stack.isEmpty()) break;
	    tree = stack.pop$raw();
	    action.accept(tree.value());
	    tree.children().foritm((tx) -> stack.push$exn(tx));
	}
	return;
    }
//
    /*
    public static<T>
	void BreadthFirstSearch
	(FnGtree<T> root, Consumer<? super T> action) {
	FnGtree<T> tree;
	MyQueueList<FnGtree<T>> queue = new MyQueueList<FnGtree<T>>();
	queue.enque$exn(root);
	while (true) {
	    if (queue.isEmpty()) break;
	    tree = queue.deque$raw();
	    action.accept(tree);
	    tree.children().foritm((tx) -> queue.enque$exn(tx));
	}
	return;
    }
    */
}
