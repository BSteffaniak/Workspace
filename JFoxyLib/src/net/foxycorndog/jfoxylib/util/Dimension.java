package net.foxycorndog.jfoxylib.util;

/**
 * Class used to organize a size.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 28, 2013 at 8:21:34 PM
 * @since	v0.1
 * @version Feb 28, 2013 at 8:21:34 PM
 * @version	v0.1
 */
public class Dimension
{
	private int width, height;
	
	/**
	 * Create a Dimension with this size (0, 0).
	 */
	public Dimension()
	{
		
	}
	
	/**
	 * Create a Dimension with the size (width, height).
	 * 
	 * @param width The horizontal size of this Dimension.
	 * @param height The vertical size of this Dimension.
	 */
	public Dimension(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Create a Dimension from an existing java.awt.Dimension.
	 * 
	 * @param dimension The Dimension to take the values from.
	 */
	public Dimension(java.awt.Dimension dimension)
	{
		this.width  = dimension.width;
		this.height = dimension.height;
	}
	
	/**
	 * @return The horizontal size of this Dimension.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Sets the horizontal size of this Dimension.
	 * 
	 * @param width The horizontal size.
	 */
	public void setX(int width)
	{
		this.width = width;
	}

	/**
	 * @return The vertical size of this Dimension.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the vertical size of this Dimension.
	 * 
	 * @param height The vertical size.
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * Method to set the size of the Dimension with (width, height).
	 * 
	 * @param width The horizontal location.
	 * @param height The vertical location.
	 */
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Method that constructs a String to print out in place of this
	 * Dimension Object.
	 * 
	 * @return What to print out for this Dimension Object.
	 */
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + width + ", " + height + " }";
		
		return str;
	}
}