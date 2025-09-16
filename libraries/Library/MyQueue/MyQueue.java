import java.util.function.Consumer;
import java.util.function.BiConsumer;

interface MyQueue<T> {
//
    int size();
//
    boolean isFull(); // checks for fullness
    boolean isEmpty(); // checks for emptiness
//
    T top$raw(); // defined if !isEmpty()
    T top$opt(); // defined if !isEmpty() // T is optional
    T top$exn() throws MyQueueEmptyExn; // defined if !isEmpty() 
//
    T deque$raw(); // defined if !isEmpty()
    T deque$opt(); // defined if !isEmpty() // T is optional
    T deque$exn() throws MyQueueEmptyExn; // defined if !isEmpty() 
//
    void enque$raw(T itm); // defined if !isFull()
    void enque$exn(T itm) throws MyQueueFullExn; // defined if !isFull()
    boolean enque$opt(T itm); // defined if !isFull() // true/false: succ/fail
//
    void foritm(Consumer<? super T> action);
    void iforitm(BiConsumer<Integer, ? super T> action);
//
    void System$out$print();
}
