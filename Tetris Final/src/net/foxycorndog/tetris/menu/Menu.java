package net.foxycorndog.tetris.menu;

/**
 * Abstract class for all Menus used in the Tetris game.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:13:05 PM
 * @since	v0.1
 * @version	Apr 20, 2013 at 11:13:05 PM
 * @version	v0.1
 */
public abstract class Menu
{
	private int	x, y;
	
	/**
	 * Default constructor of the Menu.
	 */
	public Menu()
	{
		
	}
	
	/**
	 * Get the x position of the Menu.
	 * 
	 * @return The x position of the Menu.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Get the y position of the Menu.
	 * 
	 * @return The y position of the Menu.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Set the x and y location of the Menu.
	 * 
	 * @param x The new x location of the Menu.
	 * @param y The new y location of the Menu.
	 */
	public void setLocation(int x, int y)
	{
		this.x = y;
		this.y = y;
	}
	
	/**
	 * Do any logic needed by the Menu.
	 */
	public abstract void loop();
	
	/**
	 * Render the Components of the Menu.
	 */
	public abstract void render();
	
	/**
	 * Dispose of the Components used by the Menu.
	 */
	public abstract void dispose();
}