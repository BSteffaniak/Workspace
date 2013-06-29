package net.foxycorndog.jfoxyutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jun 16, 2013 at 4:24:52 PM
 * @since	v0.1
 * @version	Jun 16, 2013 at 4:24:52 PM
 * @version	v0.1
 */
public class Grid<E>
{
	private	int										size;
	
	private	HashMap<Integer, HashMap<Integer, E>>	grid;
	
	/**
	 * 
	 */
	public Grid()
	{
		grid = new HashMap<Integer, HashMap<Integer, E>>();
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public E get(int x, int y)
	{
		return grid.get(x).get(y);
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param element
	 * @return
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
	 * 
	 * 
	 * @param x
	 * @param y
	 */
	public void remove(int x, int y)
	{
		grid.get(x).remove(y);
		
		size--;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return size <= 0;
	}
	
//	/**
//	 * 
//	 * 
//	 * @return
//	 */
//	public ArrayList<GridLocation> getValues()
//	{
//		ArrayList<E> values = new ArrayList<E>();
//		
//		Collection<HashMap<Integer, E>> list = grid.values();
//		
//		Iterator i = list.iterator();
//		
//		while (i.hasNext())
//		{
//			values.add(new GridLocation<E>((E)i.next());
//		}
//		
//		return values;
//	}
}