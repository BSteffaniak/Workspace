package net.foxycorndog.jfoxylib.util;

/**
 * Class used to store information of the location of a Rectangle, as
 * well as the size of it in 2 dimensional space.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:24:17 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 9:23:17 PM
 * @version	v0.2
 */
public class Rectangle extends Bounds
{
	/**
	 * Generate a String representation of the Rectangle instance.
	 * 
	 * @return The String representation of the Rectangle instance.
	 */
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + getX() + ", " + getY() + ", " + getWidth() + ", " + getHeight() + " }";
		
		return str;
	}
}