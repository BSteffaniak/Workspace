package net.foxycorndog.jfoxylib.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Aug 4, 2013 at 2:35:44 PM
 * @since	v0.3
 * @version	Aug 4, 2013 at 2:35:44 PM
 * @version	v0.3
 */
public class Distance
{
	/**
	 * Get the distance between the two Points.
	 * 
	 * @param x1 The horizontal location of the first Point.
	 * @param y1 The vertical location of the first Point.
	 * @param x2 The horizontal location of the second Point.
	 * @param y2 The vertical location of the second Point.
	 */
	public static double points(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}