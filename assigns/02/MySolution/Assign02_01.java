public class Assign02_01 {
    /*
     * HX-2025-02-13: 10 points
     * The 'int' type in Java is for integers of some fixed precision.
     * More precisely, each integer of the type int is represented as a sequence of
     * bits
     * of some fixed length. Please write a program that finds this length. Your
     * program
     * is expected return the correct answer instantly. Note that you can only use
     * arithmetic
     * operations here. In particular, NO BIT-WISE OPERATIONS ARE ALLOWED.
     */

    public static int getBitLength(int n) {
        if (n <= 0) {
            return -1;
        }
        return (int) (Math.log(n) / Math.log(2)) + 1;
        // log(n)/log(2) makes log2(n), which is important as we want the number of the
        // bits, which is counted in powers of 2.
        // (int) floors the decimal result of the log
        // add one to get the number of bits required
    }

    public static void main(String[] argv) {
        // Please give your implementation here
        int x = 32;

        System.out.println(getBitLength(x));

    }
}
