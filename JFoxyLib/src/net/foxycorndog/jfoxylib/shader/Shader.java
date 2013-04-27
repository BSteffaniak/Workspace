package net.foxycorndog.jfoxylib.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform2i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform3i;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4i;
import static org.lwjgl.opengl.GL20.glUniformMatrix2;
import static org.lwjgl.opengl.GL20.glUniformMatrix3;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttrib1d;
import static org.lwjgl.opengl.GL20.glVertexAttrib1f;
import static org.lwjgl.opengl.GL20.glVertexAttrib1s;
import static org.lwjgl.opengl.GL20.glVertexAttrib2d;
import static org.lwjgl.opengl.GL20.glVertexAttrib2f;
import static org.lwjgl.opengl.GL20.glVertexAttrib2s;
import static org.lwjgl.opengl.GL20.glVertexAttrib3d;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;
import static org.lwjgl.opengl.GL20.glVertexAttrib3s;
import static org.lwjgl.opengl.GL20.glVertexAttrib4d;
import static org.lwjgl.opengl.GL20.glVertexAttrib4f;
import static org.lwjgl.opengl.GL20.glVertexAttrib4s;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:11:41 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:11:41 PM
 * @version	v0.2
 */
public class Shader
{
	private int programId;
	
	private HashMap<String, Integer> uniformCache;
	
//	public Shader(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
//	{
//		if (parentDir == null)
//		{
//			parentDir = "";
//		}
//		
//		create(parentDir, vertexShaderLocations, fragmentShaderLocations);
//	}
//	
//	public Shader(String vertexShaderLocations[], String fragmentShaderLocations[])
//	{
//		create("", vertexShaderLocations, fragmentShaderLocations);
//	}
	
	/**
	 * 
	 * 
	 * @param vertexShaderSources
	 * @param fragmentShaderSources
	 */
	public void loadString(String vertexShaderSources[], String fragmentShaderSources[])
	{
		uniformCache = new HashMap<String, Integer>();
		
		programId = loadShaderProgram(vertexShaderSources, fragmentShaderSources);
	}
	
	/**
	 * 
	 * 
	 * @param vertexShaderLocations
	 * @param fragmentShaderLocations
	 */
	public void loadFile(String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		create("", vertexShaderLocations, fragmentShaderLocations);
	}
	
	/**
	 * 
	 * 
	 * @param parentDir
	 * @param vertexShaderLocations
	 * @param fragmentShaderLocations
	 */
	public void loadFile(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		create(parentDir, vertexShaderLocations, fragmentShaderLocations);
	}
	
	/**
	 * 
	 * 
	 * @param parentDir
	 * @param vertexShaderLocations
	 * @param fragmentShaderLocations
	 */
	private void create(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		uniformCache = new HashMap<String, Integer>();
		
		programId = loadShaderProgramFile(parentDir, vertexShaderLocations, fragmentShaderLocations);
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private int genShaderProgramId()
	{
		int id = glCreateProgram();
		
		return id;
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 */
	private void genShaderProgram(int programId)
	{
		glLinkProgram(programId);
	}
	
	/**
	 * 
	 */
	public void run()
	{
		useShaderProgram(programId);
	}
	
	/**
	 * 
	 */
	public void stop()
	{
		useShaderProgram(0);
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 */
	private void useShaderProgram(int programId)
	{
		glUseProgram(programId);
		
//		currentProgram = programId == 0 ? currentProgram : programId;
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 * @return
	 */
	private boolean validateProgram(int programId)
	{
		glValidateProgram(programId);
		
		return glGetProgrami(programId, GL_VALIDATE_STATUS) != GL_FALSE;
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 */
	private void deleteProgram(int programId)
	{
		glDeleteProgram(programId);
	}
	
	/**
	 * 
	 * 
	 * @param shaderId
	 */
	private void deleteShader(int shaderId)
	{
		glDeleteShader(shaderId);
	}
	
	/**
	 * 
	 * 
	 * @param shaderId
	 * @param shaderProgramId
	 */
	private void attachShader(int shaderId, int shaderProgramId)
	{
		glAttachShader(shaderProgramId, shaderId);
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	private int loadVertexShaderFile(String location)
	{
		StringBuilder shaderSource   = new StringBuilder();
		
		try
		{
			BufferedReader shaderReader = new BufferedReader(new FileReader(location));
			
			String line = null;
			
			while ((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append('\n');
			}
			
			shaderReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return loadVertexShader(shaderSource.toString());
	}
	
	/**
	 * 
	 * 
	 * @param source
	 * @return
	 */
	private int loadVertexShader(String source)
	{
		int vertexShader   = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexShader, source);
		glCompileShader(vertexShader);
		
		if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(vertexShader, glGetShaderi(vertexShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, "[name]");
			
			throw new RuntimeException("Vertex shader at \"" + "[name]" + "\" was not compiled correctly:\n\t" + error);
		}
		
		return vertexShader;
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	private int loadFragmentShaderFile(String location)
	{
		StringBuilder shaderSource = new StringBuilder();
		
		try
		{
			BufferedReader shaderReader = new BufferedReader(new FileReader(location));
			
			String line = null;
			
			while ((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append('\n');
			}
			
			shaderReader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return loadFragmentShader(shaderSource.toString());
	}
	
	/**
	 * 
	 * 
	 * @param source
	 * @return
	 */
	private int loadFragmentShader(String source)
	{
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentShader, source);
		glCompileShader(fragmentShader);
		
		if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(fragmentShader, glGetShaderi(fragmentShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, "[name]");
			
			throw new RuntimeException("Fragment shader at \"" + "[name]" + "\" was not compiled correctly:\n\t" + error);
		}
		
		return fragmentShader;
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	private String getName(String location)
	{
		int lastI = location.lastIndexOf('/');
		lastI     = Math.max(location.indexOf('\\'), lastI);
		
		return location.substring(lastI + 1, location.length());
	}
	
	/**
	 * 
	 * 
	 * @param error
	 * @param fileName
	 * @return
	 */
	private String formatError(String error, String fileName)
	{
		String formattedError = "";
		
		for (int i = 0; i < error.length() - 1; i ++)
		{
			try
			{
				if (error.charAt(i) == '0' && error.charAt(i + 1) == '(')
				{
					formattedError += fileName + ":";
				}
				else if (error.charAt(i) == ')' || error.charAt(i) == '(')
				{
					
				}
				else
				{
					formattedError += error.charAt(i);
					
					if (error.charAt(i) == '\n')
					{
						formattedError += '\t';
					}
				}
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				
			}
		}
		
		return formattedError;
	}
	
	/**
	 * 
	 * 
	 * @param parentDir
	 * @param vertexShaderLocation
	 * @param fragmentShaderLocation
	 * @return
	 */
	private int loadShaderProgram(String parentDir, String vertexShaderLocation, String fragmentShaderLocation)
	{
		int shaderProgram  = genShaderProgramId();
		int vertexShader   = loadVertexShaderFile(parentDir + vertexShaderLocation);
		int fragmentShader = loadFragmentShaderFile(parentDir + fragmentShaderLocation);
		attachShader(vertexShader, shaderProgram);
		attachShader(fragmentShader, shaderProgram);
		genShaderProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	/**
	 * 
	 * 
	 * @param parentDir
	 * @param vertexShaderLocations
	 * @param fragmentShaderLocations
	 * @return
	 */
	private int loadShaderProgramFile(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		int shaderProgram  = genShaderProgramId();
		
		for (int i = 0; i < vertexShaderLocations.length; i ++)
		{
			int vertexShader = loadVertexShaderFile(parentDir + vertexShaderLocations[i]);
			attachShader(vertexShader, shaderProgram);
		}
		
		for (int i = 0; i < fragmentShaderLocations.length; i ++)
		{
			int fragmentShader = loadFragmentShaderFile(parentDir + fragmentShaderLocations[i]);
			attachShader(fragmentShader, shaderProgram);
		}
		
		genShaderProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	/**
	 * 
	 * 
	 * @param vertexShaderSources
	 * @param fragmentShaderSources
	 * @return
	 */
	private int loadShaderProgram(String vertexShaderSources[], String fragmentShaderSources[])
	{
		int shaderProgram = genShaderProgramId();
		
		for (int i = 0; i < vertexShaderSources.length; i ++)
		{
			int vertexShader = loadVertexShader(vertexShaderSources[i]);
			attachShader(vertexShader, shaderProgram);
		}
		
		for (int i = 0; i < fragmentShaderSources.length; i ++)
		{
			int fragmentShader = loadFragmentShader(fragmentShaderSources[i]);
			attachShader(fragmentShader, shaderProgram);
		}
		
		genShaderProgram(shaderProgram);
		
		return shaderProgram;
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 * @param uniformName
	 * @return
	 */
	private int getUniformLocation(int programId, String uniformName)
	{
		int location = 0;
		
		if (uniformCache.containsKey(uniformName))
		{
			location = uniformCache.get(uniformName);
		}
		else
		{
			location = glGetUniformLocation(programId, uniformName);
			
			uniformCache.put(uniformName, location);
		}
		
		return location;
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void uniform4i(int uniformLocation, int x, int y, int z, int w)
	{
		glUniform4i(uniformLocation, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void uniform4i(String uniformName, int x, int y, int z, int w)
	{
		glUniform4i(getUniformLocation(programId, uniformName), x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 * @param z
	 */
	public void uniform3i(int uniformLocation, int x, int y, int z)
	{
		glUniform3i(uniformLocation, x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 * @param z
	 */
	public void uniform3i(String uniformName, int x, int y, int z)
	{
		glUniform3i(getUniformLocation(programId, uniformName), x, y, z);
	}
	
//	public void uniform4i(String uniformName, int data[])
//	{
//		int location = getUniformLocation(programId, uniformName);
//		
//		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
//		
//		buffer.put(data).rewind();
//		
//		GL20.glUniform4(location, buffer);
//	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 */
	public void uniform2i(int uniformLocation, int x, int y)
	{
		glUniform2i(uniformLocation, x, y);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 */
	public void uniform2i(String uniformName, int x, int y)
	{
		glUniform2i(getUniformLocation(programId, uniformName), x, y);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 */
	public void uniform1i(int uniformLocation, int x)
	{
		glUniform1i(uniformLocation, x);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 */
	public void uniform1i(String uniformName, int x)
	{
		glUniform1i(getUniformLocation(programId, uniformName), x);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void uniform4f(int uniformLocation, float x, float y, float z, float w)
	{
		glUniform4f(uniformLocation, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void uniform4f(String uniformName, float x, float y, float z, float w)
	{
		int location = getUniformLocation(programId, uniformName);
		
		glUniform4f(location, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param data
	 */
	public void uniform4f(String uniformName, float data[])
	{
		int location = getUniformLocation(programId, uniformName);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data).rewind();
		
		GL20.glUniform4(location, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 * @param z
	 */
	public void uniform3f(int uniformLocation, float x, float y, float z)
	{
		glUniform3f(uniformLocation, x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 * @param z
	 */
	public void uniform3f(String uniformName, float x, float y, float z)
	{
		glUniform3f(getUniformLocation(programId, uniformName), x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 * @param y
	 */
	public static void uniform2f(int uniformLocation, float x, float y)
	{
		glUniform2f(uniformLocation, x, y);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 * @param y
	 */
	public void uniform2f(String uniformName, float x, float y)
	{
		glUniform2f(getUniformLocation(programId, uniformName), x, y);
	}
	
	/**
	 * 
	 * 
	 * @param uniformLocation
	 * @param x
	 */
	public void uniform1f(int uniformLocation, float x)
	{
		glUniform1f(uniformLocation, x);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param x
	 */
	public void uniform1f(String uniformName, float x)
	{
		glUniform1f(getUniformLocation(programId, uniformName), x);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param buffer
	 */
	public void uniformMatrix2f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix2(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param buffer
	 */
	public void uniformMatrix2f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix2(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param array
	 */
	public void uniformMatrix2f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param array
	 */
	public void uniformMatrix2f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param buffer
	 */
	public void uniformMatrix3f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix3(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param buffer
	 */
	public void uniformMatrix3f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix3(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param array
	 */
	public void uniformMatrix3f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param array
	 */
	public void uniformMatrix3f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param buffer
	 */
	public void uniformMatrix4f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param buffer
	 */
	public void uniformMatrix4f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param array
	 */
	public void uniformMatrix4f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param uniformName
	 * @param transpose
	 * @param array
	 */
	public void uniformMatrix4f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * 
	 * 
	 * @param programId
	 * @param attribName
	 * @return
	 */
	public int getAttribLocation(int programId, String attribName)
	{
		return glGetAttribLocation(programId, attribName);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void vertexAttrib4s(int attribLocation, short x, short y, short z, short w)
	{
		glVertexAttrib4s(attribLocation, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 */
	public void vertexAttrib3s(int attribLocation, short x, short y, short z)
	{
		glVertexAttrib3s(attribLocation, x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 */
	public void vertexAttrib2s(int attribLocation, short x, short y)
	{
		glVertexAttrib2s(attribLocation, x, y);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 */
	public void vertexAttrib1s(int attribLocation, short x)
	{
		glVertexAttrib1s(attribLocation, x);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void vertexAttrib4f(int attribLocation, float x, float y, float z, float w)
	{
		glVertexAttrib4f(attribLocation, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 */
	public void vertexAttrib3f(int attribLocation, float x, float y, float z)
	{
		glVertexAttrib3f(attribLocation, x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 */
	public void vertexAttrib2f(int attribLocation, float x, float y)
	{
		glVertexAttrib2f(attribLocation, x, y);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 */
	public void vertexAttrib1f(int attribLocation, float x)
	{
		glVertexAttrib1f(attribLocation, x);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public void vertexAttrib4d(int attribLocation, double x, double y, double z, double w)
	{
		glVertexAttrib4d(attribLocation, x, y, z, w);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 * @param z
	 */
	public void vertexAttrib3d(int attribLocation, double x, double y, double z)
	{
		glVertexAttrib3d(attribLocation, x, y, z);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 * @param y
	 */
	public void vertexAttrib2d(int attribLocation, double x, double y)
	{
		glVertexAttrib2d(attribLocation, x, y);
	}
	
	/**
	 * 
	 * 
	 * @param attribLocation
	 * @param x
	 */
	public void vertexAttrib1d(int attribLocation, double x)
	{
		glVertexAttrib1d(attribLocation, x);
	}
}