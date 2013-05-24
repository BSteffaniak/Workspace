package net.foxycorndog.jfoxylib.opengl;

/**
 * Class used to create an instance of a frame buffer that is used in
 * OpenGL versions >= 3.0.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:50:04 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:50:04 PM
 * @version	v0.2
 */
public class FrameBuffer
{
	/**
	 * Create a default FrameBuffer instance.
	 */
	public FrameBuffer()
	{
		if (!isSupported())
		{
			throw new UnsupportedOperationException("You must have OpenGL version 3.0 or greater to use FrameBuffers. (You have " + GL.getVersion() + ")");
		}
	}
	
	/**
	 * Get whether FrameBuffer objects are supported with the current
	 * OpenGL version.
	 * 
	 * @return Whether FrameBuffer objects are supported with the current
	 * 		OpenGL version.
	 */
	private static boolean isSupported()
	{
		return GL.getVersion().compareTo("3.0") >= 0;
	}
}