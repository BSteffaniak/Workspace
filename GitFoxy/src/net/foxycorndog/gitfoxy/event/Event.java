package net.foxycorndog.gitfoxy.event;

/**
 * Base class for every Event handler. Keeps track of when the
 * Event was created.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 29, 2013 at 6:33:30 PM
 * @since	v0.1
 * @version	Apr 29, 2013 at 6:33:30 PM
 * @version	v0.1
 */
public class Event
{
	private long when;
	
	/**
	 * Create an Event and save what time it was created.
	 */
	public Event()
	{
		when = System.currentTimeMillis();
	}
	
	/**
	 * Get the time in milliseconds that the Event was created.
	 *
	 * @return The time in milliseconds when the Event was created.
	 */
	public long getWhen()
	{
		return when;
	}
}