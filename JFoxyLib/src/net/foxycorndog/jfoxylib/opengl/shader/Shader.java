package net.foxycorndog.jfoxylib.opengl.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
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
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

/**
 * Classed use to create an Object-Oriented way to access the
 * functionality of an OpenGL shader.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:11:41 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 12:41:39 PM
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
	 * Load the Shader from a String array. The first group of Strings is
	 * for the sources of the vertex shaders. The next group of Strings
	 * is for the sources of the fragment shaders.
	 * 
	 * @param vertexShaderSources The array of Strings that hold the
	 * 		source for each of the vertex shaders.
	 * @param fragmentShaderSources The array of Strings that hold the
	 * 		source for each of the fragment shaders.
	 */
	public void loadFromSource(String vertexShaderSources[], String fragmentShaderSources[])
	{
		uniformCache = new HashMap<String, Integer>();
		
		int shaderProgram = genShaderProgramId();
		
		for (int i = 0; i < vertexShaderSources.length; i ++)
		{
			int vertexShader = loadVertexShaderFromSource(vertexShaderSources[i]);
			attachShader(vertexShader, shaderProgram);
		}
		
		for (int i = 0; i < fragmentShaderSources.length; i ++)
		{
			int fragmentShader = loadFragmentShaderFromSource(fragmentShaderSources[i]);
			attachShader(fragmentShader, shaderProgram);
		}
		
		genShaderProgram(shaderProgram);
		
		programId = shaderProgram;
	}
	
	/**
	 * Load a Shader from some Files located at the locations given
	 * by the String arrays.
	 * 
	 * @param vertexShaderLocations The String array containing the
	 * 		locations of each of the Vertex Shaders to load.
	 * @param fragmentShaderLocations The String array containing the
	 * 		locations of each of the Fragment Shaders to load.
	 */
	public void loadFromFile(String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		loadFromFile("", vertexShaderLocations, fragmentShaderLocations);
	}
	
	/**
	 * Load a Shader from some Files located at the locations given
	 * by the String arrays. Each of the Strings in the arrays will
	 * be modified to start with the parentDir String given.<br>
	 * <br>
	 * For example:<br>
	 * loadFile("C:/shaders", new String[] { "vert.vs" }, new String[]
	 * 		{ "frag.fs" });<br>
	 * will load the shaders "vert.vs" and "frag.fs" from the folder
	 * located at "C:/shaders"
	 * 
	 * @param parentDir The directory to locate the shaders relative
	 * 		from.
	 * @param vertexShaderLocations The String array containing the
	 * 		locations of each of the Vertex Shaders to load.
	 * @param fragmentShaderLocations The String array containing the
	 * 		locations of each of the Fragment Shaders to load.
	 */
	public void loadFromFile(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
	{
		uniformCache = new HashMap<String, Integer>();
		
		parentDir = parentDir.replace('\\', '/');
		
		if (!parentDir.endsWith("/"))
		{
			parentDir += "/";
		}
		
		int shaderProgram  = genShaderProgramId();
		
		for (int i = 0; i < vertexShaderLocations.length; i ++)
		{
			int vertexShader = loadVertexShaderFromFile(parentDir + vertexShaderLocations[i]);
			attachShader(vertexShader, shaderProgram);
		}
		
		for (int i = 0; i < fragmentShaderLocations.length; i ++)
		{
			int fragmentShader = loadFragmentShaderFromFile(parentDir + fragmentShaderLocations[i]);
			attachShader(fragmentShader, shaderProgram);
		}
		
		genShaderProgram(shaderProgram);
		
		programId = shaderProgram;
	}
	
	/**
	 * Generate an ID number for the Shader to be identified by.
	 * 
	 * @return The ID number.
	 */
	private int genShaderProgramId()
	{
		int id = glCreateProgram();
		
		return id;
	}
	
	/**
	 * Link up the Shader's parts together so it can function.
	 * 
	 * @param programId The ID of the shader to link up.
	 */
	private void genShaderProgram(int programId)
	{
		glLinkProgram(programId);
	}
	
	/**
	 * Run the Shader's files.
	 */
	public void run()
	{
		useShaderProgram(programId);
	}
	
	/**
	 * Stop using the Shader.
	 */
	public void stop()
	{
		useShaderProgram(0);
	}
	
	/**
	 * Set the Shader as the current running program.
	 * 
	 * @param programId The ID number of the Shader to use.
	 */
	private void useShaderProgram(int programId)
	{
		glUseProgram(programId);
		
//		currentProgram = programId == 0 ? currentProgram : programId;
	}
	
//	/**
//	 * 
//	 * 
//	 * @param programId
//	 * @return
//	 */
//	private boolean validateProgram(int programId)
//	{
//		glValidateProgram(programId);
//		
//		return glGetProgrami(programId, GL_VALIDATE_STATUS) != GL_FALSE;
//	}
	
	/**
	 * Delete the Shader program from OpenGL.
	 * 
	 * @param programId The ID number of the Shader to delete.
	 */
	private void deleteProgram(int programId)
	{
		glDeleteProgram(programId);
	}
	
//	/**
//	 * 
//	 * 
//	 * @param shaderId
//	 */
//	private void deleteShader(int shaderId)
//	{
//		glDeleteShader(shaderId);
//	}
	
	/**
	 * Delete the Shader program from the memory.
	 */
	public void delete()
	{
		deleteProgram(programId);
	}
	
	/**
	 * Attach a Shader to the Shader program.
	 * 
	 * @param shaderId The Shader ID number to attach.
	 * @param shaderProgramId The Shader Program ID to attach the Shader
	 * 		to.
	 */
	private void attachShader(int shaderId, int shaderProgramId)
	{
		glAttachShader(shaderProgramId, shaderId);
	}
	
	/**
	 * Load the Vertex Shader from the File at the given location.
	 * 
	 * @param location The location of the Vertex Shader to load.
	 * @return The ID number of the new OpenGL Vertex Shader instance.
	 */
	private int loadVertexShaderFromFile(String location)
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
		
		return loadVertexShaderFromSource(shaderSource.toString());
	}
	
	/**
	 * Load the Vertex Shader from the source given in the source
	 * String parameter.
	 * 
	 * @param source The String instance containing the source of the
	 * 		Vertex Shader to load.
	 * @return The ID number of the new OpenGL Vertex Shader instance.
	 */
	private int loadVertexShaderFromSource(String source)
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
	 * Load the Fragment Shader from the File at the given location.
	 * 
	 * @param location The location of the Fragment Shader to load.
	 * @return The ID number of the new OpenGL Fragment Shader instance.
	 */
	private int loadFragmentShaderFromFile(String location)
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
		
		return loadFragmentShaderFromSource(shaderSource.toString());
	}

	/**
	 * Load the Fragment Shader from the source given in the source
	 * String parameter.
	 * 
	 * @param source The String instance containing the source of the
	 * 		Fragment Shader to load.
	 * @return The ID number of the new OpenGL Fragment Shader instance.
	 */
	private int loadFragmentShaderFromSource(String source)
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
	
//	/**
//	 * 
//	 * 
//	 * @param location
//	 * @return
//	 */
//	private String getName(String location)
//	{
//		int lastI = location.lastIndexOf('/');
//		lastI     = Math.max(location.indexOf('\\'), lastI);
//		
//		return location.substring(lastI + 1, location.length());
//	}
	
	/**
	 * Format the given OpenGL Shader error. The error given was
	 * generated when OpenGL could not correctly compile a Shader.
	 * 
	 * @param error The error message given by OpenGL.
	 * @param fileName The file-name of the problematic Shader file.
	 * @return The formatted error String.
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
	
//	/**
//	 * 
//	 * 
//	 * @param parentDir
//	 * @param vertexShaderLocation
//	 * @param fragmentShaderLocation
//	 * @return
//	 */
//	private int loadShaderProgram(String parentDir, String vertexShaderLocation, String fragmentShaderLocation)
//	{
//		int shaderProgram  = genShaderProgramId();
//		int vertexShader   = loadVertexShaderFromFile(parentDir + vertexShaderLocation);
//		int fragmentShader = loadFragmentShaderFromFile(parentDir + fragmentShaderLocation);
//		attachShader(vertexShader, shaderProgram);
//		attachShader(fragmentShader, shaderProgram);
//		genShaderProgram(shaderProgram);
//		
//		return shaderProgram;
//	}
	
//	/**
//	 * 
//	 * 
//	 * @param parentDir
//	 * @param vertexShaderLocations
//	 * @param fragmentShaderLocations
//	 * @return
//	 */
//	private int loadShaderProgramFromFile(String parentDir, String vertexShaderLocations[], String fragmentShaderLocations[])
//	{
//		parentDir = parentDir.replace('\\', '/');
//		
//		if (!parentDir.endsWith("/"))
//		{
//			parentDir += "/";
//		}
//		
//		int shaderProgram  = genShaderProgramId();
//		
//		for (int i = 0; i < vertexShaderLocations.length; i ++)
//		{
//			int vertexShader = loadVertexShaderFromFile(parentDir + vertexShaderLocations[i]);
//			attachShader(vertexShader, shaderProgram);
//		}
//		
//		for (int i = 0; i < fragmentShaderLocations.length; i ++)
//		{
//			int fragmentShader = loadFragmentShaderFromFile(parentDir + fragmentShaderLocations[i]);
//			attachShader(fragmentShader, shaderProgram);
//		}
//		
//		genShaderProgram(shaderProgram);
//		
//		return shaderProgram;
//	}
	
//	/**
//	 * 
//	 * 
//	 * @param vertexShaderSources
//	 * @param fragmentShaderSources
//	 * @return
//	 */
//	private int loadShaderProgramFromSource(String vertexShaderSources[], String fragmentShaderSources[])
//	{
//		int shaderProgram = genShaderProgramId();
//		
//		for (int i = 0; i < vertexShaderSources.length; i ++)
//		{
//			int vertexShader = loadVertexShaderFromSource(vertexShaderSources[i]);
//			attachShader(vertexShader, shaderProgram);
//		}
//		
//		for (int i = 0; i < fragmentShaderSources.length; i ++)
//		{
//			int fragmentShader = loadFragmentShaderFromSource(fragmentShaderSources[i]);
//			attachShader(fragmentShader, shaderProgram);
//		}
//		
//		genShaderProgram(shaderProgram);
//		
//		return shaderProgram;
//	}
	
	/**
	 * Get the location, according to OpenGL, of the uniform with the
	 * specified name.
	 * 
	 * @param programId The Shader Program to search for the uniform
	 * 		from.
	 * @param uniformName The variable name of the uniform you want to
	 * 		get.
	 * @return The id number of the uniform according to OpenGL.
	 */
	public int getUniformLocation(int programId, String uniformName)
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
	 * Set the integer value of the uniform vec4 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void uniform4i(int uniformLocation, int x, int y, int z, int w)
	{
		glUniform4i(uniformLocation, x, y, z, w);
	}
	
	/**
	 * Set the integer value of the uniform vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void uniform4i(String uniformName, int x, int y, int z, int w)
	{
		glUniform4i(getUniformLocation(programId, uniformName), x, y, z, w);
	}
	
	/**
	 * Set the integer value of the uniform vec3 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void uniform3i(int uniformLocation, int x, int y, int z)
	{
		glUniform3i(uniformLocation, x, y, z);
	}
	
	/**
	 * Set the integer value of the uniform vec3 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
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
	 * Set the integer value of the uniform vec2 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void uniform2i(int uniformLocation, int x, int y)
	{
		glUniform2i(uniformLocation, x, y);
	}
	
	/**
	 * Set the value of the uniform vec2 variable with the specified
	 * name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void uniform2i(String uniformName, int x, int y)
	{
		glUniform2i(getUniformLocation(programId, uniformName), x, y);
	}
	
	/**
	 * Set the value of the uniform integer variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The value to set the integer.
	 */
	public void uniform1i(int uniformLocation, int x)
	{
		glUniform1i(uniformLocation, x);
	}
	
	/**
	 * Set the value of the uniform integer variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The value to set the integer.
	 */
	public void uniform1i(String uniformName, int x)
	{
		glUniform1i(getUniformLocation(programId, uniformName), x);
	}
	
	/**
	 * Set the float value of the uniform vec4 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void uniform4f(int uniformLocation, float x, float y, float z, float w)
	{
		glUniform4f(uniformLocation, x, y, z, w);
	}
	
	/**
	 * Set the float value of the uniform vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void uniform4f(String uniformName, float x, float y, float z, float w)
	{
		int location = getUniformLocation(programId, uniformName);
		
		glUniform4f(location, x, y, z, w);
	}
	
	/**
	 * Set the float value of the uniform vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param data A float array of 4 values to set the uniform variable
	 * 		with.
	 */
	public void uniform4f(String uniformName, float data[])
	{
		int location = getUniformLocation(programId, uniformName);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data).rewind();
		
		GL20.glUniform4(location, buffer);
	}
	
	/**
	 * Set the float value of the uniform vec3 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void uniform3f(int uniformLocation, float x, float y, float z)
	{
		glUniform3f(uniformLocation, x, y, z);
	}
	
	/**
	 * Set the float value of the uniform vec3 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void uniform3f(String uniformName, float x, float y, float z)
	{
		glUniform3f(getUniformLocation(programId, uniformName), x, y, z);
	}
	
	/**
	 * Set the float value of the uniform vec2 variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public static void uniform2f(int uniformLocation, float x, float y)
	{
		glUniform2f(uniformLocation, x, y);
	}
	
	/**
	 * Set the float value of the uniform vec2 variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void uniform2f(String uniformName, float x, float y)
	{
		glUniform2f(getUniformLocation(programId, uniformName), x, y);
	}
	
	/**
	 * Set the value of the uniform float variable with at the
	 * specified location within the Shader.
	 * 
	 * @param uniformLocation The location assigned to the variable
	 * 		by the OpenGL Shader program.
	 * @param x The value to set the float.
	 */
	public void uniform1f(int uniformLocation, float x)
	{
		glUniform1f(uniformLocation, x);
	}
	
	/**
	 * Set the value of the uniform float variable with the
	 * specified name in the Shader.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param x The value to set the float.
	 */
	public void uniform1f(String uniformName, float x)
	{
		glUniform1f(getUniformLocation(programId, uniformName), x);
	}
	
	/**
	 * Set the value of the 2 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix2f(String uniformName, FloatBuffer buffer)
	{
		uniformMatrix2f(uniformName, false, buffer);
	}
	
	/**
	 * Set the value of the 2 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix2f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix2(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Set the value of the 2 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix2f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * Set the value of the 2 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix2f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix2(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Set the value of the 3 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix3f(String uniformName, FloatBuffer buffer)
	{
		uniformMatrix3f(uniformName, false, buffer);
	}
	
	/**
	 * Set the value of the 3 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix3f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix3(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Set the value of the 3 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix3f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * Set the value of the 3 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix3f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix3(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Set the value of the 4 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix4f(String uniformName, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * Set the value of the 4 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param buffer The FloatBuffer object containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix4f(String uniformName, boolean transpose, FloatBuffer buffer)
	{
		glUniformMatrix4(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Set the value of the 4 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix4f(String uniformName, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(programId, uniformName), false, buffer);
	}
	
	/**
	 * Set the value of the 4 dimensional float Shader matrix with
	 * the specified uniform name.
	 * 
	 * @param uniformName The name of the uniform variable that is to
	 * 		be set.
	 * @param transpose Whether or not to transpose the values for the
	 * 		uniform matrix.
	 * @param array The float array containing the values to
	 * 		set the uniform matrix with.
	 */
	public void uniformMatrix4f(String uniformName, boolean transpose, float array[])
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.rewind();
		
		glUniformMatrix4(getUniformLocation(programId, uniformName), transpose, buffer);
	}
	
	/**
	 * Get the location, according to OpenGL, of the attrib with the
	 * specified name.
	 * 
	 * @param programId The Shader Program to search for the attrib
	 * 		from.
	 * @param attribName The variable name of the attrib you want to
	 * 		get.
	 * @return The id number of the attrib according to OpenGL.
	 */
	public int getAttribLocation(int programId, String attribName)
	{
		return glGetAttribLocation(programId, attribName);
	}
	
	/**
	 * Set the short value of the attrib vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void vertexAttrib4s(int attribLocation, short x, short y, short z, short w)
	{
		glVertexAttrib4s(attribLocation, x, y, z, w);
	}
	
	/**
	 * Set the short value of the attrib vec3 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void vertexAttrib3s(int attribLocation, short x, short y, short z)
	{
		glVertexAttrib3s(attribLocation, x, y, z);
	}
	
	/**
	 * Set the short value of the attrib vec2 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void vertexAttrib2s(int attribLocation, short x, short y)
	{
		glVertexAttrib2s(attribLocation, x, y);
	}
	
	/**
	 * Set the value of the short attrib variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The short value to set the attrib variable to.
	 */
	public void vertexAttrib1s(int attribLocation, short x)
	{
		glVertexAttrib1s(attribLocation, x);
	}
	
	/**
	 * Set the float value of the attrib vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void vertexAttrib4f(int attribLocation, float x, float y, float z, float w)
	{
		glVertexAttrib4f(attribLocation, x, y, z, w);
	}
	
	/**
	 * Set the float value of the attrib vec3 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void vertexAttrib3f(int attribLocation, float x, float y, float z)
	{
		glVertexAttrib3f(attribLocation, x, y, z);
	}
	
	/**
	 * Set the float value of the attrib vec2 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void vertexAttrib2f(int attribLocation, float x, float y)
	{
		glVertexAttrib2f(attribLocation, x, y);
	}
	
	/**
	 * Set the value of the float attrib variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The float value to set the attrib variable to.
	 */
	public void vertexAttrib1f(int attribLocation, float x)
	{
		glVertexAttrib1f(attribLocation, x);
	}
	
	/**
	 * Set the double value of the attrib vec4 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec4.
	 * @param y The second value to set in the vec4.
	 * @param z The third value to set in the vec4.
	 * @param w The fourth value to set in the vec4.
	 */
	public void vertexAttrib4d(int attribLocation, double x, double y, double z, double w)
	{
		glVertexAttrib4d(attribLocation, x, y, z, w);
	}
	
	/**
	 * Set the double value of the attrib vec3 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec3.
	 * @param y The second value to set in the vec3.
	 * @param z The third value to set in the vec3.
	 */
	public void vertexAttrib3d(int attribLocation, double x, double y, double z)
	{
		glVertexAttrib3d(attribLocation, x, y, z);
	}
	
	/**
	 * Set the double value of the attrib vec2 variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The first value to set in the vec2.
	 * @param y The second value to set in the vec2.
	 */
	public void vertexAttrib2d(int attribLocation, double x, double y)
	{
		glVertexAttrib2d(attribLocation, x, y);
	}
	
	/**
	 * Set the value of the double attrib variable with the
	 * specified name in the Shader.
	 * 
	 * @param attribLocation The name of the attrib variable that is to
	 * 		be set.
	 * @param x The double value to set the attrib variable to.
	 */
	public void vertexAttrib1d(int attribLocation, double x)
	{
		glVertexAttrib1d(attribLocation, x);
	}
}