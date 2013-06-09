package net.foxycorndog.jfoxylib.opengl.bundle;

import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER_BINDING;
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

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

/**
 * Class used to store float data in order of index.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 16, 2013 at 3:23:16 AM
 * @since	v0.1
 * @version	Feb 26, 2013 at 6:39:16 AM
 * @version	v0.2
 */
public class Buffer
{
	private	int			id;
	private	int			size;
	
	private	FloatBuffer	buffer;
	private	ByteBuffer	mapBuffer;
	
	/**
	 * Create a Buffer suitable for storing a specific amount
	 * of floats.
	 * 
	 * @param size The specific amount of floats to use.
	 */
	public Buffer(int size)
	{
		this.size = size;
		
		buffer    = BufferUtils.createFloatBuffer(size);
		
		id        = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, id);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * @return The OpenGL GL_ARRAY_BUFFER id.
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * @return The capacity of the Buffer. The max amount of
	 *		data that can be stored in this Buffer.
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * @return The current position of the Buffer. This is the
	 *		index that you will next insert values into.
	 */
	public int getPosition()
	{
		return buffer.position();
	}
	
	/**
	 * Set the place in which the Buffer will next insert data
	 *		into.
	 * 
	 * @param position The position to set for the Buffer.
	 */
	public void setPosition(int position)
	{
		buffer.position(position);
	}
	
	/**
	 * Get the float data at the specified index.
	 * 
	 * @param index The index in which to get the data from.
	 * @return The data at the specified index.
	 */
	public float getData(int index)
	{
		return buffer.get(index);
	}
	
	/**
	 * Method used for setting the value at the specified
	 * index to the specified value of the float.
	 * 
	 * @param index The index of the float data.
	 * @param data The data to set it to.
	 */
	public void setData(int index, float data)
	{
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
	}
	
	/**
	 * Method used for setting the values at the specified
	 * index to the specified values of the floats in the
	 * data array.
	 * 
	 * @param index The index of the float data.
	 * @param data The data to set it to.
	 */
	public void setData(int index, float data[])
	{
		buffer.position(index);
	
		buffer.put(data);
		
		buffer.rewind();
	}
	
//	/**
//	 * 
//	 * 
//	 * @param offset
//	 * @param size
//	 * @param dx
//	 * @param dy
//	 * @param dz
//	 */
//	public void translate(int offset, int size, float dx, float dy, float dz)
//	{
//		buffer.position(offset);
//		
//		for (int i = 0; i < size; i += 3)
//		{
//			buffer.put(buffer.get(offset + i + 0) + dx);
//			buffer.put(buffer.get(offset + i + 1) + dy);
//			buffer.put(buffer.get(offset + i + 2) + dz);
//		}
//		
//		buffer.rewind();
//	}
	
	/**
	 * Bind the Buffer and mapBuffer in order to edit the data within the
	 * Buffer.
	 */
	public void beginEditing()
	{
//		int currentlyBound = GL11.glGetInteger(GL_ARRAY_BUFFER_BINDING);
//		
//		if (currentlyBound == id)
//		{
//			return;
//		}
		
		glBindBuffer(GL_ARRAY_BUFFER, id);
		
//		System.out.println(currentlyBound + ", " + id);
		
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, null);
		
		buffer    = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
	}
	
	/**
	 * Unbind the Buffer. No longer able to edit after this call.
	 */
	public void endEditing()
	{
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Create a String representing all of the float data in the Buffer.
	 * 
	 * @return A String representing all of the float data in the Buffer.
	 */
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.getClass().getSimpleName() + " { ");
		
		for (int i = 0; i < size; i++)
		{
			builder.append(buffer.get(i) + ", ");
		}
		
		if (size > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		builder.append(" }");
		
		return builder.toString();
	}
}
