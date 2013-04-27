package net.foxycorndog.jfoxyutil;

/**
 * File:			BSTree.java
 * Author:			Braden Steffaniak
 * Programming:		APCS
 * Last Modified:	11Feb2013
 * Description:		Interface used for a Binary Search Tree.
 */
public interface BSTree
{
	/**
	 * Adds a Comparable to the tree using the compare method
	 * to determine where to put it.
	 */
	public void add(Comparable c);
	
	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 * 
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
	 */
	public boolean remove(Comparable c);
}