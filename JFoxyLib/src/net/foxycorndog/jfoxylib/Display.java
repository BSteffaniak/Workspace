package net.foxycorndog.jfoxylib;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;

/**
 * Class that is used to hold information of the screen Display.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 2:43:28 PM
 * @since	v0.2
 * @version Apr 26, 2013 at 8:44:28 PM
 * @version	v0.2
 */
public class Display
{
	private static int	width, height;
	
	/**
	 * Acquire the screen size of the Desktop.
	 */
	static
	{
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
		width  = size.width;
		height = size.height;
	}
	
	/**
	 * Get the horizontal size of the main Monitor Display in pixels.
	 * 
	 * @return The horizontal size of the main Monitor Display in pixels.
	 */
	public static int getWidth()
	{
		return width;
	}

	/**
	 * Get the vertical size of the main Monitor Display in pixels.
	 * 
	 * @return The vertical size of the main Monitor Display in pixels.
	 */
	public static int getHeight()
	{
		return height;
	}
}