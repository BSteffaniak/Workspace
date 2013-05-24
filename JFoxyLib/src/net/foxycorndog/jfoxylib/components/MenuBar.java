public class MenuBar extends Panel
{
	private	boolean				autoResize;
	
	private	ArrayList<MenuItem>	menuItems;
	
	/**
	 * Create a MenuBar inside of the specified parent Panel.
	 */
	public MenuBar(Panel parent)
	{
		super(parent);
		
		menuItems = new ArrayList<MenuItem>();
		
		autoResize = true;
	}
	
	/**
	 * Add the MenuItem to the end of the MenuBar and then update the
	 * MenuBar.
	 */
	public void addMenuItem(MenuItem item)
	{
		menuItems.add(item, menuItems.size());
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
		
		menuItems.add(item, index);
		
		update();
	}
	
	/**
	 * Set whether to auto-resize the MenuBar according to when MenuItems
	 * are added, removed, or changed.
	 */
	public void setAutoResize(boolean autoResize)
	{
		this.autoResize = autoResize;
		
		uodate();
	}
	
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
}
