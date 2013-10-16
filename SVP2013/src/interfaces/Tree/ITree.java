package interfaces.Tree;

import interfaces.IVisitor;

public interface ITree {

	/**
	 * Assigns some node to specified value and adds it to the tree
	 * 
	 * @param value
	 */
	public void add(Integer value);

	/**
	 * @param value
	 * @return true if on of the nodes of the tree already assigned to specified value
	 */
	public boolean contains(Integer value);

	/**
	 * Removes from the tree (first met) node which is assigned to specified value
	 * 
	 * @param value
	 * @return false if there is no such node
	 */
	public boolean remove(Integer value);

	/**
	 * @return maximum value of length between root-node and any other stored in this tree
	 */
	public int depth();

	/**
	 * Counts nodes lying in specified level of the tree. "Level" means a set of nodes with similiar
	 * length (depth) from the root-node
	 * 
	 * @param level
	 * @return amount of nodes in this level
	 */
	public int getSizeOfLevel(int level);

	/**
	 * Visits every node in this tree (using DFS algorithm) and acts on it.
	 * 
	 * @param visitor
	 */
	public void depthFirstVisiting(IVisitor visitor);
}
