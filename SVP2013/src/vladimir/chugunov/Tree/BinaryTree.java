package vladimir.chugunov.Tree;

import interfaces.IVisitor;

import java.util.ConcurrentModificationException;

public class BinaryTree implements interfaces.Tree.ITree {

	private static class Node {
		Integer	value;
		Node	left;
		Node	right;

		public Node(Node l, Node r, Integer v) {
			value = v;
			left = l;
			right = r;
		}

		public void createSibling(Integer v) {
			if (v == null || v < value) {
				left = new Node(null, null, v);
			} else {
				right = new Node(null, null, v);
			}
		}

		public String toString() {
			return value == null ? "null" : value.toString();
		}

		public Integer removeLeaf(Node node) {
			if (left != null && left.equals(node)) {
				if (left.left != null || left.right != null) { throw new IllegalStateException(); }
				Integer retVal = left.value;
				left = null;
				return retVal;
			} else if (right != null && right.equals(node)) {
				if (right.left != null || right.right != null) { throw new IllegalStateException(); }
				Integer retVal = right.value;
				right = null;
				return retVal;
			}
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Chached depth value
	 */
	private int	depth;

	/**
	 * Root of the node. There's no no node which has link on it
	 */
	private Node	root;

	{
		System.out.println(sc(123));
	}
	int sc(int a){
		return (a==0)?0:a%10+sc(a/10);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Integer value) {
		char[] c = {'2'};
		
		String s = new String(c);
		invalidateDepthCache();
		if (root == null) {
			root = new Node(null, null, value);
		} else {
			getNodeToAdd(value).createSibling(value);
		}
	}

	/**
	 * Content of tree changed so previosly computed value is not valid
	 */
	private void invalidateDepthCache() {
		depth = -1;
	}

	private Node getNodeToAdd(Integer value) {
		Node p1 = root;
		Node p2 = null;
		while (p1 != null) {
			p2 = p1;
			if (value == null || value < p1.value) {
				p1 = p1.left;
			} else {
				p1 = p1.right;
			}
		}
		return p2;
	}

	@Override
	public boolean contains(Integer value) {
		Node node = root;
		while (node != null) {
			if (node.value == value) {
				return true;
			} else {
				node = (value == null || value < node.value) ? node.left : node.right;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Integer value) {
		Node parent = null;
		Node node = root;
		while (node != null) {
			if (node.value == value) {
				removeNode(node, parent);
				invalidateDepthCache();
				return true;
			} else {
				parent = node;
				node = (value == null || value < node.value) ? node.left : node.right;
			}
		}
		return false;
	}

	private Integer removeNode(Node node, Node parent) {
		if (node.left != null) {
			Node n = node.left;
			Node nParent = node;
			while (n.right != null) {
				nParent = n;
				n = n.right;
			}
			Integer retVal = node.value;
			node.value = removeNode(n, nParent);
			return retVal;
		} else if (node.right != null) {
			Node n = node.right;
			Node nParent = node;
			while (n.left != null) {
				nParent = n;
				n = n.left;
			}
			Integer retVal = node.value;
			node.value = removeNode(n, nParent);
			return retVal;
		} else if (parent == null) {
			if (node != root) { throw new IllegalArgumentException(); }
			Integer retVal = root.value;
			root = null;
			return retVal;
		} else {
			return parent.removeLeaf(node);
		}
	}

	@Override
	public int depth() {
		if (depth < 0) {
			depth = getDepth(root);
		}
		return depth;
	}

	public int getDepth(Node n) {
		if (n == null) { return 0; }

		int l = getDepth(n.left);
		int r = getDepth(n.right);
		return Math.max(l, r) + 1;
	}

	private int	levelCounter;

	@Override
	public synchronized int getSizeOfLevel(int level) {
		levelCounter = 0;
		getLevelWidth(root, level, 0);
		return levelCounter;
	}

	private void getLevelWidth(Node n, int level, int currDepth) {
		if (n == null) {
			return;
		} else if (level == currDepth) {
			levelCounter++;
		} else {
			if (n.left != null) {
				getLevelWidth(n.left, level, currDepth + 1);
			}
			if (n.right != null) {
				getLevelWidth(n.right, level, currDepth + 1);
			}
		}
	}

	@Override
	public void depthFirstVisiting(IVisitor visitor) {
		dfs(root, visitor, 0);
	}
	
	public void sortedVisiting(IVisitor visitor){
		dfs(root, visitor, 1);
	}

	private void dfs(Node n, IVisitor visitor, int when) {
		if (when == 0) {
			visitor.visit(n);
		}
		if (n.left != null) {
			dfs(n.left, visitor, when);
		}
		if (when == 1) {
			visitor.visit(n);
		}
		if (n.right != null) {
			dfs(n.right, visitor, when);
		}
		if (when == 2) {
			visitor.visit(n);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		depth();// cache depth value
		printer(sb, root, 0);
		return sb.toString();
	}

	private void printer(StringBuilder sb, Node n, int currDepth) {
		if (depth == -1) { throw new ConcurrentModificationException(); }

		if (n.left != null) {
			printer(sb, n.left, currDepth + 1);
		}

		for (int i = 0; i < 2 * (depth - currDepth - 1); i++) {
			sb.append(' ');
		}
		sb.append(n.value);
		sb.append("——\n");

		if (n.right != null) {
			printer(sb, n.right, currDepth + 1);
		}
	}

	public static void main(String[] args) {
		final BinaryTree tree = new BinaryTree();
		int level = 7;
		IVisitor visitor = new Printer();

		tree.add(1);
		tree.add(13);
		tree.add(21);
		tree.add(5);
		tree.add(7);
		tree.add(0);
		tree.add(-1);
		tree.add(10);
		tree.add(8);
		tree.add(9);
		tree.add(null);

		System.out.println("tree depth: " + tree.depth());
		System.out.println("number of nodes on level " + level + ": " + tree.getSizeOfLevel(level));

		System.out.println(" ---- contains ---- ");
		System.out.println(tree.contains(7));
		System.out.println(tree.contains(100));

		System.out.println(" ---- remove ---- ");
		System.out.print(tree.contains(13) + "  remove(13)  ");
		tree.remove(13);
		System.out.println(tree.contains(13));
		System.out.println("tree depth: " + tree.depth());
		System.out.println("number of nodes on level " + level + ": " + tree.getSizeOfLevel(level));

		System.out.println(" ---- visitor ---- ");
		tree.depthFirstVisiting(visitor);
		
		System.out.println();
		tree.sortedVisiting(new Printer());
	}

	public static final class Printer implements IVisitor {

		@Override
		public void visit(Object obj) {
			System.out.println(obj);
		}

	};

}
