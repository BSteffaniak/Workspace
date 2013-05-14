package net.foxycorndog.jfoxylib.events;

/**
 *
 *
 * @author	bmsteffaniak
 * @since	May 10, 2013 at 7:07:59 AM
 * @since	v
 * @version	May 10, 2013 at 7:07:59 AM
 * @version	v
 */
public interface KeyListener
{
	public void keyDown(KeyEvent event);

	public void keyPressed(KeyEvent event);

	public void keyReleased(KeyEvent event);

	public void keyTyped(KeyEvent event);
}
