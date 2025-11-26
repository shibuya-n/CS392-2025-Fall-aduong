//
// HX-2025-11-20: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
public class Quiz02_05 {
    public static class RBTnode {
        int key;
        int color; // Red = 0; Black = 1
        RBTnode lchild;
        RBTnode rchild;
    }

    //
    // HX: 10 points for this one
    // HX: If your implementation only
    // visit each node in [rbt] at most once,
    // then it will be rewarded with some bonus
    // points (up to 20 bonus points).
    // For instance, if you compute the size of
    // height of a tree, then you already visit
    // each node once.
    //

    public static int checkRBT(RBTnode rbt) {
        if (rbt == null)
            return 0;

        if (rbt.color == 0) {
            if (rbt.lchild != null && rbt.lchild.color == 0)
                return -1;
            if (rbt.rchild != null && rbt.rchild.color == 0)
                return -1;
        }

        int leftBH = checkRBT(rbt.lchild);
        if (leftBH == -1)
            return -1;

        int rightBH = checkRBT(rbt.rchild);
        if (rightBH == -1)
            return -1;

        if (leftBH != rightBH)
            return -1;

        return leftBH + (rbt.color == 1 ? 1 : 0);

    }

    public static boolean isRBT(RBTnode rbt) {
        // HX: Please implement a function that
        // tests whether a given RBTnode is a valid
        // red-black tree. If it is unclear what a
        // red-black tree, you can readily find it on-line
        // Note that you are not asked to check if rbt is
        // a binary search tree in this case.

        if (rbt == null) {
            return true;
        }

        if (rbt.color != 1)
            return false;

        return (checkRBT(rbt) != -1);
    }

    private static RBTnode buildBalancedRBT(int lo, int hi, int depth) {
        if (lo > hi)
            return null;

        int mid = lo + (hi - lo) / 2;
        RBTnode node = new RBTnode();
        node.key = mid;

        node.color = (depth % 2 == 0) ? 1 : 0;

        node.lchild = buildBalancedRBT(lo, mid - 1, depth + 1);
        node.rchild = buildBalancedRBT(mid + 1, hi, depth + 1);

        return node;

    }

    //
    // HX: 20 points
    // This is largely about understanding red-black trees.
    // Please explain BRIEFLY as to why the generated RBT is
    // of minimal black height (not height).
    //
    public static boolean genRedBLackBST() {
        // Please genenerate a binary search RBT that
        // contains exactly 1 million keys: 0, 1, 2, ..., 999999
        // such that the black height (not height) of this tree is
        // minimal (that is, as small as possible). What is this black
        // height? Please give a brief explanation on your implementation
        // strategy.

        RBTnode root = buildBalancedRBT(0, 999999, 0);

        return isRBT(root);

    }

    public static void main(String[] args) {
        // Please add minimal testing code for isRBT()
        // Please add minimal testing code for genRedBlackBST()
        // Test 1: root black, two red children (valid RBT)
        RBTnode t1 = new RBTnode();
        t1.key = 30;
        t1.color = 1; // black

        t1.lchild = new RBTnode();
        t1.lchild.key = 20;
        t1.lchild.color = 0; // red

        t1.rchild = new RBTnode();
        t1.rchild.key = 40;
        t1.rchild.color = 0; //

        System.out.println("test1: " + isRBT(t1));

        // Test 2: unequal black height (invalid)
        // Root black, left child black, right child red
        RBTnode t2 = new RBTnode();
        t2.key = 60;
        t2.color = 1; // black

        t2.lchild = new RBTnode();
        t2.lchild.key = 55;
        t2.lchild.color = 1; // black

        t2.rchild = new RBTnode();
        t2.rchild.key = 65;
        t2.rchild.color = 0; // red

        System.out.println("test1: " + isRBT(t2));

        // should return true if BST is a valid array
        boolean works = genRedBLackBST();
        System.out.println("gen works: " + works);
    }
}
