/*
  HX-2025-09-15: 10 points
*/
public class Assign03_01 {
    //
    // HX-2025-09-15:
    // This implementation of f91
    // is not tail-recursive. Please
    // translate it into a version that
    // is tail-recursive
    //

    public static int f91(int n) {
        if (n > 100) {
            return n - 10;
        }

        else {
            return f91(f91(n + 11));
        }

    }

    public static int tailF91(int n, int x) {
        if (x == 0) {
            return n;
        } else if ((n > 100) && (x != 0)) {
            return tailF91(n - 10, x - 1);
        } else {
            return tailF91(n + 11, x + 1);

        }
    }

    public static int tailF91(int n) {
        return tailF91(n, 1);
    }

    public static void main(String[] argv) {
        // Please write some testing code here
        System.out.println(tailF91(70));
        System.out.println(tailF91(102));
    }
}
