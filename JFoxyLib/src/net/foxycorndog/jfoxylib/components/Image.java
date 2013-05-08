package net.foxycorndog.jfoxylib.components;

import java.awt.image.BufferedImage;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;

/**
 * Used to render an Image to the screen as a Bitmap.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 10, 2013 at 12:55:25 AM
 * @since	v0.2
 * @version Mar 10, 2013 at 12:55:25 AM
 * @version	v0.2
 */
public class Image extends Component
{
	private Texture	texture;
	
	private Bundle	bundle;
	
	/**
	 * Construct an Image in the specified parent Panel.
	 * 
	 * @param parent The Panel that is to be the parent of this Image.
	 */
	public Image(Panel parent)
	{
		super(parent);
		
		bundle = new Bundle(4, 2, true, false);
	}
	
	/**
	 * Set the Image of this Image Component.
	 * 
	 * @param image The new Image of this Image Component.
	 */
	public void setImage(BufferedImage image)
	{
		setImage(image, 1, 1);
	}
	
	/**
	 * Set the Texture of this Image Component.
	 * 
	 * @param image The new Texture of this Image Component.
	 */
	public void setImage(Texture image)
	{
		setImage(image, 1, 1);
	}
	
	/**
	 * Set the Image of this Image Component.
	 * 
	 * @param image The new Image of this Image Component.
	 * @param rx The amount of times to repeat the Image horizontally.
	 * @param ry The amount of times to repeat the Image vertically.
	 */
	public void setImage(BufferedImage image, int rx, int ry)
	{
		texture = new Texture(image);
		
		int width  = image.getWidth() * rx;
		int height = image.getHeight() * ry;
		
		setSize(width, height);
		
		bundle.beginEditingTextures();
		{
			bundle.setTextures(0, GL.genRectTextures(texture.getImageOffsets(), rx, ry));
		}
		bundle.endEditingTextures();
	}
	
	/**
	 * Set the Texture of this Image Component.
	 * 
	 * @param image The new Texture of this Image Component.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 */
	public void setImage(Texture image, int rx, int ry)
	{
		texture = image;
		
		int width  = image.getWidth() * rx;
		int height = image.getHeight() * ry;
		
		setSize(width, height);
		
		bundle.beginEditingTextures();
		{
			bundle.setTextures(0, GL.genRectTextures(texture.getImageOffsets(), rx, ry));
		}
		bundle.endEditingTextures();
	}
	
	/**
	 * Set the size of this Image to the specified size.
	 * 
	 * @param width The new horizontal size of the Image.
	 * @param height The new vertical size of the Image.
	 */
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		bundle.beginEditingVertices();
		{
			bundle.setVertices(0, GL.genRectVerts(0, 0, width, height));
		}
		bundle.endEditingVertices();
	}

	/**
	 * Renders the Image to the screen.
	 */
	public void render()
	{
		update();
		
		if (texture == null)
		{
			return;
		}
		
		GL.pushMatrix();
		{
			GL.translate(getX(), getY(), 0);
			
			bundle.render(GL.QUADS, texture);
		}
		GL.popMatrix();
	}
}