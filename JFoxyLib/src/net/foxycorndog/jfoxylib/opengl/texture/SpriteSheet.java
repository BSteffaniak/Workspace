package net.foxycorndog.jfoxylib.opengl.texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class used to create an easy interface to use SpriteSheets to
 * get data from.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:47:14 PM
 * @since	v0.2
 * @version	Jul 2, 2013 at 1:35:35 PM
 * @version	v0.2
 */
public class SpriteSheet extends Texture
{
	private int	cols, rows;
	
	/**
	 * Create a SpriteSheet from the File at the specified location with
	 * the specified amount of columns and rows.
	 * 
	 * @param location The location of the Image File containing the
	 * 		SpriteSheet.
	 * @param cols The number of columns in the SpriteSheet.
	 * @param rows The number of rows in the SpriteSheet.
	 * @throws IOException Thrown if there is an error reading from the
	 * 		Image File at the specified location.
	 */
	public SpriteSheet(String location, int cols, int rows) throws IOException
	{
		this(ImageIO.read(new File(location)), cols, rows);
	}
	
	/**
	 * Create a SpriteSheet from the given BufferedImage with the
	 * specified amount of columns and rows.
	 * 
	 * @param image The BufferedImage instance to use to create the
	 * 		SpriteSheet.
	 * @param cols The number of columns in the SpriteSheet.
	 * @param rows The number of rows in the SpriteSheet.
	 */
	public SpriteSheet(BufferedImage image, int cols, int rows)
	{
		super(image);
		
		this.cols = cols;
		this.rows = rows;
	}
	
	/**
	 * Get the float offsets of the SpriteSheet for the section of the
	 * SpriteSheet located at (x, y) and that takes up the specified
	 * amount of columns and rows.
	 * 
	 * @param x The horizontal location to get the offsets from.
	 * 		(left = 0)
	 * @param y The vertical location to get the offsets from.
	 * 		(top = 0)
	 * @param cols The number of columns to get the offsets for.
	 * @param rows The number of rows to get the offsets for.
	 * @return A float array containing values used to map a Texture
	 * 		to a polygon.
	 */
	public float[] getImageOffsets(int x, int y, int cols, int rows)
	{
		float offsets[] = new float[4];
		
		float xo = x % this.cols;
		float yo = (this.rows - y - rows) % this.rows;
		
		float w = 1;
		float h = 1;
		
		offsets[0] = (xo / this.cols) * w;
		offsets[1] = (yo / this.rows) * h;
		offsets[2] = ((xo + (float)cols) / this.cols) * w;
		offsets[3] = ((yo + (float)rows) / this.rows) * h;
		
		return offsets;
	}
	
	/**
	 * Get the number of columns that are in the SpriteSheet.
	 * 
	 * @return The number of columns that are in the SpriteSheet.
	 */
	public int getNumCols()
	{
		return cols;
	}
	
	/**
	 * Get the number of rows that are in the SpriteSheet.
	 * 
	 * @return The number of rows that are in the SpriteSheet.
	 */
	public int getNumRows()
	{
		return rows;
	}
	
	/**
	 * Get the number of pixels that each column takes up on the
	 * SpriteSheet.
	 * 
	 * @return The number of pixels that each column takes up on the
	 * 		SpriteSheet.
	 */
	public int getColSize()
	{
		return getWidth() / cols;
	}
	
	/**
	 * Get the number of pixels that each row takes up on the
	 * SpriteSheet.
	 * 
	 * @return The number of pixels that each row takes up on the
	 * 		SpriteSheet.
	 */
	public int getRowSize()
	{
		return getHeight() / rows;
	}
}