package net.foxycorndog.jfoxylib.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:47:14 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:47:14 PM
 * @version	v0.2
 */
public class SpriteSheet extends Texture
{
	private int	cols, rows;
	
	/**
	 * 
	 * 
	 * @param location
	 * @param cols
	 * @param rows
	 * @throws IOException
	 */
	public SpriteSheet(String location, int cols, int rows) throws IOException
	{
		this(ImageIO.read(new File(location)), cols, rows);
	}
	
	/**
	 * 
	 * 
	 * @param image
	 * @param cols
	 * @param rows
	 */
	public SpriteSheet(BufferedImage image, int cols, int rows)
	{
		super(image);
		
		this.cols = cols;
		this.rows = rows;
	}
	
	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public float[] getImageOffsets(int x, int y, int width, int height)
	{
		float offsets[] = new float[4];
		
		float xo = x % cols;
		float yo = (rows - y - height) % rows;
		
		float w = 1;
		float h = 1;
		
		offsets[0] = (xo / cols) * w;
		offsets[1] = (yo / rows) * h;
		offsets[2] = ((xo + (float)width) / cols) * w;
		offsets[3] = ((yo + (float)height) / rows) * h;
		
		return offsets;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getCols()
	{
		return cols;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getRows()
	{
		return rows;
	}
}