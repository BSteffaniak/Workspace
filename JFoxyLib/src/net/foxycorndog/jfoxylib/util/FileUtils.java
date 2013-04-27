package net.foxycorndog.jfoxylib.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:21:30 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:21:30 PM
 * @version	v0.2
 */
public class FileUtils
{
	/**
	 * 
	 * 
	 * @param str
	 * @param endings
	 * @return
	 */
	public static boolean contains(String str, String endings[])
	{
		for (String ending : endings)
		{
			if (str.equals(ending))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * 
	 * @param file
	 * @return
	 */
	public static boolean delete(File file)
	{
		if (file.isDirectory())
		{
			File subFiles[] = file.listFiles();
			
			for (int i = 0; i < subFiles.length; i ++)
			{
				delete(subFiles[i]);
			}
		}
		
		return file.delete();
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static boolean isFileName(String location)
	{
		location = removeEndingSlashes(location);
		
		for (int i = location.length() - 1; i >= 0 && location.charAt(i) != '/'; i --)
		{
			if (location.charAt(i) == '.')
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static String getParentFolder(String location)
	{
		location = removeEndingSlashes(location);
		
		int index = location.length() - 1;
		
		while (index >= 0)
		{
			if (location.charAt(index) == '/')
			{
				index--;
				
				break;
			}
			else
			{
				index--;
			}
		}
		
		return removeEndingSlashes(location.substring(0, index + 1));
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static String removeEndingSlashes(String location)
	{
		int lastIndex = location.length() - 1;
		
		while (lastIndex >= 0 && location.charAt(lastIndex) == '/')
		{
			lastIndex--;
		}
		
		return location.substring(0, lastIndex + 1);
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static String getFileName(String location)
	{
		location       = location.replace('\\', '/');
		
		location       = removeEndingSlashes(location);
		
		int firstIndex = location.lastIndexOf('/') + 1;
		
		return location.substring(firstIndex, location.length());
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static String getFileNameWithoutExtension(String location)
	{
		location = getFileName(location);
		
		int lastIndex = location.lastIndexOf('.');
		
		if (lastIndex > 0)
		{
			location = location.substring(0, lastIndex);
		}
		
		return location;
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public static String removeExtension(String location)
	{
		int lastIndex = location.lastIndexOf('.');
		
		if (lastIndex > 0)
		{
			location = location.substring(0, lastIndex);
		}
		
		return location;
	}
	
	/**
	 * 
	 * 
	 * @param path
	 * @param relativeTo
	 * @return
	 */
	public static String getPrecedingPath(String path, String relativeTo)
	{
		File relativeFile = new File(relativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				relativeTo = getParentFolder(relativeTo);
			}
			
			String folderName = "/" + getFileName(relativeTo) + "/";
			
			int index = path.lastIndexOf(folderName);
			
			return path.substring(0, index);
//		}
//		else
//		{
//			throw new IllegalArgumentException('"' + relativeTo + '"' + " must be an existing location.");
//		}
	}
	
	/**
	 * 
	 * 
	 * @param path
	 * @param relativeTo
	 * @return
	 */
	public static String getPathRelativeTo(String path, String relativeTo)
	{
		File relativeFile = new File(relativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				relativeTo = getParentFolder(relativeTo);
			}
			
			String folderName = "/" + getFileName(relativeTo) + "/";
			
			int index = path.lastIndexOf(folderName);
			
			return path.substring(index + folderName.length());
//		}
//		else
//		{
//			throw new IllegalArgumentException('"' + relativeTo + '"' + " must be an existing location.");
//		}
	}
	
	/**
	 * Read the text from a File and store it in a String array.
	 * 
	 * @param location the location of the file to read from.
	 * @return A String array filled with each line in order.
	 */
	public static String[] readFile(String location)
	{
		File file = new File(location);
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			ArrayList<String> temp = new ArrayList<String>();
			
			String line = reader.readLine();
			
			while (line != null)
			{
				temp.add(line);
				
				line = reader.readLine();
			}
			
			reader.close();
			
			return temp.toArray(new String[0]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @param text
	 */
	public static void writeFile(String location, String text)
	{
		File file = new File(location);
		
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			
			writer.print(text);
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public static String getAbsolutePath(String location) throws IOException
	{
		File fileLocation = new File(location);
		
		String loc        = fileLocation.getCanonicalPath();
		
		return loc.replace('\\', '/');
	}
}