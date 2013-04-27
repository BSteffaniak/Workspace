package net.foxycorndog.arrowide;

/**
 * Class used to organize the data that is used for each Program that
 * is ran.
 * 
 * @author	Braden Steffaniak
 * @since	Mar 23, 2013 at 11:11:12 PM
 * @since	v0.2
 * @version Mar 23, 2013 at 11:11:12 PM
 * @version	v0.2
 */
public class Program
{
	private String			name;
	
	private StringBuilder	text;
	
	private Process 		process;
	
	/**
	 * Create a Program with the specified Process.
	 * 
	 * @param process The Process that is linked with this Program.
	 */
	public Program(Process process, String name)
	{
		this.process = process;
		
		this.name    = name;
		
		this.text    = new StringBuilder();
	}
	
	/**
	 * Get the name of the Program.
	 * 
	 * @return The name of the Program.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get the process that is linked with this Program.
	 * 
	 * @return The process that is linked with this Program.
	 */
	public Process getProcess()
	{
		return process;
	}
	
	/**
	 * Get the text of the Program's console.
	 * 
	 * @return The text of the Program's console.
	 */
	public String getText()
	{
		return text.toString();
	}
	
	/**
	 * Append a String to the text of the Program.
	 * 
	 * @param text The String to append.
	 */
	public void append(String text)
	{
		this.text.append(text);
	}
}