package net.foxycorndog.jfoxylib.util;

/**
 * 
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
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 * 
	 * @param p
	 * @return
	 */
	public Point3f minus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Point3f minus(float x, float y, float z)
	{
		return new Point3f(this.x - x, this.y - y, this.z - z);
	}
	
	/**
	 * 
	 * 
	 * @param p
	 * @return
	 */
	public Point3f plus(Point3f p)
	{
		return minus(p.x, p.y, p.z);
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Point3f plus(float x, float y, float z)
	{
		return new Point3f(this.x + x, this.y + y, this.z + z);
	}
	
	/**
	 * 
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	public void move(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * 
	 * 
	 * @param x
	 */
	public void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * 
	 * 
	 * @param y
	 */
	public void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public float getZ()
	{
		return z;
	}
	
	/**
	 * 
	 * 
	 * @param z
	 */
	public void setZ(float z)
	{
		this.z = z;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public Point3f clone()
	{
		return new Point3f(x, y, z);
	}
}