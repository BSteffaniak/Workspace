package net.foxycorndog.jfoxylib.components;

import java.io.IOException;

import net.foxycorndog.jfoxylib.Color;
import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.font.Font;
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
	private	char			caretChar;
	
	private	int				caretPosition;
	
	private	float			textScale;
	
	private	long			startTime, currentTime;
	
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
				int  code = event.getKeyCode();
				
				char c    = 0;
				
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
						boolean changed = false;
						
						if (code == Keyboard.KEY_BACKSPACE)
						{
							if (caretPosition > 0)
							{
								builder.deleteCharAt(caretPosition - 1);
								
								changed = true;
								
								caretPosition--;
							}
						}
						else if (code == Keyboard.KEY_SPACE)
						{
							builder.insert(caretPosition, ' ');
							
							caretPosition++;
							
							changed = true;
						}
						else
						{
							c = Keyboard.getChar(code);
							
							if (c != 0)
							{
								if (shift)
								{
									c = Keyboard.shiftKey(c);
								}
							}
						}
						
						if (changed)
						{
							textUpdated();
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
					
					builder.insert(caretPosition, c);
					
					caretPosition++;
					
					textUpdated();
				}
				else
				{
					if (code == Keyboard.KEY_LEFT)
					{
						caretPosition--;
						
						if (caretPosition < 0)
						{
							caretPosition = 0;
						}
					}
					else if (code == Keyboard.KEY_RIGHT)
					{
						caretPosition++;
						
						if (caretPosition > builder.length())
						{
							caretPosition = builder.length();
						}
					}
				}
			}
			
			public void keyDown(KeyEvent event)
			{
				
			}
		});
		
		caretChar = '|';
	}
	
	/**
	 * Called when the text in the TextField has been updated.
	 */
	private void textUpdated()
	{
		startTime = System.currentTimeMillis();
	}

	/**
	 * Set whether to enable the Component or disable the Component.
	 * 
	 * @param enabled Whether to enable or disable the Component.
	 */
	public void setEnabled(boolean enabled)
	{
		if (enabled)
		{
			startTime   = System.currentTimeMillis();
			currentTime = System.currentTimeMillis();
		}
		
		super.setEnabled(enabled);
	}
	
	/**
	 * Get the Character that will be blinking in the TextField.
	 * 
	 * @return The Character that will be blinking in the TextField.
	 */
	public char getCaretChar()
	{
		return caretChar;
	}
	
	/**
	 * Set the Character that will be blinking in the TextField.
	 * 
	 * @param caretChar The Character that will be blinking in the
	 * 		TextField.
	 */
	public void setCaretChar(char caretChar)
	{
		this.caretChar = caretChar;
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
		textScale        = getScaledHeight() / (float)font.getGlyphHeight();
		
		float floor = (float)Math.floor(textScale);
		
		float diff  = textScale - floor;
		
		if (diff >= 0.5 && diff < 0.75)
		{
			textScale = floor + 0.5f;
		}
		else
		{
			textScale = floor;
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
		image.setTexture(location);
		
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
		
		caretPosition = builder.length();
		
		if (caretPosition < 0)
		{
			caretPosition = 0;
		}
	}
	
	/**
	 * Get the scale that the text will be rendered in.
	 * 
	 * @return The scale that the text will be rendered in.
	 */
	public float getTextScale()
	{
		return textScale;
	}
	
	/**
	 * Set the scale that the text will be rendered in.
	 * 
	 * @param scale The scale that the text will be rendered in.
	 */
	public void setTextScale(float scale)
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
			
//			GL.beginClipping(0, 0, getWidth(), getHeight());
//			{
				float offsetY = (getScaledHeight() / 2) - (font.getGlyphHeight() * (textScale)) / 2;
				
				Color color = null;
				
				if (fontColor != null)
				{
					color = GL.getColor();
					
					GL.setColor(fontColor);
				}
				
				boolean tick = true;
				
				currentTime = System.currentTimeMillis();
				
				if (currentTime >= startTime + 1000)
				{
					startTime = currentTime;
				}
				else if (currentTime >= startTime + 500)
				{
					tick = false;
				}
				
				StringBuilder finalText = new StringBuilder(builder);
				
				if (tick)
				{
					finalText.insert(caretPosition, caretChar);
				}
				
				font.render(finalText.toString(), 2, offsetY, 0, textScale, getParent());
				
				if (fontColor != null)
				{
					GL.setColor(color);
				}
//			}
//			GL.endClipping();
		}
		GL.popMatrix();
	}
}