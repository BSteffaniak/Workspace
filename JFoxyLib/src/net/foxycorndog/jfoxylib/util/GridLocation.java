package net.foxycorndog.jfoxylib.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jun 16, 2013 at 4:40:05 PM
 * @since	v
 * @version	Jun 16, 2013 at 4:40:05 PM
 * @version	v
 */
public class GridLocation<E>
{
	private	int	x, y;
	
	private E	data;
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param data
	 */
	public GridLocation(int x, int y, E data)
	{
		this.x    = x;
		this.y    = y;
		
		this.data = data;
	}
}