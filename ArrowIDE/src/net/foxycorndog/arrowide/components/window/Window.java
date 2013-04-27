package net.foxycorndog.arrowide.components.window;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;

import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

public class Window
{
	private boolean						dragging, maximized, fullscreen;
	private boolean						custom;
	
	private int							minimumWidth, minimumHeight;
	private int							borderSize;
	private int							startX, startY, width, height;
	private int							dragType;
	
	private Point						sizeBefore, locationBefore;
	
	private Cursor						defaultCursor, leftResizeCursor, topLeftResizeCursor, topRightResizeCursor, topResizeCursor;
	
	private Tracker						tracker;
	
	private Listener					moveListener;
	
	private ControlListener				trackListener;
	
	private Composite					content;
	
	private Shell						shell;
	
	private Display						display;
	
	private ArrayList<WindowListener>	listeners;
	
	private static final int			BOTTOM = 1, TOP = 2, LEFT = 3, RIGHT = 4, BOTTOM_LEFT = 5, BOTTOM_RIGHT = 6, TOP_LEFT = 7, TOP_RIGHT = 8;
	
	public Window(Display display, boolean custom)
	{
		this.display = display;
		
		if (custom)
		{
			shell = new Shell(display, SWT.NO_TRIM);
		}
		else
		{
			shell = new Shell(display);
		}
//		shell.setCapture(true);
//		shell.setLayoutDeferred(true);
//		shell.setTouchEnabled(false);
		
		sizeBefore     = new Point(0, 0);
		locationBefore = new Point(0, 0);
		
		listeners = new ArrayList<WindowListener>();
		
		content = new Composite(shell, SWT.NONE);
		
		setCustom(custom);
		
		shell.addControlListener(new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				setContentSize();
			}
			
			public void controlMoved(ControlEvent e)
			{
				
			}
		});
	}
	
	private int getDragType(int x, int y)
	{
		if (x >= borderSize)
		{
			if (x < shell.getSize().x - borderSize)
			{
				if (y >= borderSize)
				{
					return BOTTOM;
				}
				else
				{
					return TOP;
				}
			}
			else
			{
				if (y >= borderSize)
				{
					if (y < shell.getSize().y - borderSize)
					{
						return RIGHT;
					}
					else
					{
						return BOTTOM_RIGHT;
					}
				}
				else
				{
					return TOP_RIGHT;
				}
			}
		}
		else
		{
			if (y >= borderSize)
			{
				if (y < shell.getSize().y - borderSize)
				{
					return LEFT;
				}
				else
				{
					return BOTTOM_LEFT;
				}
			}
			else
			{
				return TOP_LEFT;
			}
		}
	}
	
	private void setContentSize()
	{
		int bSize = borderSize * 2;
		
		if (isMaximized() || isFullscreen())
		{
			bSize = 0;
		}
		
		content.setSize(shell.getClientArea().width - bSize, shell.getClientArea().height - bSize);
		content.setLocation(bSize / 2, bSize / 2);
		
		if (width > minimumWidth)
		{
			width  = content.getSize().x;
		}

		if (height > minimumHeight)
		{
			height = content.getSize().y;
		}
	}
	
	public void setCustom(boolean custom)
	{
		if (this.custom != custom)
		{
			if (custom)
			{
				defaultCursor = shell.getCursor();
				
				Image image = new Image(display, "res/images/leftresizecursor.png");
				leftResizeCursor = new Cursor(display, image.getImageData(), image.getBounds().width / 2, image.getBounds().height / 2);
				
				image = new Image(display, "res/images/topresizecursor.png");
				topResizeCursor = new Cursor(display, image.getImageData(), image.getBounds().width / 2, image.getBounds().height / 2);
				
				image = new Image(display, "res/images/topleftresizecursor.png");
				topLeftResizeCursor = new Cursor(display, image.getImageData(), image.getBounds().width / 2, image.getBounds().height / 2);
				
				image = new Image(display, "res/images/toprightresizecursor.png");
				topRightResizeCursor = new Cursor(display, image.getImageData(), image.getBounds().width / 2, image.getBounds().height / 2);
				
				borderSize = 4;
				
				minimumWidth  = borderSize * 2;
				minimumHeight = borderSize * 2;
				
				width  = minimumWidth  + 1;
				height = minimumHeight + 1;
				
				tracker = new Tracker(shell, SWT.NONE);
				
				trackListener = new ControlListener()
				{
					public void controlResized(ControlEvent e)
					{
						
					}
					
					public void controlMoved(ControlEvent e)
					{
						Point loc = new Point(tracker.getRectangles()[0].x + 99999, tracker.getRectangles()[0].y + 99999);
						
						int dx = loc.x - startX;
						int dy = loc.y - startY;
						int x2 = shell.getLocation().x;
						int y2 = shell.getLocation().y;
						
						if (dragType == LEFT || dragType == BOTTOM_LEFT || dragType == TOP_LEFT)
						{
							dx *= -1;
						}
						
						if (dragType == TOP || dragType == TOP_LEFT || dragType == TOP_RIGHT)
						{
							dy *= -1;
						}
						
						width  += dx;
						height += dy;
						
						if ((width > 0 || height > 0))
						{
							int newWidth  = shell.getSize().x;
							int newHeight = shell.getSize().y;
							
							if (width > 0 || dx < 0)
							{
								if (dragType != TOP && dragType != BOTTOM)
								{
									newWidth += dx;
									
									if (newWidth < minimumWidth)
									{
										newWidth = minimumWidth;
									}
								}
							}
							if (height > 0 || dy < 0)
							{
								if (dragType != LEFT && dragType != RIGHT)
								{
									newHeight += dy;
									
									if (newHeight < minimumHeight)
									{
										newHeight = minimumHeight;
									}
								}
							}
							
							if (newWidth != shell.getSize().x || newHeight != shell.getSize().y)
							{
								
								if (dragType == TOP_LEFT || dragType == LEFT || dragType == BOTTOM_LEFT)
								{
									x2 -= dx;
								}
								
								if (dragType == TOP_LEFT || dragType == TOP || dragType == TOP_RIGHT)
								{
									y2 -= dy;
								}
						
								shell.setBounds(x2, y2, newWidth, newHeight);
							}
						}
						
						startX = loc.x;
						startY = loc.y;
					}
				};
				
				tracker.addControlListener(trackListener);
				
				moveListener = new Listener()
				{
					public void handleEvent(Event event)
					{
						if (event.type == SWT.MouseDown)
						{
							dragType = getDragType(event.x, event.y);
							dragging = true;
							
							startX = 0;
							startY = 0;
							
							tracker.setRectangles(new Rectangle[] { new Rectangle(-99999, -99999, 0, 0) });
							tracker.open();
							
							tracker.close();
							dragging = false;
							dragType = 0;
						}
						else if (event.type == SWT.MouseMove)
						{
							int type = getDragType(event.x, event.y);
							
							if (type == TOP_LEFT || type == BOTTOM_RIGHT)
							{
								shell.setCursor(topLeftResizeCursor);
							}
							else if (type == TOP || type == BOTTOM)
							{
								shell.setCursor(topResizeCursor);
							}
							else if (type == LEFT || type == RIGHT)
							{
								shell.setCursor(leftResizeCursor);
							}
							else if (type == TOP_RIGHT || type == BOTTOM_LEFT)
							{
								shell.setCursor(topRightResizeCursor);
							}
						}
						else if (event.type == SWT.MouseExit)
						{
							shell.setCursor(defaultCursor);
						}
					}
				};
				
				shell.addListener(SWT.MouseDown, moveListener);
				shell.addListener(SWT.MouseMove, moveListener);
				shell.addListener(SWT.MouseExit, moveListener);
			}
			else
			{
				tracker.removeControlListener(trackListener);
				
				shell.removeListener(SWT.MouseDown, moveListener);
				shell.removeListener(SWT.MouseMove, moveListener);
				shell.removeListener(SWT.MouseExit, moveListener);
				
				tracker      = null;
				moveListener = null;
			}
		}
		
		this.custom = custom;
	}
	
	public Color getBackground()
	{
		return content.getBackground();
	}
	
	public void setBackground(Color color)
	{
		content.setBackground(color);
	}
	
	public boolean isDisposed()
	{
		return shell.isDisposed();
	}
	
	public void dispose()
	{
		shell.dispose();
	}
	
	public void open()
	{
		shell.open();
	}
	
	public void setFocus()
	{
		shell.setFocus();
	}
	
	public void forceFocus()
	{
		shell.forceFocus();
	}
	
	public void setActive()
	{
		shell.setActive();
	}
	
	public void forceActive()
	{
		shell.forceActive();
	}
	
	public Composite getContentPanel()
	{
		return content;
	}
	
	public Shell getShell()
	{
		return shell;
	}
	
	public Rectangle getBounds()
	{
		return shell.getBounds();
	}
	
	public int getX()
	{
		return shell.getLocation().x;
	}
	
	public int getY()
	{
		return shell.getLocation().y;
	}
	
	public Point getLocation()
	{
		return shell.getLocation();
	}
	
	public void setLocation(int x, int y)
	{
		shell.setLocation(x, y);
		locationBefore = shell.getLocation();
	}
	
	public Point getSize()
	{
		return shell.getSize();
	}
	
	public void setSize(int width, int height)
	{
		shell.setSize(width, height);
		setContentSize();
		sizeBefore = shell.getSize();
	}
	
	public int getWidth()
	{
		return shell.getSize().x;
	}
	
	public int getHeight()
	{
		return shell.getSize().y;
	}
	
	public Image getIcon()
	{
		return shell.getImage();
	}
	
	public void setIcon(Image icon)
	{
		boolean doit = true;
		
		for (int i = listeners.size() - 1; i >= 0; i--)
		{
			if (!listeners.get(i).iconChanged(icon))	
			{
				doit = false;
			}
		}
		
		if (doit)
		{
			shell.setImage(icon);
		}
	}
	
	public String getTitle()
	{
		return shell.getText();
	}
	
	public void setTitle(String title)
	{
		boolean doit = true;
		
		for (int i = listeners.size() - 1; i >= 0; i--)
		{
			if (!listeners.get(i).titleChanged(title))	
			{
				doit = false;
			}
		}
		
		if (doit)
		{
			shell.setText(title);
		}
	}
	
	public void addControlListener(ControlListener listener)
	{
		shell.addControlListener(listener);
	}

	public void addPaintListener(PaintListener listener)
	{
		content.addPaintListener(listener);
	}
	
	public void addListener(int eventType, Listener listener)
	{
		shell.addListener(eventType, listener);
	}
	
	public boolean isMaximized()
	{
		return maximized;
	}
	
	public void setMaximized(boolean maximized)
	{
		this.maximized = maximized;
		
		if (custom)
		{
			if (maximized)
			{
				sizeBefore = shell.getSize();
				locationBefore = shell.getLocation();
				
				Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
				java.awt.Rectangle useableSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
				
				shell.setSize(screenSize.width, useableSize.height);
				shell.setLocation(useableSize.x, useableSize.y);
			}
			else
			{
				shell.setSize(sizeBefore);
				shell.setLocation(locationBefore);
			}
		}
		else
		{
			shell.setMaximized(maximized);
		}
	}
	
	public boolean isMinimized()
	{
		return shell.getMinimized();
	}
	
	public boolean isFullscreen()
	{
		return fullscreen;
	}
	
	public void setFullscreen(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
		
//		if (fullscreen)
//		{
//			sizeBefore = shell.getSize();
//			locationBefore = shell.getLocation();
//			
//			Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
//			
//			shell.setSize(screenSize.width, screenSize.height);
//			shell.setLocation(0, 0);
//		}
//		else
//		{
//			shell.setSize(sizeBefore);
//			shell.setLocation(locationBefore);
//		}
		
		shell.setFullScreen(fullscreen);
	}
	
	public void setMinimized(boolean minimized)
	{
		shell.setMinimized(minimized);
	}
	
	public Rectangle getClientArea()
	{
		return new Rectangle(0, 0, content.getSize().x, content.getSize().y);
	}
	
	public void close()
	{
		shell.close();
	}
	
	public boolean isVisible()
	{
		return shell.isVisible();
	}
	
	public void setVisible(boolean visible)
	{
		shell.setVisible(visible);
	}
	
	public int getBorderSize()
	{
		return borderSize;
	}
	
	public void setBorderSize(int borderSize)
	{
		this.borderSize = borderSize;
		
		setContentSize();
	}
	
	public void setMinimumSize(int width, int height)
	{
		this.minimumWidth  = width;
		this.minimumHeight = height;
	}
	
	public int getMinimumWidth()
	{
		return minimumWidth;
	}
	
	public void setMinimumWidth(int width)
	{
		this.minimumWidth  = width;
	}
	
	public int getMinimumHeight()
	{
		return minimumHeight;
	}
	
	public void setMinimumHeight(int height)
	{
		this.minimumHeight = height;
	}
	
	public Color getBorderColor()
	{
		return shell.getBackground();
	}
	
	public void setBorderColor(Color color)
	{
		shell.setBackground(color);
	}
	
	public void redraw()
	{
		shell.redraw();
		content.redraw();
	}
	
	public void addWindowListener(WindowListener listener)
	{
		listeners.add(listener);
	}
}