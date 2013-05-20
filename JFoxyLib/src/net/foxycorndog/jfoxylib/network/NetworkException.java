package net.foxycorndog.jfoxylib.network;

/**
 * Exception thrown when anything goes wrong with the connection
 * of a Network.
 * 
 * @author	Braden Steffaniak
 * @since	May 19, 2013 at 9:02:17 PM
 * @since	v0.2
 * @version	May 19, 2013 at 9:02:17 PM
 * @version	v0.2
 */
public class NetworkException extends RuntimeException
{
	/**
	 * Create the Exception with a message to describe the error.
	 * 
	 * @param message The message describing the error.
	 */
	public NetworkException(String message)
	{
		super(message);
	}
}