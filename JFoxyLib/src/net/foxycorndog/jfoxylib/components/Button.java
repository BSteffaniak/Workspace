package net.foxycorndog.jfoxylib.components;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

/**
 * Class used as a button on the screen.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 12:10:22 AM
 * @since	v0.2
 * @version Mar 10, 2013 at 12:10:22 AM
 * @version	v0.2
 */
public class Button extends Image
{
	private boolean						hovered;
	
	private String						text;
	
	private Font						font;
	
	private Texture						normalTexture, hoverTexture;
	
	private ArrayList<ButtonListener>	buttonListeners;
	
	/**
	 * Construct a Button in the specified parent Panel.
	 * 
	 * @param parent The Panel that is to be the parent of this Button.
	 */
	public Button(Panel parent)
	{
		super(parent);
		
		buttonListeners = new ArrayList<ButtonListener>();
		
		Frame.add(this);
		
		addButtonListener(new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				if (hoverTexture != null)
				{
					setImage(normalTexture);
				}
				
				hovered = false;
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				if (hoverTexture != null)
				{
					setImage(hoverTexture, false);
				}
				
				hovered = true;
			}
		});
	}
	
	/**
	 * Set the Image of this Button Component.
	 * 
	 * @param image The new Image of this Button Component.
	 */
	public void setImage(BufferedImage image)
	{
		setImage(image, true);
	}
	
	/**
	 * Set the Texture of this Button Component.
	 * 
	 * @param image The new Texture of this Button Component.
	 */
	public void setImage(Texture image)
	{
		setImage(image, true);
	}
	
	/**
	 * Set the Image of this Button Component.
	 * 
	 * @param image The new Image of this Button Component.
	 * @param set Whether or not to set the local variable of the normal
	 * 		Image.
	 */
	private void setImage(BufferedImage image, boolean set)
	{
		super.setImage(image);
		
		if (set)
		{
			normalTexture = new Texture(image);
		}
	}
	
	/**
	 * Set the Texture of this Button Component.
	 * 
	 * @param image The new Texture of this Button Component.
	 * @param set Whether or not to set the local variable of the normal
	 * 		Image.
	 */
	private void setImage(Texture image, boolean set)
	{
		super.setImage(image);
		
		if (set)
		{
			normalTexture = image;
		}
	}
	
	/**
	 * Set the Image of this Button Component that will display when
	 * the Button is hovered by the Mouse.
	 * 
	 * @param image The new Image of this Button Component that will
	 * 		display when the Button is hovered by the Mouse.
	 */
	public void setHoverImage(BufferedImage image)
	{
		hoverTexture = new Texture(image);
	}
	
	/**
	 * Add the specified ButtonListener to this Button.
	 * 
	 * @param listener The ButtonListener to add to this Button.
	 * @return Whether the ButtonListener was added successfully or not.
	 */
	public boolean addButtonListener(ButtonListener listener)
	{
		return buttonListeners.add(listener);
	}
	
	/**
	 * Remove the specified ButtonListener from this Button.
	 * 
	 * @param listener The ButtonListener to remove from this Button.
	 * @return Whether the remove was successful or not.
	 */
	public boolean removeButtonListener(ButtonListener listener)
	{
		return buttonListeners.remove(listener);
	}
	
	/**
	 * Get the ButtonListeners from this Button.
	 * 
	 * @return The ButtonListeners from this Button.
	 */
	public ArrayList<ButtonListener> getButtonListeners()
	{
		return buttonListeners;
	}
	
	/**
	 * Return whether the Button is being hovered by the Mouse or not.
	 * 
	 * @return Whether the Button is being hovered by the Mouse or not.
	 */
	public boolean isHovered()
	{
		return hovered;
	}
	
	/**
	 * Get the Font that renders the text.
	 * 
	 * @return The Font that renders the text.
	 */
	public Font getFont()
	{
		return font;
	}
	
	/**
	 * Set the Font that renders the text.
	 * 
	 * @param font The Font that renders the text.
	 */
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	/**
	 * Get the text that renders on the Button.
	 * 
	 * @return The text that renders on the Button.
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * Set the text that renders on the Button.
	 * 
	 * @param text The text that renders on the Button.
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
//	/**
//	 * Set whether the Button is being hovered by the Mouse or not.
//	 * 
//	 * @param hovered Whether the Button is being hovered by
//	 * 		the Mouse or not.
//	 */
//	public void setHovered(boolean hovered)
//	{
//		this.hovered = hovered;
//	}
	
	/**
	 * Dispose this Button from the Listeners.
	 * 
	 * @return Whether it was successfully disposed.
	 */
	public boolean dispose()
	{
		boolean disposed = super.dispose();
		
		buttonListeners = null;
		
		return disposed;
	}

	/**
	 * Renders the Button to the screen.
	 */
	public void render()
	{
		super.render();
		
		if (text != null && text.length() > 0 && font != null)
		{
			float scale = (getHeight() * 0.7f) / font.getGlyphHeight();
			
			scale = (float)Math.floor(scale);
			
			float textX = getX() + (getWidth() / 2) - (font.getWidth(text) * scale) / 2;
			float textY = getY() + (getHeight() / 2) - (font.getGlyphHeight() * scale) / 2;
			
			font.render(text, textX, textY, 0, scale, getParent());
		}
	}
}