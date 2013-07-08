package net.foxycorndog.jfoxylib.components;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.SpriteSheet;
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
	private	boolean	indirectBundle;
	
	private	int		x, y;
	private	int		bundleX, bundleY;
	private	int		cols, rows;
	private	int		rx, ry;
	private	int		offset;
	
	private	Texture	texture;
	
	private	Bundle	bundle;
	
	/**
	 * Construct an Image in the specified parent Panel.
	 * 
	 * @param parent The Panel that is to be the parent of this Image.
	 */
	public Image(Panel parent)
	{
		this(parent, new Bundle(3 * 2, 2, true, false), 0);
		
		this.indirectBundle = false;
	}
	
	/**
	 * Construct an Image in the specified parent Panel with the specified
	 * Bundle instance.
	 * 
	 * @param parent The Panel that is to be the parent of this Image.
	 * @param bundle The Bundle to use to store and render the Image.
	 * @param offset The offset in the Bundle in which to add the Image.
	 * 		An Image takes up 12 values in the Bundle.
	 */
	public Image(Panel parent, Bundle bundle, int offset)
	{
		super(parent);
		
		this.bundle         = bundle;
		
		this.offset         = offset;
		
		this.indirectBundle = true;
	}
	
	/**
	 * Get the column that this Image is located on in the SpriteSheet
	 * Texture.
	 * 
	 * @return The column that this Image is located on in the SpriteSheet
	 * 		Texture.
	 */
	public int getSpriteX()
	{
		return x;
	}

	/**
	 * Set the column that this Image is located on in the SpriteSheet
	 * Texture.
	 * 
	 * @param rows The column that this Image is located on in the
	 * 		SpriteSheet Texture.
	 */
	public void setSpriteX(int x)
	{
		this.x = x;
	}
	
	/**
	 * Get the row that this Image is located on in the SpriteSheet
	 * Texture.
	 * 
	 * @return The row that this Image is located on in the SpriteSheet
	 * 		Texture.
	 */
	public int getSpriteY()
	{
		return y;
	}
	
	/**
	 * Set the row that this Image is located on in the SpriteSheet
	 * Texture.
	 * 
	 * @param rows The row that this Image is located on in the
	 * 		SpriteSheet Texture.
	 */
	public void setSpriteY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Get the number of columns that this Image takes up on the
	 * SpriteSheet Texture.
	 * 
	 * @return The number of columns that this Image takes up on the
	 * 		SpriteSheet Texture.
	 */
	public int getSpriteCols()
	{
		return cols;
	}

	/**
	 * Set the number of columns that this Image takes up on the
	 * SpriteSheet Texture.
	 * 
	 * @param rows The number of columns that this Image takes up on the
	 * 		SpriteSheet Texture.
	 */
	public void setSpriteCols(int cols)
	{
		this.cols = cols;
	}
	
	/**
	 * Get the number of rows that this Image takes up on the
	 * SpriteSheet Texture.
	 * 
	 * @return The number of rows that this Image takes up on the
	 * 		SpriteSheet Texture.
	 */
	public int getSpriteRows()
	{
		return rows;
	}
	
	/**
	 * Set the number of rows that this Image takes up on the SpriteSheet
	 * Texture.
	 * 
	 * @param rows The number of rows that this Image takes up on the
	 * 		SpriteSheet Texture.
	 */
	public void setSpriteRows(int rows)
	{
		this.rows = rows;
	}
	
	/**
	 * Get the Texture of the Image.
	 * 
	 * @return The Texture instance used in the Image.
	 */
	public Texture getTexture()
	{
		return texture;
	}
	
	/**
	 * Set the Texture of this Image Component by giving the location
	 * of the image file.
	 * 
	 * @param location The location of the Image.
	 * @throws IOException Thrown if there was any trouble reading the
	 * 		image.
	 */
	public void setTexture(String location) throws IOException
	{
		setTexture(new Texture(location), 1, 1, true);
	}
	
	/**
	 * Set the Texture of this Image Component by giving a BufferedImage
	 * that will be decoded into a Texture.
	 * 
	 * @param image The new Image of this Image Component.
	 */
	public void setTexture(BufferedImage image)
	{
		setTexture(image, 1, 1, true);
	}
	
	/**
	 * Set the Texture of this Image Component by giving an instance
	 * of a Texture.
	 * 
	 * @param image The new Texture of this Image Component.
	 */
	public void setTexture(Texture texture)
	{
		setTexture(texture, 1, 1, true);
	}
	
	/**
	 * Set the Texture of this Image Component by giving a BufferedImage
	 * that will be decoded into a Texture, and then repeated the
	 * specific amount of times horizontally and vertically.
	 * 
	 * @param image The new Image of this Image Component.
	 * @param rx The amount of times to repeat the Image horizontally.
	 * @param ry The amount of times to repeat the Image vertically.
	 */
	public void setTexture(BufferedImage image, int rx, int ry)
	{
		setTexture(image, rx, ry, true);
	}
	
	/**
	 * Set the Texture of this Image Component by giving an instance
	 * of a Texture, and then repeat it the specific amount of times
	 * horizontally and vertically.
	 * 
	 * @param image The new Texture of this Image Component.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 */
	public void setTexture(Texture texture, int rx, int ry)
	{
		setTexture(texture, rx, ry, true);
	}
	
	/**
	 * Set the Texture of this Image Component by giving the location
	 * of the image.
	 * 
	 * @param location The location of the Image.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 * @throws IOException Thrown if there was any trouble reading the
	 * 		image.
	 */
	public void setTexture(String location, boolean beginEditingBundle) throws IOException
	{
		setTexture(new Texture(location), 1, 1, beginEditingBundle);
	}
	
	/**
	 * Set the Texture of this Image Component by giving a BufferedImage
	 * that will be decoded into a Texture.
	 * 
	 * @param image The new Image of this Image Component.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void setTexture(BufferedImage image, boolean beginEditingBundle)
	{
		setTexture(image, 1, 1, beginEditingBundle);
	}
	
	/**
	 * Set the Texture of this Image Component.
	 * 
	 * @param image The new Texture of this Image Component.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void setTexture(Texture image, boolean beginEditingBundle)
	{
		setTexture(image, 1, 1, beginEditingBundle);
	}
	
	/**
	 * Set the Texture of this Image Component by giving a BufferedImage
	 * that will be decoded into a Texture, and then repeated the
	 * specific amount of times horizontally and vertically.
	 * 
	 * @param image The new Image of this Image Component.
	 * @param rx The amount of times to repeat the Image horizontally.
	 * @param ry The amount of times to repeat the Image vertically.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void setTexture(BufferedImage image, int rx, int ry, boolean beginEditingBundle)
	{
		setTexture(new Texture(image), rx, ry, beginEditingBundle);
	}
	
	/**
	 * Set the Texture of this Image Component by giving an instance
	 * of a Texture, and then repeat it the specific amount of times
	 * horizontally and vertically.
	 * 
	 * @param image The new Texture of this Image Component.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void setTexture(Texture image, int rx, int ry, boolean beginEditingBundle)
	{
		texture = image;
		
		this.rx = rx;
		this.ry = ry;
		
		updateTexture(beginEditingBundle);
	}
	
	/**
	 * Remove the Image and make this Image instance show nothing.
	 */
	public void disposeImage()
	{
		disposeImage(true);
	}
	
	/**
	 * Remove the Image and make this Image instance show nothing.
	 * 
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void disposeImage(boolean beginEditingBundle)
	{
		if (beginEditingBundle)
		{
			bundle.beginEditingTextures();
		}
		
		bundle.setTextures(offset, new float[3 * 2 * 2]);
		
		if (beginEditingBundle)
		{
			bundle.endEditingTextures();
		}
		
		texture = null;
		
		x       = 0;
		y       = 0;
		cols    = 0;
		rows    = 0;
	}
	
	/**
	 * Update the Texture in case the SpriteSheet properties
	 * (x, y, cols, rows) were changed.
	 */
	public void updateTexture()
	{
		updateTexture(true);
	}
	
	/**
	 * Update the Texture in case the SpriteSheet properties
	 * (x, y, cols, rows) were changed.
	 * 
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Textures.
	 */
	public void updateTexture(boolean beginEditingBundle)
	{
		int width  = texture.getWidth()  * rx;
		int height = texture.getHeight() * ry;
		
		float offsets[] = null;
		
		if (cols != 0 && rows != 0)
		{
			SpriteSheet sprites = (SpriteSheet)texture;
			
			offsets = sprites.getImageOffsets(x, y, cols, rows);
			
			width  /= sprites.getNumCols();
			height /= sprites.getNumRows();
			
			width  *= cols;
			height *= rows;
		}
		else
		{
			offsets = texture.getImageOffsets();
		}
		
		if (beginEditingBundle)
		{
			setSize(width, height);
			
			bundle.beginEditingTextures();
		}
		
		bundle.setTextures(offset, GL.genRectTextures(offsets, rx, ry));
		
		if (beginEditingBundle)
		{
			bundle.endEditingTextures();
		}
	}
	
	/**
	 * Set the location of this Image to the specified location.
	 * 
	 * @param x The new horizontal location of the Image.
	 * @param y The new vertical location of the Image.
	 */
	public void setLocation(int x, int y)
	{
		setLocation(x, y, true);
	}
	
	/**
	 * Set the location of this Image to the specified location.
	 * 
	 * @param x The new horizontal location of the Image.
	 * @param y The new vertical location of the Image.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Vertices.
	 */
	public void setLocation(int x, int y, boolean beginEditingBuffer)
	{
		super.setLocation(x, y);
		
		if (indirectBundle)
		{
			if (beginEditingBuffer)
			{
				bundle.beginEditingVertices();
			}
			
			bundle.setVertices(offset, GL.genRectVerts(x, y, getScaledWidth(), getScaledHeight()));
			
			if (beginEditingBuffer)
			{
				bundle.endEditingVertices();
			}
		}
	}
	
	/**
	 * Set the size of this Image to the specified size.
	 * 
	 * @param width The new horizontal size of the Image.
	 * @param height The new vertical size of the Image.
	 */
	public void setSize(int width, int height)
	{
		setSize(width, height, true);
	}
	
	/**
	 * Set the size of this Image to the specified size.
	 * 
	 * @param width The new horizontal size of the Image.
	 * @param height The new vertical size of the Image.
	 * @param beginEditingBundle Whether or not to tell the Bundle
	 * 		instance to begin editing the Vertices.
	 */
	public void setSize(int width, int height, boolean beginEditingBuffer)
	{
		super.setSize(width, height);
		
		if (beginEditingBuffer)
		{
			bundle.beginEditingVertices();
		}
		
		bundle.setVertices(offset, GL.genRectVerts(0, 0, width, height));
		
		if (beginEditingBuffer)
		{
			bundle.endEditingVertices();
		}
	}

	/**
	 * Renders the Image to the screen.
	 */
	public void render()
	{
		if (isVisible())
		{
			GL.pushMatrix();
			{
				GL.translate(getX(), getY(), 0);
				GL.scale(getScale(), getScale(), 1);
				
				update();
				
				if (texture == null)
				{
					GL.popMatrix();
					
					return;
				}
				
				bundle.render(GL.TRIANGLES, offset, 3 * 2 * 2, texture);
			}
			GL.popMatrix();
		}
	}
}