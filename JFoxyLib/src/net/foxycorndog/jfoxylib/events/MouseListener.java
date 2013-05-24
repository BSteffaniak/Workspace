package net.foxycorndog.jfoxylib.events;

/**
 * Class that is used to listen for MouseEvents that occur whenever an
 * event has occurred with the Mouse. Such Events may include: A button
 * press, a button release, a button down, a button up, the mouse entering
 * the Frame, the mouse exiting the Frame.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:44:25 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:44:25 PM
 * @version	v0.2
 */
public interface MouseListener
{
	/**
	 * Method called when a Mouse button has been pressed.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mousePressed(MouseEvent event);
	
	/**
	 * Method called when a Mouse button has been previously been pressed
	 * and is now released.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseReleased(MouseEvent event);
	
	/**
	 * Method called when a Mouse button is down.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseDown(MouseEvent event);
	
	/**
	 * Method called when a Mouse button has been released.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseUp(MouseEvent event);
	
	/**
	 * Method called when the Mouse has been moved.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseMoved(MouseEvent event);
	
	/**
	 * Method called when the Mouse has entered the Frame.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseEntered(MouseEvent event);
	
	/**
	 * Method called when the Mouse has exited the Frame.
	 * 
	 * @param event The MouseEvent describing the Event that has
	 * 		occurred.
	 */
	public void mouseExited(MouseEvent event);
}