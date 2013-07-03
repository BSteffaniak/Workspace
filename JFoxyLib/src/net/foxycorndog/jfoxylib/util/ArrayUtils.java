package net.foxycorndog.jfoxylib.util;

/**
 * Class used for declaring utility method for arrays.
 * 
 * @author	Braden Steffaniak
 * @since	Jul 2, 2013 at 6:47:13 PM
 * @since	v0.2
 * @version	Jul 2, 2013 at 6:47:13 PM
 * @version	v0.2
 */
public class ArrayUtils
{
	/**
	 * Search for the specified Object in the array given.
	 * 
	 * @param needle The Object to search for in the array.
	 * @param haystack The array to search for the Object in.
	 * @return Whether or not the Object is in the array.
	 */
	public static <E> boolean contains(E needle, E haystack[])
	{
		return indexOf(needle, haystack) > -1;
	}
	
	/**
	 * Calculate how many occurrences of the specified Object are in the
	 * array given.
	 * 
	 * @param needle The Object to search for.
	 * @param haystack The array to search for the Object in.
	 * @return The amount of occurrences of the Object that are in the
	 * 		array.
	 */
	public static <E> int occurrences(E needle, E haystack[])
	{
		int num = 0;
		
		for (int i = 0; i < haystack.length; i++)
		{
			if (needle.equals(haystack[i]))
			{
				num++;
			}
		}
		
		return num;
	}
	
	/**
	 * Calculate the first index of the Object in the given array. If the
	 * Object does not exist in the array, this method returns -1.
	 * 
	 * @param needle The Object to get the index of in the array.
	 * @param haystack The array to search for the Object in.
	 * @return The first index of the Object in the array.
	 */
	public static <E> int indexOf(E needle, E haystack[])
	{
		return indexOf(needle, haystack, 0);
	}
	
	/**
	 * Calculate the first index of the Object in the given array at, or
	 * after the startIndex. If the Object does not exist in the array
	 * at, or after the startIndex, this method returns -1.
	 * 
	 * @param needle The Object to get the index of in the array.
	 * @param haystack The array to search for the Object in.
	 * @param startIndex The index in the array to start looking for the
	 * 		Object at.
	 * @return The first index of the Object in the array at, or after the
	 * 		start index.
	 */
	public static <E> int indexOf(E needle, E haystack[], int startIndex)
	{
		for (int i = startIndex; i < haystack.length; i++)
		{
			if (needle.equals(haystack[i]))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Calculate the last index of the Object in the given array. If
	 * the Object does not exist in the array, this method returns -1.
	 * 
	 * @param needle The Object to get the index of in the array.
	 * @param haystack The array to search for the Object in.
	 * @return The last index of the Object in the array.
	 */
	public static <E> int lastIndexOf(E needle, E haystack[])
	{
		return lastIndexOf(needle, haystack, haystack.length - 1);
	}
	
	/**
	 * Calculate the first index of the Object in the given array at, or
	 * before the startIndex. If the Object does not exist in the array
	 * at, or before the startIndex, this method returns -1.
	 * 
	 * @param needle The Object to get the index of in the array.
	 * @param haystack The array to search for the Object in.
	 * @param startIndex The index in the array to start looking for the
	 * 		Object at.
	 * @return The last index of the Object in the array at, or before the
	 * 		start index.
	 */
	public static <E> int lastIndexOf(E needle, E haystack[], int startIndex)
	{
		for (int i = startIndex; i >= 0; i--)
		{
			if (needle.equals(haystack[i]))
			{
				return i;
			}
		}
		
		return -1;
	}
}