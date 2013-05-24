package net.foxycorndog.jfoxylib.opengl.bundle;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

/**
 * Holds a central collection of vertices, textures, and colors.
 * These entities can be rendered in several ways if needed.
 * 
 * @file	Bundle.java
 * @author	Braden Steffaniak
 * @since	Feb 16, 2013 at 3:23:29 AM
 * @since	v0.1
 * @version	May 7, 2013 at 6:42:29 AM
 * @version	v0.2
 */
public class Bundle
{
	private int								vertsPosition, texturesPosition, colorsPosition;
	private int								vertexAmount, vertexSize;
	private int								verticesId, texturesId, colorsId;
	
	private Buffer							verticesBuffer, texturesBuffer, colorsBuffer;
//	private IndicesBuffer					vertexIndices, normalIndices;
//	
//	private Shader							colorShader;
//	
//	private HashMap<Integer, VertexOffset>	vertexOffsets;
//	private HashMap<Integer, TextureOffset>	textureOffsets;
	
	private static int	id;
	
//	/**
//	 * 
//	 * 
//	 * @author	Braden Steffaniak
//	 * @since	Apr 26, 2013 at 9:58:09 PM
//	 * @since	v
//	 * @version	Apr 26, 2013 at 9:58:09 PM
//	 * @version	v
//	 */
//	private class VertexOffset
//	{
//		private int	shape;
//		private int	offset;
//		private int	vertexSize;
//		private int	length;
//		
//		public VertexOffset(int shape, int offset, int vertexSize, int length)
//		{
//			this.shape      = shape;
//			this.offset     = offset;
//			this.vertexSize = vertexSize;
//			this.length     = length;
//		}
//	}
//	
//	/**
//	 * 
//	 * 
//	 * @author	Braden Steffaniak
//	 * @since	Apr 26, 2013 at 9:58:13 PM
//	 * @since	v
//	 * @version	Apr 26, 2013 at 9:58:13 PM
//	 * @version	v
//	 */
//	private class TextureOffset
//	{
//		private int	offset;
//		private int	length;
//		
//		public TextureOffset(int offset, int length)
//		{
//			this.offset     = offset;
//			this.length     = length;
//		}
//	}
	
	/**
	 * Create a Bundle with the specified amount of needed
	 * vertices. Also specify the amount of dimensions the
	 * vertex will be in, as well as whether to enable textures
	 * and color Buffers with the Buffer.
	 * 
	 * @param vertexAmount The amount of needed vertices.
	 * @param vertexSize The amount of dimensions the vertex
	 *		will be in.
	 * @param textures Whether to enable texture Buffers for this Bundle.
	 * @param colors Whether to enable color Buffers for this Bundle.
	 */
	public Bundle(int vertexAmount, int vertexSize, boolean textures, boolean colors)
	{
		this.vertexAmount = vertexAmount;
		this.vertexSize   = vertexSize;
		
		verticesBuffer    = new Buffer(vertexAmount * vertexSize);
		verticesId        = verticesBuffer.getId();
		
		if (textures)
		{
			texturesBuffer = new Buffer(vertexAmount * 2);
			texturesId     = texturesBuffer.getId();
		}
		
		if (colors)
		{
			colorsBuffer = new Buffer(vertexAmount * 4);
			colorsId     = colorsBuffer.getId();
		
			if (vertexAmount > 0)
			{
				colorsBuffer.beginEditing();
				{
					float cols[] = new float[vertexAmount * 4];
					
					for (int i = 0; i < cols.length; i++)
					{
						cols[i] = 1;
					}
					
					colorsBuffer.setData(0, cols);
				}
				colorsBuffer.endEditing();
			}
		}
	}
	
	/**
	 * Create a Bundle from the specified Vertices Buffer with the
	 * specified vertex size, Textures Buffer, and Colors Buffer.
	 * 
	 * @param verticesBuffer The Vertices Buffer to create the Bundle
	 * 		with.
	 * @param texturesBuffer The Textures Buffer to create the Bundle
	 * 		with.
	 * @param colorsBuffer The Colors Buffer to create the Bundle with.
	 * @param vertexSize The size of the vertices in the Vertex Buffer.
	 */
	public Bundle(Buffer verticesBuffer, Buffer texturesBuffer, Buffer colorsBuffer, int vertexSize)
	{
		this.verticesBuffer = verticesBuffer;
		verticesId = verticesBuffer.getId();
		
		if (texturesBuffer != null)
		{
			this.texturesBuffer = texturesBuffer;
			texturesId = texturesBuffer.getId();
		}
		
		if (colorsBuffer != null)
		{
			this.colorsBuffer   = colorsBuffer;
			colorsId   = colorsBuffer.getId();
		}
		
		this.vertexSize   = vertexSize;
		this.vertexAmount = verticesBuffer.getSize() / vertexSize;
	}
	
	/**
	 * Create a Bundle from the specified vertices buffer id, textures
	 * buffer id, and colors buffer id. You also need to specify the
	 * amount of vertices are in the vertices buffer as well as the size
	 * of the vertices in the vertices buffer.
	 * 
	 * @param verticesId The OpenGL Buffer id for the vertices buffer.
	 * @param texturesId The OpenGL Buffer id for the textures buffer.
	 * @param colorsId The OpenGL Buffer id for the colors buffer.
	 * @param vertexAmount The amount of vertices that are in the
	 * 		vertices buffer.
	 * @param vertexSize The size of the vertices in the vertices
	 * 		buffer.
	 */
	public Bundle(int verticesId, int texturesId, int colorsId, int vertexAmount, int vertexSize)
	{
		this.verticesId   = verticesId;
		this.texturesId   = texturesId;
		this.colorsId     = colorsId;
		
		this.vertexSize   = vertexSize;
		this.vertexAmount = vertexAmount;
	}
	
//	/**
//	 * 
//	 * 
//	 * @param amount
//	 */
//	private void createColorShader(int amount)
//	{
////		String vertexShader =
////				"\nvoid main()" +
////				"\n{" +
////				"\n	gl_TexCoord[0] = gl_MultiTexCoord0;" +
////				"\n	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" +
////				"\n}";
////		
////		String fragmentShader =
////				"\nuniform sampler2D	texture;" +
////				"\nuniform vec4			colors[" + amount / 10 + "];" +
////				"\n" +
////				"\nvoid main()" +
////				"\n{" +
////				"\n	gl_FragColor = texture2D(texture, gl_TexCoord[0].st);" +
////				"\n	gl_FragColor = gl_FragColor * colors[0];" +
////				"\n}";
////		
////		colorShader = new Shader();
////		colorShader.loadString(new String[] { vertexShader }, new String[] { fragmentShader });
//	}
	
	/**
	 * Get the position that the Vertices Buffer is set at currently.
	 * 
	 * @return The position that the Vertices Buffer is set at currently.
	 */
	public int getVerticesPosition()
	{
		return vertsPosition;
	}
	
	/**
	 * Get the position that the Textures Buffer is set at currently.
	 * 
	 * @return The position that the Textures Buffer is set at currently.
	 */
	public int getTexturesPosition()
	{
		return texturesPosition;
	}
	
	/**
	 * Get the Vertices Buffer instance that this Bundle is using.
	 * 
	 * @return The Vertices Buffer instance that this Bundle is using.
	 */
	public Buffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	/**
	 * Set the Vertices Buffer instance that this Bundle will use.
	 * 
	 * @param buffer The Vertices Buffer instance that this Bundle will
	 * 		use.
	 */
	public void setVerticesBuffer(Buffer buffer)
	{
		this.verticesBuffer = buffer;
	}
	
	/**
	 * Set the vertices at the specified offset in the Vertices Buffer.
	 * 
	 * @param offset The offset in the Vertices Buffer. (This offset is
	 * 		per vertex, not per vertex * vertexSize, so use smaller
	 * 		numbers)
	 * @param verts The new float array to set the data at the offset to.
	 */
	public void setVertices(int offset, float verts[])
	{
		verticesBuffer.setData(offset * vertexSize, verts);
		
//		int id = ++this.id;
		
//		vertexOffsets.put(id, new VertexOffset(shape, offset, vertexSize, verts.length));
		
//		return id;
	}
	
	/**
	 * Add the specified float array to the end of the Vertices Buffer's
	 * current position.
	 * 
	 * @param verts The float array to append.
	 */
	public void addVertices(float verts[])
	{
		setVertices(vertsPosition, verts);
		
		vertsPosition += verts.length / vertexSize;
		
//		return id;
	}
	
//	public void translate(int offset, int numVertices, float dx, float dy, float dz)
//	{
//		verticesBuffer.translate(offset * vertexSize, numVertices * vertexSize, dx, dy, dz);
//	}
	
	/**
	 * Get the Textures Buffer instance that this Bundle is using.
	 * 
	 * @return The Textures Buffer instance that this Bundle is using.
	 */
	public Buffer getTexturesBuffer()
	{
		return texturesBuffer;
	}
	
	/**
	 * Set the Textures Buffer instance that this Bundle will use.
	 * 
	 * @param buffer The Textures Buffer instance that this Bundle will
	 * 		use.
	 */
	public void setTexturesBuffer(Buffer buffer)
	{
		this.texturesBuffer = buffer;
	}
	
	/**
	 * Set the textures at the specified offset in the Textures Buffer.
	 * 
	 * @param offset The offset in the Textures Buffer. (This offset is
	 * 		per texture, not per texture * textureSize, so use smaller
	 * 		numbers)
	 * @param textures The new float array to set the data at the offset
	 * 		to.
	 */
	public void setTextures(int offset, float textures[])
	{
		texturesBuffer.setData(offset * 2, textures);
		
//		int id = ++this.id;

//		textureOffsets.put(id, new TextureOffset(offset, textures.length));
		
//		return id;
	}
	
	/**
	 * Add the specified float array to the end of the Textures Buffer's
	 * current position.
	 * 
	 * @param verts The float array to append.
	 */
	public void addTextures(float textures[])
	{
		setTextures(texturesPosition, textures);
		
		texturesPosition += textures.length / 2;
		
//		return id;
	}
	
	/**
	 * Get the Colors Buffer instance that this Bundle is using.
	 * 
	 * @return The Colors Buffer instance that this Bundle is using.
	 */
	public Buffer getColorsBuffer()
	{
		return colorsBuffer;
	}
	
	/**
	 * Set the Colors Buffer instance that this Bundle will use.
	 * 
	 * @param buffer The Colors Buffer instance that this Bundle will
	 * 		use.
	 */
	public void setColorsBuffer(Buffer buffer)
	{
		this.colorsBuffer = buffer;
	}
	
	/**
	 * Set the colors at the specified offset in the Colors Buffer.
	 * 
	 * @param offset The offset in the Colors Buffer. (This offset is
	 * 		per color, not per color * colorSize, so use smaller
	 * 		numbers)
	 * @param colors The new float array to set the data at the offset
	 * 		to.
	 */
	public void setColors(int offset, float colors[])
	{
		colorsBuffer.setData(offset * 4, colors);
		
//		int id = ++this.id;

//		textureOffsets.put(id, new TextureOffset(offset, textures.length));
		
//		return id;
	}
	
	/**
	 * Add the specified float array to the end of the Colors Buffer's
	 * current position.
	 * 
	 * @param verts The float array to append.
	 */
	public void addColors(float colors[])
	{
		setColors(colorsPosition, colors);
		
		colorsPosition += colors.length / 4;
		
//		return id;
	}
	
	/**
	 * 
	 */
	public void beginEditingVertices()
	{
		verticesBuffer.beginEditing();
	}
	
	/**
	 * 
	 */
	public void endEditingVertices()
	{
		verticesBuffer.endEditing();
	}
	
	/**
	 * 
	 */
	public void beginEditingTextures()
	{
		texturesBuffer.beginEditing();
	}
	
	/**
	 * 
	 */
	public void endEditingTextures()
	{
		texturesBuffer.endEditing();
	}
	
	/**
	 * 
	 */
	public void beginEditingColors()
	{
		colorsBuffer.beginEditing();
	}
	
	/**
	 * 
	 */
	public void endEditingColors()
	{
		colorsBuffer.endEditing();
	}
	
	/**
	 * 
	 * 
	 * @param shape
	 * @param texture
	 */
	public void render(int shape, Texture texture)
	{
		render(shape, 0, vertexAmount, texture);
	}
	
	/**
	 * 
	 * 
	 * @param shape
	 * @param start
	 * @param amount
	 * @param texture
	 */
	public void render(int shape, int start, int amount, Texture texture)
	{
		beginVerticesDraw();//start * vertexSize);
		beginTexturesDraw();//start * 2);
		beginColorsDraw();
		
		texture.bind();
		
		glDrawArrays(shape, start, amount);
		
		endColorsDraw();
		endTexturesDraw();
		endVerticesDraw();
		
//		Texture.unbind();
	}
	
	/**
	 * 
	 */
	private void beginVerticesDraw()
	{
		glEnableClientState(GL_VERTEX_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesId);

		glVertexPointer(vertexSize, GL11.GL_FLOAT, 0, 0);
		
//		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vertexIndicesId);
	}
	
	/**
	 * 
	 */
	private void endVerticesDraw()
	{
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
	}
	
	/**
	 * 
	 */
	private void beginTexturesDraw()
	{
		GL.setTextureWrapSMethod(GL.getTextureWrapSMethod());
		GL.setTextureWrapTMethod(GL.getTextureWrapTMethod());
		
		GL.setTextureScaleMinMethod(GL.getTextureScaleMinMethod());
		GL.setTextureScaleMagMethod(GL.getTextureScaleMagMethod());
		
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texturesId);

		glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
	}
	
	/**
	 * 
	 */
	private void endTexturesDraw()
	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		GL.setTextureScaleMinMethod(GL.getTextureScaleMinMethod());
		GL.setTextureScaleMagMethod(GL.getTextureScaleMagMethod());
		
		GL.setTextureWrapSMethod(GL.getTextureWrapSMethod());
		GL.setTextureWrapTMethod(GL.getTextureWrapTMethod());
	}
	
	/**
	 * 
	 */
	private void beginColorsDraw()
	{
		if (colorsId == 0)
		{
			return;
		}
		
		glEnableClientState(GL11.GL_COLOR_ARRAY);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorsId);
		
		GL11.glColorPointer(4, GL11.GL_FLOAT, 0, 0);
	}
	
	/**
	 * 
	 */
	private void endColorsDraw()
	{
		if (colorsId == 0)
		{
			return;
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL11.GL_COLOR_ARRAY);
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public String toString()
	{
		return verticesBuffer.toString();
	}
}
