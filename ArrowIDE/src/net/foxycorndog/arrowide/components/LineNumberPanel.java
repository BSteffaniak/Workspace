package net.foxycorndog.arrowide.components;

import net.foxycorndog.arrowide.ArrowIDE;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.sun.xml.internal.messaging.saaj.util.CharWriter;

public class LineNumberPanel extends Composite
{
	private GC			gc;
	private GC			bufferGC;
	
	private Image		buffer;
	
	private CodeField	field;
	
	private Color		backgroundColor;
	
	public LineNumberPanel(Composite parent, int param, final CodeField field)
	{
		super(parent, param);
		
		this.field = field;
		
		this.backgroundColor = new Color(Display.getDefault(), 220, 220, 220);
		
		final LineNumberPanel thisPanel = this;
		
		field.addCodeFieldListener(new CodeFieldListener()
		{
			public void keyPressed(CodeFieldEvent event)
			{
				
			}
		});
		
		field.addControlListener(new ControlListener()
		{
			public void controlMoved(ControlEvent e)
			{
//				thisPanel.setLocation(field.getX() - thisPanel.getSize().x, field.getY());
			}

			public void controlResized(ControlEvent e)
			{
				thisPanel.setSize(thisPanel.getSize().x, field.getSize().y);
				field.getSize();
//				thisPanel.setLocation(field.getX(), field.getY());
			}
		});
		
		field.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				int length   = String.valueOf(field.getLineCount()).length() + 1;
				
				int newWidth = length * getCharWidth();
				
				if (newWidth != thisPanel.getSize().x)
				{
					thisPanel.setSize(newWidth, thisPanel.getSize().y);
					
					
				}
				
				createBuffer();
				drawBuffer();
				
				thisPanel.redraw();
			}
		});
		
		field.getVerticalBar().addSelectionListener(new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				redraw();
			}
			
			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		int charWidth = getCharWidth();
		
		setSize(charWidth * 2, field.getSize().y);
		setLocation(field.getX(), field.getY());
		
		field.setLocation(field.getLocation().x + getSize().x, field.getLocation().y);
		field.setSize(field.getWidth() - getSize().x, field.getHeight());
		
		createBuffer();
		drawBuffer();
		
		addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				e.gc.drawImage(buffer, 0, -field.getTopPixel());
				
				e.gc.dispose();
			}
		});
	}
	
	private void drawBuffer()
	{
		int height = getCharHeight();

        bufferGC.setBackground(backgroundColor);
		bufferGC.fillRectangle(0, 0, buffer.getImageData().width, buffer.getImageData().height);
		
		int charWidth = getCharWidth();
		
		int maxLength = String.valueOf(field.getLineCount()).length() + 1;
		
		for (int i = 0; i < field.getLineCount(); i++)
		{
			int length = String.valueOf((i + 1)).length() + 1;
			
			int dif    = maxLength - length;
			
			bufferGC.drawString((i + 1) + ".", dif * charWidth, height * i + 1);
		}
	}
	
	private void createBuffer()
	{	
		PaletteData palette = new PaletteData(0xFF , 0xFF00 , 0xFF0000);
		ImageData data = new ImageData(getSize().x, field.getLineCount() * getCharHeight(), 24, palette);
		buffer = new Image(Display.getDefault(), data);
		buffer.setBackground(backgroundColor);
		
		bufferGC = new GC(buffer);
		bufferGC.setFont(field.getFont());
		bufferGC.setBackground(backgroundColor);
	}
	
	public int getCharWidth()
	{
		GC g = new GC(field);
	    FontMetrics fm = g.getFontMetrics();
	    int charWidth = fm.getAverageCharWidth();
	    g.dispose();
	    
	    return charWidth;
	}
	
	public int getCharHeight()
	{
		int offset = 0;
		
		if (ArrowIDE.PROPERTIES.get("os.name").equals("windows"))
		{
			offset = 1;
		}
		
		GC g = new GC(field);
	    FontMetrics fm = g.getFontMetrics();
	    int charHeight = fm.getHeight() + offset;
	    g.dispose();
	    
	    return charHeight;
	}
}