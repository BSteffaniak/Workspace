package net.foxycorndog.jfoxylib.font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Panel;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Buffer;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.SpriteSheet;
import net.foxycorndog.jfoxylib.util.Bounds2f;

import org.lwjgl.opengl.GL11;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:46:35 PM
 * @since	v0.2
 * @version	Apr 26, 2013 at 10:46:35 PM
 * @version	v0.2
 */
public class Font
{
	private int cols, rows;
	private int yOff, xOff;
	private int width, height;
	private int glyphWidth, glyphHeight;
	private int ids;
	private int letterMargin;
	
	private SpriteSheet characters;
	
	private int idArray[][];
	
	private HashMap<Character, int[]> charSequence;
	private HashMap<String, int[]>  history;
	
	public static final int	LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 0, TOP = 2;
	
	/**
	 * 
	 * 
	 * @param location
	 * @param cols
	 * @param rows
	 * @param charSequence
	 */
	public Font(String location, int cols, int rows, char charSequence[])
	{
		this.cols         = cols;
		this.rows         = rows;
		
		try
		{
			characters        = new SpriteSheet(location, cols, rows);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		this.width        = characters.getWidth();
		this.height       = characters.getHeight();
		
		this.glyphWidth   = width / cols;
		this.glyphHeight  = height / rows;
		
		this.charSequence = new HashMap<Character, int[]>();
		this.history      = new HashMap<String, int[]>();
		
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
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param scale
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param panel
	 * @return
	 */
	public Bounds2f getRenderBounds(String text, float x, float y, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		Bounds2f bounds = new Bounds2f();
		
		float glScale[] = GL.getAmountScaled();
		
		float scaleX = glScale[0];
		float scaleY = glScale[1];
		
		char chars[]    = text.toCharArray();
		
		String lines[]  = null;
		
		int longestLine = 0;
		int count       = 0;
		
		ArrayList<String> tempLines = new ArrayList<String>();
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] != '\n')
			{
				count++;
				
				longestLine = count > longestLine ? count : longestLine;
				
				builder.append(chars[i]);
			}
			else
			{
				count = 0;
				
				tempLines.add(builder.toString());
			}
		}
		
		lines = tempLines.toArray(new String[0]);
		
		int numLines = lines.length + 1;
		
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
			
			x -= ((longestLine * glyphWidth) * (scale / 2));
		}
		else if (horizontalAlignment == RIGHT)
		{
			if (panel != null)
			{
				x += panel.getWidth() / scaleX;
			}
			else
			{
				x += Frame.getWidth() / scaleX;
			}
			
			x -= longestLine * glyphWidth * scale;
		}
		if (verticalAlignment == CENTER)
		{
			if (panel != null)
			{
				y += (panel.getHeight() / scaleY) / 2;
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
				y += panel.getHeight() / scaleY;
			}
			else
			{
				y += Frame.getHeight() / scaleY;
			}
			
			y -= glyphHeight * scale;
		}
		
		bounds.setX(x * scaleX);
		bounds.setY(y * scaleY);
		bounds.setWidth(longestLine * glyphWidth * scaleX);
		bounds.setHeight(numLines * (glyphHeight + 1) * scaleY);
		
		return bounds;
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param panel
	 * @return
	 */
	public Bounds2f render(String text, float x, float y, float z, Panel panel)
	{
		return render(text, x, y, z, 1, panel);
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param scale
	 * @param panel
	 * @return
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, Panel panel)
	{
		return render(text, x, y, z, scale, LEFT, BOTTOM, panel);
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param panel
	 * @return
	 */
	public Bounds2f render(String text, float x, float y, float z, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return render(text, x, y, z, 1, horizontalAlignment, verticalAlignment, panel);
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param scale
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param panel
	 * @return
	 */
	public Bounds2f render(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, panel);
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param scale
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param panel
	 * @return
	 */
	public Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, null, null, panel);
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param scale
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param vertices
	 * @param textures
	 * @param panel
	 * @return
	 */
	private Bounds2f renderVertexBuffer(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Buffer vertices, Buffer textures, Panel panel)
	{
		int vId  = 0;
		int tId  = 0;
		int viId = 0;
		int size = 0;
		
		float glScale[] = GL.getAmountScaled();
		
		float scaleX = glScale[0];
		float scaleY = glScale[1];
		
		Bundle bundle = null;
		
		char chars[]    = text.toCharArray();
		
		Bounds2f bounds = getRenderBounds(text, x, y, scale, horizontalAlignment, verticalAlignment, panel);
		
		x = bounds.getX() / scaleX;
		y = bounds.getY() / scaleY;
		
//		x /= scaleX;
//		y /= scaleY;
		
		if (history.containsKey(text) && history.get(text)[1] == 1)
		{
			vId  = history.get(text)[2];
			tId  = history.get(text)[3];
			viId = history.get(text)[4];
			size = history.get(text)[5];
		}
		else
		{
			if (vertices == null && textures == null)
 			{
				vertices = new Buffer(text.length() * 4 * 2);
				textures = new Buffer(text.length() * 4 * 2);
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
			
			float yOffset = 0;
			float xOffset = 0;
			
			for (int i = 0; i < chars.length; i ++)
			{
				if (chars[i] != '\n')
				{
					try
					{
						int charX       = charSequence.get(chars[i])[0];
						int charY       = charSequence.get(chars[i])[1];
						
						float offsets[] = characters.getImageOffsets(charX, charY, 1, 1);
						
						vertices.beginEditing();
						{
							vertices.setData(i * 4 * 2, GL.genRectVerts(xOffset, yOffset, glyphWidth, glyphHeight));
						}
						vertices.endEditing();
						
						textures.beginEditing();
						{
							textures.setData(i * 4 * 2, GL.genRectTextures(offsets));
						}
						textures.endEditing();
						
						xOffset += glyphWidth + letterMargin;
					}
					catch (NullPointerException e)
					{
						if (chars[i] == ' ')
						{
							
						}
						else
						{
							addToHistory(text, vId, tId, viId, size, vertices, textures, null);
							
							return null;
						}
					}
				}
				else
				{
					yOffset -= glyphHeight + 1;
					xOffset  = 0;
				}
			}
			
//			vertices.genIndices(GL.QUADS, null);
			
			vId  = vertices.getId();
			tId  = textures.getId();
//			viId = vertices.getIndicesId(0);
			
			addToHistory(text, vId, tId, viId, size, vertices, textures, null);
		}
		
		bundle = new Bundle(vId, tId, 0, size / 2, 2);
		
		GL.pushMatrix();
		{
			GL.translate(x, y, z);
			GL.scale(scale, scale, 1);
			
			bundle.render(GL.QUADS, characters);
		}
		GL.popMatrix();
		
		return bounds;
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param x
	 * @param y
	 * @param z
	 * @param scale
	 * @param horizontalAlignment
	 * @param verticalAlignment
	 * @param panel
	 */
	public void renderDisplayList(String text, float x, float y, float z, float scale, int horizontalAlignment, int verticalAlignment, Panel panel)
	{
		characters.bind();
		
		int id = 0;
		
		if (history.containsKey(text) && history.get(text)[1] == 0)
		{
			if (horizontalAlignment == CENTER)
			{
				if (panel != null)
				{
					x += panel.getWidth() / 2;
				}
				else
				{
					x += Frame.getWidth() / 2;
				}
				
				x -= text.length() * scale * glyphWidth / 2;
			}
			else if (horizontalAlignment == RIGHT)
			{
				if (panel != null)
				{
					x += panel.getWidth();
				}
				else
				{
					x += Frame.getWidth();
				}
				
				x -= text.length() * scale * glyphWidth;
			}
			if (verticalAlignment == CENTER)
			{
				if (panel != null)
				{
					y += panel.getHeight() / 2;
				}
				else
				{
					x += Frame.getHeight() / 2;
				}
				
				y -= glyphHeight * scale / 2;
			}
			else if (verticalAlignment == TOP)
			{
				if (panel != null)
				{
					y += panel.getHeight();
				}
				else
				{
					x += Frame.getHeight();
				}
				
				y -= glyphHeight * scale;
			}
			
			id = history.get(text)[2];
		}
		else
		{
			id = GL11.glGenLists(1);
			
			GL11.glNewList(id, GL11.GL_COMPILE);
			{
				char chars[] = text.toCharArray();
				
				for (int i = 0; i < chars.length; i ++)
				{
					if (chars[i] == '\n')
					{
						renderDisplayList(text.substring(0, i), x, y /*+ ((glyphHeight + 1) / 2) * scale*/ , z, scale, horizontalAlignment, verticalAlignment, panel);
						renderDisplayList(text.substring(i + 1), x, y - (glyphHeight + 1) * scale/*((glyphHeight + 1) / 2) * scale*/, z, scale, horizontalAlignment, verticalAlignment, panel);
						
						GL11.glEndList();
						addToHistory(text, id);
	//					y += ((glyphHeight + 1) / 2) * scale;
						return;
					}
				}
				
				if (horizontalAlignment == CENTER)
				{
					x += panel.getWidth() / 2;
					x -= text.length() * scale * glyphWidth / 2;
				}
				else if (horizontalAlignment == RIGHT)
				{
					x += panel.getWidth();
					x -= text.length() * scale * glyphWidth;
				}
				if (verticalAlignment == CENTER)
				{
					y += panel.getHeight() / 2;
					y -= glyphHeight * scale / 2;
				}
				else if (verticalAlignment == TOP)
				{
					y += panel.getHeight();
					y -= glyphHeight * scale;
				}
				
				for (int i = 0; i < chars.length; i ++)
				{
					try
					{
						int charX       = charSequence.get(chars[i])[0];
						int charY       = charSequence.get(chars[i])[1];
						
						float offsets[] = characters.getImageOffsets(charX, charY, 1, 1);
					
						GL.pushMatrix();
						{
//							GL.translatef(x, y, z);
//							GL.scalef(scale, scale, 1);
							
							GL11.glBegin(GL.QUADS);
							{
			//					Correct View
								
								GL11.glTexCoord2f(offsets[2], offsets[1]);
								GL11.glVertex2f(i * glyphWidth + glyphWidth + (letterMargin * i), 0);
								
								GL11.glTexCoord2f(offsets[2], offsets[3]);
								GL11.glVertex2f(i * glyphWidth + glyphWidth + (letterMargin * i), glyphHeight);
								
								GL11.glTexCoord2f(offsets[0], offsets[3]);
								GL11.glVertex2f(i * glyphWidth + (letterMargin * i), glyphHeight);
								
								GL11.glTexCoord2f(offsets[0], offsets[1]);
								GL11.glVertex2f(i * glyphWidth + (letterMargin * i), 0);
								
			//					Reversed View
			//					
			//					GL.glTexCoord2f(offsets[2], offsets[1]);
			//					GL.glVertex2f(- i * glyphWidth, 0);
			//					
			//					GL.glTexCoord2f(offsets[2], offsets[3]);
			//					GL.glVertex2f(- i * glyphWidth, glyphHeight);
			//					
			//					GL.glTexCoord2f(offsets[0], offsets[3]);
			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, glyphHeight);
			//					
			//					GL.glTexCoord2f(offsets[0], offsets[1]);
			//					GL.glVertex2f(- i * glyphWidth + glyphWidth, 0);
							}
							GL11.glEnd();
						}
						GL.popMatrix();
					}
					catch (NullPointerException e)
					{
						if (chars[i] == ' ')
						{
							
						}
						else
						{
							GL11.glEndList();
							
							addToHistory(text, id);
							
							return;
						}
					}
				}
			}
			GL11.glEndList();
			
			addToHistory(text, id);
		}
			
		GL.pushMatrix();
		{
			GL.translate(x, y, z);
			GL.scale(scale, scale, 1);
			
			GL11.glCallList(id);
		}
		GL.popMatrix();
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @param id
	 */
	private void addToHistory(String text, int id)
	{
		ids = ids + 1 < 100 ? ids + 1 : 0;
		
//		if (idArray[ids] != null)
//		{
//			GL11.glDeleteLists(idArray[ids][0], 1);
//		}
//		
//		idArray[ids] = new int[] { id };
		
		history.put(text, new int[] { ids, 0, id });
	}

	/**
	 * 
	 * 
	 * @param text
	 * @param vId
	 * @param tId
	 * @param viId
	 * @param size
	 * @param s
	 * @param s2
	 * @param s3
	 */
	private void addToHistory(String text, int vId, int tId, int viId, int size, Buffer s, Buffer s2, Buffer s3)
	{
		ids = ids + 1 < 100 ? ids + 1 : 0;
		
//		if (idArray[ids] != null)
//		{
//			GL15.glDeleteBuffers(idArray[ids][0]);
//			GL15.glDeleteBuffers(idArray[ids][1]);
//			GL15.glDeleteBuffers(idArray[ids][2]);
//		}
//		
//		idArray[ids] = new int[] { vId, tId, viId };
		
		history.put(text, new int[] { ids, 1, vId, tId, viId, size });
//		history2.put(text, new LightBuffer[] { s, s2, s3 });
	}
	
	/**
	 * 
	 * 
	 * @param text
	 * @return
	 */
	public int getWidth(String text)
	{
		int width = 0;
		
		for (int i = 0; i < text.length(); i ++)
		{
			if (text.charAt(i) == ' ')
			{
				width += glyphWidth;
			}
			else
			{
				width += glyphWidth;
			}
		}
		
		return width;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getGlyphWidth()
	{
		return glyphWidth;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getGlyphHeight()
	{
		return glyphHeight;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getLetterMargin()
	{
		return letterMargin;
	}
	
	/**
	 * 
	 * 
	 * @param letterMargin
	 */
	public void setLetterMargin(int letterMargin)
	{
		this.letterMargin = letterMargin;
	}
}