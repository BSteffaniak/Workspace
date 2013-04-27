package net.foxycorndog.jfoxyutil;

import java.util.ArrayList;

/**
 * Class that is used as a queue.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 22, 2013 at 9:26:14 PM
 * @since	v0.1
 * @version	Apr 22, 2013 at 9:26:14 PM
 * @version	v0.1
 */
public class Queue<E>
{
	ArrayList<E>	queue;
	
	/**
	 * Instantiate the ArrayList that will be used as a queue.
	 */
	public Queue()
	{
		queue = new ArrayList<E>();
	}
	
	/**
	 * Enqueue an element to the end of the queue.
	 * 
	 * @param element The element to enqueue.
	 */
	public void enqueue(E element)
	{
		queue.add(element);
	}
	
	/**
	 * Dequeue the first element added (that has yet to be removed)
	 * from the queue and return it to you.
	 * 
	 * @return The first element that has yet to be removed.
	 */
	public E dequeue()
	{
		return queue.remove(0);
	}
	
	/**
	 * Peek at the last added element in the Queue without
	 * removing it.
	 * 
	 * @return The last element added in the Queue.
	 */
	public E peek()
	{
		return peek(queue.size() - 1);
	}
	
	/**
	 * Peek at the element in the Queue at the specified
	 * index without removing it.
	 * 
	 * @param index The index of the Queue to peek at. 0 == oldest,
	 * 		size() - 1 == newest.
	 * @return The element at the specified index in the Queue.
	 */
	public E peek(int index)
	{
		return queue.get(index);
	}
	
	/**
	 * Get the size of the Queue.
	 * 
	 * @return The number of elements in the Queue.
	 */
	public int size()
	{
		return queue.size();
	}
	
	/**
	 * Return whether or not the Queue is empty of any elements.
	 * 
	 * @return Whether or not the Queue is empty of any elements.
	 */
	public boolean isEmpty()
	{
		return queue.size() <= 0;
	}
	
	/**
	 * Create a String representation of this Queue instance. Represents
	 * the elements from last added to first added.
	 * 
	 * @return  A String representation of this Queue instance.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getClass().getSimpleName() + " { ");
		
		for (int i = queue.size() - 1; i >= 0; i--)
		{
			builder.append(queue.get(i) + ", ");
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		if (queue.size() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		
		builder.append(" }");
		
		return builder.toString();
	}
}