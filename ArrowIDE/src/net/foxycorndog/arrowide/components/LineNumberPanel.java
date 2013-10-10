package net.foxycorndog.arrowide.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.sun.xml.internal.messaging.saaj.util.CharWriter;

public class LineNumberPanel extends Composite
{
	private GC			gc;
	
	private CodeField	field;
	
	public LineNumberPanel(Composite parent, int param, final CodeField field)
	{
		super(parent, param);
		
		this.field = field;
		
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
//				thisPanel.setLocation(field.getX(), field.getY());
			}
		});
		
		field.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				thisPanel.setSize((String.valueOf(field.getLineCount()).length() + 1) * getCharWidth(), thisPanel.getSize().y);
				
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
		
		addPaintListener(new PaintListener()
		{
			public void paintControl(PaintEvent e)
			{
				int height = getCharHeight();
				
				e.gc.setFont(field.getFont());
				
				for (int i = 0; i < field.getLineCount(); i++)
				{
					e.gc.drawString((i + 1) + ".", 0, height * i - field.getTopPixel());
				}
				
				e.gc.dispose();
			}
		});
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
		GC g = new GC(field);
	    FontMetrics fm = g.getFontMetrics();
	    int charHeight = fm.getHeight() + 1;
	    g.dispose();
	    
	    return charHeight;
	}
}