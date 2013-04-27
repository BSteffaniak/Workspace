package net.foxycorndog.jfoxylib.web;

/**
 * Exception thrown when there is an error when connecting to a server.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 14, 2013 at 6:50:26 PM
 * @since	v0.1
 * @version Mar 14, 2013 at 6:50:26 PM
 * @version	v0.1
 */
public class ConnectionException extends RuntimeException
{
	/**
	 * Create a ConnectionException with the specified message.
	 * 
	 * @param message The message to be used with this Exception.
	 */
	public ConnectionException(String message)
	{
		super(message);
	}
}