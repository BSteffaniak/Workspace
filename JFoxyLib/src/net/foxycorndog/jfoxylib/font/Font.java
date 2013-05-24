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
	private int							cols, rows;
	private int							yOff, xOff;
	private int							width, height;
	private int							glyphWidth, glyphHeight;
	private int							ids;
	private int							letterMargin, lineOffset;
	
	private SpriteSheet					characters;
	
	private int							idArray[][];
	
	private HashMap<Character, int[]>	charSequence;
	private HashMap<String, int[]>		history;
	
	public static final int				LEFT = 0, CENTER = 1, RIGHT = 2, BOTTOM = 0, TOP = 2;
	
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
		this.cols         = cols;
		this.rows         = rows;
		
		characters        = new SpriteSheet(location, cols, rows);
		
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
		
		lineOffset = 1;
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
		return render(text, x, y, z, 1, panel);
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
		return render(text, x, y, z, scale, LEFT, BOTTOM, panel);
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
		return render(text, x, y, z, 1, horizontalAlignment, verticalAlignment, panel);
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
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, panel);
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
		return renderVertexBuffer(text, x, y, z, scale, horizontalAlignment, verticalAlignment, null, null, panel);
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
						if (!charSequence.containsKey(chars[i]))	
						{
							continue;
						}
						
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
							addToHistory(text, vId, tId, viId, size);
							
							return null;
						}
					}
				}
				else
				{
					yOffset -= glyphHeight + lineOffset;
					xOffset  = 0;
				}
			}
			
//			vertices.genIndices(GL.QUADS, null);
			
			vId  = vertices.getId();
			tId  = textures.getId();
//			viId = vertices.getIndicesId(0);
			
			addToHistory(text, vId, tId, viId, size);
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
	public int getLineOffset()
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
	public void setLineOffset(int lineOffset)
	{
		this.lineOffset = lineOffset;
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
	 * Get the amount of pixels that the specified String would take up
	 * horizontally.
	 * 
	 * @param text The String of text to get the width of.
	 * @return The amount of pixels that the specified String would take
	 * 		up horizontally.
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
	 * Get the amount of pixels that the specified String would take up
	 * vertically.
	 * 
	 * @param text The String of text to get the height of.
	 * @return The amount of pixels that the specified String would take
	 * 		up vertically.
	 */
	public int getHeight(String text)
	{
		int height = 0;
		
		String lines[] = text.split("\n");
		
		int numLines = lines.length;
		
		if (lines[lines.length - 1].length() <= 0)
		{
			numLines--;
		}
		
		if (numLines > 0)
		{
			height = (getGlyphHeight() + lineOffset) * numLines - lineOffset;
		}
		
		return height;
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
	public int getLetterMargin()
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
	public void setLetterMargin(int letterMargin)
	{
		this.letterMargin = letterMargin;
	}
}