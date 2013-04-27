package net.foxycorndog.jfoxylib.events;

import net.foxycorndog.jfoxylib.components.Button;

/**
 * Class used for each Button Event that is dispatched.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:18 PM
 * @since	v0.1
 * @version	Mar 10, 2013 at 1:53:11 PM
 * @version	v0.2
 */
public class ButtonEvent extends Event
{
	private Button	source;
	
	private boolean	buttons[];
	
	/**
	 * Create a ButtonEvent with the specified source.
	 * 
	 * @param source The Button that had an action performed on it.
	 */
	public ButtonEvent(Button source, boolean buttons[])
	{
		this.source  = source;
		
		this.buttons = buttons;
	}
	
	/**
	 * Get the source of the Event.
	 * 
	 * @return The Button that caused the event.
	 */
	public Button getSource()
	{
		return source;
	}
	
	/**
	 * Get what buttons were pressed during the event.
	 * 
	 * @return A boolean array with a description of what buttons were
	 * 		pressed during the event. (LMB = 0, RMB = 1, Wheel = 2)
	 */
	public boolean[] getButtons()
	{
		return buttons;
	}
}