package interfaces.Tree;

import interfaces.IVisitor;

public interface ITree {
	
	public void add(Integer value);
	public boolean contains(Integer value);
	public boolean remove(Integer value);
		
	/**
	 * @return deep of this tree.
	 */
	public int getTreeDeep();

	/**
	 * @param level
	 * @return number of nodes on <code>level</code>
	 */
	public int getNumberOfNode(int level);
	
	/**
	 * @param visitor 
	 */
	public void goLeftTree(IVisitor visitor); 
}
