package net.foxycorndog.jfoxylib.events;

/**
 * Base class for all of the other Events. Holds the information for the
 * time that the Event happened.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 3:28:46 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:32:46 PM
 * @version	v0.2
 */
public class Event
{
	private long	when;
	
	/**
	 * Create an Event and save the time in which it was created.
	 */
	public Event()
	{
		when         = System.currentTimeMillis();
	}
	
	/**
	 * Get when the event occurred.
	 * 
	 * @return When the event occurred.
	 */
	public long when()
	{
		return when;
	}
}