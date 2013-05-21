package net.foxycorndog.tetris.board;

/**
* File:  			Location.java
* Author:			Henry Thomas Rybolt
* Programming:	  	3rd Hour
* Last Modified: 	May 06, 2013
* Description:      A Location is a positional vector.
*/
public class Location implements Cloneable
{
	private int x;
	private int y;

	/**
	 * constructs a location with a x-coordinate of x and a y-coordinate
	 * of y
	 */
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * returns true if two locations are equal
	 */
	public boolean equals(Location l)
	{
		return(x == l.getX() && y == l.getY());
	}

	/**
	 * adds two locations like their positional vectors
	 */
	public Location add(Location l)
	{
		return new Location(x + l.getX(), y + l.getY());
	}

	/**
	 * subtracts two locations like their positional vectors
	 */
	public Location subtract(Location l)
	{
		return new Location(x - l.getX(), y - l.getY());
	}
	
	/**
	 * returns the x-coordinate of the location
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * returns the y-coordinate of the location
	 */
	public int getY()
	{
		return y;
	}
	
	
	/**
	 * rotates a location about the origin 90 degrees clockwise
	 */
	public void rotateC()
	{
		int temp = x;
		x        = y;
		y        = -temp;
	}
	
	/**
	 * rotates a location about the origin 90 degrees counter-clockwise
	 */
	public void rotateCC()
	{
		int temp = x;
		x        = -y;
		y        = temp;
//		int temp = getX();
//		setX(-getY());
//		setX(temp);
	}
	
	/**
	 * sets the y-coordinate to y
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * sets the x-coordinate to x
	 */
	public void setX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Converts this class to a String representation.
	 */
	public String toString()
	{
		return this.getClass().getSimpleName() + " { " + x + ", " + y + " }";
	}
	
	/**
	 * Create an exact clone except for the reference.
	 */
	public Location clone()
	{
		Location location = null;
		
		try
		{
			location = (Location)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		return location;
	}
}