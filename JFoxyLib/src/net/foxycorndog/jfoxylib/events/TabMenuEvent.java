package net.foxycorndog.jfoxylib.events;

import net.foxycorndog.jfoxylib.components.TabItem;

/**
 * Class created whenever any TabMenuListener has an Event that
 * occurs.
 * 
 * @author	Braden Steffaniak
 * @since	Jul 4, 2013 at 2:02:34 PM
 * @since	v0.2
 * @version	Jul 4, 2013 at 2:02:34 PM
 * @version	v0.2
 */
public class TabMenuEvent extends Event
{
	private	TabItem	source;
	
	/**
	 * Create a TabMenuEvent.
	 * 
	 * @param source The source of the Event that occurred.
	 */
	public TabMenuEvent(TabItem source)
	{
		this.source = source;
	}
	
	/**
	 * Get the TabItem that was the source of the Event.
	 * 
	 * @return The TabItem instance that caused the Event.
	 */
	public TabItem getSource()
	{
		return source;
	}
}