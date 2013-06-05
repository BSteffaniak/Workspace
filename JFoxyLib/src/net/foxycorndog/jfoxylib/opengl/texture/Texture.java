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
	private int		id;
	private int		width, height;
	
	private float	texWid, texHei;
	
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
			id = loadTexture(image, recreate);
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
	private int loadTexture(BufferedImage image, boolean recreate)
	{
		// In which ID will we be storing this texture?
	    int id = newTextureId();
	    
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
	    
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	    
	    // Set all of our texture parameters:
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR_MIPMAP_NEAREST);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_S,     GLES10.GL_REPEAT);
//	    GLES10.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_WRAP_T,     GLES10.GL_REPEAT);
	    
//	    ByteBuffer bb = extract(bmp);
	    
//	    GLES10.glTexImage2D(GLES10.GL_TEXTURE_2D, 0, GLES10.GL_RGBA, width, height, 0, GLES10.GL_RGBA, GLES10.GL_UNSIGNED_BYTE, bb);
	    
	    if (recreate)
	    {
//	    WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 4, null);
//	    ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
	    
//		    BufferedImage img = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
		    BufferedImage img = new BufferedImage(width, height, BufferedImage.BITMASK);
	    	
		    Graphics g = img.getGraphics();
		    g.setColor(new Color(0, 0, 0, 0));
		    g.fillRect(0, 0, width, height);
		    g.drawImage(image, 0, 0, null);
		    g.dispose();
		    
		    image = img;
	    }
	    
	    int pixels[] = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
//	    byte pixelsb[] = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4); // 4 for RGBA, 3 for RGB
        buffer.order(ByteOrder.BIG_ENDIAN);
        
        IntBuffer ib = buffer.asIntBuffer();
        
        for(int y = height - 1; y >= 0; y--)
        {
            for(int x = 0; x < width; x++)
            {
                int pixel = pixels[x + y * width];
                
                int ri = ((pixel >> 16) & 0xFF);
                int gi = ((pixel >> 8)  & 0xFF);
                int bi = ((pixel >> 0)  & 0xFF);
                int ai = ((pixel >> 24) & 0xFF);
                
                ib.put(ri << 24 | gi << 16 | bi << 8 | ai);
            }
        }
        buffer.position(0);
//        buffer.rewind(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using 
        // whatever OpenGL method you want, for example:
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	    
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
	    
	    return id;
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