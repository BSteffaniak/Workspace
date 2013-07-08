package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import org.lwjgl.Sys;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.opengl.GL;

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
	private					boolean					disposed;
	private					boolean					visible;
	private					boolean					focused;
	private					boolean					enabled;
	private					boolean					alignmentSet;
	
	private					int						horizontalAlignment, verticalAlignment;
	private					int						verticalOrigin;
	
	private					float					x, y;
	private					float					alignX, alignY;
	private					float					displayX, displayY;
	private					float					width, height;
	private					float					scaleX, scaleY;
	private					float					scale;
	
	private					Panel					parent;
	
	private	static			ArrayList<Component>	components;
	
	public	static	final	int						LEFT = 0, CENTER = 1, RIGHT = 2,
													BOTTOM = 0, TOP = 2;
	
	/**
	 * Initialize the Component class stuff.
	 */
	static
	{
		components = new ArrayList<Component>();
	}
	
	/**
	 * Construct a Component with the specified parent Panel.
	 * 
	 * @param parent The parent Panel of this Component.
	 */
	public Component(Panel parent)
	{
		if (parent == null)
		{
			parent = Frame.getPanel();
		}
		
		components.add(this);
		
		this.enabled  = true;
		
		this.parent   = parent;
		
		this.visible  = true;
		
		this.disposed = false;
		
		if (parent != null)
		{
			parent.addChild(this);
		}
		
		scaleX = 1;
		scaleY = 1;
		
		setScale(1);
		setVerticalOrigin(BOTTOM);
	}
	
	/**
	 * Get the location in which the vertical origin lies. If the origin
	 * is at the TOP, then (0, 0) is located at the top left.
	 * 
	 * @return The vertical location in which the origin lies.
	 */
	public int getVerticalOrigin()
	{
		return verticalOrigin;
	}
	
	/**
	 * Set the vertical location of the origin.<br>
	 * Options include:
	 * 	<ul>
	 * 		<li>TOP - The origin is located at the top.</li>
	 * 		<li>BOTTOM - The origin is located at the bottom.</li>
	 * 	</ul>
	 * 
	 * @param verticalOrigin The vertical location of the origin.
	 */
	public void setVerticalOrigin(int verticalOrigin)
	{
		this.verticalOrigin = verticalOrigin;
		
		if (!alignmentSet)
		{
			if (verticalOrigin == BOTTOM)
			{
				
			}
			else if (verticalOrigin == TOP)
			{
				verticalAlignment = TOP;
			}
		}
	}
	
	/**
	 * Get the scale that the Component is currently rendered in. The
	 * scale will only affect the (x, y) axiis. Not the z axis.
	 * 
	 * @return The scale that the Component is currently rendered in.
	 */
	public float getScale()
	{
		return scale;
	}
	
	/**
	 * Set the scale that the Component will be rendered in.
	 * 
	 * @param scale The new scale to render the Component in.
	 */
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	/**
	 * Get whether the Component is enabled or not.
	 * 
	 * @return Whether the Component is enabled or not.
	 */
	public boolean isEnabled()
	{
		return enabled;
	}
	
	/**
	 * Set whether to enable the Component or disable the Component.
	 * 
	 * @param enabled Whether to enable or disable the Component.
	 */
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	/**
	 * Get whether the Component is focused on or not.
	 * 
	 * @return Whether the Component is focused on or not.
	 */
	public boolean isFocused()
	{
		return focused;
	}
	
	/**
	 * Set whether to focus on the Component or not.
	 * 
	 * @param focused Whether to focus on the Component or not.
	 */
	public void setFocused(boolean focused)
	{
		if (focused)
		{
			for (int i = components.size() - 1; i >= 0; i--)
			{
				Component c = components.get(i);
				
				c.setFocused(false);
			}
		}
		
		this.focused = focused;
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
	public float getX()
	{
		return x + alignX;
	}

	/**
	 * Get the vertical location of this Component.
	 * 
	 * @return The vertical location of this Component.
	 */
	public float getY()
	{
		return y + alignY;
	}
	
	/**
	 * Set the location of this Component to the specified location.
	 * 
	 * @param x The new horizontal location of the Component.
	 * @param y The new vertical location of the Component.
	 */
	public void setLocation(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the horizontal size of this Component with consideration
	 * of the scale.
	 * 
	 * @return The scaled horizontal size of this Component.
	 */
	public float getScaledWidth()
	{
		return width * scaleX;
	}

	/**
	 * Get the vertical size of this Component with consideration
	 * of the scale.
	 * 
	 * @return The scaled vertical size of this Component.
	 */
	public float getScaledHeight()
	{
		return height * scaleY;
	}

	/**
	 * Get the horizontal size of this Component.
	 * 
	 * @return The horizontal size of this Component.
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * Get the vertical size of this Component.
	 * 
	 * @return The vertical size of this Component.
	 */
	public float getHeight()
	{
		return height;
	}
	
	/**
	 * Set the size of this Component to the specified size.
	 * 
	 * @param width The new horizontal size of the Component.
	 * @param height The new vertical size of the Component.
	 */
	public void setSize(float width, float height)
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
	 * Get the horizontal location that this Component was last
	 * displayed on the screen.
	 * 
	 * @return The horizontal location that this Component was last
	 * 		displayed on the screen.
	 */
	public float getDisplayX()
	{
		return displayX;//(getX()) * getFullScale() + getTranslatedX();
	}

	/**
	 * Get the vertical location that this Component was last
	 * displayed on the screen.
	 * 
	 * @return The vertical location that this Component was last
	 * 		displayed on the screen.
	 */
	public float getDisplayY()
	{
		return displayY;//(getY()) * getFullScale() + getTranslatedY();
	}
	
	/**
	 * Set the location that the Component was rendered to the Display
	 * at.
	 * 
	 * @param x The horizontal location in pixels that the Component was
	 * 		rendered at.
	 * @param y The vertical location in pixels that the Component was
	 * 		rendered at.
	 */
	public void setDisplayLocation(float x, float y)
	{
		this.displayX = x;
		this.displayY = y;
	}
	
	/**
	 * Get the scale of the Component taking into account the scale of the
	 * Parent Components.
	 * 
	 * @return The full scale of the Component.
	 */
	public float getFullScale()
	{
		float scale = this.scale;
		
		Component parent = this.parent;
		
		while (parent != null)
		{
			scale *= parent.scale;
			
			parent = parent.getParent();
		}
		
		return scale;
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
		boolean disposed = Frame.remove(this);
		
		components.remove(this);
		
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
		alignmentSet = true;
		
		this.horizontalAlignment = horizontal;
		this.verticalAlignment   = vertical;
		
		align();
	}
	
	/**
	 * Set the alignment variables to their correct values.
	 */
	private void align()
	{
		float width    = 0;
		float height   = 0;
		
		float scaleWid = getWidth()  * scale;//  * scaleX / scale;
		float scaleHei = getHeight() * scale;// * scaleY / scale;
		
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
			alignX = (width / 2) - (scaleWid / 2);
			
//			alignX -= ((scaleWid * scale) - scaleWid) / 2;
		}
		else if (horizontalAlignment == RIGHT)
		{
			alignX = width - scaleWid;
			
//			alignX -= ((scaleWid * scale) - scaleWid);
		}
		
		// If we want to align it at the top.
		if ((verticalOrigin == BOTTOM && verticalAlignment == TOP) || (verticalOrigin == TOP && verticalAlignment == TOP))
		{
			alignY = height - scaleHei;
		}
		else if (verticalAlignment == CENTER)
		{
			alignY = (height / 2) - (scaleHei / 2);
		}
		
		
//		alignX /= scaleX;
//		alignY /= scaleY;
		
//		alignX /= scale;
//		alignY /= scale;
		
//		alignX = 0;
//		alignY = 0;
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
		
		float loc[] = GL.getCurrentLocation();
		
		setDisplayLocation(loc[0], loc[1]);
		
		align();
	}
	
	/**
	 * Method that must be implemented. Renders the Component to
	 * the screen.
	 */
	public abstract void render();
}
