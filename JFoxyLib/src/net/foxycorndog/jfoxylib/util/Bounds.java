package net.foxycorndog.jfoxylib.util;

/**
 * Class that is used to keep the values of the Bounds of anything.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 28, 2013 at 8:22:19 PM
 * @since	v0.1
 * @version Feb 28, 2013 at 8:22:19 PM
 * @version	v0.1
 */
public class Bounds
{
	private int	x, y;
	private int	width, height;
	
	/**
	 * Create a Bounds at the location (0, 0) with the size (0, 0).
	 */
	public Bounds()
	{
		
	}
	
	/**
	 * Create a Point at the location (x, y) with the size
	 * (width, height).
	 * 
	 * @param x The horizontal location of the Bounds.
	 * @param y The vertical location of the Bounds.
	 * @param width The horizontal size of the Bounds.
	 * @param height The vertical size of the Bounds.
	 */
	public Bounds(int x, int y, int width, int height)
	{
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * @return The horizontal location of this Bounds.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Sets the horizontal location of this Bounds.
	 * 
	 * @param x The horizontal value.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return The vertical location of this Bounds.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Sets the vertical location of this Bounds.
	 * 
	 * @param y The vertical value.
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Method to set the location of the Bounds at (x, y).
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
	 * @return The horizontal size of this Bounds.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Sets the horizontal size of this Bounds.
	 * 
	 * @param x The horizontal size.
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return The vertical size of this Bounds.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the vertical size of this Bounds.
	 * 
	 * @param y The vertical size.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	/**
	 * Method to set the size of the Bounds with (width, height).
	 * 
	 * @param x The horizontal size.
	 * @param y The vertical size.
	 */
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Method that constructs a String to print out in place of this
	 * Bounds Object.
	 * 
	 * @return What to print out for this Bounds Object.
	 */
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + x + ", " + y + ", " + width + ", " + height + " }";
		
		return str;
	}
}