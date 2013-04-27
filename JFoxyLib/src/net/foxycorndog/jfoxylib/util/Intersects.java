package net.foxycorndog.jfoxylib.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:22:29 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:22:29 PM
 * @version	v0.2
 */
public class Intersects
{
	/**
	 * 
	 * 
	 * @param x1
	 * @param y1
	 * @param width1
	 * @param height1
	 * @param x2
	 * @param y2
	 * @param width2
	 * @param height2
	 * @return
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
	 * 
	 * 
	 * @param x1
	 * @param y1
	 * @param width1
	 * @param height1
	 * @param x2
	 * @param y2
	 * @param width2
	 * @param height2
	 * @return
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
	 * 
	 * 
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param width1
	 * @param height1
	 * @param depth1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param width2
	 * @param height2
	 * @param depth2
	 * @return
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