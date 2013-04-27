package net.foxycorndog.jfoxylib.bundle;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glMapBuffer;
import static org.lwjgl.opengl.GL15.glUnmapBuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 10:29:20 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 10:29:20 PM
 * @version	v0.2
 */
public class IndicesBuffer
{
	private int			id;
	private int			size;
	
	private ShortBuffer	buffer;
	private ByteBuffer	mapBuffer;
	
	/**
	 * 
	 * 
	 * @param size
	 */
	public IndicesBuffer(int size)
	{
		this.size = size;
		
		buffer = BufferUtils.createShortBuffer(size);
		
		id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
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
	 * @param index
	 * @param data
	 */
	public void setData(int index, short data)
	{
		beginEditing();
		
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
		
		endEditing();
	}
	
	/**
	 * 
	 * 
	 * @param index
	 * @param data
	 */
	public void setData(int index, short data[])
	{
		beginEditing();
		
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
		
		endEditing();
	}
	
	/**
	 * 
	 */
	private void beginEditing()
	{
		glBindBuffer(GL_ARRAY_BUFFER, id);
		
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asShortBuffer();
	}
	
	/**
	 * 
	 */
	private void endEditing()
	{
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < size; i++)
		{
			builder.append(buffer.get(i) + ", ");
		}
		
		return builder.toString();
	}
}