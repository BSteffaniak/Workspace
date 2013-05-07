package net.foxycorndog.jfoxylib.bundle;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import net.foxycorndog.jfoxylib.graphics.Texture;
import net.foxycorndog.jfoxylib.graphics.opengl.GL;

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
		
		verticesBuffer = new Buffer(vertexAmount * vertexSize);
		verticesId = verticesBuffer.getId();
		
		if (textures)
		{
			texturesBuffer = new Buffer(vertexAmount * 2);
			texturesId = texturesBuffer.getId();
		}
		
		if (colors)
		{
			colorsBuffer   = new Buffer(vertexAmount * 4);
			colorsId   = colorsBuffer.getId();
		
			colorsBuffer.beginEditing();
			
			float cols[] = new float[vertexAmount * 4];
			
			for (int i = 0; i < cols.length; i++)
			{
				cols[i] = 1;
			}
			
			colorsBuffer.setData(0, cols);
			
			colorsBuffer.endEditing();
		}
	}
	
	/**
	 * 
	 * 
	 * @param verticesBuffer
	 * @param texturesBuffer
	 * @param colorsBuffer
	 * @param vertexSize
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
	 * 
	 * 
	 * @param verticesId
	 * @param texturesId
	 * @param colorsId
	 * @param vertexAmount
	 * @param vertexSize
	 */
	public Bundle(int verticesId, int texturesId, int colorsId, int vertexAmount, int vertexSize)
	{
		this.verticesId   = verticesId;
		this.texturesId   = texturesId;
		this.colorsId     = colorsId;
		
		this.vertexSize   = vertexSize;
		this.vertexAmount = vertexAmount;
	}
	
	/**
	 * 
	 * 
	 * @param amount
	 */
	private void createColorShader(int amount)
	{
//		String vertexShader =
//				"\nvoid main()" +
//				"\n{" +
//				"\n	gl_TexCoord[0] = gl_MultiTexCoord0;" +
//				"\n	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" +
//				"\n}";
//		
//		String fragmentShader =
//				"\nuniform sampler2D	texture;" +
//				"\nuniform vec4			colors[" + amount / 10 + "];" +
//				"\n" +
//				"\nvoid main()" +
//				"\n{" +
//				"\n	gl_FragColor = texture2D(texture, gl_TexCoord[0].st);" +
//				"\n	gl_FragColor = gl_FragColor * colors[0];" +
//				"\n}";
//		
//		colorShader = new Shader();
//		colorShader.loadString(new String[] { vertexShader }, new String[] { fragmentShader });
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getVerticesPosition()
	{
		return vertsPosition;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getTexturesPosition()
	{
		return texturesPosition;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Buffer getVerticesBuffer()
	{
		return verticesBuffer;
	}
	
	/**
	 * 
	 * 
	 * @param buffer
	 */
	public void setVerticesBuffer(Buffer buffer)
	{
		this.verticesBuffer = buffer;
	}
	
	/**
	 * 
	 * 
	 * @param offset
	 * @param verts
	 * @return
	 */
	public int setVertices(int offset, float verts[])
	{
		verticesBuffer.setData(offset * vertexSize, verts);
		
		int id = ++this.id;
		
//		vertexOffsets.put(id, new VertexOffset(shape, offset, vertexSize, verts.length));
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * @param verts
	 * @return
	 */
	public int addVertices(float verts[])
	{
		int id = setVertices(vertsPosition, verts);
		
		vertsPosition += verts.length / vertexSize;
		
		return id;
	}
	
//	public void translate(int offset, int numVertices, float dx, float dy, float dz)
//	{
//		verticesBuffer.translate(offset * vertexSize, numVertices * vertexSize, dx, dy, dz);
//	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Buffer getTexturesBuffer()
	{
		return texturesBuffer;
	}
	
	/**
	 * 
	 * 
	 * @param buffer
	 */
	public void setTexturesBuffer(Buffer buffer)
	{
		this.texturesBuffer = buffer;
	}
	
	/**
	 * 
	 * 
	 * @param offset
	 * @param textures
	 * @return
	 */
	public int setTextures(int offset, float textures[])
	{
		texturesBuffer.setData(offset * 2, textures);
		
		int id = ++this.id;

//		textureOffsets.put(id, new TextureOffset(offset, textures.length));
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * @param textures
	 * @return
	 */
	public int addTextures(float textures[])
	{
		int id = setTextures(texturesPosition, textures);
		
		texturesPosition += textures.length / 2;
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Buffer getColorsBuffer()
	{
		return colorsBuffer;
	}
	
	/**
	 * 
	 * 
	 * @param buffer
	 */
	public void setColorsBuffer(Buffer buffer)
	{
		this.colorsBuffer = buffer;
	}
	
	/**
	 * 
	 * 
	 * @param offset
	 * @param colors
	 * @return
	 */
	public int setColors(int offset, float colors[])
	{
		colorsBuffer.setData(offset * 4, colors);
		
		int id = ++this.id;

//		textureOffsets.put(id, new TextureOffset(offset, textures.length));
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * @param colors
	 * @return
	 */
	public int addColors(float colors[])
	{
		int id = setColors(colorsPosition, colors);
		
		colorsPosition += colors.length / 4;
		
		return id;
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
