package net.foxycorndog.jfoxylib.util;

/**
 * Class used to store values of a Point with 3 float values.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:23:09 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:23:09 PM
 * @version	v0.2
 */
public class Point3f implements Cloneable
{
	private float x, y, z;
	
	/**
	 * Create a Point at the location (x, y, z).
	 * 
	 * @param x The horizontal location of this Point.
	 * @param y The vertical location of this Point.
	 * @param z The oblique (depth) location of this Point.
	 */
	public Point3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Subtract the values from the given Point with this Point.<br>
	 * For example:<br>
	 * Point3f p1 = new Point3f(3, 6, 1);<br>
	 * Point3f p2 = new Point3f(1, 6, 4);<br>
	 * Point3f p3 = p1.minus(p2);<br>
	 * p3 contains the values (2, 0, -3). The values in the other Points
	 * remain unchanged.
	 * 
	 * @param p The Point to use to subtract from.
	 * @return A new Point3f with the values of p - this.
	 */
	public Point3f minus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	/**
	 * Subtract the values from the given values with this Point.<br>
	 * For example:<br>
	 * Point3f p1 = new Point3f(3, 6, 1);<br>
	 * Point3f p2 = p1.minus(1, 6, 4);<br>
	 * p2 contains the values (2, 0, -3). The values in p1 remain
	 * unchanged.
	 * 
	 * @param x The horizontal value to subtract from.
	 * @param y The vertical value to subtract from.
	 * @param z The oblique (depth) value to subtract from.
	 * @return A new Point3f with the values of values - this.values.
	 */
	public Point3f minus(float x, float y, float z)
	{
		return new Point3f(this.x - x, this.y - y, this.z - z);
	}
	
	/**
	 * Add the values from the given Point to this Point.<br>
	 * For example:<br>
	 * Point3f p1 = new Point3f(3, 6, 1);<br>
	 * Point3f p2 = new Point3f(1, 6, 4);<br>
	 * Point3f p3 = p1.plus(p2);<br>
	 * p3 contains the values (4, 12, 5). The values in the other Points
	 * remain unchanged.
	 * 
	 * @param p The Point to use to add to.
	 * @return A new Point3f with the values of p + this.
	 */
	public Point3f plus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	/**
	 * Add the values from the given values to this Point.<br>
	 * For example:<br>
	 * Point3f p1 = new Point3f(3, 6, 1);<br>
	 * Point3f p2 = p1.plus(1, 6, 4);<br>
	 * p2 contains the values (4, 12, 5). The values in p1 remain
	 * unchanged.
	 * 
	 * @param x The horizontal value to add to.
	 * @param y The vertical value to add to.
	 * @param z The oblique (depth) value to add to.
	 * @return A new Point3f with the values of values + this.values.
	 */
	public Point3f plus(float x, float y, float z)
	{
		return new Point3f(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * Move the Point3f instance as if it is a Point in space.<br>
	 * For example:<br>
	 * Point3f p = new Point3f(5, 7, -3);<br>
	 * p.move(-4, 3, 12);<br>
	 * p now contains the values (1, 10, 9).
	 * 
	 * @param dx The amount to move the Point horizontally.
	 * @param dy The amount to move the Point vertically.
	 * @param dz The amount to move the Point obliquely (depth).
	 */
	public void move(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;
	}
	
	/**
	 * Set the values of the Point3f.
	 * 
	 * @param x The new horizontal value of the Point.
	 * @param y The new vertical value of the Point.
	 * @param z The new oblique (depth) value of the Point.
	 */
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Get the horizontal value of the Point3f.
	 * 
	 * @return The horizontal value of the Point3f.
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Set the horizontal value of the Point3f.
	 * 
	 * @param x The horizontal value of the Point3f.
	 */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * Get the vertical value of the Point3f.
	 * 
	 * @return The vertical value of the Point3f.
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Set the vertical value of the Point3f.
	 * 
	 * @param y The vertical value of the Point3f.
	 */
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * Get the oblique (depth) value of the Point3f.
	 * 
	 * @return The oblique (depth) value of the Point3f.
	 */
	public float getZ()
	{
		return z;
	}
	
	/**
	 * Set the oblique (depth) value of the Point3f.
	 * 
	 * @param z The oblique (depth) value of the Point3f.
	 */
	public void setZ(float z)
	{
		this.z = z;
	}
	
	/**
	 * Generate a String representation of the Point3f instance.
	 * 
	 * @return A String representation of the Point3f instance.
	 */
	public String toString()
	{
		return this.getClass().getSimpleName() + " { " + x + ", " + y + ", " + z + " }";
	}
	
	/**
	 * Create a new Point at the same location as the current one.
	 * 
	 * @return A new Point at the same location as the current one.
	 */
	public Point3f clone()
	{
		return new Point3f(x, y, z);
	}
}