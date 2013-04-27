package net.foxycorndog.arrowide.components;

import java.io.InputStream;

import net.foxycorndog.arrowide.color.ColorUtils;
import net.foxycorndog.arrowide.components.window.Window;
import net.foxycorndog.arrowide.components.window.WindowListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;

import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

public class TitleBar
{
	private boolean			dragging, dontSetX, wasRestored, wasMaximized;
	
	private int				startX, startY, mouseX, mouseY;
	private int				flags;
	
	private Color			hoverColor, normalColor;

	private GC				titleGC;
	
	private Composite		composite;

	private Label			closeButton, restoreButton, minimizeButton;
	private Label			titleLabel, iconLabel;

	private Listener		buttonListener;

	private static Image	closeImage, restoreImage, maximizeImage, minimizeImage;
	
	static
	{
		closeImage    = new Image(Display.getCurrent(), "res/images/closeimage.png");
		restoreImage  = new Image(Display.getCurrent(), "res/images/restoreimage.png");
		maximizeImage = new Image(Display.getCurrent(), "res/images/maximizeimage.png");
		minimizeImage = new Image(Display.getCurrent(), "res/images/minimizeimage.png");
	}
	
	public TitleBar(final Window parent, final int size, int flags)
	{
		this.flags = flags;
		
		composite = new Composite(parent.getContentPanel(), SWT.NONE);
		
		composite.setSize(parent.getSize().x, size);
		
		buttonListener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (event.type == SWT.MouseUp)
				{
					// If the click is still on the button.
					if (event.x >= 0 && event.x < size && event.y >= 0 && event.y < size)
					{
						if (event.widget == closeButton)
						{
							parent.close();
						}
						else if (event.widget == restoreButton)
						{
							dontSetX = true;
							parent.setMaximized(!parent.isMaximized());
						}
						else if (event.widget == minimizeButton)
						{
							parent.setMinimized(true);
						}
					}
				}
				else if (event.type == SWT.MouseEnter)
				{
					Label button = (Label)event.widget;
					
					button.setBackground(hoverColor);
				}
				else if (event.type == SWT.MouseExit)
				{
					Label button = (Label)event.widget;
					
					button.setBackground(normalColor);
				}
			}
		};
		
		addButtons();
		
		titleLabel = new Label(composite, SWT.NONE);
		FontData fd = titleLabel.getFont().getFontData()[0];
		fd.setHeight(14);
		titleLabel.setFont(new Font(Display.getDefault(), fd));
		
		titleGC    = new GC(titleLabel);
		setTitle(parent.getTitle());
		
		iconLabel = new Label(composite, SWT.NONE);
		setIcon(parent.getIcon());
		
		dontSetX = true;
		wasRestored = true;
		wasMaximized = parent.isMaximized();
		
		ControlListener resizeListener = new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				composite.setSize(parent.getClientArea().width, size);
				
				setTitleLocation();
				
				layoutButtons();
				
				if (parent.isMaximized())
				{
					if (restoreButton != null)
					{
						restoreButton.setImage(restoreImage);
					}
				}
				else
				{
					if (wasMaximized && !dontSetX)
					{
						parent.setLocation(mouseX - parent.getSize().x / 2, parent.getLocation().y);
					}
					
					if (restoreButton != null)
					{
						restoreButton.setImage(maximizeImage);
					}
				}
				
				dontSetX = false;
				
				wasRestored = false;
				
				wasMaximized = false;
				
				if (parent.isFullscreen())
				{
					if (minimizeButton != null)
					{
						minimizeButton.setEnabled(false);//.setVisible(false);
					}
					if (restoreButton != null)
					{
						restoreButton.setEnabled(false);//.setVisible(false);
					}
				}
				else
				{
					if (minimizeButton != null)
					{
						minimizeButton.setEnabled(true);//.setVisible(true);
					}
					if (restoreButton != null)
					{
						restoreButton.setEnabled(true);//.setVisible(true);
					}
				}
			}
			
			public void controlMoved(ControlEvent e)
			{
				
			}
		};
		
		parent.addControlListener(resizeListener);
		
		final Tracker tracker = new Tracker(parent.getShell(), SWT.NONE);
		
		tracker.addControlListener(new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				
			}
			
			public void controlMoved(ControlEvent e)
			{
				if (parent.isMaximized())
				{
					parent.setMaximized(false);
				}
				
				Point loc = new Point(tracker.getRectangles()[0].x + 99999, tracker.getRectangles()[0].y + 99999);
				
				int dx = loc.x - startX;
				int dy = loc.y - startY;
				
				parent.setLocation(parent.getLocation().x + dx, parent.getLocation().y + dy);
				
				startX = loc.x;
				startY = loc.y;
			}
		});
		
		Listener moveListener = new Listener()
		{
			public void handleEvent(Event event)
			{
				if (!parent.isFullscreen())
				{
					if (event.type == SWT.MouseDown)
					{
						dragging = true;
						
						Control control = (Control)event.widget;
						
						mouseX = event.x + parent.getBorderSize() + control.getLocation().x;
						mouseY = event.y + parent.getBorderSize() + control.getLocation().y;
						
						wasMaximized = parent.isMaximized();
						
						startX = 0;
						startY = 0;
						
						tracker.setRectangles(new Rectangle[] { new Rectangle(-99999, -99999, 0, 0) });
						tracker.open();
						
						tracker.close();
						dragging = false;
						
						if (parent.getLocation().y + mouseY <= 0)
						{
							wasRestored = true;
							parent.setMaximized(true);
						}
					}
					else if (event.type == SWT.MouseDoubleClick)
					{
						dontSetX = true;
						wasRestored = true;
						parent.setMaximized(!parent.isMaximized());
					}
				}
			}
		};
		
		titleLabel.addListener(SWT.MouseDown, moveListener);
		titleLabel.addListener(SWT.MouseDoubleClick, moveListener);
		composite.addListener(SWT.MouseDown, moveListener);
		composite.addListener(SWT.MouseDoubleClick, moveListener);
		
		parent.addWindowListener(new WindowListener()
		{
			public boolean titleChanged(String newTitle)
			{
				setTitle(newTitle);
				
				return true;
			}
			
			public boolean iconChanged(Image newIcon)
			{
				setIcon(newIcon);
				
				return true;
			}
		});
		
		composite.setBackgroundMode(SWT.INHERIT_DEFAULT);
	}
	
	private void setTitle(String title)
	{
		int textWidth  = titleGC.textExtent(title).x;
		int textHeight = titleGC.textExtent(title).y;
		
		titleLabel.setText(title);
		titleLabel.setSize(textWidth, textHeight);
		
		setTitleLocation();
	}
	
	private void setTitleLocation()
	{
		int textWidth  = titleLabel.getSize().x;
		int textHeight = titleLabel.getSize().y;
		
		if ((flags & SWT.CENTER) != 0)
		{
			titleLabel.setLocation(getWidth() / 2 - textWidth / 2, 0);
		}
		else if ((flags & SWT.RIGHT) != 0)
		{
			titleLabel.setLocation(getWidth() - textWidth, 0);
		}
		else
		{
			titleLabel.setLocation(0, 0);
		}
	}
	
	private void setIcon(Image icon)
	{
//		if (icon != null)
//		{
//			int width  = getHeight() - 2;
//			int height = getHeight() - 2;
//			
//			Image scaled = new Image(Display.getDefault(), icon.getImageData());
//			ImageData id = scaled.getImageData().scaledTo(width, height);
//			
//			scaled = new Image(Display.getDefault(), id);
//			
////			GC gc = new GC(scaled);
////			gc.setAntialias(SWT.ON);
////			gc.setInterpolation(SWT.HIGH);
////			gc.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 0, 0, width, height);
////			gc.dispose();
////			
////			scaled = new Image(Display.getDefault(), scaled.getImageData());
//			
//			iconLabel.setImage(scaled);
//			iconLabel.setSize(width, height);
//			iconLabel.setLocation(3, 1);
//			iconLabel.setBackground(composite.getBackground());
//		}
	}
	
	private void addButtons()
	{
		if ((flags & SWT.CLOSE) != 0)
		{
			closeButton = new Label(composite, SWT.NONE);
			closeButton.setSize(getHeight(), getHeight());
			closeButton.setImage(closeImage);
			
			closeButton.addListener(SWT.MouseUp, buttonListener);
			closeButton.addListener(SWT.MouseEnter, buttonListener);
			closeButton.addListener(SWT.MouseExit, buttonListener);
		}
		
		if ((flags & SWT.MIN) != 0)
		{
			minimizeButton = new Label(composite, SWT.PUSH | SWT.FLAT);
			minimizeButton.setSize(getHeight(), getHeight());
			minimizeButton.setImage(minimizeImage);
			
			minimizeButton.addListener(SWT.MouseUp, buttonListener);	
			minimizeButton.addListener(SWT.MouseEnter, buttonListener);
			minimizeButton.addListener(SWT.MouseExit, buttonListener);
		}
		
		if ((flags & SWT.MAX) != 0)
		{
			restoreButton = new Label(composite, SWT.PUSH | SWT.FLAT);
			restoreButton.setSize(getHeight(), getHeight());
			restoreButton.setImage(restoreImage);
			
			restoreButton.addListener(SWT.MouseUp, buttonListener);	
			restoreButton.addListener(SWT.MouseEnter, buttonListener);
			restoreButton.addListener(SWT.MouseExit, buttonListener);
		}
		
		setForeground(new Color(Display.getDefault(), 40, 40, 40));
		setBackground(new Color(Display.getDefault(), 140, 140, 140));
	}
	
	private void layoutButtons()
	{
		int offset = 0;
		
		if (PROPERTIES.get("os.name").equals("macosx"))
		{
			if (closeButton != null && closeButton.isVisible())
			{
				closeButton.setLocation(offset, 0);
				
				offset += getHeight() + 1;
			}
			if (minimizeButton != null && minimizeButton.isVisible())
			{
				minimizeButton.setLocation(offset, 0);
				
				offset += getHeight() + 1;
			}
			if (restoreButton != null && restoreButton.isVisible())
			{
				restoreButton.setLocation(offset, 0);
				
				offset += getHeight() + 1;
			}
		}
		else
		{
			if (closeButton != null && closeButton.isVisible())
			{
				closeButton.setLocation(getWidth() - closeButton.getSize().x - offset, 0);
				
				offset += getHeight() + 1 + closeButton.getSize().x;
			}
			if (restoreButton != null && restoreButton.isVisible())
			{
				restoreButton.setLocation(getWidth() - offset, 0);
				
				offset += getHeight() + 1;
			}
			if (minimizeButton != null && minimizeButton.isVisible())
			{
				minimizeButton.setLocation(getWidth() - offset, 0);
				
				offset += getHeight() + 1;
			}
		}
	}
	
	public Color getBackground()
	{
		return composite.getBackground();
	}
	
	public void setBackground(Color color)
	{
		composite.setBackground(color);
	}
	
	public Color getForeground()
	{
		return normalColor;
	}
	
	public void setForeground(Color color)
	{
		normalColor = color;

		setButtonsColor(color);
		
		hoverColor = ColorUtils.darken(color, 20);
	}
	
	private void setButtonsColor(Color color)
	{
		if (closeButton != null)
		{
			closeButton.setBackground(color);
		}
		if (restoreButton != null)
		{
			restoreButton.setBackground(color);
		}
		if (minimizeButton != null)
		{
			minimizeButton.setBackground(color);
		}
	}
	
	public int getX()
	{
		return composite.getLocation().x;
	}
	
	public int getY()
	{
		return composite.getLocation().y;
	}
	
	public int getWidth()
	{
		return composite.getSize().x;
	}
	
	public int getHeight()
	{
		return composite.getSize().y;
	}
}