package vladimir.chugunov.Tree;

import interfaces.IVisitor;
import interfaces.Tree.ITree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import lisa.kapitonova.Lists.Visitor;

/**
 * Typical binary tree storing {@link Integer} values
 * 
 * @author Alpen Ditrix
 * @see {@link ITree}
 */
public class BinaryTree implements interfaces.Tree.ITree {

	/**
	 * Basic storage element of tree
	 * 
	 * @author Alpen Ditrix
	 */
	private static class Node {
		Integer	value;
		Node	left;
		Node	right;

		Node(Node l, Node r, Integer v) {
			value = v;
			left = l;
			right = r;
		}

		/**
		 * Adds a child to this node with {@link #value} {@code== v}. If there was another child it
		 * will be replaced
		 * 
		 * @param v
		 */
		void createChild(Integer v) {
			if (v == null || v < value) {
				left = new Node(null, null, v);
			} else {
				right = new Node(null, null, v);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public String toString() {
			return value == null ? "null" : value.toString();
		}

		/**
		 * Removes leaf node which is child of this node
		 * 
		 * @param node
		 *            which to remove
		 * @return value of removed node
		 * @throws IllegalStateException
		 *             if node intented to remove actually is not a leaf
		 * @throws IllegalArgumentException
		 *             if node intented to remove is not a child of this node
		 * 
		 */
		Integer removeLeaf(Node node) {
			if (left != null && left.equals(node)) {
				
				if (left.left != null || left.right != null) {
					throw new IllegalStateException("Wrong specified leaf");
				}

				Integer retVal = left.value;
				left = null;
				return retVal;
			} else if (right != null && right.equals(node)) {

				if (right.left != null || right.right != null) {
					throw new IllegalStateException("Wrong specified leaf");
				}

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
	private int		depth;

	/**
	 * Root of the node. There's no no node which has link on it
	 */
	private Node	root;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Integer value) {
		invalidateDepthCache();
		if (root == null) {
			root = new Node(null, null, value);
		} else {
			getNodeToAdd(value).createChild(value);
		}
	}

	/**
	 * Content of tree changed, hence previosly computed value is not valid
	 */
	private void invalidateDepthCache() {
		depth = -1;
	}

	/**
	 * @param value
	 * @return {@link Node}, which may be parent of new {@link Node} with {@link Node#value}
	 *         {@code == value} (this param)
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Some extraordinary magic
	 * 
	 * @param node
	 * @param parent
	 * @return
	 */
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
			if (node != root) {
				throw new IllegalArgumentException();
			}
			Integer retVal = root.value;
			root = null;
			return retVal;
		} else {
			return parent.removeLeaf(node);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int depth() {
		if (depth < 0) {
			depth = getDepth(root);
		}
		return depth;
	}

	/**
	 * @param n
	 *            root of current subtree
	 * @return depth of current subtree
	 */
	public int getDepth(Node n) {
		if (n == null) {
			return 0;
		}

		int l = getDepth(n.left);
		int r = getDepth(n.right);
		return Math.max(l, r) + 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized int getSizeOfLevel(int level) {
		LevelCounter lc = new LevelCounter(root);
		lc.computeWidthOfLevel(level);
		return lc.getResult();
	}

	/**
	 * Class for hiding implementation of level width computation
	 * 
	 * @author Alpen Ditrix
	 */
	public class LevelCounter {

		/**
		 * Node from which computation will start
		 */
		private final Node	subroot;
		private int			levelCounter	= 0;

		/**
		 * @param root
		 *            node frome where to start computation
		 */
		LevelCounter(Node root) {
			subroot = root;
		}

		/**
		 * @return result of computation
		 */
		int getResult() {
			return levelCounter;
		}

		/**
		 * Computes amount of elements on depth {@code level}
		 * 
		 * @param level
		 */
		void computeWidthOfLevel(int level) {
			computeWidth(subroot, level, 0);
		}

		/**
		 * Goes through subtree of {@code n} from {@code currDepth} to {@code level}. Each time they
		 * equals {@link LevelCounter} will be incremented
		 * 
		 * @param n
		 *            root of current subtree
		 * @param level
		 *            where to go
		 * @param currDepth
		 *            current depth from {@link LevelCounter#subroot}
		 */
		private void computeWidth(Node n, int level, int currDepth) {
			if (n == null) {
				return;
			} else if (level == currDepth) {
				levelCounter++;
			} else {
				if (n.left != null) {
					computeWidth(n.left, level, currDepth + 1);
				}
				if (n.right != null) {
					computeWidth(n.right, level, currDepth + 1);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void depthFirstVisiting(IVisitor visitor) {
		dfs(root, visitor, 0);
	}

	/**
	 * Visits every node in this tree int the natural order (low value first, high value last) and
	 * acts on it.
	 * 
	 * @param visitor
	 */
	public void sortedVisiting(IVisitor visitor) {
		dfs(root, visitor, 1);
	}

	/**
	 * Visits every node in this tree (using DFS algorithm) and acts on it.
	 * 
	 * @param visitor
	 * @param n rott of current subtree
	 * @param when when to execute {@link Visitor}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		depth();// cache depth value
		printer(sb, root, 0);
		return sb.toString();
	}

	/**
	 * Creates string which represents subtree of node {@code n} as {@link String}
	 * 
	 * @param sb
	 *            where to append element
	 * @param n
	 *            root of current subtree
	 * @param currDepth
	 */
	private void printer(StringBuilder sb, Node n, int currDepth) {
		if (depth == -1) {
			throw new ConcurrentModificationException();
		}

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
		int level = 2;
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
		System.out.println(tree);
		System.out.println();
		tree.sortedVisiting(new Printer());
	}

	public static final class Printer implements IVisitor {

		@Override
		public void visit(Object obj) {
			System.out.println(obj);
		}

	}

	@Override
	public Iterator getIterator() {
		return null;
	};
	/*
	 * private class DfsTreeIterator implements Iterator {
	 * 
	 * private Stack<Node> pathToHere = new Stack<>();
	 * private Node next = root;
	 * private Node returnedToRemove;
	 * 
	 * public DfsTreeIterator(){
	 * next = root;
	 * }
	 * 
	 * @Override
	 * public boolean hasNext() {
	 * return next != null;
	 * }
	 * 
	 * @Override
	 * public Object next() {
	 * pathToHere.add(next);
	 * returnedToRemove = next;
	 * Node retVal = next;
	 * 
	 * next = newNext();
	 * }
	 * 
	 * private Node newNext(Node n) {
	 * 1 if(n.left!=null){
	 * return n.left;
	 * } else if(n.right !=null){
	 * return n.right;
	 * } else {
	 * return
	 * }
	 * }
	 * 
	 * @Override
	 * public void remove() {
	 * BinaryTree.this.removeNode(node, parent)
	 * }
	 * }
	 */

}
