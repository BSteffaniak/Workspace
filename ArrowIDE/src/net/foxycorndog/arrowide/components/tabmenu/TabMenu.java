package net.foxycorndog.arrowide.components.tabmenu;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabFolderListener;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class TabMenu
{
	private int							maxCharacters, maxWidth;

	private Composite					composite;

	private CTabFolder					tabFolder, widthFolder;

	private TabMenu						thisObject;

	private HashMap<Integer, CTabItem>	tabs;
	private HashMap<CTabItem, Integer>	tabIds;
	private HashMap<Integer, String>	tabsText;

	private ArrayList<TabMenuListener>	listeners;

	private static int					staticId;
	
	public TabMenu(Composite composite)
	{
		this.composite = composite;
		
		listeners = new ArrayList<TabMenuListener>();
		
		tabFolder = new CTabMenu(composite, SWT.NONE);
		tabFolder.setBackground(new Color(Display.getCurrent(), 199, 238, 255));
		tabFolder.setForeground(new Color(Display.getCurrent(), 0, 0, 0));
		tabFolder.setTabHeight(20);
		
		widthFolder = new CTabMenu(composite, SWT.NONE);
		widthFolder.setTabHeight(20);
		widthFolder.setSize(99999, 999);
		widthFolder.setVisible(false);
//		widthFolder.setSimple(false);
		
		tabFolder.setSize(0, tabFolder.getTabHeight() + 3);
//		tabFolder.setSimple(false);
		
		tabs          = new HashMap<Integer, CTabItem>();
		tabIds        = new HashMap<CTabItem, Integer>();
		tabsText      = new HashMap<Integer, String>();
		
		maxCharacters = 20;
		
		thisObject    = this;
		
		tabFolder.addSelectionListener(new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				CTabItem item = (CTabItem)e.item;
				
				tabSelected(item);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		final TabMenu thisMenu = this;
		
		tabFolder.addCTabFolder2Listener(new CTabFolder2Listener()
		{
			public void close(CTabFolderEvent e)
			{
				CTabItem item = (CTabItem)e.item;
				
				int      id   = tabIds.get(item);
				
				boolean close = true;

				for (int i = listeners.size() - 1; i >= 0; i--)
				{
					if (!listeners.get(i).tabClosing(new TabMenuEvent(thisMenu, id)))
					{
						close = false;
					}
				}
				
				if (close)
				{
					tabs.remove(id);
					tabIds.remove(item);
					tabsText.remove(id);
					
					subtractWidth(item.getBounds().width);
				}
				else
				{
					e.doit = false;
				}
			}

			public void minimize(CTabFolderEvent e)
			{
				
			}

			public void maximize(CTabFolderEvent e)
			{
				
			}

			public void restore(CTabFolderEvent e)
			{
				
			}

			public void showList(CTabFolderEvent e)
			{
				
			}
		});
		
		tabFolder.redraw();
	}
	
	private void tabSelected(CTabItem item)
	{
		int id = tabIds.get(item);
		
		for (int i = listeners.size() - 1; i >= 0; i--)
		{
			listeners.get(i).tabSelected(new TabMenuEvent(this, id));
		}
	}
	
	public int getSelected()
	{
		int      index = tabFolder.getSelectionIndex();
		
		CTabItem item  = tabFolder.getItem(index);
		
		if (tabIds.containsKey(item))
		{
			return tabIds.get(item);
		}
		
		return -1;
	}
	
	public void setMaxWidth(int maxWidth)
	{
		this.maxWidth = maxWidth;
		
//		setWidth(maxWidth);
	}
	
	public String getTabText(int id)
	{
		return tabsText.get(id);
	}
	
	public String getTabShortenedText(int id)
	{
		return tabs.get(id).getText();
	}
	
	public void setTabText(int id, String text)
	{
		tabsText.put(id, text);
		
		if (text.length() > maxCharacters)
		{
			text = text.substring(0, maxCharacters - 3) + "...";
		}
		
		CTabItem wid = new CTabItem(widthFolder, SWT.CLOSE);
		wid.setText(text);
		
		CTabItem item = tabs.get(id);
		
		addWidth(wid.getBounds().width - item.getBounds().width);
		
		wid.dispose();
		
		item.setText(text);
		
//		tabFolder.redraw();
	}
	
	public int addTab(String text)
	{
		int id = ++staticId;
		
		tabsText.put(id, text);
		
		if (text.length() > maxCharacters)
		{
			text = text.substring(0, maxCharacters - 3) + "...";
		}
		
		CTabItem wid = new CTabItem(widthFolder, SWT.CLOSE);
		wid.setText(text);
		
		CTabItem item = new CTabItem(tabFolder, SWT.CLOSE);
		item.setText(text);
		
		addWidth(wid.getBounds().width);
		
		wid.dispose();
		
		tabs.put(id, item);
		tabIds.put(item, id);
		
		tabFolder.setSelection(item);
		
//		tabFolder.redraw();
		
//		setWidth(maxWidth);
		
		return id;
	}
	
	public void setSelection(int id)
	{
		CTabItem tab = tabs.get(id);
		
		tabFolder.setSelection(tab);
		
		tabSelected(tab);
	}
	
	public Color getBackground()
	{
		return tabFolder.getBackground();
	}
	
	public void setBackground(Color color)
	{
		tabFolder.setBackground(color);
	}
	
	public int getHeight()
	{
		return tabFolder.getSize().y;
	}
	
	private void setWidth(int width)
	{
		tabFolder.setSize(width, tabFolder.getSize().y);
	}
	
	private void addWidth(int width)
	{
		int wid = tabFolder.getSize().x + width;
		
		wid = wid > maxWidth ? maxWidth : wid;
		
		tabFolder.setSize(wid, tabFolder.getSize().y);
	}
	
	private void subtractWidth(int width)
	{
		int wid = tabFolder.getSize().x - width;
		
		tabFolder.setSize(wid, tabFolder.getSize().y);
	}
	
	public void setLocation(int x, int y)
	{
		tabFolder.setLocation(x, y);
	}
	
	public int getX()
	{
		return tabFolder.getBounds().x;
	}
	
	public int getY()
	{
		return tabFolder.getBounds().y;
	}
	
	public void setMaxCharacters(int maxCharacters)
	{
		this.maxCharacters = maxCharacters;
	}
	
	public void addListener(TabMenuListener listener)
	{
		listeners.add(listener);
	}
	
	private class CTabMenu extends CTabFolder
	{
		public CTabMenu(Composite parent, int style)
		{
			super(parent, style);
			
//			super.
		}
		
		
	}
}