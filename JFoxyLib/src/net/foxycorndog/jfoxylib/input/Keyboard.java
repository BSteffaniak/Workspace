package net.foxycorndog.jfoxylib.input;

import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.opengl.GL;

import org.lwjgl.LWJGLException;

/**
 * Class that is used to calculate and obtain information about the
 * Keyboard and its specific keys.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:54:39 PM
 * @since	v0.1
 * @version	May 22, 2013 at 9:06:39 PM
 * @version	v0.2
 */
public class Keyboard
{
	private static org.lwjgl.input.Keyboard keyboard;
	
	/**
	 * Create the LWJGL keyboard.
	 */
	static
	{
		try
		{
			keyboard.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static final int KEY_ESCAPE			= keyboard.KEY_ESCAPE;
	public static final int KEY_F1				= keyboard.KEY_F1;
	public static final int KEY_F2				= keyboard.KEY_F2;
	public static final int KEY_F3				= keyboard.KEY_F3;
	public static final int KEY_F4				= keyboard.KEY_F4;
	public static final int KEY_F5				= keyboard.KEY_F5;
	public static final int KEY_F6				= keyboard.KEY_F6;
	public static final int KEY_F7				= keyboard.KEY_F7;
	public static final int KEY_F8				= keyboard.KEY_F8;
	public static final int KEY_F9				= keyboard.KEY_F9;
	public static final int KEY_F10				= keyboard.KEY_F10;
	public static final int KEY_F11				= keyboard.KEY_F11;
	public static final int KEY_F12				= keyboard.KEY_F12;
	public static final int KEY_TICK			= keyboard.KEY_NONE;
	public static final int KEY_1				= keyboard.KEY_1;
	public static final int KEY_2				= keyboard.KEY_2;
	public static final int KEY_3				= keyboard.KEY_3;
	public static final int KEY_4				= keyboard.KEY_4;
	public static final int KEY_5				= keyboard.KEY_5;
	public static final int KEY_6				= keyboard.KEY_6;
	public static final int KEY_7				= keyboard.KEY_7;
	public static final int KEY_8				= keyboard.KEY_8;
	public static final int KEY_9				= keyboard.KEY_9;
	public static final int KEY_0				= keyboard.KEY_0;
	public static final int KEY_NUMPAD1			= keyboard.KEY_NUMPAD1;
	public static final int KEY_NUMPAD2			= keyboard.KEY_NUMPAD2;
	public static final int KEY_NUMPAD3			= keyboard.KEY_NUMPAD3;
	public static final int KEY_NUMPAD4			= keyboard.KEY_NUMPAD4;
	public static final int KEY_NUMPAD5			= keyboard.KEY_NUMPAD5;
	public static final int KEY_NUMPAD6			= keyboard.KEY_NUMPAD6;
	public static final int KEY_NUMPAD7			= keyboard.KEY_NUMPAD7;
	public static final int KEY_NUMPAD8			= keyboard.KEY_NUMPAD8;
	public static final int KEY_NUMPAD9			= keyboard.KEY_NUMPAD9;
	public static final int KEY_NUMPAD0			= keyboard.KEY_NUMPAD0;
	public static final int KEY_MINUS			= keyboard.KEY_MINUS;
	public static final int KEY_EQUALS			= keyboard.KEY_EQUALS;
	public static final int KEY_BACKSPACE		= keyboard.KEY_BACK;
	public static final int KEY_TAB				= keyboard.KEY_TAB;
	public static final int KEY_Q				= keyboard.KEY_Q;
	public static final int KEY_W				= keyboard.KEY_W;
	public static final int KEY_E				= keyboard.KEY_E;
	public static final int KEY_R				= keyboard.KEY_R;
	public static final int KEY_T				= keyboard.KEY_T;
	public static final int KEY_Y				= keyboard.KEY_Y;
	public static final int KEY_U				= keyboard.KEY_U;
	public static final int KEY_I				= keyboard.KEY_I;
	public static final int KEY_O				= keyboard.KEY_O;
	public static final int KEY_P				= keyboard.KEY_P;
	public static final int KEY_LEFT_BRACKET	= keyboard.KEY_LBRACKET;
	public static final int KEY_RIGHT_BRACKET	= keyboard.KEY_RBRACKET;
	public static final int KEY_BACKSLASH		= keyboard.KEY_BACKSLASH;
	public static final int KEY_CAPS_LOCK		= keyboard.KEY_CAPITAL;
	public static final int KEY_A				= keyboard.KEY_A;
	public static final int KEY_S				= keyboard.KEY_S;
	public static final int KEY_D				= keyboard.KEY_D;
	public static final int KEY_F				= keyboard.KEY_F;
	public static final int KEY_G				= keyboard.KEY_G;
	public static final int KEY_H				= keyboard.KEY_H;
	public static final int KEY_J				= keyboard.KEY_J;
	public static final int KEY_K				= keyboard.KEY_K;
	public static final int KEY_L				= keyboard.KEY_L;
	public static final int KEY_SEMICOLON		= keyboard.KEY_SEMICOLON;
	public static final int KEY_APOSTROPHE		= keyboard.KEY_APOSTROPHE;
	public static final int KEY_ENTER			= keyboard.KEY_RETURN;
	public static final int KEY_RETURN			= KEY_ENTER;
	public static final int KEY_LEFT_SHIFT		= keyboard.KEY_LSHIFT;
	public static final int KEY_Z				= keyboard.KEY_Z;
	public static final int KEY_X				= keyboard.KEY_X;
	public static final int KEY_C				= keyboard.KEY_C;
	public static final int KEY_V				= keyboard.KEY_V;
	public static final int KEY_B				= keyboard.KEY_B;
	public static final int KEY_N				= keyboard.KEY_N;
	public static final int KEY_M				= keyboard.KEY_M;
	public static final int KEY_COMMA			= keyboard.KEY_COMMA;
	public static final int KEY_PERIOD			= keyboard.KEY_PERIOD;
	public static final int KEY_SLASH			= keyboard.KEY_SLASH;
	public static final int KEY_RIGHT_SHIFT		= keyboard.KEY_RSHIFT;
//	public static final int KEY_FUNCTION		= keyboard.KEY;
	public static final int KEY_CONTROL			= keyboard.KEY_LCONTROL;
	public static final int KEY_ALT				= keyboard.KEY_LMENU;
	public static final int KEY_COMMAND			= keyboard.KEY_LWIN;
	public static final int KEY_LWIN			= KEY_COMMAND;
	public static final int KEY_SPACE			= keyboard.KEY_SPACE;
	public static final int KEY_LEFT			= keyboard.KEY_LEFT;
	public static final int KEY_DOWN			= keyboard.KEY_DOWN;
	public static final int KEY_RIGHT			= keyboard.KEY_RIGHT;
	public static final int KEY_UP				= keyboard.KEY_UP;
	
	private static final boolean keys[];
	private static final boolean next[];
	
	private static final ArrayList<KeyListener>	listeners;
	
	private static boolean	capsLockOn;
	
	private static int		length;
	
	static
	{
		length = keyboard.KEYBOARD_SIZE;
		
		keys = new boolean[length];
		next = new boolean[length];
		
		listeners = new ArrayList<KeyListener>();
		
		Keyboard.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent event)
			{
				
			}
			
			public void keyReleased(KeyEvent event)
			{
				
			}
			
			public void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == Keyboard.KEY_CAPS_LOCK)
				{
					capsLockOn = !capsLockOn;
				}
			}
			
			public void keyDown(KeyEvent event)
			{
				
			}
		});
	}
	
	/**
	 * Destroy the keyboard and make it unusable.
	 */
	public static void destroy()
	{
		keyboard.destroy();
	}
	
	/**
	 * Get whether the specified key is pressed down at the current
	 * moment or not.
	 * 
	 * @param key The key code to check if pressed or not.
	 * @return Whether the specified key is pressed down at the current
	 * 		moment or not.
	 */
	public static boolean isKeyDown(int key)
	{
		return keyboard.isKeyDown(key);
	}
	
//	/**
//	 * 
//	 * 
//	 * @return
//	 */
//	public static int getEventKey()
//	{
//		return keyboard.getEventKey();
//	}
	
//	/**
//	 * 
//	 * 
//	 * @return
//	 */
//	public static boolean next()
//	{
//		return keyboard.next();
//	}
	
	/**
	 * Update the specified key's current status.
	 * 
	 * @param keyCode The code of the key to update.
	 * @return If the key is still pressed.
	 */
	private static boolean next(int keyCode)
	{
		boolean nxt = keys[keyCode] && next[keyCode];
		
//		keys[keyId] = false;
		
		return nxt;
	}
	
	/**
	 * Update the keyboard and all of the key status's
	 */
	public static void update()
	{
		KeyEvent event;
		
		for (int i = 0; i < keys.length; i ++)
		{
			if (keys[i])
			{
				for (int j = 0; j < listeners.size(); j++)
				{
					KeyListener listener = listeners.get(j);
					
					event = new KeyEvent(keyboard.getKeyName(i), i, getChar(i));
					
					listener.keyDown(event);
				}
			}
			
			if (!keys[i] && keyboard.isKeyDown(i))
			{
				next[i] = true;
				
				for (int j = 0; j < listeners.size(); j++)
				{
					KeyListener listener = listeners.get(j);
					
					event = new KeyEvent(keyboard.getKeyName(i), i, getChar(i));
					
					listener.keyPressed(event);
				}
			}
			else
			{
				if (keys[i] && !keyboard.isKeyDown(i))
				{
					for (int j = 0; j < listeners.size(); j++)
					{
						KeyListener listener = listeners.get(j);
						
						event = new KeyEvent(keyboard.getKeyName(i), i, getChar(i));
						
						listener.keyReleased(event);
						listener.keyTyped(event);
					}
				}
				
				next[i] = false;
			}
			
			keys[i] = keyboard.isKeyDown(i);
		}
	}
	
//	public static boolean[] getKeys()
//	{
//		refresh();
//		
//		return keys;
//	}
	
	/**
	 * Get the name of the key with the specified key code.
	 * 
	 * @param keyCode The key code for the specified key.
	 * @return The name of the specified key with the specified key code.
	 */
	public static String getKeyName(int keyCode)
	{
		return keyboard.getKeyName(keyCode);
	}
	
	/**
	 * Get the key code of the key with the specified name.
	 * 
	 * @param name The name of the key.
	 * @return The key code of the key with the specified name.
	 */
	public static int getKeyCode(String name)
	{
		name = name.toUpperCase();
		
		for (int i = 0; i < length; i ++)
		{
			if (getKeyName(i).equals(name))
			{
				return i;
			}
		}
		
		if (name.equals("ENTER"))
		{
			return KEY_ENTER;
		}
		
		return 0;
	}
	
	/**
	 * Add the specified KeyListener instance to the list of Listeners
	 * that will be notified of KeyEvents.
	 * 
	 * @param listener The KeyListener instance to add to the list of
	 * 		listeners.
	 * @return Whether it was successfully added to the list or not.
	 */
	public static boolean addKeyListener(KeyListener listener)
	{
		return listeners.add(listener);
	}
	
	/**
	 * Remove the specified KeyListener instance from the list of
	 * Listeners that are notified of KeyEvents.
	 * 
	 * @param listener The KeyListener instance to remove from the
	 * 		list of listeners.
	 * @return Whether it was successfully removed from the list or
	 * 		not.
	 */
	public static boolean removeKeyListener(KeyListener listener)
	{
		return listeners.remove(listener);
	}
	
	/**
	 * Get whether the caps lock is on or not.
	 * 
	 * @return Whether the caps lock is on or not.
	 */
	public static boolean isCapsLockOn()
	{
		return capsLockOn;
	}
	
	/**
	 * Get the char that represents the keyCode (if there is one).
	 * 
	 * (ONLY WORKS WITH SYMBOLS)
	 * 
	 * @param keyCode The keyCode of the key.
	 * @return The char that represnets the keyCode.
	 */
	public static char getChar(int keyCode)
	{
		if (keyCode == KEY_SEMICOLON)
		{
			return ';';
		}
		else if (keyCode == KEY_MINUS)
		{
			return '-';
		}
		else if (keyCode == KEY_EQUALS)
		{
			return '=';
		}
		else if (keyCode == KEY_LEFT_BRACKET)
		{
			return '[';
		}
		else if (keyCode == KEY_RIGHT_BRACKET)
		{
			return ']';
		}
		else if (keyCode == KEY_BACKSLASH)
		{
			return '\\';
		}
		else if (keyCode == KEY_APOSTROPHE)
		{
			return '\'';
		}
		else if (keyCode == KEY_COMMA)
		{
			return ',';
		}
		else if (keyCode == KEY_PERIOD)
		{
			return '.';
		}
		else if (keyCode == KEY_SLASH)
		{
			return '/';
		}
		else if (keyCode == KEY_TICK)
		{
			return '`';
		}
		
		return 0;
	}
	
	/**
	 * Return the character that would be equivalent to pressing shift
	 * on the specified key.
	 * 
	 * (ONLY WORKS WITH NUMBERS AND SYMBOLS)
	 * 
	 * @param key The key that is being simulated with a shift press.
	 * @return The output of the Shift+key combination.
	 */
	public static char shiftKey(char key)
	{
		if (key == '1')
		{
			return '!';
		}
		else if (key == '2')
		{
			return '@';
		}
		else if (key == '3')
		{
			return '#';
		}
		else if (key == '4')
		{
			return '$';
		}
		else if (key == '5')
		{
			return '%';
		}
		else if (key == '6')
		{
			return '^';
		}
		else if (key == '7')
		{
			return '&';
		}
		else if (key == '8')
		{
			return '*';
		}
		else if (key == '9')
		{
			return '(';
		}
		else if (key == '0')
		{
			return ')';
		}
		else if (key == '-')
		{
			return '_';
		}
		else if (key == '=')
		{
			return '+';
		}
		else if (key == '`')
		{
			return '~';
		}
		else if (key == '[')
		{
			return '{';
		}
		else if (key == ']')
		{
			return '}';
		}
		else if (key == '\\')
		{
			return '|';
		}
		else if (key == ';')
		{
			return ':';
		}
		else if (key == '\'')
		{
			return '"';
		}
		else if (key == ',')
		{
			return '<';
		}
		else if (key == '.')
		{
			return '>';
		}
		else if (key == '/')
		{
			return '?';
		}
		
		return key;
	}
	
	/**
	 * Get the amount of keys that are available on the current
	 * Keyboard instance.
	 * 
	 * @return The amount of keys that are available on the current
	 * 		Keyboard instance.
	 */
	public static int getAmountOfKeys()
	{
		return length;
	}
	
//	/**
//	 * 
//	 */
//	public static void poll()
//	{
//		keyboard.poll();
//	}
}