package net.foxycorndog.jfoxylib.events;

/**
 * Class that is used to listen for KeyEvents that occur whenever a
 * key on the Keyboard is pressed, held down, released, or
 * typed (pressed and then released).
 * 
 * @author	bmsteffaniak
 * @since	May 10, 2013 at 7:07:59 AM
 * @since	v0.2
 * @version	May 10, 2013 at 7:07:59 AM
 * @version	v0.2
 */
public interface KeyListener
{
	/**
	 * Method called when a key is held down.
	 * 
	 * @param event The KeyEvent that describes the Event that has
	 * 		occurred.
	 */
	public void keyDown(KeyEvent event);
	
	/**
	 * Method called when a key is pressed.
	 * 
	 * @param event The KeyEvent that describes the Event that has
	 * 		occurred.
	 */
	public void keyPressed(KeyEvent event);
	
	/**
	 * Method called when a key is released.
	 * 
	 * @param event The KeyEvent that describes the Event that has
	 * 		occurred.
	 */
	public void keyReleased(KeyEvent event);
	
	/**
	 * Method called when a key is typed (pressed and then released.
	 * 
	 * @param event The KeyEvent that describes the Event that has
	 * 		occurred.
	 */
	public void keyTyped(KeyEvent event);
}