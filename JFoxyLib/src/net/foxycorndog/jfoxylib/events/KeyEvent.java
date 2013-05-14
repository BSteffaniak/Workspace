package net.foxycorndog.jfoxylib.events;

/**
 * 
 * 
 * @author	bmsteffaniak
 * @since	May 10, 2013 at 7:07:55 AM
 * @since	v
 * @version	May 10, 2013 at 7:07:55 AM
 * @version	v
 */
public class KeyEvent extends Event
{
	private	int	code;
	
	private	String	description;
	
	/**
	 * Default constructor.
	 * 
	 * @param description The description of the KeyEvent.
	 * @param code The key code of the KeyEvent
	 */
	public KeyEvent(String description, int code)
	{
		this.description = description;
		
		this.code        = code;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getCode()
	{
		return code;
	}
}
