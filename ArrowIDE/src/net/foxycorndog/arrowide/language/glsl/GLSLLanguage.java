package net.foxycorndog.arrowide.language.glsl;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glShaderSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.event.CompilerEvent;
import net.foxycorndog.arrowide.event.CompilerListener;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.CompileOutput;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;

public class GLSLLanguage
{
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0),
			VERSION_COLOR = new Color(Display.getCurrent(), 180, 180, 0);
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties("//", "/*", "*/", new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(new char[] {  }, new char[] { '=' }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		GLSLKeyword.init();
	}
	
	public static void run(String classLocation, ConsoleStream stream)
	{
		
	}
	
	public static void loadVertexShader(String fileLocation, String shaderCode, PrintStream stream, ArrayList<CompilerListener> compilerListeners)
	{
		String name = FileUtils.getFileName(fileLocation);
		
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexShader, shaderCode);
		glCompileShader(vertexShader);
		
		String error = "";
		
		boolean successful = glGetShader(vertexShader, GL_COMPILE_STATUS) != GL_FALSE;
		
		if (!successful)
		{
			error = glGetShaderInfoLog(vertexShader, glGetShader(vertexShader, GL_INFO_LOG_LENGTH));
			
			error = "Vertex shader at \"" + name + "\" was not compiled correctly:\n" + formatError(error, name, true);
			
			stream.println(error);
		}
		
		final String outputFiles[] = new String[] {};
		
		CompileOutput outputs[] = new CompileOutput[1];
		
		outputs[0] = new CompileOutput(0, 0, 0, successful ? 0 : 1, error);
		
		for (int i = compilerListeners.size() - 1; i >= 0; i--)
		{
			CompilerEvent event = new CompilerEvent(outputFiles, outputs, stream, fileLocation);
			
			compilerListeners.get(i).compiled(event);
		}
	}
	
	public static void loadFragmentShader(String fileLocation, String shaderCode, PrintStream stream, ArrayList<CompilerListener> compilerListeners)
	{
		String name = FileUtils.getFileName(fileLocation);
		
		int fragmentShader   = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentShader, shaderCode);
		glCompileShader(fragmentShader);
		
		String error = "";
		
		boolean successful = glGetShader(fragmentShader, GL_COMPILE_STATUS) != GL_FALSE;
		
		if (!successful)
		{
			error = glGetShaderInfoLog(fragmentShader, glGetShader(fragmentShader, GL_INFO_LOG_LENGTH));
			
			error = "Fragment shader at \"" + name + "\" was not compiled correctly:\n" + formatError(error, name, true);
			
			stream.println(error);
		}
		
		final String outputFiles[] = new String[] {};
		
		CompileOutput outputs[] = new CompileOutput[1];
		
		outputs[0] = new CompileOutput(0, 0, 0, successful ? 0 : 1, "ASDF");
		
		for (int i = compilerListeners.size() - 1; i >= 0; i--)
		{
			CompilerEvent event = new CompilerEvent(outputFiles, outputs, stream, fileLocation);
			
			compilerListeners.get(i).compiled(event);
		}
	}
	
	public static int loadVertexShaderFromFile(String location)
	{
		int vertexShader   = glCreateShader(GL_VERTEX_SHADER);
		
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
		
		glShaderSource(vertexShader, shaderSource);
		glCompileShader(vertexShader);
		
		if (glGetShader(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(vertexShader, glGetShader(vertexShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, getName(location), true);
			
			throw new RuntimeException("Vertex shader at \"" + location + "\" was not compiled correctly:\n\t" + error);
		}
		
		return vertexShader;
	}
	
	public static int loadFragmentShaderFromFile(String location)
	{
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		
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
		
		glShaderSource(fragmentShader, shaderSource);
		glCompileShader(fragmentShader);
		
		if (glGetShader(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			String error = glGetShaderInfoLog(fragmentShader, glGetShader(fragmentShader, GL_INFO_LOG_LENGTH));
			
			error = formatError(error, getName(location), true);
			
			throw new RuntimeException("Fragment shader at \"" + location + "\" was not compiled correctly:\n\t" + error);
		}
		
		return fragmentShader;
	}
	
	private static String getName(String location)
	{
		int lastI = location.lastIndexOf('/');
		lastI     = Math.max(location.indexOf('\\'), lastI);
		
		return location.substring(lastI + 1, location.length());
	}
	
	private static String formatError(String error, String fileName, boolean tabs)
	{
		String formattedError = tabs ? "\t" : "";
		
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
					
					if (tabs && error.charAt(i) == '\n')
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
}