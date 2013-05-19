package net.foxycorndog.jfoxylib.events;

/**
 * 
 * 
 * @author	bmsteffaniak
 * @since	May 10, 2013 at 7:07:55 AM
 * @since	v0.2
 * @version	May 10, 2013 at 7:07:55 AM
 * @version	v0.2
 */
public class KeyEvent extends Event
{
	private	char	character;
	private	int		code;
	
	private	String	description;
	
	/**
	 * Default constructor.
	 * 
	 * @param description The description of the KeyEvent.
	 * @param code The key code of the KeyEvent
	 */
	public KeyEvent(String description, int code, char character)
	{
		this.description = description;
		
		this.code        = code;
		
		this.character   = character;
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
	public int getKeyCode()
	{
		return code;
	}
	
	/**
	 * Get the character that the KeyEvent was created with.
	 * 
	 * @return The character that the KeyEvent was created with.
	 */
	public char getCharacter()
	{
		return character;
	}
}
