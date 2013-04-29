package net.foxycorndog.gitfoxy.command;

import java.io.File;
import java.io.IOException;

public class Command
{
	/**
	 * Execute the specified command.
	 * 
	 * @param command The String command to be executed.
	 */
	public static void execute(String command)
	{
		execute(new String[] { command });
	}
	
	public static void execute(String commands[])
	{
		execute(commands, null);
	}
	
	/**
	 * Execute the specified commands in order of index.
	 * 
	 * @param commands The String commands to be executed.
	 * @param directory The working directory to execute the command in.
	 */
	public static void execute(String commands[], String directory)
	{
		final ProcessBuilder builder = new ProcessBuilder(commands);
		
		if (directory != null)
		{
			File dir = new File(directory);
			
			builder.directory(dir);
		}
		
		new Thread()
		{
			public void run()
			{
				try
				{
					Process process = builder.start();
					
					try
					{
						process.waitFor();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
}