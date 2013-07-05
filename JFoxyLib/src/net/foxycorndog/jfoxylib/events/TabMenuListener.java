package net.foxycorndog.jfoxylib.events;

/**
 * Interface that is used to listen for Events that occur whenever
 * a TabMenu has anything interesting occur.
 * 
 * @author	Braden Steffaniak
 * @since	Jul 4, 2013 at 2:02:10 PM
 * @since	v0.2
 * @version	Jul 4, 2013 at 2:02:10 PM
 * @version	v0.2
 */
public interface TabMenuListener
{
	/**
	 * Called whenever a TabItem in the TabItemMenu has been pressed down
	 * by the Mouse.
	 * 
	 * @param event The TabMenuEvent that describes the Event that
	 * 		occurred.
	 */
	public void tabPressed(TabMenuEvent event);

	/**
	 * Called whenever a TabItem in the TabItemMenu has been released
	 * by the Mouse.
	 * 
	 * @param event The TabMenuEvent that describes the Event that
	 * 		occurred.
	 */
	public void tabReleased(TabMenuEvent event);
}