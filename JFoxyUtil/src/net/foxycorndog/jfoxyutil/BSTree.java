package net.foxycorndog.jfoxyutil;

/**
 * Interface used for a Binary Search Tree.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 11, 2013 at 9:31:13 AM
 * @since	v0.1
 * @version	Jul 3, 2013 at 12:31:13 AM
 * @version	v0.1
 */
public interface BSTree
{
	/**
	 * Adds a Comparable to the tree using the compare method
	 * to determine where to put it.
	 * 
	 * @param c The Comparable to add to the Tree.
	 */
	public void add(Comparable c);
	
	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 * 
	 * @param c The Comparable instance to search for in the Tree.
	 * @return Whether the Comparable is in the tree.
	 */
	public boolean search(Comparable c);
	
	/**
	 * Returns the maximum length of any leaf in the tree.
	 */
	public int height();
	
	/**
	 * Prints the Tree using Pre-Order traversal.
	 */
	public String toStringPreOrder();
	
	/**
	 * Prints the Tree elements using In-Order traversal.
	 */
	public String toStringInOrder();
	
	/**
	 * Removes the specified Comparable from the tree.
	 * 
	 * @param c The Comparable instance to remove from the tree.
	 */
	public boolean remove(Comparable c);
}