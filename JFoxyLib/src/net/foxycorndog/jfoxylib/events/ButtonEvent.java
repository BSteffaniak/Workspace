package net.foxycorndog.jfoxylib.events;

import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.input.Mouse;

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
	private	int		button;
	
	private Button	source;
	
	public	static	final	int	LEFT_MOUSE_BUTTON = Mouse.LEFT_MOUSE_BUTTON, RIGHT_MOUSE_BUTTON = Mouse.RIGHT_MOUSE_BUTTON, MOUSE_WHEEL_BUTTON = Mouse.MOUSE_WHEEL_BUTTON;
	
	/**
	 * Create a ButtonEvent with the specified source.
	 * 
	 * @param source The Button that had an action performed on it.
	 */
	public ButtonEvent(Button source, int button)
	{
		this.source  = source;
		
		this.button  = button;
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
	 * Get what button was pressed during the event.
	 * 
	 * @return An integer that represents the Button pressed.
	 */
	public int getButton()
	{
		return button;
	}
}