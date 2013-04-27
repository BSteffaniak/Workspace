package net.foxycorndog.jfoxylib.events;

/**
 * 
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
	 * 
	 * 
	 * @param event
	 */
	public void mousePressed(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseReleased(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseDown(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseUp(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseMoved(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseEntered(MouseEvent event);
	
	/**
	 * 
	 * 
	 * @param event
	 */
	public void mouseExited(MouseEvent event);
}