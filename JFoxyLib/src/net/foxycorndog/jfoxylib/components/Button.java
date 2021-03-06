package net.foxycorndog.jfoxylib.components;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Color;
import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;

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
	
	private	Image						backgroundImage;
	
	private String						text;
	
	private	Color						fontColor;
	
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
		
		initButton();
	}
	
	/**
	 * Construct a Button in the specified parent Panel with the specified
	 * Bundle instance.
	 * 
	 * @param parent The Panel that is to be the parent of this Button.
	 * @param bundle The Bundle to use to store and render the Button.
	 * @param offset The offset in the Bundle in which to add the Button.
	 * 		A Button takes up 12 values in the Bundle.
	 */
	public Button(Panel parent, Bundle bundle, int offset)
	{
		super(parent, bundle, offset);
		
		initButton();
	}
	
	/**
	 * Initialize the things needed for a functional Button.
	 */
	private void initButton()
	{
		buttonListeners = new ArrayList<ButtonListener>();
		
		Frame.add(this);
		
		addButtonListener(new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				if (hoverTexture != null)
				{
					setTexture(normalTexture);
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
					setNormalImage(hoverTexture, false);
				}
				
				hovered = true;
			}

			public void buttonUp(ButtonEvent event)
			{
				
			}

			public void buttonDown(ButtonEvent event)
			{
				
			}
		});
	}
	
	/**
	 * Get the Color that the Font is currently rendered in.
	 * 
	 * @return The Color instance that the Font is rendered in.
	 */
	public Color getFontColor()
	{
		return fontColor;
	}
	
	/**
	 * Set the Color that the Font will be rendered in.
	 * 
	 * @param color The Color instance to set it to.
	 */
	public void setFontColor(Color color)
	{
		this.fontColor = color;
	}
	
	/**
	 * Get the Image that is used to render the background of the Button.
	 * 
	 * @return The Image instance of the background Image.
	 */
	public Image getBackgroundImage()
	{
		return backgroundImage;
	}
	
	/**
	 * Set the Image that renders behind the Button as the background.
	 * 
	 * @param image The background Image instance.
	 */
	public void setBackgroundImage(Image image)
	{
		this.backgroundImage = image;
	}
	
	/**
	 * Set the Image that renders behind the Button as the background
	 * by creating an Image with the given Texture.
	 * 
	 * @param texture The background Texture instance.
	 */
	public void setBackgroundImage(Texture texture)
	{
		Image img = new Image(null);
		img.setTexture(texture);
		
		setBackgroundImage(img);
	}
	
	/**
	 * Set the Image that renders behind the Button as the background by
	 * creating a Texture with the Image file at the specified location
	 * and then creating an Image instance from the created Texture
	 * instance.
	 * 
	 * @param imageLocation The location of the Image in the os's
	 * 		file system.
	 * @throws IOException Thrown if the Image at the specified location
	 * 		could not be found.
	 */
	public void setBackgroundImage(String imageLocation) throws IOException
	{
		Texture texture = new Texture(imageLocation);
		
		setBackgroundImage(texture);
	}
	
	/**
	 * Set the Image of this Button Component.
	 * 
	 * @param image The new Image of this Button Component.
	 */
	public void setTexture(BufferedImage image)
	{
		setNormalImage(image, true);
	}
	
	/**
	 * Set the Texture of this Button Component.
	 * 
	 * @param image The new Texture of this Button Component.
	 */
	public void setTexture(Texture image)
	{
		setNormalImage(image, true);
	}
	
	/**
	 * Set the Texture of this Button Component.
	 * 
	 * @param image The new Image of this Button Component.
	 */
	public void setImage(Image image)
	{
		setNormalImage(image.getTexture(), true);
	}
	
	/**
	 * Set the Image of this Button Component.
	 * 
	 * @param image The new Image of this Button Component.
	 * @param set Whether or not to set the local variable of the normal
	 * 		Image.
	 */
	private void setNormalImage(BufferedImage image, boolean set)
	{
		setNormalImage(new Texture(image), set);
	}
	
	/**
	 * Set the Texture of this Button Component.
	 * 
	 * @param image The new Texture of this Button Component.
	 * @param set Whether or not to set the local variable of the normal
	 * 		Image.
	 */
	private void setNormalImage(Texture image, boolean set)
	{
		super.setTexture(image);
		
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

		if (normalTexture == null && font != null && text != null)
		{
			setSize((int)Math.ceil(font.getWidth(text)), (int)Math.ceil(font.getHeight(text)));
		}
	}
	
	/**
	 * Set whether to enable the Button or disable the Button.
	 * 
	 * @param enabled Whether to enable or disable the Button.
	 */
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
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
		
		if (normalTexture == null && font != null && text != null)
		{
			setSize((int)Math.ceil(font.getWidth(text)), (int)Math.ceil(font.getHeight(text)));
		}
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
		if (backgroundImage != null)
		{
			GL.pushMatrix();
			{
				GL.translate(getX(), getY(), 0);
				GL.scale(getScale(), getScale(), 1);
				
				backgroundImage.render();
			}
			GL.popMatrix();
		}
		
		super.render();
		
		if (isVisible())
		{
			if (text != null && text.length() > 0 && font != null)
			{
				float scale = 1;//(getHeight() * 0.7f) / font.getGlyphHeight();
				
				scale = scale >= 1 ? (float)Math.floor(scale) : scale;
	//			scale = 1;
				
				float textX = getX() + (getWidth()  * getScale() / 2) - (font.getWidth(text)  * scale) / 2;
				float textY = getY() + (getHeight() * getScale() / 2) - (font.getHeight(text) * scale) / 2;
				
				textX   = Math.round(textX);
				textY   = Math.round(textY); 
				
				Color c = null;
				
				if (fontColor != null)
				{
					c = GL.getColor();

					GL.setColor(fontColor);
				}
				
				font.render(text, textX, textY, 0, scale, getParent());

				if (c != null)
				{
					GL.setColor(c);
				}
			}
		}
	}
}
