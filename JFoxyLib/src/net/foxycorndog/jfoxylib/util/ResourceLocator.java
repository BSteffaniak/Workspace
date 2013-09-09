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
 * @since	v0.2
 * @version	Jun 28, 2013 at 12:57:07 PM
 * @version	v0.2
 */
public class ResourceLocator
{
	private static String		nativesLocation;
	
	private static final String	projectDirectory;
	
	/**
	 * Locate the project.
	 */
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
	
	/**
	 * Get the root directory that the JFoxyLib project is located.
	 * 
	 * @return A String instance containing the root directory location
	 * 		of the JFoxyLib project.
	 */
	public static String getProjectDirectory()
	{
		return projectDirectory;
	}
	
	/**
	 * Get the location of the natives folder in the file system.
	 * If the value is null, the natives are searched for in the working
	 * directory.
	 * 
	 * @return The location of the natives folder in the file system.
	 */
	public static String getNativesLocation()
	{
		return nativesLocation;
	}
	
	/**
	 * Set the location to search for the natives folder in.
	 * 
	 * @param nativesLocation The parent directory of the natives folder.
	 */
	public static void setNativesLocation(String nativesLocation)
	{
		if (!nativesLocation.endsWith("/"))
		{
			nativesLocation += "/";
		}
		
		ResourceLocator.nativesLocation = nativesLocation;
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
		
		String nativeLocation = null;
		
		if (nativesLocation == null)
		{
			nativeLocation = projectDirectory + "native/" + os;
		}
		else
		{
			nativeLocation = nativesLocation + "native/" + os;
		}
		
//		System.setProperty("java.library.path", System.getProperty("java.library.path") + ";" + nativeLocation + ";");
		System.setProperty("org.lwjgl.librarypath", nativeLocation);
	}
}