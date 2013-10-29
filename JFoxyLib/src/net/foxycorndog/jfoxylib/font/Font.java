package net.foxycorndog.jfoxylib.font;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Buffer;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.SpriteSheet;
import net.foxycorndog.jfoxylib.util.Bounds;
import net.foxycorndog.jfoxylib.util.Bounds2f;
import net.foxycorndog.jfoxylib.util.ImageData;
import net.foxycorndog.jfoxylib.util.Queue;
import net.foxycorndog.jfoxylib.util.ResourceLocator;

/**
 * Class used to load an image full of glyphs and bring them to
 * function as a Font that can render Strings according to the
 * given character set of the Font image.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:46:35 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:46:35 PM
 * @version	v0.2
 */
public class Font
{
	private 				int							cols, rows;
	private 				int							xOff, yOff;
	private 				int							width, height;
	private 				int							glyphWidth, glyphHeight;
	private 				int							ids;
	
	private 				float						letterMargin, lineOffset;
	
	private					ImageData					characterData;
	
	private 				SpriteSheet					characters;
	
	private 				int							idArray[][];
	
	private 				HashMap<Character, int[]>	charSequence;
	private		 			HashMap<String, FontText>	history;
	private					HashMap<Character, Bounds>	charBounds;
	
	private	static	final	ArrayList<Font>				fonts;
	
	private	static	final	Font						DEFAULT_FONT;
	
	public	static	final	int							LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 0, TOP = 2;
	
	static
	{
		fonts = new ArrayList<Font>();
		
		SpriteSheet sprites = null;
		ImageData   data    = null;
		
		try
		{
			BufferedImage image = ImageIO.read(new File(ResourceLocator.getProjectDirectory() + "res/images/fonts/font.png"));
			
			sprites = new SpriteSheet(image, 26, 4);
			data    = new ImageData(image);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		DEFAULT_FONT = new Font(sprites,
				new char[]
				{
					'A', 'B', 'C', 'D', 'E', 'F',  'G', 'H', 'I', 'J', 'K', 'L',  'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
					'a', 'b', 'c', 'd', 'e', 'f',  'g', 'h', 'i', 'j', 'k', 'l',  'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
					'0', '1', '2', '3', '4', '5',  '6', '7', '8', '9', '_', '-',  '+', '=', '~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
					'?', '>', '<', ';', ':', '\'', '"', '{', '}', '[', ']', '\\', '|', ',', '.', '/', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '
				}, data);
		
		DEFAULT_FONT.setLetterMargin(1);
		DEFAULT_FONT.setTrimBounds(true);
	}
	
	/**
	 * Create a Font from the image at the specified location. The Font
	 * image will be split up into the specified amount of columns and
	 * rows. Each of the characters will correspond with the given
	 * charSequence char array.
	 * 
	 * @param location The location of the Font image.
	 * @param cols The number of columns in the Font image.
	 * @param rows The number of rows in the Font image.
	 * @param charSequence The character array that corresponds with each
	 * 		Character in the Font image.
	 * @throws IOException Thrown if the Font image file was not found.
	 */
	public Font(String location, int cols, int rows, char charSequence[]) throws IOException
	{
		this(new SpriteSheet(location, cols, rows), charSequence, new ImageData(location));
	}
	
	/**
	 * Create a Font from the specified SpriteSheet. Each of the
	 * characters will correspond with the given charSequence char
	 * array.
	 * 
	 * @param sprites The SpriteSheet that holds all of the characters.
	 * 		It must be split into the correct divisions for each letter
	 * 		to work properly.
	 * @param charSequence The character array that corresponds with each
	 * 		Character in the Font image.
	 */
	public Font(SpriteSheet sprites, char charSequence[], ImageData data)
	{
		characters        = sprites;
		
		characterData     = data;
		
		this.cols         = characters.getNumCols();
		this.rows         = characters.getNumRows();
		
		this.width        = characters.getWidth();
		this.height       = characters.getHeight();
		
		this.glyphWidth   = width  / cols;
		this.glyphHeight  = height / rows;
		
		this.charSequence = new HashMap<Character, int[]>();
		this.history      = new HashMap<String, FontText>();
		
		this.idArray      = new int[100][];
		
		for (int y = 0; y < rows; y ++)
		{
			for (int x = 0; x < cols; x ++)
			{
				if (x + y * cols >= charSequence.length)
				{
					break;
				}
				
				char c = charSequence[x + y * cols];
				
				if (!this.charSequence.containsKey(c))
				{
					this.charSequence.put(c, new int[] { x, y });
				}
			}
		}
		
		lineOffset = 1;
		
		fonts.add(this);
	}
	
	/**
	 * Get whether or not the Bounds for the Font's Glyphs have been
	 * trimmed and prepared to fit together like a PBJ sandwich baby.
	 * 
	 * @return Whether or not the Bounds have been trimmed neatly.
	 */
	public boolean doesTrimBounds()
	{
		return charBounds != null;
	}
	
	/**
	 * Whether or not to trim the Glyphs Bounds so that they fit perfectly
	 * together in beautiful harmony. Yes, do it.
	 * 
	 * @param trim Whether or not to trim the Bounds.
	 */
	public void setTrimBounds(boolean trim)
	{
		if (trim)
		{
			charBounds = new HashMap<Character, Bounds>();
			
			Set<Character> chars = charSequence.keySet();
			
			Iterator<Character> i = chars.iterator();
			
			while (i.hasNext())
			{
				Character c      = i.next();
				
				Bounds  bounds   = new Bounds();
				
				if (c == ' ')
				{
					bounds.setWidth(glyphWidth);
//					bounds.setHeight(glyphHeight);
					bounds.setY(glyphHeight);
					
					charBounds.put(c, bounds);
					
					continue;
				}
				
				int x = charSequence.get(c)[0];
				int y = characters.getNumRows() - charSequence.get(c)[1] - 1;
				
				x    *= glyphWidth;
				y    *= glyphHeight;
				
				byte pixels[]  = characterData.getDataBytes(x, y, glyphWidth, glyphHeight);
				
				boolean begin  = true;
				
				int     left   = 0;
				int     right  = 0;
				int     top    = 0;
				int     bottom = 0;
				
				for (int x2 = 0; x2 < glyphWidth; x2++)
				{
					int num = 0;
					
					for (int y2 = 0; y2 < glyphHeight; y2++)
					{
						if (pixels[(x2 + y2 * glyphWidth) * 4 + 3] != 0)
						{
							begin = false;
							
							break;
						}
						
						num++;
					}
					
					if (num >= glyphHeight)
					{
						if (begin)
						{
							left++;
						}
						else
						{
							break;
						}
					}
				}
				
				begin = true;
				
				for (int x2 = glyphWidth - 1; x2 > left; x2--)
				{
					int num = 0;
					
					for (int y2 = 0; y2 < glyphHeight; y2++)
					{
						// If the alpha channel is not 0
						if (pixels[(x2 + y2 * glyphWidth) * 4 + 3] != 0)
						{
							begin = false;
							
							break;
						}
						
						num++;
					}
					
					if (num >= glyphHeight)
					{
						if (begin)
						{
							right++;
						}
						else
						{
							break;
						}
					}
				}
				
				begin = true;
				
				for (int y2 = 0; y2 < glyphHeight; y2++)
				{
					int num = 0;

					for (int x2 = 0; x2 < glyphWidth; x2++)
					{
						if (pixels[(x2 + y2 * glyphWidth) * 4 + 3] != 0)
						{
							begin = false;
							
							break;
						}
						
						num++;
					}
					
					if (num >= glyphWidth)
					{
						if (begin)
						{
							bottom++;
						}
						else
						{
							break;
						}
					}
				}
				
				begin = true;
				
				for (int y2 = glyphHeight - 1; y2 > bottom; y2--)
				{
					int num = 0;
					
					for (int x2 = 0; x2 < glyphWidth; x2++)
					{
						if (pixels[(x2 + y2 * glyphWidth) * 4 + 3] != 0)
						{
							begin = false;
							
							break;
						}
						
						num++;
					}
					
					if (num >= glyphWidth)
					{
						if (begin)
						{
							top++;
						}
						else
						{
							break;
						}
					}
				}
				
				bounds.setX(left);
				bounds.setY(bottom);
				bounds.setWidth(glyphWidth - left - right);
				bounds.setHeight(glyphHeight - bottom - top);
				
				charBounds.put(c, bounds);
			}
		}
		else
		{
			charBounds = null;
		}
	}
	
	/**
	 * Get the Bounds that the Font render will take up with the specified
	 * text, location, scale, alignment, and panel given to it.
	 * 
	 * @param text The text to get the Bounds with.
	 * @param x The horizontal location that the text would be rendered
	 * 		to.
	 * @param y The vertical location that the text would be rendered
	 * 		to.
	 * @param scale The scale that the Font would be rendered by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel that the Font will be rendered inside of.
	 * 		Passing a null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text will take up after rendered.
	 */
	public Bounds2f getRenderBounds(String text, float x, float y, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		Bounds2f bounds = new Bounds2f();
		
		float glScale[] = GL.getAmountScaled();
		
		float scaleX    = glScale[0];
		float scaleY    = glScale[1];
		
		float width     = getWidth(text);
		float height    = getHeight(text);
		
		int   numLines  = getNumLines(text);
		
		if (horizontalAlignment == CENTER)
		{
			if (panel != null)
			{
				x += (Frame.getWidth() / scaleX) / 2;
			}
			else
			{
				x += (Frame.getWidth() / scaleX) / 2;
			}
			
			x -= (width * (scale / 2));
		}
		else if (horizontalAlignment == RIGHT)
		{
			if (panel != null)
			{
				x += panel.getScaledWidth() / scaleX;
			}
			else
			{
				x += Frame.getWidth() / scaleX;
			}
			
			x -= width * scale;
		}
		if (verticalAlignment == CENTER)
		{
			if (panel != null)
			{
				y += (panel.getScaledHeight() / scaleY) / 2;
			}
			else
			{
				y += (Frame.getHeight() / scaleY) / 2;
			}
			
			y -= (glyphHeight * numLines) * (scale / 2);
		}
		else if (verticalAlignment == TOP)
		{
			if (panel != null)
			{
				y += panel.getScaledHeight() / scaleY;
			}
			else
			{
				y += Frame.getHeight() / scaleY;
			}
			
			y -= glyphHeight * scale;
		}
		
		bounds.setX(x * scaleX);
		bounds.setY(y * scaleY);
		bounds.setWidth(width * scaleX);
		bounds.setHeight(height * scaleY);
		
		return bounds;
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, Panel panel)
	{
		return render(text, x, y, z, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, Panel panel, boolean saveHistory)
	{
		return render(text, x, y, z, 1, panel, saveHistory);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, Panel panel)
	{
		return render(text, x, y, z, scale, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, Panel panel, boolean saveHistory)
	{
		return render(text, x, y, z, scale, LEFT, BOTTOM, panel, saveHistory);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return render(text, x, y, z, horizontalAlignment, verticalAlignment, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, int horizontalAlignment, int verticalAlignment, Panel panel, boolean saveHistory)
	{
		return render(text, x, y, z, 1, horizontalAlignment, verticalAlignment, panel, saveHistory);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return render(text, x, y, z, scale, horizontalAlignment, verticalAlignment, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel, boolean saveHistory)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, panel, saveHistory);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	private Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	private Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel, boolean saveHistory)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, null, null, panel, saveHistory);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel
	 * using vertex buffers.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param vertices The Buffer that holds the vertices used for
	 * 		rendering the Font.
	 * @param textures The Buffer that holds the Textures used for
	 * 		each of the letters in the text.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @return The Bounds that the text was rendered inside of.
	 */
	private Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Buffer vertices, Buffer textures, Panel panel)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, vertices, textures, panel, true);
	}
	
	/**
	 * Render the text at the specified location to the specified Panel
	 * using vertex buffers.
	 * 
	 * @param text The text to render to the Panel.
	 * @param x The horizontal location to render the text.
	 * @param y The vertical location to render the text.
	 * @param z The oblique location to render the text.
	 * @param scale The scale to render the text by.
	 * @param horizontalAlignment The horizontal alignment that the text
	 * 		will be aligned by.
	 * @param verticalAlignment The vertical alignment that the text will
	 * 		be aligned by.
	 * @param vertices The Buffer that holds the vertices used for
	 * 		rendering the Font.
	 * @param textures The Buffer that holds the Textures used for
	 * 		each of the letters in the text.
	 * @param panel The Panel to render the text inside of. Passing a
	 * 		null value here will result in the Panel being
	 * 		treated of as the Frame.
	 * @param saveHistory Whether or not to save the specified text to the
	 * 		history buffer.
	 * @return The Bounds that the text was rendered inside of.
	 */
	private Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Buffer vertices, Buffer textures, Panel panel, boolean saveHistory)
	{
		if (text == null || text.length() <= 0)
		{
			return new Bounds2f(0, 0, 0, 0);
		}
		
		int      vId       = 0;
		int      tId       = 0;
		int      viId      = 0;
		int      size      = 0;
		
		float    glScale[] = GL.getAmountScaled();
		
		float    scaleX    = glScale[0];
		float    scaleY    = glScale[1];
		
		Bundle   bundle    = null;
		
		char     chars[]   = text.toCharArray();
		
		Bounds2f bounds    = getRenderBounds(text, x, y, scale, horizontalAlignment, verticalAlignment, panel);
		
//		x = Math.round(bounds.getX() / scaleX);
//		y = Math.round(bounds.getY() / scaleY);
		
		x = bounds.getX() / scaleX;
		y = bounds.getY() / scaleY;
		
//		x /= scaleX;
//		y /= scaleY;
		
		if (history.containsKey(text))
		{
			vId  = history.get(text).vertexBufferId;
			tId  = history.get(text).texturesBufferId;
			viId = history.get(text).vertexIndicesBuffer;
			size = history.get(text).size;
		}
		else
		{
			if (vertices == null && textures == null)
 			{
				vertices = new Buffer(text.length() * 3 * 2 * 2);
				textures = new Buffer(text.length() * 3 * 2 * 2);
			}
			else if (vertices != null || textures != null)
			{
				throw new IllegalArgumentException("Vertices and textures must either both be null or both have a value.");
			}
			
			size = vertices.getSize();
			
//			for (int i = 0; i < chars.length; i ++)
//			{
//				if (chars[i] == '\n')
//				{
//					renderVertexBuffer(text.substring(0, i), x, y /*+ ((glyphHeight + 1) / 2) * scale*/ , z, scale, horizontalAlignment, verticalAlignment, vertices, textures, panel);
//					renderVertexBuffer(text.substring(i + 1), x, y - (glyphHeight + 1)/*((glyphHeight + 1) / 2) * scale*/, z, scale, horizontalAlignment, verticalAlignment, vertices, textures, panel);
//					
//					addToHistory(text, vId, tId, viId, size, vertices, textures, null);
//					
//					return;
//				}
//			}
			
			int      line      = 0;
			int      lines     = getNumLines(text);
			
			Bounds2f heights[] = new Bounds2f[lines];
			float    widths[]  = new float[lines];
			
			float    yOffset   = 0;
			float    xOffset   = 0;
			
			if (lines > 1)
			{
				if (verticalAlignment == BOTTOM)
				{
					String lastLines = text.substring(text.indexOf('\n') + 1);
					
					yOffset = getHeight(lastLines);
				}
				else if (verticalAlignment == TOP)
				{
					yOffset -= lineOffset;
				}
			}
			
			float width = getWidth(text);
			
			if (lines > 1)
			{
				widths = getLineWidths(text);
				
				if (horizontalAlignment == RIGHT)
				{
					xOffset += width - widths[line];
				}
				else if (horizontalAlignment == CENTER)
				{
					xOffset += (width - widths[line]) / 2;
				}
			}
			
			if (charBounds != null)
			{
				heights = getLineHeights(text);
				
				if (verticalAlignment == BOTTOM)
				{
					yOffset -= glyphHeight - heights[line].getHeight();
				}
				
				if (lines > 1)
				{
					yOffset += lineOffset;
				}
			}
			
			for (int i = 0; i < chars.length; i ++)
			{
				if (chars[i] == '\n')
				{
					line++;
					
					yOffset -= glyphHeight + lineOffset;
					xOffset  = 0;

					if (charBounds != null)
					{
						float lineHeight = heights[line].getHeight() + heights[line].getY();
						
						if (lineHeight > 0)
						{
							yOffset += glyphHeight - lineHeight;
							yOffset += heights[line - 1].getY();
						}
					}
					
					if (horizontalAlignment == RIGHT)
					{
						xOffset += width - widths[line];
					}
					else if (horizontalAlignment == CENTER)
					{
						xOffset += (width - widths[line]) / 2;
					}
				}
				else
				{
					int   charX     = 0;
					int   charY     = 0;
					
					float offsets[] = null;
					
					try
					{
						if (!charSequence.containsKey(chars[i]))	
						{
							continue;
						}
						
						charX   = charSequence.get(chars[i])[0];
						charY   = charSequence.get(chars[i])[1];
						
						offsets = characters.getImageOffsets(charX, charY, 1, 1);
					}
					catch (NullPointerException e)
					{
//						if (chars[i] == ' ')
//						{
//							
//						}
//						else
//						{
//							addToHistory(text, vId, tId, viId, size);
//							
//							return null;
//						}
						
						chars[i] = '?';
						
						charX    = charSequence.get(chars[i])[0];
						charY    = charSequence.get(chars[i])[1];
						
						offsets  = characters.getImageOffsets(charX, charY, 1, 1);
					}
					
					int cleft  = 0;
					int cwidth = 0;
					
					if (charBounds != null)
					{
						cleft    = charBounds.get(chars[i]).getX();
						cwidth   = charBounds.get(chars[i]).getWidth();
						
						xOffset -= charBounds.get(chars[i]).getX();
					}
					
					vertices.beginEditing();
					{
						vertices.setData(i * 3 * 2 * 2, GL.genRectVerts(xOffset, yOffset, glyphWidth, glyphHeight));
					}
					vertices.endEditing();
					
					textures.beginEditing();
					{
						textures.setData(i * 3 * 2 * 2, GL.genRectTextures(offsets));
					}
					textures.endEditing();
					
					if (charBounds != null)
					{
						xOffset += cwidth + cleft;
					}
					else
					{
						xOffset += glyphWidth;
					}
					
					xOffset += letterMargin;
				}
			}
			
//			vertices.genIndices(GL.QUADS, null);
			
			vId  = vertices.getId();
			tId  = textures.getId();
//			viId = vertices.getIndicesId(0);
			
			if (saveHistory)
			{
				addToHistory(text, vId, tId, viId, size);
			}
		}
		
		bundle = new Bundle(vId, tId, 0, size / 2, 2);
		
		GL.pushMatrix();
		{
			GL.translate(x, y, z);
			GL.scale(scale, scale, 1);
			
			bundle.render(GL.TRIANGLES, characters);
		}
		GL.popMatrix();
		
		if (history.containsKey(text))
		{
			history.get(text).lastRendered = System.currentTimeMillis();
		}
		
		return bounds;
	}
	
	/**
	 * Get whether the specified char is a valid character contained in
	 * the character set given to the Font during construction.
	 * 
	 * @param c The character to check if it is a valid character or not.
	 * @return Whether the character is valid or not.
	 */
	public boolean containsChar(char c)
	{
		return charSequence.containsKey(c);
	}
	
	/**
	 * Get the amount of pixels that there will be between a new-line in
	 * a String of text rendered.
	 * 
	 * @return The amount of pixels that there will be between a new-line
	 * 		in a String of text rendered.
	 */
	public float getLineOffset()
	{
		return lineOffset;
	}
	
	/**
	 * Set the amount of pixels that there will be between a new-line in
	 * a String of text rendered.
	 * 
	 * @param lineOffset The amount of pixels that there will be between
	 * 		a new-line in a String of text rendered.
	 */
	public void setLineOffset(float lineOffset)
	{
		this.lineOffset = lineOffset;
	}
	
	/**
	 * Get the number of lines that the specified String of text takes
	 * up on the Display.
	 * 
	 * @param text The String of text to get the number of lines from.
	 * @return The number of lines in the String of text.
	 */
	public int getNumLines(String text)
	{
		if (text.length() <= 0)
		{
			return 0;
		}
		
		int   numLines  = 1;
		
		for (int i = 0; i < text.length(); i++)
		{
			if (text.charAt(i) == '\n')
			{
				numLines++;
			}
		}
		
		return numLines;
	}
	
//	/**
//	 * 
//	 * 
//	 * @param text
//	 * @param x
//	 * @param y
//	 * @param z
//	 * @param scale
//	 * @param horizontalAlignment
//	 * @param verticalAlignment
//	 * @param panel
//	 */
//	public void renderDisplayList(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
//	{
//		characters.bind();
//		
//		int id = 0;
//		
//		if (history.containsKey(text) && history.get(text)[1] == 0)
//		{
//			if (horizontalAlignment == CENTER)
//			{
//				if (panel != null)
//				{
//					x += panel.getWidth() / 2;
//				}
//				else
//				{
//					x += Frame.getWidth() / 2;
//				}
//				
//				x -= text.length() * scale * glyphWidth / 2;
//			}
//			else if (horizontalAlignment == RIGHT)
//			{
//				if (panel != null)
//				{
//					x += panel.getWidth();
//				}
//				else
//				{
//					x += Frame.getWidth();
//				}
//				
//				x -= text.length() * scale * glyphWidth;
//			}
//			if (verticalAlignment == CENTER)
//			{
//				if (panel != null)
//				{
//					y += panel.getHeight() / 2;
//				}
//				else
//				{
//					x += Frame.getHeight() / 2;
//				}
//				
//				y -= glyphHeight * scale / 2;
//			}
//			else if (verticalAlignment == TOP)
//			{
//				if (panel != null)
//				{
//					y += panel.getHeight();
//				}
//				else
//				{
//					x += Frame.getHeight();
//				}
//				
//				y -= glyphHeight * scale;
//			}
//			
//			id = history.get(text)[2];
//		}
//		else
//		{
//			id = GL11.glGenLists(1);
//			
//			GL11.glNewList(id, GL11.GL_COMPILE);
//			{
//				char chars[] = text.toCharArray();
//				
//				for (int i = 0; i < chars.length; i ++)
//				{
//					if (chars[i] == '\n')
//					{
//						renderDisplayList(text.substring(0, i), x, y /*+ ((glyphHeight + 1) / 2) * scale*/ , z, scale, horizontalAlignment, verticalAlignment, panel);
//						renderDisplayList(text.substring(i + 1), x, y - (glyphHeight + 1) * scale/*((glyphHeight + 1) / 2) * scale*/, z, scale, horizontalAlignment, verticalAlignment, panel);
//						
//						GL11.glEndList();
//						addToHistory(text, id);
//	//					y += ((glyphHeight + 1) / 2) * scale;
//						return;
//					}
//				}
//				
//				if (horizontalAlignment == CENTER)
//				{
//					x += panel.getWidth() / 2;
//					x -= text.length() * scale * glyphWidth / 2;
//				}
//				else if (horizontalAlignment == RIGHT)
//				{
//					x += panel.getWidth();
//					x -= text.length() * scale * glyphWidth;
//				}
//				if (verticalAlignment == CENTER)
//				{
//					y += panel.getHeight() / 2;
//					y -= glyphHeight * scale / 2;
//				}
//				else if (verticalAlignment == TOP)
//				{
//					y += panel.getHeight();
//					y -= glyphHeight * scale;
//				}
//				
//				for (int i = 0; i < chars.length; i ++)
//				{
//					try
//					{
//						int charX       = charSequence.get(chars[i])[0];
//						int charY       = charSequence.get(chars[i])[1];
//						
//						float offsets[] = characters.getImageOffsets(charX, charY, 1, 1);
//					
//						GL.pushMatrix();
//						{
////							GL.translatef(x, y, z);
////							GL.scalef(scale, scale, 1);
//							
//							GL11.glBegin(GL.QUADS);
//							{
//			//					Correct View
//								
//								GL11.glTexCoord2f(offsets[2], offsets[1]);
//								GL11.glVertex2f(i * glyphWidth + glyphWidth + (letterMargin * i), 0);
//								
//								GL11.glTexCoord2f(offsets[2], offsets[3]);
//								GL11.glVertex2f(i * glyphWidth + glyphWidth + (letterMargin * i), glyphHeight);
//								
//								GL11.glTexCoord2f(offsets[0], offsets[3]);
//								GL11.glVertex2f(i * glyphWidth + (letterMargin * i), glyphHeight);
//								
//								GL11.glTexCoord2f(offsets[0], offsets[1]);
//								GL11.glVertex2f(i * glyphWidth + (letterMargin * i), 0);
//								
//			//					Reversed View
//			//					
//			//					GL.glTexCoord2f(offsets[2], offsets[1]);
//			//					GL.glVertex2f(- i * glyphWidth, 0);
//			//					
//			//					GL.glTexCoord2f(offsets[2], offsets[3]);
//			//					GL.glVertex2f(- i * glyphWidth, glyphHeight);
//			//					
//			//					GL.glTexCoord2f(offsets[0], offsets[3]);
//			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, glyphHeight);
//			//					
//			//					GL.glTexCoord2f(offsets[0], offsets[1]);
//			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, 0);
//							}
//							GL11.glEnd();
//						}
//						GL.popMatrix();
//					}
//					catch (NullPointerException e)
//					{
//						if (chars[i] == ' ')
//						{
//							
//						}
//						else
//						{
//							GL11.glEndList();
//							
//							addToHistory(text, id);
//							
//							return;
//						}
//					}
//				}
//			}
//			GL11.glEndList();
//			
//			addToHistory(text, id);
//		}
//			
//		GL.pushMatrix();
//		{
//			GL.translate(x, y, z);
//			GL.scale(scale, scale, 1);
//			
//			GL11.glCallList(id);
//		}
//		GL.popMatrix();
//	}
	
//	/**
//	 * 
//	 * 
//	 * @param text
//	 * @param id
//	 */
//	private void addToHistory(String text, int id)
//	{
//		ids = ids + 1 < 100 ? ids + 1 : 0;
//		
////		if (idArray[ids] != null)
////		{
////			GL11.glDeleteLists(idArray[ids][0], 1);
////		}
////		
////		idArray[ids] = new int[] { id };
//		
//		history.put(text, new int[] { ids, 0, id });
//	}

	/**
	 * Add the String of text that was previously rendered to the history
	 * of rendered Strings. This is used in order to save time if a String
	 * has already been previously rendered. Instead of creating the
	 * vertex buffer all over again, just reuse the old one.
	 * 
	 * @param text The String of text to add to the history.
	 * @param vId The vertex buffer's id.
	 * @param tId The texture buffer's id.
	 * @param viId The vertex indices buffer's id.
	 * @param size the size of the buffer that is being added to the
	 * 		history.
	 */
	private void addToHistory(String text, int vId, int tId, int viId, int size)
	{
		ids = ids + 1;// < 100 ? ids + 1 : 0;
		
		history.put(text, new FontText(text, vId, tId, viId, size));
	}
	
	/**
	 * Get the amount of pixels that the specified String would take up
	 * horizontally.
	 * 
	 * @param text The String of text to get the width of.
	 * @return The amount of pixels that the specified String would take
	 * 		up horizontally.
	 */
	public float getWidth(String text)
	{
		float width  = 0;
		
		float max    = 0;
		
		int   cwidth = 0;
		
		for (int i = 0; i < text.length(); i ++)
		{
			char c = text.charAt(i);
			
			if (c == '\n')
			{
				if (i > 0 && max == width)
				{
					max -= letterMargin;
				}
				
				width = 0;
			}
			else
			{
				if (charBounds != null)
				{
					cwidth = charBounds.get(c).getWidth();
					
					width += cwidth;
				}
				else
				{
					width += glyphWidth;
				}
				
				width += letterMargin;
			
				if (width > max)
				{
					max = width;
				}
			}
		}
		
		if (max > 0 && max == width)
		{
			max -= letterMargin;
		}
		
		return max;
	}
	
	public float[] getLineWidths(String text)
	{
		float widths[] = new float[getNumLines(text)];
		
		int   line  = 0;
		
		float width = 0;
		
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			if (c == '\n')
			{
				if (width > 0)
				{
					width -= letterMargin;
				}
				
				widths[line] = width;
				
				width = 0;
				
				line++;
			}
			else
			{
				int w = charBounds.get(c).getWidth();
				
				width += w + letterMargin;
			}
		}
		
		if (width > 0)
		{
			width -= letterMargin;
		}
		
		widths[line] = width;
		
		return widths;
	}
	
	/**
	 * Get the amount of pixels that the specified String would take up
	 * vertically.
	 * 
	 * @param text The String of text to get the height of.
	 * @return The amount of pixels that the specified String would take
	 * 		up vertically.
	 */
	public float getHeight(String text)
	{
		float height = 0;
		
		if (charBounds == null)
		{
			int numLines = getNumLines(text);
			
			if (numLines > 0)
			{
				height = (glyphHeight + lineOffset) * numLines - lineOffset;
			}
		}
		else
		{
			Bounds2f heights[] = getLineHeights(text);
			
			for (int i = 0; i < heights.length; i++)
			{
				height += heights[i].getHeight() + lineOffset;
			}
			
			if (heights.length > 0)
			{
				height -= lineOffset;
			}
		}
		
		return height;
	}
	
	/**
	 * Get the height of each line in order of an array.
	 * 
	 * @param text The text to get the height of each line from.
	 * @return An array containing float values that represent the
	 * 		height of each line of the text.
	 */
	private Bounds2f[] getLineHeights(String text)
	{
		Bounds2f heights[] = new Bounds2f[getNumLines(text)];
		
		int line = 0;
		int min  = glyphHeight;
		int max  = 0;
		
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			if (c == '\n')
			{
				heights[line] = new Bounds2f();
				
				if (min == glyphHeight)
				{
					min = 0;
				}
				
				heights[line].setY(min);
				heights[line].setHeight(max - min);
				
				max = 0;
				min = glyphHeight;
				
				line++;
			}
			else
			{
				int y = charBounds.get(c).getY();
				int h = charBounds.get(c).getHeight();
				
				if (y < min)
				{
					min = y;
				}
				if (y + h > max)
				{
					max = y + h;
				}
			}
		}
		
		heights[line] = new Bounds2f();
		
		if (min == glyphHeight)
		{
			min = 0;
		}
		
		heights[line].setY(min);
		heights[line].setHeight(max - min);
		
		return heights;
	}
	
	/**
	 * Get the width that each of the Font's Glyphs take up.
	 * 
	 * @return The width that each of the Font's Glyphs take up.
	 */
	public int getGlyphWidth()
	{
		return glyphWidth;
	}

	/**
	 * Get the height that each of the Font's Glyphs take up.
	 * 
	 * @return The height that each of the Font's Glyphs take up.
	 */
	public int getGlyphHeight()
	{
		return glyphHeight;
	}
	
	/**
	 * Get the amount of pixels that the letters will be spaced by when
	 * rendered.
	 * 
	 * @return The amount of pixels that the letters will be spaced by when
	 * 		rendered.
	 */
	public float getLetterMargin()
	{
		return letterMargin;
	}
	
	/**
	 * Set the amount of pixels that the letters will be spaced by when
	 * rendered.
	 * 
	 * @param letterMargin The amount of pixels that the letters will be
	 * 		spaced by when rendered.
	 */
	public void setLetterMargin(float letterMargin)
	{
		this.letterMargin = letterMargin;
	}
	
	/**
	 * Get the default Font that is used in this library.
	 * 
	 * @return The default Font that is used in this library.
	 */
	public static Font getDefaultFont()
	{
		return DEFAULT_FONT;
	}
	
	/**
	 * Updates the Font class. Removes any old Strings in the history
	 * that need to be removed.
	 */
	public static void update()
	{
		long current = System.currentTimeMillis();
		
		for (int i = 0; i < fonts.size(); i++)
		{
			Font font = fonts.get(i);
			
			Collection<FontText> values = font.history.values();
			
			Queue<String>	remove = new Queue<String>();
			
			Iterator<FontText> it = values.iterator();
			
			while (it.hasNext())
			{
				FontText ft = it.next();
				
				if (current - ft.lastRendered >= 10000)
				{
					remove.enqueue(ft.text);
				}
			}
			
			while (!remove.isEmpty())
			{
				String text = remove.dequeue();
				
				font.history.remove(text);
			}
		}
	}
	
	/**
	 * Class used to store Font text in the history for later use
	 * if needed.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Jun 28, 2013 at 11:17:13 PM
	 * @since	v0.2
	 * @version	Jun 28, 2013 at 11:17:13 PM
	 * @version	v0.2
	 */
	private class FontText
	{
		private	int		vertexBufferId, texturesBufferId, vertexIndicesBuffer;
		private	int		size;
		private	int		id;
		
		private	long	lastRendered;
		
		private	String	text;
		
		/**
		 * Create an instance of FontText to keep track of a String of
		 * text that was recently added to the history.
		 * 
		 * @param text The String of text to add to the history.
		 * @param vId The vertex buffer's id.
		 * @param tId The texture buffer's id.
		 * @param viId The vertex indices buffer's id.
		 * @param size the size of the buffer that is being added to the
		 * 		history.
		 */
		public FontText(String text, int vId, int tId, int viId, int size)
		{
			this.id                  = ids;
			
			this.text                = text;
			
			this.vertexBufferId      = vId;
			this.texturesBufferId    = tId;
			this.vertexIndicesBuffer = viId;
			
			this.size                = size;
		}
	}
}