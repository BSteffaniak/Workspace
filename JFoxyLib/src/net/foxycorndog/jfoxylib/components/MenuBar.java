package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;

/**
 * Class used to create an organized bar used as a menu for any
 * specific purpose or another.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 30, 2013 at 10:33:27 PM
 * @since	v0.2
 * @version	Jun 30, 2013 at 10:33:27 PM
 * @version	v0.2
 */
public class MenuBar extends Panel
{
	private	boolean				autoResize;
	
	private	Bundle				bundle;
	
	private	ArrayList<MenuItem>	menuItems;
	
	/**
	 * Create a MenuBar inside of the specified parent Panel.
	 */
	public MenuBar(Panel parent)
	{
		super(parent);
		
		menuItems  = new ArrayList<MenuItem>();
		
		autoResize = true;
	}
	
	/**
	 * Add the MenuItem to the end of the MenuBar and then update the
	 * MenuBar.
	 */
	public void addMenuItem(MenuItem item)
	{
		addMenuItem(item, menuItems.size());
	}
	
	/**
	 * Add the MenuItem to the specified index of the MenuBar and then
	 * update the MenuBar.
	 */
	public void addMenuItem(MenuItem item, int index)
	{
		if (index > menuItems.size())
		{
			throw new IndexOutOfBoundsException("The index '" + index + "' must be <= the amount of MenuItems '" + menuItems.size() + "' in the MenuBar");
		}
		
		menuItems.add(index, item);
		
		update();
	}
	
	/**
	 * Set whether to auto-resize the MenuBar according to when MenuItems
	 * are added, removed, or changed.
	 */
	public void setAutoResize(boolean autoResize)
	{
		this.autoResize = autoResize;
		
		update();
	}
	
	/**
	 * Get whether or not the MenuBar will dynamically change the size
	 * whenever MenuItems are added or removed from the bar.
	 * 
	 * @return Whether or not the MenuBar auto-resizes.
	 */
	public boolean doesAutoResize()
	{
		return autoResize;
	}
	
	/**
	 * Update the MenuBar to the current MenuItems.
	 */
	public void update()
	{
		
	}
	
	/**
	 * Render the MenuBar at its current location.
	 * 
	 * @see net.foxycorndog.jfoxylib.components.Panel#render()
	 */
	public void render()
	{
		bundle.render(GL.TRIANGLES, GL.WHITE);
	}
}
