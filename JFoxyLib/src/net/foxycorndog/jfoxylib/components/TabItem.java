package net.foxycorndog.jfoxylib.components;

import net.foxycorndog.jfoxylib.font.Font;

/**
 * Class used to organize items within a TabMenu.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 30, 2013 at 10:33:40 PM
 * @since	v0.2
 * @version	Jul 2, 2013 at 12:28:40 AM
 * @version	v0.2
 */
public class TabItem extends Button
{
	/**
	 * Create a TabItem with the specified name.
	 * 
	 * @param name The name of the TabItem to create.
	 * @param parent The TabMenu to add the TabItem to.
	 */
	public TabItem(String name, TabMenu parent)
	{
		super(parent);
		
		setFont(Font.getDefaultFont());
		setText(name);
	}
	
	/**
	 * Get the TabMenu that the TabItem instance belongs to.
	 * 
	 * @see net.foxycorndog.jfoxylib.components.Component#getParent()
	 */
	public TabMenu getParent()
	{
		return (TabMenu)super.getParent();
	}
	
	/**
	 * Get the name of the TabItem.
	 * 
	 * @return The name of the TabItem.
	 */
	public String getName()
	{
		return getText();
	}
}
