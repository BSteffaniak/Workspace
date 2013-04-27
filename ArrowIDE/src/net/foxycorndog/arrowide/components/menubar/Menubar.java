package net.foxycorndog.arrowide.components.menubar;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

public class Menubar
{
	private int								currentSize;
	
	private Composite						composite, parent;

	private GC								gc;
	
	private Listener						hoverListener;
	private Listener						selectionListener;
	private FocusListener					focusListener;
	private DropdownMenuListener			menuListener;
	
	private HashMap<String, DropdownMenu>	menus;
	private ArrayList<MenubarListener>		listeners;

	public Menubar(final Composite parent)
	{
		this.parent = parent;
		
		composite = new Composite(parent, SWT.NONE);
		composite.setSize(parent.getSize().x, 25);
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
		
		gc = new GC(composite);
		
		menus     = new HashMap<String, DropdownMenu>();
		listeners = new ArrayList<MenubarListener>();
		
		selectionListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				Control control = (Control)e.widget;
				
				if (control instanceof Label)
				{
					Label label = (Label)control;
					
					Point point = composite.toDisplay(new Point(control.getLocation().x, control.getLocation().y + control.getSize().y));
					
					DropdownMenu menu = menus.get(label.getText());
					
					menu.setLocation(point.x, point.y);
					menu.open();
				}
			}
		};
		
		menuListener = new DropdownMenuListener()
		{
			public void itemSelected(String text)
			{
				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					listeners.get(i).subItemPressed(text);
				}
			}
			
			public void itemHovered(String text)
			{
				if (menus.containsKey(text))
				{
					DropdownMenu menu = menus.get(text);
					
					DropdownMenu array[] = menus.values().toArray(new DropdownMenu[0]);
					
					for (int i = 0; i < array.length; i++)
					{
						if (!array[i].isAncestor(menu))
						{
							array[i].close();
						}
					}
					
					DropdownMenu parent = menu.getParent();
					
					Point point = parent.getLocation(text);
					
					point.x = 0;
					
					point.x += parent.getLocation().x + parent.getSize().x;
					point.y += parent.getLocation().y;
					
					menu.setLocation(point.x, point.y);
					menu.open();
				}
			}
			
			public void itemUnhovered(String text)
			{
				if (menus.containsKey(text))
				{
					DropdownMenu menu = menus.get(text);
					
					menu.close();
				}
			}
		};
		
		hoverListener = new Listener()
		{
			public void handleEvent(Event event)
			{
//				ToolItem item = composite.getItem(new Point(event.x, event.y));
				
//				System.out.println(item);
			}
		};
		
		Display.getDefault().addFilter(SWT.MouseDown, new Listener()
		{
			public void handleEvent(Event event)
			{
				DropdownMenu array[] = menus.values().toArray(new DropdownMenu[0]);
				
				for (int i = 0; i < array.length; i++)
				{
					array[i].close();
				}
			}
		});
		
//		toolBar.addListener(SWT.MouseMove, hoverListener);
	}
	
	public void setBackground(Color color)
	{
		composite.setBackground(color);
	}
	
	public void addMenuHeader(String name)
	{
		int textWidth  = gc.stringExtent(name).x;
		int textHeight = gc.stringExtent(name).y;
		
		Label item = new Label(composite, SWT.NONE);
		item.setSize(textWidth, textHeight);
		item.setLocation(currentSize, 0);
		item.setText(name);
		
		currentSize += textWidth + 10;
		
		DropdownMenu menu = new DropdownMenu(null);
		menu.addDropdownMenuListener(menuListener);
		
		menus.put(name, menu);
		item.addListener(SWT.MouseDown, selectionListener);
	}
	
	public void addMenuSubItem(String name, String parentId)
	{
		DropdownMenu menu = menus.get(parentId);
		
		String id  = parentId + ">" + name;
		
		menu.addMenuItem(name, id);
		
		DropdownMenu newMenu = new DropdownMenu(menu);
		newMenu.addDropdownMenuListener(menuListener);
		
		menus.put(id, newMenu);
	}
	
	public void addSeparator(String headerId)
	{
		menus.get(headerId).addSeparator();
	}
	
	public int getWidth()
	{
		return composite.getSize().x;
	}
	
	public int getHeight()
	{
		return composite.getSize().y;
	}
	
	public void setSize(int width, int height)
	{
		composite.setSize(width, height);
	}
	
	public int getX()
	{
		return composite.getLocation().x;
	}
	
	public int getY()
	{
		return composite.getLocation().y;
	}
	
	public void setLocation(int x, int y)
	{
		composite.setLocation(x, y);
	}
	
	public void addListener(MenubarListener listener)
	{
		listeners.add(listener);
	}
}