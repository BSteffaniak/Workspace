package net.foxycorndog.jfoxylib.input;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.events.MouseEvent;
import net.foxycorndog.jfoxylib.events.MouseListener;

import org.lwjgl.LWJGLException;

/**
 * Class that is used to calculate and obtain information about the
 * Mouse and cursor.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 9, 2013 at 3:08:15 AM
 * @since	v0.1
 * @version May 22, 2013 at 9:06:15 PM
 * @version	v0.2
 */
public class Mouse
{	
	private static	org.lwjgl.input.Mouse		mouse;
	
	private static	boolean						grabbed;
	private static	boolean						inFrame;
	private	static	boolean						pointerInfoUpdated;
	
	private static	int							oldX, oldY;
	private static	int							dx, dy;
	private static	int							dWheel;
	private static	int							forcedX, forcedY;
	
	private static	PointerInfo					pointerInfo;
	
	private static	Robot						robot;
	
	private static	boolean						able[];
	
	private static	ArrayList<MouseListener>	listeners;
	
	public  static	final int					BUTTON_0 = 0;
	public  static	final int					BUTTON_1 = 1;
	public  static	final int					BUTTON_2 = 2;
	
	public  static	final int					LEFT_MOUSE_BUTTON  = BUTTON_0;
	public  static	final int					RIGHT_MOUSE_BUTTON = BUTTON_1;
	public  static	final int					MOUSE_WHEEL_BUTTON = BUTTON_2;
	
	static
	{
		try
		{
			mouse.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		listeners = new ArrayList<MouseListener>();
		
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			e.printStackTrace();
		}
		
		able = new boolean[3];
		
		for (int i = 0; i < able.length; i++)
		{
			able[i] = true;
		}
		
//		pointerInfo = MouseInfo.getPointerInfo();

		mouse.setClipMouseCoordinatesToWindow(false);
	}
	
//	/**
//	 * Set whether or not to clip the coordinates 
//	 * 
//	 * @param clip
//	 */
//	public static void setClipMouseCoordinatesToWindow(boolean clip)
//	{
//		mouse.setClipMouseCoordinatesToWindow(clip);
//	}
	
	/**
	 * Set the location of the cursor to the specified location on the
	 * Monitors display.
	 * 
	 * @param x The horizontal location from left to right. (left = 0)
	 * @param y The vertical location from top to bottom. (top = 0)
	 */
	public static void setLocation(int x, int y)
	{
//		pointerInfo = MouseInfo.getPointerInfo();
		
		int offX = x - getDisplayX();
		int offY = -(y - getDisplayY());
		
		robot.mouseMove(x, y);
		
		forcedX = offX;
		forcedY = offY;
		
//		System.out.println("offsets: " + offX + ", " + offY);
	}
	
	/**
	 * Get the integer form of the Pointers horizontal location in
	 * the Frame.
	 * 
	 * @return The horizontal location of the Pointer in the Frame.
	 */
	public static int getX()
	{
		return mouse.getX();
	}
	
	/**
	 * Get the integer form of the pointers vertical location in
	 * the Frame.
	 * 
	 * @return The vertical location of the pointer in the Frame.
	 */
	public static int getY()
	{
		return mouse.getY();
	}
	
	/**
	 * Get the pointers horizontal location in the Monitors Display.
	 * 
	 * @return The pointers horizontal location in the Monitors Display.
	 */
	public static int getDisplayX()
	{
		checkPointerInfo();
		
		return pointerInfo.getLocation().x;
	}
	
	/**
	 * Get the pointers vertical location in the Monitors Display.
	 * 
	 * @return The pointers vertical location in the Monitors Display.
	 */
	public static int getDisplayY()
	{
		checkPointerInfo();
		
		return pointerInfo.getLocation().y;
	}
	
	/**
	 * Check whether or not the PointerInfo has been updated or not.
	 * If it has not updated, then update it to the latest position.
	 */
	private static void checkPointerInfo()
	{
		if (!pointerInfoUpdated)
		{
			pointerInfo = MouseInfo.getPointerInfo();
		}
	}
	
	/**
	 * Get the displacement horizontal value of the cursor since the
	 * last frame.
	 * 
	 * @return The displacement horizontal value of the cursor.
	 */
	public static int getDX()
	{
		return dx;
	}
	
	/**
	 * Get the displacement vertical value of the cursor since the
	 * last frame.
	 * 
	 * @return The displacement vertical value of the cursor.
	 */
	public static int getDY()
	{
		return dy;
	}
	
	/**
	 * Returns whether the specified button is down.<br>
	 * <br>
	 * left mouse button = 0<br>
	 * right mouse button = 1<br>
	 * mouse wheel button = 2
	 * 
	 * @param button
	 * @return
	 */
	public static boolean isButtonDown(int button)
	{
		return mouse.isButtonDown(button);
	}
	
	/**
	 * Returns whether the mouse is grabbed by the Frame.
	 * 
	 * @return Whether the mouse is grabbed by the Frame.
	 */
	public static boolean isGrabbed()
	{
		return grabbed;
	}
	
	/**
	 * Set whether the mouse should be grabbed by the Frame or not.
	 * 
	 * @param grabbed Whether the Frame should grab the mouse or not.
	 */
	public static void setGrabbed(boolean grabbed)
	{
		if (grabbed != Mouse.grabbed)
		{
//			if (grabbed)
//			{
//				listeners.add(grabListener);
//			}
//			else
//			{
//				listeners.remove(grabListener);
//			}
//			
//			Mouse.grabbed = grabbed;
		
			Mouse.grabbed = grabbed;
			
			mouse.setGrabbed(grabbed);
		}
	}
	
	/**
	 * Get the displacement of the mouse wheel.
	 * 
	 * @return The displacement value of the mouse wheel.
	 */
	public static int getDWheel()
	{
		return dWheel;
	}
	
	/**
	 * Returns whether the Mouse cursor is inside the bounds of the Frame.
	 * 
	 * @return Whether the Mouse cursor is inside the bounds of the Frame.
	 */
	public static boolean isInFrame()
	{
		return inFrame;
	}
	
	/**
	 * Method used to update the Mouse each frame. Does all of the
	 * calculations for displacements and also notifies the listeners
	 * for their appropriate calls.
	 */
	public static void update()
	{
//		pointerInfo = MouseInfo.getPointerInfo();
		
		boolean newInFrame = mouse.isInsideWindow();
		
		dx = mouse.getX() - oldX;//pointerInfo.getLocation().x - oldX;
		dy = mouse.getY() - oldY;//pointerInfo.getLocation().y - oldY;
		
		if (dx != 0 || dy != 0)
		{
			pointerInfoUpdated = false;
		}
		
		dWheel = mouse.getDWheel();
		
		if (dx != 0 || dy != 0)
		{
			for (int i = listeners.size() - 1; i >= 0; i--)
			{
				MouseEvent event = new MouseEvent(getX(), getY(), -1);
				
				listeners.get(i).mouseMoved(event);
			}
		}
		
		if (newInFrame != inFrame)
		{
			for (int i = listeners.size() - 1; i >= 0; i--)
			{
				MouseEvent event = new MouseEvent(getX(), getY(), -1);
			
				if (newInFrame)
				{
					listeners.get(i).mouseEntered(event);
				}
				else
				{
					listeners.get(i).mouseExited(event);
				}
			}
		}
		
		for (int button = 0; button < 3; button++)
		{
			if (Mouse.isButtonDown(button))
			{
				if (able[button])
				{
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						MouseEvent event = new MouseEvent(getX(), getY(), button);
						
						listeners.get(i).mousePressed(event);
					}
					
					able[button] = false;
				}
				
				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					MouseEvent event = new MouseEvent(getX(), getY(), button);
					
					listeners.get(i).mouseDown(event);
				}
			}
			else
			{
				if (!able[button])
				{
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						MouseEvent event = new MouseEvent(getX(), getY(), button);
						
						listeners.get(i).mouseReleased(event);
					}
				}
				
				able[button] = true;
				
				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					MouseEvent event = new MouseEvent(getX(), getY(), button);
					
					listeners.get(i).mouseUp(event);
				}
			}
		}
		
		dx     -= forcedX;
		dy     -= forcedY;
		
		oldX    = mouse.getX();
		oldY    = mouse.getY();
		
		forcedX = 0;
		forcedY = 0;
		
		inFrame = newInFrame;
		
		mouse.next();
	}
	
	/**
	 * Add a MouseListener to be notified for actions.
	 * 
	 * @param listener The MouseListener instance to be used.
	 * @return Whether the add was successful.
	 */
	public static boolean addMouseListener(MouseListener listener)
	{
		return listeners.add(listener);
	}
	
	/**
	 * Remove the specified MouseListener from the list to be called.
	 * 
	 * @param listener The MouseListener to remove.
	 * @return Whether the remove was successful.
	 */
	public static boolean removeMouseListener(MouseListener listener)
	{
		return listeners.remove(listener);
	}
}