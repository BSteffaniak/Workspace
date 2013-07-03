package net.foxycorndog.jfoxyutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class used to organize data upon a Grid data structure.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 16, 2013 at 4:24:52 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 12:45:39 AM
 * @version	v0.1
 */
public class Grid<E>
{
	private	int										size;
	
	private	HashMap<Integer, HashMap<Integer, E>>	grid;
	
	/**
	 * Create a Grid.
	 */
	public Grid()
	{
		grid = new HashMap<Integer, HashMap<Integer, E>>();
	}
	
	/**
	 * Get the element at the location (x, y) from the Grid.
	 * 
	 * @param x The horizontal location on the Grid to get the element
	 * 		from.
	 * @param y The vertical location on the Grid to get the element
	 * 		from.
	 * @return The element instance at the specified location.
	 */
	public E get(int x, int y)
	{
		return grid.get(x).get(y);
	}
	
	/**
	 * Set the value of the element at the specified location (x, y) on
	 * the Grid.
	 * 
	 * @param x The horizontal location on the Grid to add the element
	 * 		to.
	 * @param y The vertical location on the Grid to add the element
	 * 		to.
	 * @param element The element instance to add to the Grid.
	 * @return Whether or not the element was successfully added to the
	 * 		Grid.
	 */
	public boolean set(int x, int y, E element)
	{
		if (!grid.containsKey(x))
		{
			grid.put(x, new HashMap<Integer, E>());
		}
		
		grid.get(x).put(y, element);
		
		size++;
		
		return true;
	}
	
	/**
	 * Remove the element at the specified location of (x, y).
	 * 
	 * @param x The horizontal location on the Grid to remove the element
	 * 		from.
	 * @param y The vertical location on the Grid to remove the element
	 * 		from.
	 */
	public void remove(int x, int y)
	{
		grid.get(x).remove(y);
		
		size--;
	}
	
	/**
	 * Get whether or not the Grid is empty.
	 * 
	 * @return Whether or not the Grid is empty.
	 */
	public boolean isEmpty()
	{
		return size <= 0;
	}
	
	/**
	 * Get all of the values from the Grid.
	 * 
	 * @return An ArrayList full of all of the values on the Grid.
	 */
	public ArrayList<E> getValues()
	{
		ArrayList<E> values = new ArrayList<E>();
		
		Collection<HashMap<Integer, E>> list = grid.values();
		
		Iterator iterator = list.iterator();
		
		while (iterator.hasNext())
		{
			Object next = iterator.next();
			
			HashMap<Integer, E> map = (HashMap<Integer, E>)next;
			
			Collection<E> elements = map.values();
			
			for (E element : elements)
			{
				values.add(element);
			}
		}
		
		return values;
	}
}