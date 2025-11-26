import java.util.Random;

//
// HX-2025-11-20: 50 points
// A partial implementation of
// randomized doubly linked binary search tree
// 30 points for reroot and 20 points for insert
//
public class Quiz02_06 {
	Node root = null;
	Random rand = new Random();

	public class Node {
		int key; // key stored in the node
		int size; // size of the tree rooted as the node
		Node parent; // parent of the node
		Node lchild; // left-child of the node
		Node rchild; // right-child of the node

		Node(int key) {
			this.key = key;
			this.size = 1;
			this.parent = null;
			this.lchild = null;
			this.rchild = null;
		}
	}

	// Helper: update size of a node based on children
	private void updateSize(Node n) {
		if (n == null)
			return;
		n.size = 1;
		if (n.lchild != null)
			n.size += n.lchild.size;
		if (n.rchild != null)
			n.size += n.rchild.size;
	}

	// Helper: right rotation at node n
	private void rotateRight(Node n) {
		if (n == null || n.lchild == null)
			return;
		Node p = n.parent;
		Node l = n.lchild;

		// Move l's right child to n's left
		n.lchild = l.rchild;
		if (l.rchild != null)
			l.rchild.parent = n;

		// Make l the parent of n
		l.rchild = n;
		n.parent = l;

		// Connect l to n's former parent
		l.parent = p;
		if (p == null) {
			root = l;
		} else if (p.lchild == n) {
			p.lchild = l;
		} else {
			p.rchild = l;
		}

		// Update sizes
		updateSize(n);
		updateSize(l);
	}

	// Helper: left rotation at node n
	private void rotateLeft(Node n) {
		if (n == null || n.rchild == null)
			return;
		Node p = n.parent;
		Node r = n.rchild;

		// Move r's left child to n's right
		n.rchild = r.lchild;
		if (r.lchild != null)
			r.lchild.parent = n;

		// Make r the parent of n
		r.lchild = n;
		n.parent = r;

		// Connect r to n's former parent
		r.parent = p;
		if (p == null) {
			root = r;
		} else if (p.lchild == n) {
			p.lchild = r;
		} else {
			p.rchild = r;
		}

		// Update sizes
		updateSize(n);
		updateSize(r);
	}

	// Helper: get a random node from tree rooted at n
	private Node getRandomNode(Node n) {
		if (n == null)
			return null;
		int idx = rand.nextInt(n.size);
		return getNodeByIndex(n, idx);
	}

	// Helper: get node at given index (in-order traversal)
	private Node getNodeByIndex(Node n, int idx) {
		if (n == null)
			return null;
		int leftSize = (n.lchild == null) ? 0 : n.lchild.size;

		if (idx < leftSize) {
			return getNodeByIndex(n.lchild, idx);
		} else if (idx == leftSize) {
			return n;
		} else {
			return getNodeByIndex(n.rchild, idx - leftSize - 1);
		}
	}

	public void reroot() {
		// HX-2025-11-20: 30 points
		// [reroot] picks a node RANDOMLY and
		// uses rotations to turn this picked node
		// into the root of a new binary search tree
		// (containing the same set of keys)

		if (root == null)
			return;

		// Pick a random node
		Node target = getRandomNode(root);
		if (target == null || target == root)
			return;

		// Rotate the target node up to become root
		while (target.parent != null) {
			Node p = target.parent;
			if (p.lchild == target) {
				// target is left child, rotate right at parent
				rotateRight(p);
			} else {
				// target is right child, rotate left at parent
				rotateLeft(p);
			}
		}
	}

	public boolean insert(int key) {
		// HX-2025-11-20: 20 points
		// If key is in the tree stored at [root],
		// [insert] does nothing and just returns false
		// If key is not in the tree stored at [root],
		// the key is inserted as a leaf node and the new
		// tree is still a binary search tree and [insert]
		// returns true (to indicate insertion is done).

		if (root == null) {
			root = new Node(key);
			return true;
		}

		Node curr = root;
		while (true) {
			if (key == curr.key) {
				return false; // key already exists
			} else if (key < curr.key) {
				if (curr.lchild == null) {
					curr.lchild = new Node(key);
					curr.lchild.parent = curr;
					// Update sizes along path
					while (curr != null) {
						updateSize(curr);
						curr = curr.parent;
					}
					return true;
				}
				curr = curr.lchild;
			} else {
				if (curr.rchild == null) {
					curr.rchild = new Node(key);
					curr.rchild.parent = curr;
					// Update sizes along path
					while (curr != null) {
						updateSize(curr);
						curr = curr.parent;
					}
					return true;
				}
				curr = curr.rchild;
			}
		}
	}

	// Helper: print tree structure
	private void printTree(Node n, String prefix, boolean isLeft) {
		if (n == null)
			return;
		System.out.println(prefix + (isLeft ? "├── " : "└── ") + n.key + " (size=" + n.size + ")");
		if (n.lchild != null || n.rchild != null) {
			if (n.lchild != null)
				printTree(n.lchild, prefix + (isLeft ? "│   " : "    "), true);
			else
				System.out.println(prefix + (isLeft ? "│   " : "    ") + "├── null");
			if (n.rchild != null)
				printTree(n.rchild, prefix + (isLeft ? "│   " : "    "), false);
			else
				System.out.println(prefix + (isLeft ? "│   " : "    ") + "└── null");
		}
	}

	public void print() {
		if (root == null) {
			System.out.println("Empty tree");
		} else {
			System.out.println("Root: " + root.key + " (size=" + root.size + ")");
			printTree(root, "", false);
		}
	}

	public static void main(String[] args) {
		Quiz02_06 tree = new Quiz02_06();

		// Test insert()
		System.out.println("=== Testing insert() ===");
		System.out.println("Insert 50: " + tree.insert(50)); // true
		System.out.println("Insert 30: " + tree.insert(30)); // true
		System.out.println("Insert 70: " + tree.insert(70)); // true
		System.out.println("Insert 20: " + tree.insert(20)); // true
		System.out.println("Insert 40: " + tree.insert(40)); // true
		System.out.println("Insert 60: " + tree.insert(60)); // true
		System.out.println("Insert 80: " + tree.insert(80)); // true
		System.out.println("Insert 50: " + tree.insert(50)); // false (duplicate)

		System.out.println("\nInitial tree:");
		tree.print();

		// Test reroot()
		System.out.println("\n=== Testing reroot() ===");
		for (int i = 0; i < 3; i++) {
			tree.reroot();
			System.out.println("\nAfter reroot #" + (i + 1) + ":");
			tree.print();
		}
	}
}