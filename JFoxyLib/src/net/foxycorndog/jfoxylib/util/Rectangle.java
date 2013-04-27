package net.foxycorndog.jfoxylib.util;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:24:17 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:24:17 PM
 * @version	v0.2
 */
public class Rectangle
{
	private int	x, y;
	private int	width, height;
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Rectangle(int x, int y, int width, int height)
	{
		this.x      = x;
		this.y      = y;
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * 
	 * 
	 * @param rectangle
	 */
	public Rectangle(java.awt.Rectangle rectangle)
	{
		this.x      = rectangle.x;
		this.y      = rectangle.y;
		this.width  = rectangle.width;
		this.height = rectangle.height;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * 
	 * 
	 * @param x
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * 
	 * 
	 * @param y
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * 
	 * 
	 * @param width
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * 
	 * 
	 * @param height
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public String toString()
	{
		String str = "";
		
		str += this.getClass().getSimpleName() + " { " + x + ", " + y + ", " + width + ", " + height + " }";
		
		return str;
	}
}