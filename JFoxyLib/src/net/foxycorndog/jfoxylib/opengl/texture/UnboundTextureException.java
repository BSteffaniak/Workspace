package net.foxycorndog.jfoxylib.opengl.texture;

/**
 * Class Exception thrown whenever an operation is tried to
 * be undergone but the Texture needs to be bound.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 30, 2013 at 5:36:41 PM
 * @since	v0.2
 * @version	Jun 30, 2013 at 5:36:41 PM
 * @version	v0.2
 */
public class UnboundTextureException extends RuntimeException
{
	/**
	 * Create an UnboundTextureException instance with the specified
	 * message.
	 * 
	 * @param message The message that describes what went wrong.
	 */
	public UnboundTextureException(String message)
	{
		super(message);
	}
}