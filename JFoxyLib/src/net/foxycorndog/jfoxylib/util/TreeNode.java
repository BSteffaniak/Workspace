package net.foxycorndog.jfoxylib.util;

/**
 * Node that has references to two child nodes. Also holds a Comparable
 * as data.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 11, 2013 at 9:13:38 AM
 * @since	v0.1
 * @version	Jul 3, 2013 at 1:13:38 AM
 * @version	v0.1
 */
public class TreeNode
{
	private Comparable	data;
	
	private TreeNode	leftNode, rightNode;
	
	/**
	 * Construct a TreeNode object with the given data.
	 */
	public TreeNode(Comparable data)
	{
		this.data = data;
	}
	
	/**
	 * @return The child node to the left of this node.
	 */
	public TreeNode getLeftNode()
	{
		return leftNode;
	}
	
	/**
	 * Set the child node to the left of this node.
	 */
	public void setLeftNode(TreeNode leftNode)
	{
		this.leftNode = leftNode;
	}
	
	/**
	 * @return The child node to the right of this node.
	 */
	public TreeNode getRightNode()
	{
		return rightNode;
	}
	
	/**
	 * Set the child node to the right of this node.
	 */
	public void setRightNode(TreeNode rightNode)
	{
		this.rightNode = rightNode;
	}
	
	/**
	 * @return The Comparable data in the node.
	 */
	public Comparable getData()
	{
		return data;
	}
}