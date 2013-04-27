package net.foxycorndog.jfoxylib.graphics.opengl;

/**
 * 
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
	 * 
	 */
	public FrameBuffer()
	{
		if (!isSupported())
		{
			throw new UnsupportedOperationException("You must have OpenGL version 3.0 or greater to use FrameBuffers. (You have " + GL.getVersion() + ")");
		}
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public boolean isSupported()
	{
		return GL.getVersion().compareTo("3.0") >= 0;
	}
}