package net.foxycorndog.jfoxylib.events;

/**
 * Interface used for implementing what to do when a Frame
 * has been resized.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 3:25:15 PM
 * @since	v0.2
 * @version Apr 26, 2013 at 10:35:15 PM
 * @version	v0.2
 */
public interface FrameListener
{
	/**
	 * Abstract method called whenever the Frame has been resized.
	 * 
	 * @param e The FrameEvent passed along with the Event call.
	 */
	public void frameResized(FrameEvent e);
}