package net.foxycorndog.jfoxylib.events;


/**
 * Interface that is used to implement the buttonPressed method
 * that will be called every time that a specific Button is pressed.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:28 PM
 * @since	v0.2
 * @version	Mar 10, 2013 at 1:25:10 AM
 * @version	v0.2
 */
public interface ButtonListener
{
	/**
	 * Event called whenever the specified Button is pressed.
	 * 
	 * @param event The ButtonEvent instance linked with this event.
	 */
	public void buttonPressed(ButtonEvent event);
	
	/**
	 * Event called whenever the specified Button is released.
	 * 
	 * @param event The ButtonEvent instance linked with this event.
	 */
	public void buttonReleased(ButtonEvent event);

	/**
	 * Event called whenever the specified Button is hovered.
	 * 
	 * @param event The ButtonEvent instance linked with this event.
	 */
	public void buttonHovered(ButtonEvent event);

	/**
	 * Event called whenever the specified Button is un-hovered.
	 * 
	 * @param event The ButtonEvent instance linked with this event.
	 */
	public void buttonUnHovered(ButtonEvent event);
}