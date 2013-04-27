package net.foxycorndog.jfoxylib.util;

/**
 * Class used to organize a location.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 28, 2013 at 8:24:04 PM
 * @since	v0.1
 * @version Feb 28, 2013 at 8:24:04 PM
 * @version	v0.1
 */
public class Point
{
	private int x, y;
	
	/**
	 * Create a Point at the location (0, 0).
	 */
	public Point()
	{
		
	}
	
	/**
	 * Create a Point at the location (x, y).
	 * 
	 * @param x The horizontal location of this Point.
	 * @param y The vertical location of this Point.
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create a Point from an existing java.awt.Point.
	 * 
	 * @param point The Point to take the values from.
	 */
	public Point(java.awt.Point point)
	{
		this.x = point.x;
		this.y = point.y;
	}
	
	/**
	 * @return The horizontal value of this Point.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Sets the horizontal value of this Point.
	 * 
	 * @param x The horizontal value.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return The vertical value of this Point.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the vertical value of this Point.
	 * 
	 * @param y The vertical value.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Method to set the location of the Point at (x, y).
	 * 
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Method that constructs a String to print out in place of this
	 * Point Object.
	 * 
	 * @return What to print out for this Point Object.
	 */
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + x + ", " + y + " }";
		
		return str;
	}
}