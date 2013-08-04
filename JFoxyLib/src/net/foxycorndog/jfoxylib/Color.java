package net.foxycorndog.jfoxylib;

/**
 * Class that holds information for a color. Each color has a red,
 * green, and blue component. These components can range from 0-255.
 * 0 would represent black and 255 would represent the specific color.
 * This class also has methods to manipulate the data if needed.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 6:59:15 AM
 * @since	v0.1
 * @version	Apr 23, 2013 at 6:59:15 AM
 * @version	v0.1
 */
public class Color extends java.awt.Color
{
	private	float	r, g, b, a;
	
	public static final Color white = new Color(255, 255, 255);
	public static final Color WHITE = white;
	public static final Color lightGray = new Color(192, 192, 192);
	public static final Color LIGHT_GRAY = lightGray;
	public static final Color gray = new Color(128, 128, 128);
	public static final Color GRAY = gray;
	public static final Color darkGray = new Color(64, 64, 64);
	public static final Color DARK_GRAY = darkGray;
	public static final Color black = new Color(0, 0, 0);
	public static final Color BLACK = black;
	public static final Color red = new Color(255, 0, 0);
	public static final Color RED = red;
	public static final Color pink = new Color(255, 175, 175);
	public static final Color PINK = pink;
	public static final Color orange = new Color(255, 200, 0);
	public static final Color ORANGE = orange;
	public static final Color yellow = new Color(255, 255, 0);
	public static final Color YELLOW = yellow;
	public static final Color green = new Color(0, 255, 0);
	public static final Color GREEN = green;
	public static final Color magenta = new Color(255, 0, 255);
	public static final Color MAGENTA = magenta;
	public static final Color cyan = new Color(0, 255, 255);
	public static final Color CYAN = cyan;
	public static final Color blue = new Color(0, 0, 255);
	public static final Color BLUE = blue;
	
	/**
	 * Create a color with the specified red, green, and blue values.
	 * 
	 * @param r The red component for the Color.
	 * @param g The green component for the Color.
	 * @param b The blue component for the Color.
	 */
	public Color(int r, int g, int b)
	{
		this(r, g, b, 255);
	}
	
	/**
	 * Create a color with the specified red, green, and blue values.
	 * (0 - 255)
	 * 
	 * @param r The red component for the Color.
	 * @param g The green component for the Color.
	 * @param b The blue component for the Color.
	 * @param a The alpha component for the Color.
	 */
	public Color(int r, int g, int b, int a)
	{
		this(r / 255f, g / 255f, b / 255f, a / 255f);
	}
	
	/**
	 * Create a color with the specified red, green, and blue values.
	 * 
	 * @param r The red component for the Color.
	 * @param g The green component for the Color.
	 * @param b The blue component for the Color.
	 */
	public Color(float r, float g, float b)
	{
		this(r, g, b, 1);
	}
	
	/**
	 * Create a color with the specified red, green, and blue values.
	 * (0 - 1)
	 * 
	 * @param r The red component for the Color.
	 * @param g The green component for the Color.
	 * @param b The blue component for the Color.
	 * @param a The alpha component for the Color.
	 */
	public Color(float r, float g, float b, float a)
	{
		super(r, g, b, a);
		
		setData(r, g, b, a);
	}
	
	/**
	 * Get the red component of the Color.
	 * 
	 * @return The red component of the Color.
	 */
	public int getRed()
	{
		return Math.round(r * 255);
	}
	
	/**
	 * Get the green component of the Color.
	 * 
	 * @return The green component of the Color.
	 */
	public int getGreen()
	{
		return Math.round(g * 255);
	}
	
	/**
	 * Get the blue component of the Color.
	 * 
	 * @return The blue component of the Color.
	 */
	public int getBlue()
	{
		return Math.round(b * 255);
	}
	
	/**
	 * Get the alpha component of the Color.
	 * 
	 * @return The alpha component of the Color.
	 */
	public int getAlpha()
	{
		return Math.round(a * 255);
	}
	
	/**
	 * Get the red component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The red component of the Color in float form.
	 */
	public float getRedf()
	{
		return r;
	}
	
	/**
	 * Get the green component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The green component of the Color in float form.
	 */
	public float getGreenf()
	{
		return g;
	}
	
	/**
	 * Get the blue component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The blue component of the Color in float form.
	 */
	public float getBluef()
	{
		return b;
	}
	
	/**
	 * Get the alpha component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The alpha component of the Color in float form.
	 */
	public float getAlphaf()
	{
		return a;
	}
	
	/**
	 * Set the Color's red, green, and blue components to the specified
	 * values.
	 * 
	 * @param r The new red component.
	 * @param g The new green component.
	 * @param b The new blue component.
	 * @param a The new alpha component.
	 */
	public void setData(int r, int g, int b, int a)
	{
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
		this.a = a / 255f;
		
		checkBounds();
	}
	
	/**
	 * Set the Color's red, green, and blue components to the specified
	 * values.
	 * 
	 * @param r The new red component.
	 * @param g The new green component.
	 * @param b The new blue component.
	 * @param a The new alpha component.
	 */
	public void setData(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		
		checkBounds();
	}
	
	/**
	 * Brighten the Color the specified amount. Each of the Color
	 * components will be increased the specified amount.
	 * 
	 * eg. A Color with the values (40, 10, 200, 0) that has a call
	 * of brighten(30, 10, 0, 0) will have the resulting values of
	 * (70, 20, 200, 0)
	 * 
	 * @param r The amount of red to add.
	 * @param g The amount of green to add.
	 * @param b The amount of blue to add.
	 * @param a The amount of alpha to add.
	 */
	public void brighten(int r, int g, int b, int a)
	{
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		
		checkBounds();
	}
	
	/**
	 * Darken the Color the specified amount. Each of the Color
	 * components will be decreased the specified amount.
	 * 
	 * eg. A Color with the values (40, 10, 200, 0) that has a call
	 * of darken(30, 10, 0, 0) will have the resulting values of
	 * (10, 0, 200, 0)
	 * 
	 * @param r The amount of red to subtract.
	 * @param g The amount of green to subtract.
	 * @param b The amount of blue to subtract.
	 * @param a The amount of alpha to subtract.
	 */
	public void darken(int r, int g, int b, int a)
	{
		brighten(-r, -g, -b, -a);
	}
	
	/**
	 * Check to make sure that the Color component values are within
	 * the range of 0-255. If not, then put them back in bounds.
	 */
	private void checkBounds()
	{
		if (r < 0)
		{
			r = 0;
		}
		if (g < 0)
		{
			g = 0;
		}
		if (b < 0)
		{
			b = 0;
		}
		if (a < 0)
		{
			a = 0;
		}
		
		if (r > 1)
		{
			r = 1;
		}
		if (g > 1)
		{
			g = 1;
		}
		if (b > 1)
		{
			b = 1;
		}
		if (a > 1)
		{
			a = 1;
		}
	}
}