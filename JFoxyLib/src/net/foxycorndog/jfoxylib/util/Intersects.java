package net.foxycorndog.jfoxylib.util;

/**
 * Class used to check if two areas intersect each other.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:22:29 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 9:02:29 PM
 * @version	v0.2
 */
public class Intersects
{
	/**
	 * Check whether two rectangles with the specified dimensions
	 * intersect.
	 * 
	 * @param x1 The horizontal location of the first rectangle.
	 * @param y1 The vertical location of the first rectangle.
	 * @param width1 The width of the first rectangle.
	 * @param height1 The height of the first rectangle.
	 * @param x2 The horizontal location of the second rectangle.
	 * @param y2 The vertical location of the second rectangle.
	 * @param width2 The width of the second rectangle.
	 * @param height2 The height of the second rectangle.
	 * @return Whether or not the two rectangles intersect.
	 */
	public static boolean rectangles(long x1, long y1, long width1, long height1, long x2, long y2, long width2, long height2)
	{
		if (width2 <= 0 || height2 <= 0 || width1 <= 0 || height1 <= 0)
		{
			return false;
		}
		
		width2  += x2;
		height2 += y2;
		width1  += x1;
		height1 += y1;
		
		return ((width2  < x2 || width2  > x1) &&
				(height2 < y2 || height2 > y1) &&
				(width1  < x1 || width1  > x2) &&
				(height1 < y1 || height1 > y2));
	}
	
	/**
	 * Check whether two rectangles with the specified dimensions
	 * intersect.
	 * 
	 * @param x1 The horizontal location of the first rectangle.
	 * @param y1 The vertical location of the first rectangle.
	 * @param width1 The width of the first rectangle.
	 * @param height1 The height of the first rectangle.
	 * @param x2 The horizontal location of the second rectangle.
	 * @param y2 The vertical location of the second rectangle.
	 * @param width2 The width of the second rectangle.
	 * @param height2 The height of the second rectangle.
	 * @return Whether or not the two rectangles intersect.
	 */
	public static boolean rectangles(double x1, double y1, double width1, double height1, double x2, double y2, double width2, double height2)
	{
		if (width2 <= 0 || height2 <= 0 || width1 <= 0 || height1 <= 0)
		{
			return false;
		}
		
		width2  += x2;
		height2 += y2;
		width1  += x1;
		height1 += y1;
		
		return ((width2  < x2 || width2  > x1) &&
				(height2 < y2 || height2 > y1) &&
				(width1  < x1 || width1  > x2) &&
				(height1 < y1 || height1 > y2));
	}
	
	/**
	 * Check whether two cube with the specified dimensions
	 * intersect.
	 * 
	 * @param x1 The horizontal location of the first cube.
	 * @param y1 The vertical location of the first cube.
	 * @param width1 The width of the first cube.
	 * @param height1 The height of the first cube.
	 * @param depth1 The depth of the first cube.
	 * @param x2 The horizontal location of the second cube.
	 * @param y2 The vertical location of the second cube.
	 * @param width2 The width of the second cube.
	 * @param height2 The height of the second cube.
	 * @param depth2 The depth of the second cube.
	 * @return Whether or not the two cubes intersect.
	 */
	public static boolean cubes(double x1, double y1, double z1, double width1, double height1, double depth1, double x2, double y2, double z2, double width2, double height2, double depth2)
	{
		width2  += x2;
		height2 += y2;
		depth2  += z2;
		width1  += x1;
		height1 += y1;
		depth1  += z1;
		
		return (width1 >= x2 && x1 <= width2) && (height1 >= y2 && y1 <= height2) && (depth1 >= z2 && z1 <= depth2);
	}
}