package net.foxycorndog.jfoxylib;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Component;
import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.FrameEvent;
import net.foxycorndog.jfoxylib.events.FrameListener;
import net.foxycorndog.jfoxylib.events.MouseEvent;
import net.foxycorndog.jfoxylib.events.MouseListener;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.util.Intersects;
import net.foxycorndog.jfoxylib.util.ResourceLocator;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.DisplayMode;

/**
 * Class that is used to control the Frame used in the game.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 9, 2013 at 3:18:44 AM
 * @since	v0.1
 * @version Mar 9, 2013 at 3:18:44 AM
 * @version	v0.2
 */
public class Frame
{
	private static boolean					created;
	private static boolean					vSync;
	private static boolean					initialized;
	
	private static int						fps, targetFPS;
	private static int						width, height;
	private static int						dfps;
	private static int						pred;
	private static int						totalFPS, secondsAlive, maxFPS, minFPS;
	
	private static long						startTime  = System.currentTimeMillis();
	private static long						newOldTime = System.nanoTime();
	private static long						oldTime    = newOldTime;
	
	private static float					delta;
	
	private static Panel					panel;
	
	private static org.lwjgl.opengl.Display	display;
	
	private static ArrayList<Component>		components;
	private static ArrayList<FrameListener>	frameListeners;
	
	public static void init()
	{
		if (initialized)
		{
			return;
		}
		
		panel = new Panel(null);
		panel.setLocation(0, 0);
		panel.setSize(width, height);
		
		startTime  = System.currentTimeMillis();
		newOldTime = System.nanoTime();
		oldTime    = newOldTime;
		
		minFPS     = Integer.MAX_VALUE;
		
//		display.setSwapInterval(0);
		
		initialized = true;
	}
	
	/**
	 * Returns whether or not the Frame has been created.
	 * 
	 * @return Whether or not the Frame has been created.
	 */
	public static boolean wasCreated()
	{
		return created;
	}
	
	/**
	 * Method to create a default Frame 7/10's the width and height of
	 * the screen size.
	 */
	public static void create()
	{
		create((int)(Display.getWidth() * 0.7f), (int)(Display.getHeight() * 0.7f));
	}
	
	/**
	 * Method to create a Frame the specified size.
	 * 
	 * @param width The width of the Frame to create.
	 * @param height The height of the Frame to create.
	 */
	public static void create(int width, int height)
	{
		if (width <= 0 || height <= 0)
		{
			throw new IllegalArgumentException("The width and height of the Frame must both be > 0");
		}
		
		ResourceLocator.locateNatives();
		
		try
		{
			DisplayMode d = new DisplayMode(width, height);
			
			display.setDisplayMode(d);
			display.create();
			
			setVSyncEnabled(false);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		components     = new ArrayList<Component>();
		frameListeners = new ArrayList<FrameListener>();
		
		Mouse.addMouseListener(new MouseListener()
		{
			public void mouseUp(MouseEvent event)
			{
				
			}
			
			public void mouseReleased(MouseEvent event)
			{
				for (int i = components.size() - 1; i >= 0; i--)
				{
					if (i >= components.size())
					{
						continue;
					}
					
					Component comp = components.get(i);
					
					if (comp.isDisposed())
					{
						continue;
					}
					
					boolean intersects = intersectsMouse(comp);
					
					if (comp instanceof Button)
					{
						Button button = (Button)comp;
						
						if (intersects && button.isEnabled() && button.isVisible())
						{
							ArrayList<ButtonListener> buttonListeners = button.getButtonListeners();
							
							for (int n = buttonListeners.size() - 1; n >= 0; n--)
							{
								ButtonListener listener = buttonListeners.get(n);
								
								ButtonEvent buttonEvent = new ButtonEvent(button, event.getButton());
								
								listener.buttonReleased(buttonEvent);
							}
							
							break;
						}
					}
				}
			}
			
			public void mousePressed(MouseEvent event)
			{
				boolean focused = false;
				
				for (int i = components.size() - 1; i >= 0; i--)
				{
					if (i >= components.size())
					{
						continue;
					}
					
					Component comp = components.get(i);
					
					comp.setFocused(false);
					
					if (comp.isDisposed())
					{
						continue;
					}

					boolean intersects = intersectsMouse(comp);
					
					if (intersects && comp.isEnabled() && comp.isVisible())
					{
						if (comp instanceof Button)
						{
							Button button = (Button)comp;
							
							if (button.isHovered())
							{
								ArrayList<ButtonListener> buttonListeners = button.getButtonListeners();
								
								for (int n = buttonListeners.size() - 1; n >= 0; n--)
								{
									ButtonListener listener = buttonListeners.get(n);
									
									ButtonEvent buttonEvent = new ButtonEvent(button, event.getButton());
									
									listener.buttonPressed(buttonEvent);
								}
								
								break;
							}
						}
						
						focused = true;
						
						comp.setFocused(true);
					}
				}
			}
			
			public void mouseMoved(MouseEvent event)
			{
				for (int i = components.size() - 1; i >= 0; i--)
				{
					if (i >= components.size())
					{
						continue;
					}
					
					Component comp = components.get(i);
					
					if (comp.isDisposed())
					{
						continue;
					}
					
					boolean intersects = intersectsMouse(comp);
					
					if (comp instanceof Button)
					{
						Button button = (Button)comp;
						
						if (button.isEnabled() && button.isVisible())
						{
							ArrayList<ButtonListener> buttonListeners = button.getButtonListeners();
							
							for (int n = buttonListeners.size() - 1; n >= 0; n--)
							{
								ButtonListener listener = buttonListeners.get(n);
								
								ButtonEvent buttonEvent = new ButtonEvent(button, event.getButton());
								
								if (intersects && !button.isHovered())
								{
									listener.buttonHovered(buttonEvent);
								}
								else if (!intersects && button.isHovered())
								{
									listener.buttonUnHovered(buttonEvent);
								}
							}
						}
					}
				}
			}
			
			public void mouseDown(MouseEvent event)
			{
				for (int i = components.size() - 1; i >= 0; i--)
				{
					if (i >= components.size())
					{
						continue;
					}
					
					Component comp = components.get(i);
					
					if (comp.isDisposed())
					{
						continue;
					}
					
					boolean intersects = intersectsMouse(comp);
					
					if (comp instanceof Button)
					{
						Button button = (Button)comp;
						
						if (button.isEnabled() && button.isVisible())
						{
							ArrayList<ButtonListener> buttonListeners = button.getButtonListeners();
							
							for (int n = buttonListeners.size() - 1; n >= 0; n--)
							{
								ButtonListener listener = buttonListeners.get(n);
								
								ButtonEvent buttonEvent = new ButtonEvent(button, event.getButton());
								
								if (intersects)
								{
									listener.buttonDown(buttonEvent);
								}
								else if (!intersects)
								{
									listener.buttonUp(buttonEvent);
								}
							}
							
							break;
						}
					}
				}
			}

			public void mouseEntered(MouseEvent event)
			{
				
			}

			public void mouseExited(MouseEvent event)
			{
				for (int i = components.size() - 1; i >= 0; i--)
				{
					if (i >= components.size())
					{
						continue;
					}
					
					Component comp = components.get(i);
					
					if (comp.isDisposed())
					{
						continue;
					}
					
					boolean intersects = intersectsMouse(comp);
					
					if (comp instanceof Button)
					{
						Button button = (Button)comp;
						
						if (button.isHovered() && button.isEnabled() && button.isVisible())
						{
							ArrayList<ButtonListener> buttonListeners = button.getButtonListeners();
							
							for (int n = buttonListeners.size() - 1; n >= 0; n--)
							{
								ButtonListener listener = buttonListeners.get(n);
								
								ButtonEvent buttonEvent = new ButtonEvent(button, event.getButton());
								
								listener.buttonUnHovered(buttonEvent);
							}
						}
					}
				}
			}
		});
		
		created = true;
	} 
	
	/**
	 * Check whether the specified Component is located under the Mouse
	 * cursor.
	 * 
	 * @param comp The Component to check if it is under the Mouse or not.
	 * @return Whether the Component is under the Mouse cursor or not.
	 */
	private static boolean intersectsMouse(Component comp)
	{
		float trans[] = GL.getAmountTranslated();
		
		return comp.isVisible() && Mouse.isInFrame() &&
				Intersects.rectangles(comp.getDisplayX(), comp.getDisplayY(),
						comp.getDisplayWidth(), comp.getDisplayHeight(),
						Mouse.getX(), Mouse.getY(), 1, 1);
	}
	
	/**
	 * Updates the Frame and its Components. Called every frame.
	 */
	public static void update()
	{
		long newTime = System.currentTimeMillis();
		
		if (fps == 0 && dfps > 0)
		{
			newOldTime = System.nanoTime();
			
			int change = (int)(newOldTime - oldTime);
			
			if (change != 0)
			{
				pred = 1000000000 / change;
				
				fps  = pred;
			}
			
			delta = 60f / fps;
		
			oldTime = newOldTime;
		}
		
		if (width != display.getWidth() || height != display.getHeight())
		{
			width  = display.getWidth();
			height = display.getHeight();
			
			for (int i = frameListeners.size() - 1; i >= 0; i--)
			{
				FrameListener listener = frameListeners.get(i);
				
				FrameEvent event = new FrameEvent();
				
				listener.frameResized(event);
			}
			
			panel.setSize(width, height);
		}
		
		dfps++;
		
		if (startTime + 1000 <= newTime)
		{
			fps  = dfps;
			dfps = 0;
			
			startTime = newTime;
			
			Frame.setTitle(fps + "");
			
			totalFPS += fps;
			secondsAlive++;
			
			if (fps > maxFPS)
			{
				maxFPS = fps;
			}
			if (fps < minFPS)
			{
				minFPS = fps;
			}
			
			delta = 60f / fps;
		}
	}
	
	/**
	 * Get the main Frame Panel that holds all of the Components in the
	 * Frame.
	 * 
	 * @return The main Frame Panel instance.
	 */
	public static Panel getPanel()
	{
		return panel;
	}
	
	/**
	 * Add a Component to the Frame. Lets the listeners be active.
	 * 
	 * @param component The Component to add.
	 * @return Whether it was added successfully or not.
	 */
	public static boolean add(Component component)
	{
		return components.add(component);
	}
	
	/**
	 * Remove a Component from the Frame.
	 * 
	 * @param component The Component to remove.
	 * @return Whether it was removed successfully or not.
	 */
	public static boolean remove(Component component)
	{
		return components.remove(component);
	}
	
	/**
	 * Set whether the Frame should be resizable.
	 * 
	 * @param resizable Whether the Frame should be resizable.
	 */
	public static void setResizable(boolean resizable)
	{
		if (resizable != display.isResizable())
		{
			display.setResizable(resizable);
		}
	}
	
	/**
	 * Returns whether the Frame has Vertical Synchronization enabled or
	 * not.
	 * 
	 * @return Whether the Frame has Vertical Synchronization enabled or
	 * 		not.
	 */
	public static boolean isVSyncEnabled()
	{
		return vSync;
	}
	
	/**
	 * Limit the frame rate to the monitors capable speed to reduce
	 * the tearing of the frames. Map slow down the performance, but
	 * in most cases looks better.
	 * 
	 * @param vSync Whether or not to enable or disable vertical
	 * 		synchronization.
	 */
	public static void setVSyncEnabled(boolean vSync)
	{
		if (vSync != Frame.vSync)
		{
			display.setVSyncEnabled(vSync);
			
			Frame.vSync = vSync;
		}
	}
	
	/**
	 * Get the delta variable that determines the balance between the
	 * high and low frames for animations.
	 * 
	 * @return The delta variable value.
	 */
	public static float getDelta()
	{
		return delta;
	}
	
	/**
	 * Get the minimum value that the fps has reached throughout the
	 * execution of the program.
	 * 
	 * @return The value for the minimum fps.
	 */
	public static int getMinFPS()
	{
		return minFPS;
	}
	
	/**
	 * Get the maximum value that the fps has reached throughout the
	 * execution of the program.
	 * 
	 * @return The value for the maximum fps.
	 */
	public static int getMaxFPS()
	{
		return maxFPS;
	}
	
	/**
	 * Get the total number of frames per second the program has obtained
	 * throughout the execution of the program.
	 * 
	 * @return The total number of fps the program has obtained throughout
	 * 		the execution of the program.
	 */
	public static int getTotalFPS()
	{
		return totalFPS;
	}
	
	/**
	 * Get the total number of seconds that the program has been alive.
	 * 
	 * @return The total number of seconds that the program has been
	 * 		alive.
	 */
	public static int getSecondsAlive()
	{
		return secondsAlive;
	}
	
	/**
	 * Get the amount of frames that the Frame gets per second. Each
	 * frame includes the clearing of the screen, calls to the loop and
	 * render methods, and other methods too.
	 * 
	 * @return The most recent amount of frames per second.
	 */
	public static int getFPS()
	{
		return fps;
	}

	/**
	 * Set the value that is used to display the frames per second.
	 * 
	 * @param fps The value to set it to.
	 */
	public static void setdFPS(int fps)
	{
		Frame.fps = fps;
	}
	
	/**
	 * Get the value that is set for the target FPS. The target FPS
	 * is used to create a goal for the Frame to try to reach whenever
	 * looping through the main loop. The program tries to accomplish
	 * the specified amount of frames per second each second.
	 * 
	 * @return The target frames per second each second.
	 */
	public static int getTargetFPS()
	{
		return targetFPS;
	}
	
	/**
	 * Set the value that is set for the target FPS. The target FPS
	 * is used to create a goal for the Frame to try to reach whenever
	 * looping through the main loop. The program tries to accomplish
	 * the specified amount of frames per second each second.
	 * 
	 * @param targetFPS The target frames per second each second.
	 */
	public static void setTargetFPS(int targetFPS)
	{
		Frame.targetFPS = targetFPS;
	}
	
	/**
	 * Get the title that is displayed in the Title Bar of the Frame.
	 * 
	 * @return The title that is displayed in the Title Bar.
	 */
	public static String getTitle()
	{
		return display.getTitle();
	}
	
	/**
	 * Set the title that is displayed in the Title Bar of the Frame.
	 * 
	 * @param title The title that is to be displayed in the Title Bar.
	 */
	public static void setTitle(String title)
	{
		display.setTitle(title);
	}
	
	/**
	 * Get the horizontal location of the Frame in the Display.
	 * 
	 * @return The horizontal location of the Frame in the Display.
	 */
	public static int getX()
	{
		return display.getX();
	}
	
	/**
	 * Get the vertical location of the Frame in the Display.
	 * 
	 * @return The vertical location of the Frame in the Display.
	 */
	public static int getY()
	{
		return display.getY();
	}
	
	/**
	 * Set the location of the Frame.
	 * 
	 * @param x The horizontal location to put the Frame at.
	 * @param y The vertical location to put the Frame at.
	 */
	public static void setLocation(int x, int y)
	{
		display.setLocation(x, y);
	}
	
	/**
	 * Get the horizontal size of the Frame in the Display.
	 * 
	 * @return The horizontal size of the Frame in the Display.
	 */
	public static int getWidth()
	{
		return width;
	}
	
	/**
	 * Get the vertical size of the Frame in the Display.
	 * 
	 * @return The vertical size of the Frame in the Display.
	 */
	public static int getHeight()
	{
		return height;
	}
	
	/**
	 * Add the specified FrameListener to this Frame.
	 * 
	 * @param listener The FrameListener to add to this Frame.
	 * @return Whether the FrameListener was added successfully or not.
	 */
	public static boolean addFrameListener(FrameListener listener)
	{
		return frameListeners.add(listener);
	}
	
	/**
	 * Remove the specified FrameListener from this Frame.
	 * 
	 * @param listener The FrameListener to remove from this Frame.
	 * @return Whether the remove was successful or not.
	 */
	public static boolean removeFrameListener(FrameListener listener)
	{
		return frameListeners.remove(listener);
	}
	
//	/**
//	 * Set whether to render the Frame to the monitor display or not.
//	 * 
//	 * @param visible Whether to render the Frame to the monitor display
//	 * 		or not.
//	 */
//	public static void setVisible(boolean visible)
//	{
//		
//	}
	
//	/**
//	 * 
//	 * 
//	 * @param image16Location
//	 * @param image32Location
//	 * @throws IOException
//	 */
//	public static void setIcon(String image16Location, String image32Location) throws IOException
//	{
//		InputStream in;
//		
//		in = new FileInputStream(image16Location);
//	
//		PNGDecoder decoder = new PNGDecoder(in);
//		
//		ByteBuffer buf16 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
//		decoder.decode(buf16, decoder.getWidth() * 4, Format.RGBA);
//		buf16.flip();
//		
//		in.close();
//		
//		
//		in = new FileInputStream(image32Location);
//		
//		decoder = new PNGDecoder(in);
//		
//		ByteBuffer buf32 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
//		decoder.decode(buf32, decoder.getWidth() * 4, Format.RGBA);
//		buf32.flip();
//		
//		in.close();
//		
//		
//		Display.setIcon(new ByteBuffer[] { buf16, buf32 });
//	}
}