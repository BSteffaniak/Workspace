package net.foxycorndog.jfoxylib.components;

/**
 * Class used to organize items within a MenuBar.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 30, 2013 at 10:33:40 PM
 * @since	v0.2
 * @version	Jul 2, 2013 at 12:28:40 AM
 * @version	v0.2
 */
public class MenuItem extends Button
{
	private	String	name;
	
	/**
	 * Create a MenuItem with the specified name.
	 * 
	 * @param name The name of the MenuItem to create.
	 * @param parent The MenuBar to add the MenuItem to.
	 */
	public MenuItem(String name, MenuBar parent)
	{
		super(parent);
		
		this.name = name;
	}
	
	/**
	 * Get the MenuBar that the MenuItem instance belongs to.
	 * 
	 * @see net.foxycorndog.jfoxylib.components.Component#getParent()
	 */
	public MenuBar getParent()
	{
		return (MenuBar)super.getParent();
	}
	
	/**
	 * Get the name of the MenuItem.
	 * 
	 * @return The name of the MenuItem.
	 */
	public String getName()
	{
		return name;
	}
}
