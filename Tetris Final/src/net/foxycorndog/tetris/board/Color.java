package net.foxycorndog.tetris.board;

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
public class Color
{
	private	int					red, green, blue;
	
	public static final Color	MAGENTA = new Color(157, 0, 159),
			YELLOW = new Color(220, 212, 0), RED = new Color(159, 0, 30),
			BLUE = new Color(0, 0, 165), ORANGE = new Color(196, 106, 0),
			CYAN = new Color(0, 196, 175), GREEN = new Color(0, 196, 55);
	
	/**
	 * Create a color with the specified red, green, and blue values.
	 * 
	 * @param r The red component for the Color.
	 * @param g The green component for the Color.
	 * @param b The blue component for the Color.
	 */
	public Color(int r, int g, int b)
	{
		setData(r, g, b);
	}
	
	/**
	 * Get the red component of the Color.
	 * 
	 * @return The red component of the Color.
	 */
	public int getRed()
	{
		return red;
	}

	/**
	 * Get the green component of the Color.
	 * 
	 * @return The green component of the Color.
	 */
	public int getGreen()
	{
		return green;
	}

	/**
	 * Get the blue component of the Color.
	 * 
	 * @return The blue component of the Color.
	 */
	public int getBlue()
	{
		return blue;
	}
	
	/**
	 * Get the red component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The red component of the Color in float form.
	 */
	public float getRedf()
	{
		return red / 255f;
	}
	
	/**
	 * Get the green component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The green component of the Color in float form.
	 */
	public float getGreenf()
	{
		return green / 255f;
	}
	
	/**
	 * Get the blue component of the Color as a float value.
	 * ranging from 0-1 (0 == 0 & 1 == 255)
	 * 
	 * @return The blue component of the Color in float form.
	 */
	public float getBluef()
	{
		return blue / 255f;
	}
	
	/**
	 * Set the Color's red, green, and blue components to the specified
	 * values.
	 * 
	 * @param r The new red component.
	 * @param g The new green component.
	 * @param b The new blue component.
	 */
	public void setData(int r, int g, int b)
	{
		this.red   = r;
		this.green = g;
		this.blue  = b;
		
		checkBounds();
	}
	
	/**
	 * Brighten the Color the specified amount. Each of the Color
	 * components will be increased the specified amount.
	 * 
	 * eg. A Color with the values (40, 10, 200) that has a call
	 * of brighten(30, 10, 0) will have the resulting values of
	 * (70, 20, 200)
	 * 
	 * @param r The amount of red to add.
	 * @param g The amount of green to add.
	 * @param b The amount of blue to add.
	 */
	public void brighten(int r, int g, int b)
	{
		this.red   += r;
		this.green += g;
		this.blue  += b;
		
		checkBounds();
	}

	/**
	 * Darken the Color the specified amount. Each of the Color
	 * components will be decreased the specified amount.
	 * 
	 * eg. A Color with the values (40, 10, 200) that has a call
	 * of darken(30, 10, 0) will have the resulting values of
	 * (10, 0, 200)
	 * 
	 * @param r The amount of red to subtract.
	 * @param g The amount of green to subtract.
	 * @param b The amount of blue to subtract.
	 */
	public void darken(int r, int g, int b)
	{
		brighten(-r, -g, -b);
	}
	
	/**
	 * Check to make sure that the Color component values are within
	 * the range of 0-255. If not, then put them back in bounds.
	 */
	private void checkBounds()
	{
		if (red < 0)
		{
			red = 0;
		}
		if (green < 0)
		{
			green = 0;
		}
		if (blue < 0)
		{
			blue = 0;
		}
		
		if (red >= 256)
		{
			red = 255;
		}
		
		if (green >= 256)
		{
			green = 255;
		}
		
		if (blue >= 256)
		{
			blue = 255;
		}
	}
}