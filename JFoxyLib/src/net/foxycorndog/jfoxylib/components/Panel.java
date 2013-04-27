package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;

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
	 * Renders the Component to the screen.
	 */
	public void render()
	{
		if (isVisible())
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
	}
}