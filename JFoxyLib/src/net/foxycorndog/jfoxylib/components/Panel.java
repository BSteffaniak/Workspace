package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class that is used to contain other Components.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 12:28:06 AM
 * @since	v0.1
 * @version Mar 10, 2013 at 12:28:06 AM
 * @version	v0.2
 */
public class Panel extends Component
{
	private	boolean					independentSize;
	
	private ArrayList<Component>	children;
	
	/**
	 * Construct a Panel that is used to hold Components with the
	 * specified parent Panel.
	 * 
	 * @param parent The parent Panel of this Panel.
	 */
	public Panel(Panel parent)
	{
		super(parent);
		
		children = new ArrayList<Component>();
	}
	
	/**
	 * Get whether or not the size of the Panel is independent to
	 * the scale that the OpenGL matrix has at the rendering time.
	 * 
	 * @return Whether or not the size of the Panel is independent to
	 * 		scale at render time.
	 */
	public boolean isIndependentSize()
	{
		return independentSize;
	}
	
	/**
	 * Set whether or not the size of the Panel should be independent to
	 * the scale that the OpenGL matrix has at the rendering time.
	 * 
	 * @param independentSize Whether or not to set the size of the Panel
	 * 		to be independent to scale at render time.
	 */
	public void setIndependentSize(boolean independentSize)
	{
		this.independentSize = independentSize;
	}
	
	/**
	 * Add the specified Component as a child to this Panel. This removes
	 * any previous links to parents from the child.
	 * 
	 * @param child The child Component to add to this Panel.
	 */
	public boolean addChild(Component child)
	{
		child.setParent(this);
		
		if (children.contains(child))
		{
			return false;
		}
		
		return children.add(child);
	}
	
	/**
	 * Dispose this Panel and all of its children from the Listeners.
	 * 
	 * @return Whether it was successfully disposed.
	 */
	public boolean dispose()
	{
		boolean disposed = super.dispose();
		
		for (int i = 0; i < children.size(); i++)
		{
			children.get(i).dispose();
		}
		
		return disposed;
	}
	
	/**
	 * Render all of the Components within the Panel to the Display.
	 */
	public void renderComponents()
	{
		for (int i = children.size() - 1; i >= 0; i--)
		{
			Component child = children.get(i);
			
			if (child.isVisible())
			{
				child.render();
			}
		}
	}
	
	/**
	 * Renders the Component to the screen.
	 */
	public void render()
	{
		if (isVisible())
		{
			GL.pushMatrix();
			{
				if (independentSize)
				{
					GL.translateIgnoreScale(getX(), getY(), 0);
					
					GL.beginFrameClipping(getX(), getY(), getWidth(), getHeight());
				}
				else
				{
					GL.translate(getX(), getY(), 0);
				
					GL.beginClipping(0, 0, getWidth(), getHeight());
				}
				
				renderComponents();
				
				GL.endClipping();
			}
			GL.popMatrix();
		}
	}
}