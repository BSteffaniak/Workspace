package net.foxycorndog.arrowide.components.tabmenu;

/**
 * Class that holds the information for each event that is
 * dispatched from the TabMenu.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 24, 2013 at 1:45:46 AM
 * @since	v0.2
 * @version Mar 24, 2013 at 1:45:46 AM
 * @version	v0.2
 */
public class TabMenuEvent
{
	private int		tabId;
	
	private TabMenu source;
	
	/**
	 * Creat a TabMenuEvent with the specified source and tab id.
	 * 
	 * @param source The TabMenu that dispatched this event.
	 * @param tabId The id of the tab that was edited.
	 */
	public TabMenuEvent(TabMenu source, int tabId)
	{
		this.source = source;
		
		this.tabId  = tabId;
	}
	
	/**
	 * Get the id of the tab that was edited.
	 * 
	 * @return The id of the tab that was edited.
	 */
	public int getTabId()
	{
		return tabId;
	}
	
	/**
	 * Get the TabMenu instance that was used when the event was
	 * dispatched.
	 * 
	 * @return The instance that was used when the event was dispatched.
	 */
	public TabMenu getSource()
	{
		return source;
	}
}