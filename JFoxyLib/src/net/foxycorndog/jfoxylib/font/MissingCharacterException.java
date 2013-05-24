package net.foxycorndog.jfoxylib.font;

/**
 * Exception thrown when a Character is missing from the character
 * array given to the Font.
 * 
 * @author	Braden Steffaniak
 * @since	May 21, 2013 at 11:20:05 PM
 * @since	v0.2
 * @version	May 21, 2013 at 11:20:05 PM
 * @version	v0.2
 */
public class MissingCharacterException extends RuntimeException
{
	/**
	 * Create the Exception with a message to describe the error.
	 * 
	 * @param message The message describing the error.
	 */
	public MissingCharacterException(String message)
	{
		super(message);
	}
}