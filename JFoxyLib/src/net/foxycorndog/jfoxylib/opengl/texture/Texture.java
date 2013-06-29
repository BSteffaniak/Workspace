package net.foxycorndog.jfoxylib.opengl.texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;


import net.foxycorndog.jfoxylib.opengl.GL;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:49:13 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:49:13 PM
 * @version	v0.2
 */
public class Texture
{
	private int			id;
	private int			width, height;
	
	private float		texWid, texHei;
	
	private	IntBuffer	intBuffer;
	
	private	byte		pixels[];
	
	/**
	 * 
	 * 
	 * @param location
	 * @throws IOException
	 */
	public Texture(String location) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(location));
		
		create(img, true);
	}
	
	/**
	 * 
	 * 
	 * @param image
	 */
	public Texture(BufferedImage image)
	{
		create(image, true);
	}
	
	/**
	 * 
	 * 
	 * @param image
	 * @param recreate
	 */
	public Texture(BufferedImage image, boolean recreate)
	{
		create(image, recreate);
	}

	/**
	 * 
	 * 
	 * @param image
	 * @param recreate
	 */
	private void create(BufferedImage image, boolean recreate)
	{
		GL.pushAttrib(GL.ALL_ATTRIB_BITS);
		{
			id = newTextureId();
			
			loadTexture(image, recreate);
		}
		GL.popAttrib();
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private int newTextureId()
	{
		int textureId = GL11.glGenTextures();
		
		return textureId;
	}
	
	/**
	 * 
	 * 
	 * @param image
	 * @param recreate
	 * @return
	 */
	private void loadTexture(BufferedImage image, boolean recreate)
	{
		// In which ID will we be storing this texture?
	    
	    // We need to flip the textures vertically:
//	    Matrix flip = new Matrix();
//	    flip.postScale(1f, -1f);
	    
	    // This will tell the BitmapFactory to not scale based on the device's pixel density:
	    // (Thanks to Matthew Marshall for this bit)
//	    BitmapFactory.Options opts = new BitmapFactory.Options();
//	    opts.inScaled = false;
	    
//	    BitmapDrawable drawable = (BitmapDrawable)img.getDrawable();
//	    
//	    // Load up, and flip the texture:
//	    Bitmap temp = drawable.getBitmap();//BitmapFactory.decodeResource(activity.getResources(), resourceId, opts);
//	    
//	    Bitmap bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), flip, true);
	    
	    this.width  = image.getWidth();
	    this.height = image.getHeight();
	    
//	    temp.recycle();
	    
	    bind();
	    
	    // Set all of our texture parameters:
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S,     GLES10.GL_REPEAT);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T,     GLES10.GL_REPEAT);
	    
//	    ByteBuffer bb = extract(bmp);
	    
//	    GLES10.glTexImage2D(GLES10.GL_TEXTURE_2D, 0, GLES10.GL_RGBA, width, height, 0, GLES10.GL_RGBA, GLES10.GL_UNSIGNED_BYTE, bb);
	    
	    if (recreate)
	    {
//		    WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 4, null);
//		    ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
//	    
//		    BufferedImage img = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	    	
		    Graphics g = img.createGraphics();
		    g.setColor(new Color(0, 0, 0, 0));
		    g.fillRect(0, 0, width, height);
		    g.drawImage(image, 0, 0, null);
		    g.dispose();
		    
		    image = img;
	    }
	    
//	    int pixels[] = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	    byte pixels[] = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
	    
        // Flip the data vertically.
	    for (int y = 0; y < height / 2; y++)
        {
            for (int x = 0; x < width; x++)
            {
            	int offset  = (x + y * width) * 4;
            	int offset2 = (x + (height - y - 1) * width) * 4;
            	
            	for (int i = 0; i < 4; i++)
            	{
	                byte temp = pixels[offset + i];
	                pixels[offset + i] = pixels[offset2 + i];
	                pixels[offset2 + i] = temp;
            	}
            }
        }
	    
	    // Put the data in the right order (From ABGR to RGBA (Reverse it)).
	    for (int y = 0; y < height; y++)
	    {
	    	for (int x = 0; x < width; x++)
	    	{
            	int offset  = (x + y * width) * 4;
            	
	    		for (int i = 0; i < 2; i++)
            	{
	                byte temp = pixels[offset + i];
	                pixels[offset + i] = pixels[offset + 4 - i - 1];
	                pixels[offset + 4 - i - 1] = temp;
            	}
	    	}
	    }
	    
	    this.pixels = pixels;
	    
	    loadTexture();
	}
	
	/**
	 * 
	 * 
	 * @param pixels
	 * @return
	 */
	private void loadTexture()
	{
		bind();
	    
	    ByteBuffer byteBuffer = BufferUtils.createByteBuffer(pixels.length); // 4 for RGBA, 3 for RGB
       
	    byteBuffer.order(ByteOrder.nativeOrder());//.BIG_ENDIAN);
		byteBuffer.put(pixels);
		byteBuffer.position(0);
		
//	    byte pixelsb[] = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        
//        intBuffer = byteBuffer.asIntBuffer();
//        
//        for(int y = height - 1; y >= 0; y--)
//        {
//            for(int x = 0; x < width; x++)
//            {
//                int pixel = pixels[x + y * width];
//                
//                int ri = ((pixel >> 16) & 0xFF);
//                int gi = ((pixel >> 8)  & 0xFF);
//                int bi = ((pixel >> 0)  & 0xFF);
//                int ai = ((pixel >> 24) & 0xFF);
////                if ((ri << 24 | gi << 16 | bi << 8 | ai) != 0)
////                System.out.println(ri << 24 | gi << 16 | bi << 8 | ai);
//                
//                intBuffer.put(ri << 24 | gi << 16 | bi << 8 | ai);
//            }
//        }
//        
//        intBuffer.position(0);
//        buffer.rewind(); // FOR THE LOVE OF GOD DO NOT FORGET TH
        // You now have a ByteBuffer filled with the color data of each pixel.0
        // Now just create a texture ID and bind it. Then you can load it using 
        // whatever OpenGL method you want, for example:
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, byteBuffer);
	    
//	    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, level, internalformat, id, id, border, format, type, pixels);
	    
//	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
//	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	    
//	    // Generate, and load up all of the mipmaps:
//	    for(int level=0, height = bmp.getHeight(), width = bmp.getWidth(); true; level++) {
//	        // Push the bitmap onto the GPU:
//	        GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, level, bmp, 0);
//	        
//	        // We need to stop when the texture is 1x1:
//	        if(height==1 && width==1) break;
//	        
//	        // Resize, and let's go again:
//	        width >>= 1; height >>= 1;
//	        if(width<1)  width = 1;
//	        if(height<1) height = 1;
//	        
//	        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, true);
//	        bmp.recycle();
//	        bmp = bmp2;
//	    }
	    
	    genTexDimensions();
	}
	
//	/**
//	 * Get the integer array that holds the values of the colors for
//	 * the colors in the Texture.
//	 * 
//	 * @return The integer array instance.
//	 */
//	public byte[] getPixels()
//	{
//		return pixels;
//	}
	
	public int getPixel(int x, int y)
	{
		int pixel = 0;
		
		int r = pixels[(x + y * width) * 4 + 0];
		int g = pixels[(x + y * width) * 4 + 1];
		int b = pixels[(x + y * width) * 4 + 2];
		int a = pixels[(x + y * width) * 4 + 3];
		
		if (r < 0)
		{
			r += 256;
		}
		if (g < 0)
		{
			g += 256;
		}
		if (b < 0)
		{
			b += 256;
		}
		if (a < 0)
		{
			a += 256;
		}
		
		a *= 0x1000000;
		r *= 0x10000;
		g *= 0x100;
		b *= 0x1;
		
		pixel = r + g + b + a;
		
		return pixel;
	}
	
	public int[] getPixels(int x, int y, int width, int height)
	{
		int data[] = new int[width * height];
		
//		byteBuffer.position((x + y * width) * 4);
//			
//		for (int i = 0; i < height; i++)
//		{
//			byteBuffer.get(data, i * width * 4, width * 4);
//		}
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				data[(x2 + y2 * width)] = getPixel(x2 + x, y2 + y);
			}
		}
		
		return data;
	}
	
	public void setPixel(int x, int y, int value)
	{
		bind();
		
		byte values[] = new byte[4];
		
		byte r = (byte)((value >> 16) & 0xFF);
		byte g = (byte)((value >>  8) & 0xFF);
		byte b = (byte)((value >>  0) & 0xFF);
		byte a = (byte)((value >> 24) & 0xFF);

		values[0] = r;
		values[1] = g;
		values[2] = b;
		values[3] = a;
		
		for (int i = 0; i < 4; i++)
		{
			pixels[(x + y * width) * 4 + i] = values[i];
		}
		
		ByteBuffer buf = BufferUtils.createByteBuffer(4);
		
		buf.put(values);
		
		buf.position(0);
		
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	}
	
	public void setPixels(int x, int y, int width, int height, int values[])
	{
		bind();
		
		byte bytes[] = new byte[width * height * 4];
		
		for (int i = 0; i < bytes.length; i += 4)
		{
			byte r = (byte)((values[i / 4] >> 16) & 0xFF);
			byte g = (byte)((values[i / 4] >>  8) & 0xFF);
			byte b = (byte)((values[i / 4] >>  0) & 0xFF);
			byte a = (byte)((values[i / 4] >> 24) & 0xFF);
	
			bytes[i + 0] = r;
			bytes[i + 1] = g;
			bytes[i + 2] = b;
			bytes[i + 3] = a;
		}
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				for (int i = 0; i < 4; i++)
				{
					pixels[((x + x2) + (y + y2) * this.width) * 4 + i] = bytes[(x2 + y2 * height) * 4 + i];
				}
			}
		}
		
		ByteBuffer buf = BufferUtils.createByteBuffer(width * height * 4);
		
		buf.put(bytes);
		
		buf.position(0);
		
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	}
	
	public byte[] getPixelBytes(int x, int y)
	{
		byte data[] = new byte[4];
		
		for (int i = 0; i < 4; i++)
		{
			data[i] = pixels[(x + y * width) * 4];
		}
		
		return data;
	}
	
	public byte[] getPixelsBytes(int x, int y, int width, int height)
	{
		byte data[] = new byte[width * height * 4];
		
//		byteBuffer.position((x + y * width) * 4);
//			
//		for (int i = 0; i < height; i++)
//		{
//			byteBuffer.get(data, i * width * 4, width * 4);
//		}
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				byte d[] = getPixelBytes(x2 + x, y2 + y);
				
				for (int i = 0; i < 4; i++)
				{
					data[(x2 + y2 * width) * 4 + i] = d[i];
				}
			}
		}
		
		return data;
	}
	
	public void setPixelBytes(int x, int y, byte values[])
	{
		bind();
		
		ByteBuffer buf = BufferUtils.createByteBuffer(4);
		
		buf.put(values);
		
		buf.position(0);
		
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	}
	
	public void setPixelsBytes(int x, int y, int width, int height, byte values[])
	{
		bind();
		
		for (int y2 = 0; y2 < height; y2++)
		{
			for (int x2 = 0; x2 < width; x2++)
			{
				for (int i = 0; i < 4; i++)
				{
					pixels[((x + x2) + (y + y2) * this.width) * 4 + i] = values[(x2 + y2 * height) * 4 + i];
				}
			}
		}
		
		ByteBuffer buf = BufferUtils.createByteBuffer(width * height * 4);
		
		buf.put(values);
		
		buf.position(0);
		
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, x, y, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
	}
	
	/**
	 * 
	 * 
	 * @param fold
	 * @return
	 */
	private int get2Fold(int fold)
	{
		int ret = 2;
		
		while (ret < fold)
		{
			ret *= 2;
		}
		
		return ret;
	}
	
	/**
	 * 
	 */
	private void genTexDimensions()
	{
		int pow = 2;
		
		while (pow < width)
		{
			pow *= 2;
		}
		
		texWid = (float)width / pow;
		
		pow = 2;
		
		while (pow < height)
		{
			pow *= 2;
		}
		
		texHei = (float)height / pow;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public float[] getImageOffsets()
	{
		float offsets[] = new float[4];
		
		float w = 1;//texWid;
		float h = 1;//texHei;
		
		offsets[0] = 0;
		offsets[1] = 0;
		offsets[2] = w;
		offsets[3] = h;
		
		return offsets;
	}
	
	/**
	 * 
	 */
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * 
	 */
	public static void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}