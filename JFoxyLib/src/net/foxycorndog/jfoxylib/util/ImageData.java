package net.foxycorndog.jfoxylib.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageData
{
	private int	width, height;
	
	private int	pixels[];
	
	public ImageData(String location) throws IOException
	{
		this(ImageIO.read(new File(location)));
	}
	
	public ImageData(BufferedImage image)
	{
		this.width  = image.getWidth();
		this.height = image.getHeight();
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.BITMASK);
		
		Graphics2D g = img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		this.pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	public int[] getData(int x, int y, int width, int height)
	{
		int data[] = new int[width * height];
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				data[x2 + y2 * width] = pixels[(x + x2) + (this.height - (y + y2) - 1) * this.width];
			}
		}
		
		return data;
	}
	
	public byte[] getDataBytes(int x, int y, int width, int height)
	{
		byte data[] = new byte[width * height * 4];
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				int d = pixels[(x + x2) + (this.height - (y + y2) - 1) * this.width];
				
				data[(x2 + y2 * width) * 4 + 0] = (byte)((d >> 16) & 0xFF);
				data[(x2 + y2 * width) * 4 + 1] = (byte)((d >>  8) & 0xFF);
				data[(x2 + y2 * width) * 4 + 2] = (byte)((d >>  0) & 0xFF);
				data[(x2 + y2 * width) * 4 + 3] = (byte)((d >> 24) & 0xFF);
			}
		}
		
		return data;
	}
}