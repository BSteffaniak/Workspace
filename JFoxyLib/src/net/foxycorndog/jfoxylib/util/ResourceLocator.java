package net.foxycorndog.jfoxylib.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class used as a utility to located any resources for this
 * project.
 * 
 * @author	Braden Steffaniak
 * @since	Jun 28, 2013 at 12:57:07 PM
 * @since	v0.1
 * @version	Jun 28, 2013 at 12:57:07 PM
 * @version	v0.1
 */
public class ResourceLocator
{
	private	static	final	String	projectDirectory;
	
	static
	{
		String dir = null;
		
		try
		{
			String classLoc = GL.class.getResource("GL.class").toString();
			
			boolean jar = classLoc.startsWith("jar:") || classLoc.startsWith("rsrc:");
			
//			if (jar)
//			{
//				resLoc = new File(new File(System.getProperty("java.class.path")).getCanonicalPath()).getParentFile().getCanonicalPath();
//			}
			
			if (jar)
			{
				String workingDirectory = null;
				
				if (classLoc.startsWith("rsrc:"))
				{
					try
					{
						workingDirectory = new File(GL.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getCanonicalPath();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					// Maybe use canonical path instead.
					workingDirectory = new File(classLoc.substring(9, classLoc.lastIndexOf('!'))).getParentFile().getAbsolutePath();
				}
					
				try
				{
					workingDirectory = java.net.URLDecoder.decode(workingDirectory, "UTF-8");
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
				
				dir = workingDirectory;
			}
			else
			{
				File f = new File(GL.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			
				File parent = f.getParentFile();
				
				String resLoc = null;
				
				try
				{
					resLoc = parent.getCanonicalPath();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			
				dir = resLoc;// + "/native/" + os;
			}
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		
		dir = dir.replace('\\', '/');
		
		if (!dir.endsWith("/"))
		{
			dir += "/";
		}
		
		projectDirectory = dir;
	}
	
	public static String getProjectDirectory()
	{
		return projectDirectory;
	}
	
	/**
	 * Locate the native files needed to run the LWJGL library. They
	 * should be located in a folder labeled "native" in the same
	 * directory that the JFoxyLib is located in.
	 */
	public static void locateNatives()
	{
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.startsWith("win"))
		{
			os = "windows";
		}
		else if (os.startsWith("mac"))
		{
			os = "macosx";
		}
		else if (os.startsWith("lin"))
		{
			os = "linux";
		}
		else if (os.startsWith("sol"))
		{
			os = "solaris";
		}
		else
		{
			throw new RuntimeException("Unknown operating system!");
		}
		
		String nativeLocation = projectDirectory + "native/" + os;
		
//		System.setProperty("java.library.path", System.getProperty("java.library.path") + ";" + nativeLocation + ";");
		System.setProperty("org.lwjgl.librarypath", nativeLocation);
	}
}