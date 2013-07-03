package net.foxycorndog.jfoxylib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class used for declaring utility methods for files.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:21:30 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 7:11:30 PM
 * @version	v0.2
 */
public class FileUtils
{
	/**
	 * Delete the specified file from the Operating System's file system.
	 * If the file is a directory and contains other files, recursively
	 * delete the contents of the directory.
	 * 
	 * @param file The File instance to dele from the OS's file system.
	 * @return Whether or not the file was successfully deleted.
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
	
//	/**
//	 * 
//	 * 
//	 * @param location
//	 * @return
//	 */
//	public static boolean isFileName(String location)
//	{
//		location = removeEndingSlashes(location);
//		
//		for (int i = location.length() - 1; i >= 0 && location.charAt(i) != '/'; i --)
//		{
//			if (location.charAt(i) == '.')
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	/**
	 * Get the parent directory of the location given.
	 * 
	 * @param location The location to get the parent folder of.
	 * @return The parent folder's location.
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
	 * Remove any of the ending forward slashes from the String Object
	 * given.
	 * 
	 * @param location The String instance to remove the ending forward
	 * 		slashes from.
	 * @return A new String containing the String without ending forward
	 * 		slashes.
	 */
	public static String removeEndingSlashes(String location)
	{
		int lastIndex = location.length() - 1;
		
		while (lastIndex >= 0 && location.charAt(lastIndex) == '/')
		{
			lastIndex--;
		}
		
		String newStr = null;
		
		if (lastIndex == location.length() - 1)
		{
			newStr = location;
		}
		else
		{
			newStr = location.substring(0, lastIndex + 1);
		}
		
		return newStr;
	}
	
	/**
	 * Get the name of the file at the given location.
	 * 
	 * @param location The location of the file to get the name of.
	 * @return The file's name without the preceding path.
	 */
	public static String getFileName(String location)
	{
		location       = location.replace('\\', '/');
		
		location       = removeEndingSlashes(location);
		
		int firstIndex = location.lastIndexOf('/') + 1;
		
		return location.substring(firstIndex, location.length());
	}
	
	/**
	 * Get the name of the file at the given location without its
	 * file extension (if it contains one).
	 * 
	 * @param location The location of the file to get the name of.
	 * @return The file's name without the preceding path and extension.
	 */
	public static String getFileNameWithoutExtension(String location)
	{
		location = getFileName(location);
		
		String name = removeExtension(location);
		
		return name;
	}
	
	/**
	 * Remove the extension from the specified String instance.
	 * 
	 * @param location The String instance to remove the extension from.
	 * @return The location without the extension.
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
	 * Get the preceding path before the directory's location.<br>
	 * For example:<br>
	 * getPrecedingPath("C:/dir1/test/file53.txt", "/test/") would return
	 * "C:/dir1"
	 * 
	 * @param path The path to get the preceding path from.
	 * @param directoryRelativeTo The directory to split the path at
	 * 		and get the preceding location from.
	 * @return The preceding path before the directoryRelativeTo location.
	 */
	public static String getPrecedingPath(String path, String directoryRelativeTo)
	{
		File relativeFile = new File(directoryRelativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				directoryRelativeTo = getParentFolder(directoryRelativeTo);
			}
			
			String folderName = "/" + getFileName(directoryRelativeTo) + "/";
			
			int index = path.lastIndexOf(folderName);
			
			return path.substring(0, index);
//		}
//		else
//		{
//			throw new IllegalArgumentException('"' + relativeTo + '"' + " must be an existing location.");
//		}
	}
	
	/**
	 * Get the path after the directoryRelativeTo location.<br>
	 * For example:<br>
	 * getPathRelativeTo("C:/dir1/test/file53.txt", "/test/") would return
	 * "file53.txt"
	 * 
	 * @param path The path to get the location relative to.
	 * @param directoryRelativeTo The directory to get the path after.
	 * @return The path that is relative to both parameters.
	 */
	public static String getPathRelativeTo(String path, String directoryRelativeTo)
	{
		File relativeFile = new File(directoryRelativeTo);
		
//		if (relativeFile.exists())
//		{
			if (relativeFile.exists() && !relativeFile.isDirectory())
			{
				directoryRelativeTo = getParentFolder(directoryRelativeTo);
			}
			
			String folderName = "/" + getFileName(directoryRelativeTo) + "/";
			
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
	 * Write the specified text to the file at the specified location.
	 * 
	 * @param location The location of the file to write to.
	 * @param text The text to write to the file.
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
	 * Get the absolute path of a file given the path (relative or
	 * absolute) of a file.
	 * 
	 * @param location The location of the file to get the absolute path
	 * 		to.
	 * @return The absolute path to the file at the specified location.
	 * @throws IOException Thrown if there is an error finding the file.
	 */
	public static String getAbsolutePath(String location) throws IOException
	{
		File fileLocation = new File(location);
		
		String loc        = fileLocation.getCanonicalPath();
		
		return loc.replace('\\', '/');
	}
}