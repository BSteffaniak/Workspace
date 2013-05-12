package net.foxycorndog.jfoxylib.events;

public class KeyEvent extends Event
{
	private	int		code;
	
	private	String	description;
	
	/**
	 * Default constructor.
	 */
	public KeyEvent(String description, int code)
	{
		this.description = description;
		
		this.code        = code;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public int getCode()
	{
		return code;
	}
}
