package net.foxycorndog.jfoxylib.network;

/**
 * Exception thrown when a Network tries to do anything that it is not
 * able to do without being connected to another network.
 * 
 * @author	Braden Steffaniak
 * @since	May 19, 2013 at 3:58:54 PM
 * @since	v0.2
 * @version	May 19, 2013 at 3:58:54 PM
 * @version	v0.2
 */
public class NotConnectedException extends RuntimeException
{
	/**
	 * Create the Exception with a message to describe the error.
	 * 
	 * @param message The message describing the error.
	 */
	public NotConnectedException(String message)
	{
		super(message);
	}
}