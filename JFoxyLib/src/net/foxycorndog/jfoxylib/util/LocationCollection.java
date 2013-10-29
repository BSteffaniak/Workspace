package net.foxycorndog.jfoxylib.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * 12:17:35 Aug 10 2013
 * 
 * @author BradenSteffaniak
 *
 * @param <E>
 */
public class LocationCollection<E>
{
	private int	width, height;
	
	private HashMap<Integer, HashMap<Integer, E>> map;
	
	public LocationCollection(int width, int height)
	{
		map = new HashMap<Integer, HashMap<Integer, E>>();
		
		this.width  = width;
		this.height = height;
		
		for (int x = 0; x < width; x++)
		{
			map.put(x, new HashMap<Integer, E>());
		}
	}
	
	public E get(int x, int y)
	{
		return map.get(x).get(y);
	}
	
	public void move(int x, int y, int newX, int newY, E element)
	{
		if (put(newX, newY, element))
		{
			map.get(x).remove(y);
		}
	}
	
	public boolean put(int x, int y, E element)
	{
//		if (x < 0 || x >= width || y < 0 || y >= height)
//		{
//			return false;
//		}
		
		if (!map.get(x).containsKey(y))
		{
			map.get(x).put(y, element);
			
			return true;
		}
		
		return false;
	}
	
	public boolean remove(int x, int y)
	{
		return map.get(x).remove(y) != null;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}