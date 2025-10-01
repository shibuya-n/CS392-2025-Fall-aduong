public class MyDequeListTest {

    public static void main(String[] args) {

        MyDequeList<String> test = new MyDequeList<String>();

        test.fenque$exn("one");
        test.fenque$exn("zero");
        test.renque$exn("two");
        test.renque$exn("three");

        test.System$out$print();

        System.out.println();
        System.out.println(test.rdeque$opt());
        System.out.println(test.fpeek$opt());
        System.out.println(test.fdeque$opt());

        test.System$out$print();

        System.out.println();
        System.out.print("My Deque: (");
        test.irforitm(
                (i, x) -> {

                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(x);

                });
        System.out.print(")");

        System.out.println();
        System.out.println(test.rpeek$opt());

    }

}
