package net.foxycorndog.jfoxylib.components;

import java.io.IOException;

import net.foxycorndog.jfoxylib.Color;
import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.font.MissingCharacterException;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class used as a TextField for inputing data.
 * 
 * @author	Braden Steffaniak
 * @since	May 18, 2013 at 11:55:22 PM
 * @since	v0.2
 * @version	May 18, 2013 at 11:55:22 PM
 * @version	v0.2
 */
public class TextField extends Component
{
	private	float			scale, textScale;
	
	private	Color			fontColor;
	
	private	Image			backgroundImage;
	
	private	StringBuilder	builder;
	
	private	Font			font;
	
	/**
	 * Create a TextField with the specified parent Panel.
	 * 
	 * @param panel The parent Panel.
	 */
	public TextField(Panel panel)
	{
		super(panel);
		
		Frame.add(this);
		
		textScale = 1;
		
		builder   = new StringBuilder();
		
		Keyboard.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent event)
			{
			}
			
			public void keyReleased(KeyEvent event)
			{
			}
			
			public void keyPressed(KeyEvent event)
			{
				char c = 0;
				
				if (isFocused())
				{
					boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LEFT_SHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT_SHIFT);
						
					String desc = event.getDescription();
					
					if (desc.length() == 1)
					{
						if (!(Keyboard.isCapsLockOn() ^ shift))
						{
							desc = desc.toLowerCase();
						}
						
						c = desc.charAt(0);
						
						if (shift && !Keyboard.isCapsLockOn())
						{
							c = Keyboard.shiftKey(c);
						}
					}
					else
					{
						if (event.getKeyCode() == Keyboard.KEY_BACKSPACE)
						{
							if (builder.length() > 0)
							{
								builder.deleteCharAt(builder.length() - 1);
							}
						}
						else if (event.getKeyCode() == Keyboard.KEY_SPACE)
						{
							builder.append(' ');
						}
						else
						{
							c = Keyboard.getChar(event.getKeyCode());
							
							if (c != 0)
							{
								if (shift)
								{
									c = Keyboard.shiftKey(c);
								}
							}
						}
					}
				}
				
				if (c != 0)
				{
//					if (!font.containsChar(c))
//					{
//						throw new MissingCharacterException("The Character '" + c + "' does "
//								+ "not exist in the specified charSequence given to the Font.");
//					}
					
					builder.append(c);
				}
			}
			
			public void keyDown(KeyEvent event)
			{
				
			}
		});
	}
	
	/**
	 * Get the Font that the text in the TextField is rendered in.
	 * 
	 * @return The Font that the text in the TextField is rendered in.
	 */
	public Font getFont()
	{
		return font;
	}
	
	/**
	 * Set the Font that the text will be rendered as in the TextField.
	 * 
	 * @param font The Font that the text will be rendered as in the
	 * 		TextField.
	 */
	public void setFont(Font font)
	{
		this.font    = font;
		
		scaleFont();
//		scale *= 0.8f;
	}
	
	/**
	 * Calculate the proper scale for the Font according to the height of
	 * the TextField.
	 */
	private void scaleFont()
	{
		scale        = getHeight() / (float)font.getGlyphHeight();
		
		double floor = Math.floor(scale);
		
		double diff  = scale - floor;
		
		if (diff >= 0.5 && diff < 0.75)
		{
			scale = (float)(Math.floor(scale) + 0.5);
		}
		else
		{
			scale = (float)(Math.floor(scale));
		}
	}
	
	/**
	 * Get the Image that is rendered as the background of the TextField.
	 * 
	 * @return The Image that is rendered as the background of the
	 * 		TextField.
	 */
	public Image getBackgroundImage()
	{
		return backgroundImage;
	}
	
	/**
	 * Set the background Image of the TextField that will be rendered.
	 * 
	 * @param image The background Image of the TextField.
	 */
	public void setBackgroundImage(Image image)
	{
		this.backgroundImage = image;
		
		setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
		
		if (font != null)
		{
//			scale = getHeight() / (float)font.getGlyphHeight();
//			scale *= 0.8f;
			
			scaleFont();
		}
	}
	
	/**
	 * Set the background Image of the TextField that will be rendered.
	 * 
	 * @param location The location of the Image to set as the background.
	 * @throws IOException 
	 */
	public void setBackgroundImage(String location) throws IOException
	{
		Image image = new Image(null);
		image.setImage(location);
		
		setBackgroundImage(image);
	}
	
	/**
	 * Get the text that is in the TextField.
	 * 
	 * @return The text that is currently in the TextField.
	 */
	public String getText()
	{
		return builder.toString();
	}
	
	/**
	 * Set the text that the TextField will contain.
	 * 
	 * @param text The text that the TextField will contain.
	 */
	public void setText(String text)
	{
		builder = new StringBuilder();
		
		builder.append(text);
	}
	
	/**
	 * Get the scale that the text will be rendered in.
	 * 
	 * @return The scale that the text will be rendered in.
	 */
	public float getScale()
	{
		return textScale;
	}
	
	/**
	 * Set the scale that the text will be rendered in.
	 * 
	 * @param scale The scale that the text will be rendered in.
	 */
	public void setScale(float scale)
	{
		this.textScale = scale;
	}
	
	/**
	 * Get the Color that the Font will be rendered in.
	 * 
	 * @return The Color that the Font will be rendered in.
	 */
	public Color getFontColor()
	{
		return fontColor;
	}
	
	/**
	 * Set the Color that the Font will be rendered in.
	 * 
	 * @param color The Color that the Font will be rendered in.
	 */
	public void setFontColor(Color color)
	{
		this.fontColor = color;
	}
	
	/**
	 * @see net.foxycorndog.jfoxylib.components.Component#render()
	 */
	public void render()
	{
		if (!isVisible())
		{
			return;
		}
		
		update();
			
		GL.pushMatrix();
		{
			GL.translate(getX(), getY(), 0);
			
			backgroundImage.render();
			
			GL.beginClipping(0, 0, getWidth(), getHeight());
			{
				float offsetY = (getHeight() / 2) - (font.getGlyphHeight() * (scale * textScale)) / 2;
				
				float color[] = null;
				
				if (fontColor != null)
				{
					color = GL.getColor();
					
					GL.setColor(fontColor.getRedf(), fontColor.getGreenf(), fontColor.getBluef(), fontColor.getAlphaf());
				}
				
				font.render(builder.toString(), 2, offsetY, 0, scale * textScale, getParent());
				
				if (fontColor != null)
				{
					GL.setColor(color[0], color[1], color[2], color[3]);
				}
			}
			GL.endClipping();
		}
		GL.popMatrix();
	}
}