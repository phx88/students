package interfaces.Tree;

import interfaces.IVisitor;

public class TreeMain {
	
	static int level;
	static ITree tree;
	static IVisitor visitor;
	
	public static void main(String[] args) {
		
		tree.add(1);
		tree.add(13);
		tree.add(21);
		tree.add(5);
		tree.add(7);
		tree.add(0);
		tree.add(-1);
		tree.add(10);
		tree.add(null);
		
		System.out.println("tree depth: " + tree.depth());
		System.out.println("number of nodes on level " + level + ": " + tree.getSizeOfLevel(level));
		
		System.out.println(" ---- contains ---- ");
		System.out.println(tree.contains(7));
		System.out.println(tree.contains(100));

		System.out.println(" ---- remove ---- ");
		System.out.print(tree.contains(13) + "  remove(13)  " );
		tree.remove(13);
		System.out.println(tree.contains(13));
		System.out.println("tree depth: " + tree.depth());
		System.out.println("number of nodes on level " + level + ": " + tree.getSizeOfLevel(level));
				
		System.out.println(" ---- visitor ---- ");
		tree.depthFirstVisiting(visitor);
	}
}
