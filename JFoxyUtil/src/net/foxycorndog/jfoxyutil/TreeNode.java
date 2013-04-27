package net.foxycorndog.jfoxyutil;

/**
 * File:          TreeNode.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:   Node that has references to two child nodes.
 * Also holds a Comparable as data.
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