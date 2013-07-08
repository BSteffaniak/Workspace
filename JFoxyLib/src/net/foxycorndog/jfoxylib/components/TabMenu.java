package net.foxycorndog.jfoxylib.components;

import java.util.ArrayList;

import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.TabMenuEvent;
import net.foxycorndog.jfoxylib.events.TabMenuListener;
import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class used to create an organized bar used as a tab menu for any
 * specific purpose or another.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 30, 2013 at 10:33:27 PM
 * @since	v0.2
 * @version	Jun 30, 2013 at 10:33:27 PM
 * @version	v0.2
 */
public class TabMenu extends Panel
{
	private	boolean						autoResize;
	
	private	int							margin;
	
	private	ButtonListener				buttonListener;
	
	private	ArrayList<TabItem>			tabItems;
	
	private	ArrayList<TabMenuListener>	listeners;
	
	/**
	 * Create a TabMenu inside of the specified parent Panel.
	 */
	public TabMenu(Panel parent)
	{
		super(parent);
		
		tabItems  = new ArrayList<TabItem>();
		listeners = new ArrayList<TabMenuListener>();
		
		setAutoResize(true);
		setMargin(0);
		
		buttonListener = new ButtonListener()
		{
			public void buttonUp(ButtonEvent event)
			{
				
			}
			
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				TabItem      item = (TabItem)event.getSource();
				
				TabMenuEvent e    = new TabMenuEvent(item);
				
				for (int i = 0; i < listeners.size(); i++)
				{
					TabMenuListener listener = listeners.get(i);
					
					listener.tabReleased(e);
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				TabItem      item = (TabItem)event.getSource();
				
				TabMenuEvent e    = new TabMenuEvent(item);
				
				for (int i = 0; i < listeners.size(); i++)
				{
					TabMenuListener listener = listeners.get(i);
					
					listener.tabPressed(e);
				}
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonDown(ButtonEvent event)
			{
				
			}
		};
	}
	
	/**
	 * Set the horizontal margin between the tabs (in pixels).
	 * 
	 * @param margin The horizontal margin in pixels.
	 */
	public void setMargin(int margin)
	{
		this.margin = margin;
	}
	
	/**
	 * Add the specified TabMenuListener instance to the list of listeners
	 * to be notified of any updates.
	 * 
	 * @param listener The TabMenuListener instance to add.
	 */
	public void addListener(TabMenuListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Add the TabItem to the end of the TabMenu and then update the
	 * TabMenu.
	 */
	public void addTabItem(TabItem item)
	{
		addTabItem(item, tabItems.size());
	}
	
	/**
	 * Add the TabItem to the specified index of the TabMenu and then
	 * update the TabMenu.
	 */
	public void addTabItem(TabItem item, int index)
	{
		if (index > tabItems.size())
		{
			throw new IndexOutOfBoundsException("The index '" + index + "' must be <= the amount of TabItems '" + tabItems.size() + "' in the TabMenu");
		}
		
		tabItems.add(index, item);
		
		item.addButtonListener(buttonListener);
		
		update();
	}
	
	/**
	 * Get whether or not the TabMenu will dynamically change the size
	 * whenever TabItems are added or removed from the bar.
	 * 
	 * @return Whether or not the TabMenu auto-resizes.
	 */
	public boolean doesAutoResize()
	{
		return autoResize;
	}
	
	/**
	 * Set whether to auto-resize the TabMenu according to when TabItems
	 * are added, removed, or changed.
	 */
	public void setAutoResize(boolean autoResize)
	{
		this.autoResize = autoResize;
		
		update();
	}
	
	/**
	 * Update the TabMenu to the current TabItems.
	 */
	public void update()
	{
		super.update();
		
		if (autoResize)
		{
			float width  = 0;
			float height = 0;
			
			for (int i = 0; i < tabItems.size(); i++)
			{
				TabItem item = tabItems.get(i);
				
				item.setLocation(width, 0);
				
				width += item.getWidth() * item.getScale() + margin;
				
				if (item.getHeight() * item.getScale() > height)
				{
					height = item.getHeight() * item.getScale();
				}
			}
			
			if (tabItems.size() > 0)
			{
				width -= margin;
			}
			
			setSize(width, height);
		}
	}
	
	/**
	 * Render the TabMenu at its current location.
	 * 
	 * @see net.foxycorndog.jfoxylib.components.Panel#render()
	 */
	public void render()
	{
		super.render();
		
//		GL.pushMatrix();
//		{
//			GL.translate(getX(), getY(), 0);
//			
//			for (int i = 0; i < tabItems.size(); i++)
//			{
//				TabItem item = tabItems.get(i);
//				
//				item.render();
//			}
//		}
//		GL.popMatrix();
	}
}
