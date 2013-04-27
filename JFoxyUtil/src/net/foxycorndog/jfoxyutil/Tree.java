package net.foxycorndog.jfoxyutil;

/**
 * File:          Tree.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Feb2013
 * Description:
 */
public class Tree implements BSTree
{
	private TreeNode	root;

	/**
	 * Class used for keeping the information of a node,
	 * such as the parent node and the direction it stems
	 * off of from the parent.
	 */
	private class ChildInfo
	{
		private TreeNode		parent;

		private int				direction;

		public static final int	LEFT = 1, RIGHT = 2;

		/**
		 * Create a ChildInfo object with the parent parent.
		 * Also, specify the direction it branches.
		 */
		public ChildInfo(TreeNode parent, int direction)
		{
			this.parent    = parent;
			this.direction = direction;
		}

		/**
		 * @return The parent of the node.
		 */
		public TreeNode getParent()
		{
			return parent;
		}
	}

	/**
	 * Default constructor in place just in case I need to add anything.
	 */
	public Tree()
	{

	}
	
	/**
	 * Clears out the Tree completely of all TreeNodes.
	 */
	public void clear()
	{
		root = null;
	}

	/**
	 * Not implemented yet...
	 */
	public void insert(Comparable c)
	{

	}

	/**
	 * The public interface to add an object to the tree.
	 */
	public void add(Comparable cmp)
	{
		root = addData(root, cmp);
	}

	/**
	 * The generic implementation to add an object to the tree.
	 */
	private TreeNode addData(TreeNode r, Comparable cmp)
	{
		/* if the TreeNode is empty then create a new Node at the root, r
		 * else check if the object is smaller than the current object at TreeNode, r
		 * if smaller then recursively call “addData” to attach the object to the
		 * left side of current TreeNode, r
		 * if bigger then recursively call “addData” to attach object to right side
		 */
		if (r == null)
		{
			r = new TreeNode(cmp);
		}
		else
		{
			if (cmp.compareTo(r.getData()) < 0)
			{
				r.setLeftNode(addData(r.getLeftNode(), cmp));
			}
			else if (cmp.compareTo(r.getData()) > 0)
			{
				r.setRightNode(addData(r.getRightNode(), cmp));
			}
		}

		// reference to this TreeNode (needed to keep track of the real root)
		return r;
	}

	/**
	 * @return A string representation of the tree before ordering.
	 */
	public String toStringPreOrder()
	{
		return this.getClass().getSimpleName() + " { " + toStringPreOrder(root) + " }";
	}

	/**
	 * @return A string representation of the tree before ordering.
	 */
	public String toStringPreOrder(TreeNode node)
	{
		if (node == null)
		{
			return "";
		}
		else
		{
			return node.getData() + ", "
					+ toStringPreOrder(node.getLeftNode())
					+ toStringPreOrder(node.getRightNode());
		}
	}

	/**
	 * @return A string representation of the tree lexicographically.
	 */
	public String toString()
    {
    	return this.getClass().getSimpleName() + " { " + toStringInOrder() + " }";
    }

	/**
	 * @return A string representation of the node's left, right nodes and data.
	 */
	public String toStringInOrder()
	{
		return toStringInOrder(root);
	}

	/**
	 * @return A string representation of the node's left, right nodes and data.
	 */
    private String toStringInOrder(TreeNode node)
    {
		if (node == null)
		{
			return "";
		}
		else
		{
			return "(" + toStringInOrder(node.getLeftNode()) +
					" " + node.getData() +
					" " + toStringInOrder(node.getRightNode()) + ")";
		}
    }

	/**
	 * @return The maximum length of any leaf in the tree.
	 */
	public int height()
	{
		return height(root);
	}

	/**
	 * @return The maximum length of any leaf in the tree.
	 */
	private int height(TreeNode root)
	{
		if (root == null)
		{
			return 0;
		}

		return 1 + Math.max(height(root.getLeftNode()),
				height(root.getRightNode()));
	}

	/**
	 * @return Whether the tree is empty or not.
	 */
	public boolean isEmpty()
	{
		return root == null;
	}

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 *
	 * @return Whether the Comparable is in the tree.
	 */
    private boolean search(Comparable c, TreeNode r)
    {
		if (r == null)
		{
			return false;
		}
		else
		{
			if (c.equals(r.getData()))
			{
				return true;
			}
			else
			{
				return (search(c, r.getLeftNode()) ||
						search(c, r.getRightNode()));
			}
		}
    }

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable's parent.
	 *
	 * @return The info of the Comparable's node.
	 */
    public ChildInfo searchParent(Comparable c)
    {
		return searchParent(c, root);
    }

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable's parent.
	 *
	 * @return The info of the Comparable's node.
	 */
    private ChildInfo searchParent(Comparable c, TreeNode r)
    {
		if (r == null)
		{
			return null;
		}
		else
		{
			TreeNode left  = r.getLeftNode();
			TreeNode right = r.getRightNode();

			if (left != null && left.getData().compareTo(c) == 0)
			{
				return new ChildInfo(r, ChildInfo.LEFT);
			}
			else if (right != null && right.getData().compareTo(c) == 0)
			{
				return new ChildInfo(r, ChildInfo.RIGHT);
			}
			else
			{
				ChildInfo result = null;

				if (left != null)
				{
					result = searchParent(c, left);

					if (result != null)
					{
						return result;
					}
				}
				if (right != null)
				{
					result = searchParent(c, right);

					if (result != null)
					{
						return result;
					}
				}
			}
		}

		return null;
    }

	/**
	 * Searches the tree using the binary search method for the
	 * specified Comparable.
	 *
	 * @return Whether the Comparable is in the tree.
	 */
	public boolean search(Comparable c)
	{
		return search(c, root);
	}

	/**
	 * Removes the specified Comparable from the tree.
	 */
	public boolean remove(Comparable c)
	{
		return remove(c, root);
	}

	/**
	 * Removes the specified Comparable from the tree.
	 */
	public boolean remove(Comparable c, TreeNode delNode)
	{
		if (delNode == null)
		{
			return false;
		}
		else
		{
			if (c.compareTo(delNode.getData()) == 0)
			{
				TreeNode newNode = null;

				if (delNode == root)
				{
					if (root.getRightNode() != null)
					{
						newNode = root.getLeftNode();
						
						root = root.getRightNode();
						
						root.setLeftNode(newNode);
					}
					else
					{
						newNode = root.getLeftNode();

						newNode.setRightNode(root.getRightNode());

						root = newNode;
					}
				}
				else
				{
					ChildInfo info = searchParent(c);

					TreeNode parent = info.parent;

					if (delNode.getLeftNode() != null)
					{
						newNode = delNode.getLeftNode();

						newNode.setRightNode(delNode.getRightNode());
					}
					else if (delNode.getRightNode() != null)
					{
						newNode = delNode.getRightNode();

						if (newNode.getLeftNode() != null)
						{
							TreeNode temp = newNode;

							newNode = newNode.getLeftNode();

							temp.setLeftNode(null);

							newNode.setRightNode(temp);
						}
					}

					if (info.direction == ChildInfo.RIGHT)
					{
						parent.setRightNode(newNode);
					}
					else
					{
						parent.setLeftNode(newNode);
					}
				}

				return true;
			}
			else
			{
				return (remove(c, delNode.getLeftNode()) || remove(c, delNode.getRightNode()));
			}
		}
	}
}