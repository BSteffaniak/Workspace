package net.foxycorndog.jfoxyutil;

import java.util.ArrayList;

/**
 * Class that organizes data according to the priority from least(top)
 * to greatest(bottom).
 * 
 * @author	Braden Steffaniak
 * @since	Apr 24, 2013 at 9:33:00 PM
 * @since	v0.1
 * @version	Apr 24, 2013 at 9:33:00 PM
 * @version	v0.1
 */
public class Heap
{
	private ArrayList<Comparable> data;

	/**
	 * Creates a basic Heap with no data.
	 */
	public Heap()
	{
		data = new ArrayList<Comparable>();

		data.add(null);
	}

	/**
	 * Adds the data to the Heap and then organizes it.
	 */
	public boolean add(Comparable newData)
	{
		data.add(newData);

		organizeData(data.size() - 1);

		return true;
	}

	/**
	 * Remove the specified comparable from the Heap. Then
	 * it organizes itself.
	 */
	public Comparable remove(Comparable comp)
	{
		if (isEmpty())
		{
			throw new EmptyHeapException("Cannot remove any data " +
					"because the Heap is empty.");
		}

		// Search the Heap for the index of the Comparable.
		int index = search(comp);

		// If the Comparable was not found.
		if (index == 0)
		{
			return null;
		}

		Comparable last = data.get(data.size() - 1);

		if (comp.compareTo(last) == 0)
		{
			data.remove(last);
			return last;
		}

		Comparable<?> removed = data.get(index);

		// Replace the removed location with the last piece of data in
		// the Heap.
		data.set(index, last);
		data.remove(data.size() - 1);

		boolean settled = false;

		while (!settled)
		{
			int pIndex = getParentIndex(index);
			int lIndex = getLeftChildIndex(index);
			int rIndex = getRightChildIndex(index);

			Comparable parent     = null;
			Comparable leftChild  = null;
			Comparable rightChild = null;

			int size = data.size();

			// Checks all of the bounds.
			if (pIndex > 0)
			{
				parent = data.get(pIndex);
			}
			if (lIndex < size)
			{
				leftChild = data.get(lIndex);
			}
			if (rIndex < size)
			{
				rightChild = data.get(rIndex);
			}

			// Compares to the surrounding data
			if (parent != null && last.compareTo(parent) < 0)
			{
				swapData(pIndex, index);

				index = pIndex;
			}
			else if (leftChild != null && last.compareTo(leftChild) > 0)
			{
				if (rightChild != null && leftChild.compareTo(rightChild) > 0)
				{
					swapData(rIndex, index);

					index = rIndex;
				}
				else
				{
					swapData(lIndex, index);

					index = lIndex;
				}

			}
			else if (rightChild != null && last.compareTo(rightChild) > 0)
			{
				swapData(rIndex, index);

				index = rIndex;
			}
			else
			{
				settled = true;
			}
		}

		return removed;
	}

	/**
	 * Remove the minimum value from the
	 */
	public Comparable removeMin()
	{
		if (isEmpty())
		{
			throw new EmptyHeapException("Cannot remove the min value " +
					"because the Heap is empty.");
		}

		Comparable removed = remove(data.get(1));

		return removed;
	}

	/**
	 * Get the object that represents the minimum value
	 * Comparable in the Heap.
	 *
	 * @return The minimum value Comparable in the Heap.
	 */
	public Comparable peekMin()
	{
		if (isEmpty())
		{
			throw new EmptyHeapException("Cannot access the min value " +
					"because the Heap is empty.");
		}

		return data.get(1);
	}

	/**
	 * Returns false if the Heap has data in it, true if
	 * it does.
	 *
	 * @return Whether or not the Heap has no data in it.
	 */
	public boolean isEmpty()
	{
		return data.size() <= 1;
	}

	/**
	 * Searches the Heap for the specified Comparable. If it is found,
	 * it returns the index at which it was found. If it is not found,
	 * it returns 0.
	 *
	 * @param comp The Comparable to search the Heap for.
	 * @return The index at which the Comparable is located in the Heap.
	 * 		returns 0 if the Comparable is not located in the Heap.
	 */
	private int search(Comparable comp)
	{
		for (int i = 1; i < data.size(); i++)
		{
			if (data.get(i).compareTo(comp) == 0)
			{
				return i;
			}
		}

		return 0;
	}

	/**
	 * Swaps the data at the given indices in the data ArrayList.
	 */
	private void swapData(int index1, int index2)
	{
		Comparable temp = data.get(index1);

		data.set(index1, data.get(index2));
		data.set(index2, temp);
	}

	/**
	 * Organizes the data at the specified index. Puts the data
	 * at the location that it belongs relative to its value.
	 *
	 * @param index The index of the data to organize.
	 */
	private void organizeData(int index)
	{
		Comparable comp   = data.get(index);

		int parentIndex   = getParentIndex(index);

		Comparable parent = data.get(parentIndex);

		// While it has a parent and the parent is greater than the
		// new data.
		while (parent != null && parent.compareTo(comp) > 0)
		{
			swapData(parentIndex, index);

			// Set the index to the new index.
			index       = parentIndex;

			// Reset the parent.
			parentIndex = getParentIndex(index);
			parent      = data.get(parentIndex);
		}
	}

	/**
	 * Get the index of the parent of the data at the index.
	 *
	 * @param index The index in which the child is located.
	 * @return The index of the parent of the child data at
	 * 		the specified index.
	 */
	private int getParentIndex(int index)
	{
		return index / 2;
	}

	/**
	 * Get the index of the left child of the parent at the
	 * specified index.
	 *
	 * @param index The index in which the parent is located.
	 * @return The index of the left child of the parent data
	 * 		at the specified index.
	 */
	private int getLeftChildIndex(int index)
	{
		return index * 2;
	}

	/**
	 * Get the index of the right child of the parent data
	 * at the specified index.
	 *
	 * @param index The index in which the parent is located.
	 * @return The index of the right child of the parent
	 * 		data at the specified index.
	 */
	private int getRightChildIndex(int index)
	{
		return index * 2 + 1;
	}

	/**
	 * Method that generates a String representation of this class.
	 *
	 * @return A String representation of this class.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(this.getClass().getSimpleName() + " { ");

		int size = data.size();

		for (int i = 1; i < size; i++)
		{
			builder.append(data.get(i) + ", ");
		}

		if (size > 1)
		{
			builder.deleteCharAt(builder.length() - 1);
			builder.deleteCharAt(builder.length() - 1);
		}

		builder.append(" }");

		return builder.toString();
	}
}