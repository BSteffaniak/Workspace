package net.foxycorndog.jfoxylib.components;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * Abstract parent class that is used for every Component. Holds the
 * position, size, and parent of each Component. Also includes other
 * things too.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 12:24:21 AM
 * @since	v0.1
 * @version Mar 10, 2013 at 12:24:21 AM
 * @version	v0.2
 */
public abstract class Component
{
	private boolean	disposed;
	private boolean	visible;
	
	private int		x, y;
	private int		alignX, alignY;
	private int		width, height;
	private int		horizontalAlignment, verticalAlignment;
	
	private float	scaleX, scaleY;
	private float	translatedX, translatedY;
	
	private Panel	parent;
	
	public static final int	LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 0, TOP = 2;
	
	/**
	 * Construct a Component with the specified parent Panel.
	 * 
	 * @param parent The parent Panel of this Component.
	 */
	public Component(Panel parent)
	{
		this.parent   = parent;
		
		this.visible  = true;
		
		this.disposed = false;
		
		if (parent != null)
		{
			parent.addChild(this);
		}
	}
	
	/**
	 * Get whether this Component is visible. Whether or not the Component
	 * is being rendered.
	 * 
	 * @return Whether or not the Component is being rendered.
	 */
	public boolean isVisible()
	{
		if (!visible)
		{
			return false;
		}
		else
		{
			Panel parent = this.parent;
			
			while (parent != null)
			{
				if (!parent.isVisible())
				{
					return false;
				}
				
				parent = parent.getParent();
			}
		}
		
		return visible;
	}
	
	/**
	 * Set whether this Component is visible. Whether or not the Component
	 * should be rendered.
	 * 
	 * @param visible Whether or not the Component should be rendered.
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/**
	 * Get the horizontal location of this Component.
	 * 
	 * @return The horizontal location of this Component.
	 */
	public int getX()
	{
		return x + alignX;
	}

	/**
	 * Get the vertical location of this Component.
	 * 
	 * @return The vertical location of this Component.
	 */
	public int getY()
	{
		return y + alignY;
	}
	
	/**
	 * Set the location of this Component to the specified location.
	 * 
	 * @param x The new horizontal location of the Component.
	 * @param y The new vertical location of the Component.
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the horizontal size of this Component.
	 * 
	 * @return The horizontal size of this Component.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Get the vertical size of this Component.
	 * 
	 * @return The vertical size of this Component.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Set the size of this Component to the specified size.
	 * 
	 * @param width The new horizontal size of the Component.
	 * @param height The new vertical size of the Component.
	 */
	public void setSize(int width, int height)
	{
		this.width  = width;
		this.height = height;
	}
	
	/**
	 * Get the horizontal scale of this Component during its last update.
	 * 
	 * @return The horizontal scale of this Component.
	 */
	public float getScaleX()
	{
		return scaleX;
	}
	
	/**
	 * Get the vertical scale of this Component during its last update.
	 * 
	 * @return The vertical scale of this Component.
	 */
	public float getScaleY()
	{
		return scaleY;
	}
	
	/**
	 * Get the horizontal amount that this Component has been translated
	 * as of the last update.
	 * 
	 * @return The horizontal amount that this Component has been
	 * 		translated.
	 */
	public float getTranslatedX()
	{
		return translatedX;
	}

	/**
	 * Get the vertical amount that this Component has been translated
	 * as of the last update.
	 * 
	 * @return The vertical amount that this Component has been
	 * 		translated.
	 */
	public float getTranslatedY()
	{
		return translatedY;
	}
	
	/**
	 * Get the parent Panel of this Component.
	 * 
	 * @return The parent Panel of this Component.
	 */
	public Panel getParent()
	{
		return parent;
	}
	
	/**
	 * Set the parent Panel of this Component.
	 * 
	 * @param parent The parent Panel of this Component.
	 */
	public void setParent(Panel parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Returns whether this Component is disposed or not.
	 * 
	 * @return Whether this Component is disposed or not.
	 */
	public boolean isDisposed()
	{
		return disposed;
	}
	
	/**
	 * Dispose this Component from the Listeners.
	 * 
	 * @return Whether it was successfully disposed.
	 */
	public boolean dispose()
	{
		Frame.remove(this);
		
		disposed = true;
		
		return disposed;
	}
	
	/**
	 * Set the alignment to the specified values.
	 * 
	 * @param horizontal The horizontal alignment.
	 * @param vertical The vertical alignment.
	 */
	public void setAlignment(int horizontal, int vertical)
	{
		this.horizontalAlignment = horizontal;
		this.verticalAlignment   = vertical;
		
		align();
	}
	
	/**
	 * Set the alignment variables to their correct values.
	 */
	private void align()
	{
		int width  = 0;
		int height = 0;
		
		if (parent == null)
		{
			width  = Frame.getWidth();
			height = Frame.getHeight();
		}
		else
		{
			width  = parent.getWidth();
			height = parent.getHeight();
		}
		
		alignX = 0;
		alignY = 0;
		
		if (horizontalAlignment == CENTER)
		{
			alignX = (int)((width / 2) - (getWidth() / 2) * scaleX);
		}
		else if (horizontalAlignment == RIGHT)
		{
			alignX = (int)((width) - (getWidth()) * scaleX);
		}
		
		if (verticalAlignment == CENTER)
		{
			alignY = (int)((height / 2) - (getHeight() / 2) * scaleY);
		}
		else if (verticalAlignment == TOP)
		{
			alignY = (int)((height) - (getHeight()) * scaleY);
		}
		
		alignX = Math.round(alignX / scaleX);
		alignY = Math.round(alignY / scaleY);
	}
	
	/**
	 * Method to update the Component's information such as
	 * the alignment location.
	 */
	public void update()
	{
		float scale[] = GL.getAmountScaled();
		
		scaleX = scale[0];
		scaleY = scale[1];
		
		float trans[] = GL.getAmountTranslated();
		
		translatedX = trans[0];
		translatedY = trans[1];
		
		align();
	}
	
	/**
	 * Method that must be implemented. Renders the Component to
	 * the screen.
	 */
	public abstract void render();
}