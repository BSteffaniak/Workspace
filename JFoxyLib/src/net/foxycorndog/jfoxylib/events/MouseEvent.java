package net.foxycorndog.jfoxylib.events;

/**
 * Class created and passed along with any MouseListener Event calls.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:37:18 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:37:18 PM
 * @version	v0.2
 */
public class MouseEvent extends Event
{
	private int		x, y;
	private int		button;
	
	/**
	 * Create a MouseEvent at the specified location with the specified
	 * button index.
	 * 
	 * @param x The horizontal location of the Mouse cursor at the time of
	 * 		the Event.
	 * @param y The vertical location of the Mouse cursor at the time of
	 * 		the Event.
	 * @param button The button that may have been used to cause the
	 * 		Event.
	 */
	public MouseEvent(int x, int y, int button)
	{
		this.x      = x;
		this.y      = y;
		
		this.button = button;
	}
	
	/**
	 * Get the horizontal location of the Mouse cursor at the time of the
	 * Event.
	 * 
	 * @return The horizontal location of the Mouse cursor at the time of
	 * 		the Event.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Get the vertical location of the Mouse cursor at the time of the
	 * Event.
	 * 
	 * @return The vertical location of the Mouse cursor at the time of
	 * 		the Event.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Get the button that may have been used to cause the Event.
	 * 
	 * @return The button that may have been used to cause the Event.
	 */
	public int getButton()
	{
		return button;
	}
}