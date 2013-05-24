package net.foxycorndog.jfoxylib.events;

/**
 * Class used when a KeyListener instance has an been notified
 * of an event. Keeps track of the key code, character, and a
 * description of the key that caused the event.
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
	 * @param character The Character that caused the Event.
	 */
	public KeyEvent(String description, int code, char character)
	{
		this.description = description;
		
		this.code        = code;
		
		this.character   = character;
	}
	
	/**
	 * Get the String representation of the key that caused the Event.
	 * 
	 * @return A String representation of the key that caused the Event.
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Get the code that refers to the specific key that caused the Event.
	 * 
	 * @return The code that refers to the specific key that caused the
	 * 		Event.
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
