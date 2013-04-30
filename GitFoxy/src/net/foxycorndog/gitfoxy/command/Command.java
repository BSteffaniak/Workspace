package net.foxycorndog.gitfoxy.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import net.foxycorndog.gitfoxy.event.CommandEvent;
import net.foxycorndog.gitfoxy.event.CommandListener;

public class Command
{
	private	String		directory;
	
	private	PrintWriter	writer;
	
	private	Process		process;
	
	private	String		commands[];
	
	private	ArrayList<CommandListener>	listeners;
	
	public Command(String command)
	{
		this(command, null);
	}
	
	public Command(String command, String directory)
	{
		this(new String[] { command }, directory);
	}
	
	public Command(String commands[])
	{
		this(commands, null);
	}
	
	public Command(String commands[], String directory)
	{
		this.commands  = commands;
		this.directory = directory;
		
		listeners = new ArrayList<CommandListener>();
	}
	
	/**
	 * Execute the specified commands in order of index.
	 * 
	 * @param commands The String commands to be executed.
	 * @param directory The working directory to execute the command in.
	 */
	public void execute()
	{
		final ProcessBuilder builder = new ProcessBuilder(commands);
		
		if (directory != null)
		{
			File dir = new File(directory);
			
			builder.directory(dir);
		}
		
		final Command thisCommand = this;
		
		new Thread()
		{
			public void run()
			{
				try
				{
					process = builder.start();
					
					LogStreamReader input = new LogStreamReader(process.getInputStream(), false, thisCommand, listeners);
					Thread thread = new Thread(input, "LogStreamReader-input");
					thread.start();
					
					LogStreamReader error = new LogStreamReader(process.getErrorStream(), true, thisCommand, listeners);
					Thread thread2 = new Thread(error, "LogStreamReader-error");
					thread2.start();
					
//					LogStreamWriter output2 = new LogStreamWriter(process.getOutputStream(), false, listeners);
//					Thread thread3 = new Thread(output2, "LogStreamReader-output");
//					thread3.start();
//					process.getOutputStream().close();
					
					writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
					
					CommandEvent event = new CommandEvent(null, -1, thisCommand);
					
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						CommandListener listener = listeners.get(i);
						
						listener.onCommandStarted(event);
					}
					
					try
					{
						int wait  = process.waitFor();
						
						int value = process.exitValue();
						
						String output = input.getOutput() + error.getOutput();
						
						event = new CommandEvent(output, value, thisCommand);
						
						for (int i = listeners.size() - 1; i >= 0; i--)
						{
							CommandListener listener = listeners.get(i);
							
							listener.onResultReceived(event);
						}
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					System.out.println("done");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void addCommandListener(CommandListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeCommandListener(CommandListener listener)
	{
		listeners.remove(listener);
	}
	
	public PrintWriter getWriter()
	{
		return writer;
	}
	
	public Process getProcess()
	{
		return process;
	}
}

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 24, 2013 at 1:35:58 AM
 * @since	v0.1
 * @version Mar 24, 2013 at 1:35:58 AM
 * @version	v0.1
 */
class LogStreamReader implements Runnable
{
	private boolean			running;
	private	boolean			error;

	private String			line;
	
	private	StringBuilder	output;

	private BufferedReader	reader;
	
	private	Command			thisCommand;
	
	private	ArrayList<CommandListener>	listeners;
	
    public LogStreamReader(InputStream is, boolean error, Command thisCommand, ArrayList<CommandListener> listeners)
    {
        this.reader      = new BufferedReader(new InputStreamReader(is));
        
        this.listeners   = listeners;
        
        this.error       = error;
        
        this.thisCommand = thisCommand;
        
        running          = true;
        
        output           = new StringBuilder();
    }

    public void run()
    {
        try
        {
            while ((line = reader.readLine()) != null)
            {
//            	line = line.replace(location, "");
            	
            	if (running)
            	{
            		output.append(line + "\n");
            		System.out.println(line);
            		CommandEvent event = new CommandEvent(line, -1, thisCommand);
					
					for (int i = listeners.size() - 1; i >= 0; i--)
					{
						CommandListener listener = listeners.get(i);
						
						if (error)
						{
							listener.onErrorReceived(event);
						}
						else
						{
							listener.onOutputReceived(event);
						}
					}
            	}
            	else
            	{
//            		return;
            	}
            }
            
            line = null;
            
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void stop()
    {
    	running = false;
    }
    
    public String getOutput()
    {
    	return output.toString();
    }
}

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Mar 24, 2013 at 1:35:58 AM
 * @since	v0.1
 * @version Mar 24, 2013 at 1:35:58 AM
 * @version	v0.1
 */
class LogStreamWriter implements Runnable
{
	private boolean			running;
	private	boolean			error;

	private String			line;
	
	private	StringBuilder	output;

	private PrintWriter		writer;
	
	private	ArrayList<CommandListener>	listeners;
	
    public LogStreamWriter(OutputStream os, boolean error, ArrayList<CommandListener> listeners)
    {
        this.writer    = new PrintWriter(new OutputStreamWriter(os));
        
        this.listeners = listeners;
        
        this.error     = error;
        
        running        = true;
        
        output         = new StringBuilder();
    }

    public void run()
    {
    	writer.print("asdf");
		writer.print(System.getProperty("line.separator"));
		writer.flush();
		
    	writer.print("asdf2");
		writer.print(System.getProperty("line.separator"));
		writer.flush();
		
        writer.close();
    }
    
    public void stop()
    {
    	running = false;
    }
    
    public String getOutput()
    {
    	return output.toString();
    }
}