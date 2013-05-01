package net.foxycorndog.gitfoxy.command;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
	private	boolean		running;
	
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
		
//		builder.redirectErrorStream(true);
		
		if (directory != null)
		{
			File dir = new File(directory);
			
			builder.directory(dir);
		}
		
		final Command thisCommand = this;
		
		new Thread("Command Thread")
		{
			public void run()
			{
				try
				{
					process = builder.start();
					
					running = true;
					
					LogStreamReader error = new LogStreamReader(process.getErrorStream(), true, thisCommand, listeners);
					error.start();
					
					LogStreamReader input = new LogStreamReader(process.getInputStream(), false, thisCommand, listeners);
					input.start();
//					Thread thread = new Thread(input, "LogStreamReader-input");
//					thread.start();
					
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
						
//						int value = process.exitValue();
						
						String output = input.getOutput() + error.getOutput();
						
						event = new CommandEvent(output, 4, thisCommand);
						
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
	
	public void destroy()
	{
		process.destroy();
		
		running = false;
	}
	
	public boolean isRunning()
	{
		return running;
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
class LogStreamReader extends Thread
{
	private	boolean			error;
	
	private	int				offset;

	private String			line;
	
	private	StringBuilder	output;

	private BufferedReader	reader;
	private	InputStream		input;
	
	private	Command			thisCommand;
	
	private	ArrayList<CommandListener>	listeners;
	
    public LogStreamReader(InputStream is, boolean error, Command thisCommand, ArrayList<CommandListener> listeners)
    {
        this.reader      = new BufferedReader(new InputStreamReader(is));
        this.input       = is;
        
        this.listeners   = listeners;
        
        this.error       = error;
        
        this.thisCommand = thisCommand;
        
        output           = new StringBuilder();
    }

    public void run()
    {
        try
        {
        	System.out.println("bef");
//        	line = reader.readLine();

            line = read();
            System.out.println("aft");
        	
            while (thisCommand.isRunning() && line != null)
            {
//            	line = line.replace(location, "");
            	
        		output.append(line + "");
        		System.out.println(line + "!");
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
            	
            	line = read();
            }
            System.out.println("finished");
            
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private String read()
    {
//    	int    nRead = 0;
//        byte[] data  = new byte[4 * 4 * 4 * 4 * 4 * 4 * 4];
//            
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//        try
//		{
//			while ((nRead = input.read(data, offset, data.length - offset)) != -1)
//			{
//				
//				buffer.write(data, 0, nRead);
//			}
//			
//			buffer.flush();
//        
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//        
//        String line = new String(buffer.toByteArray());
//        
//        if (line.length() == 0)
//        {
//        	return null;
//        }
//        
//        offset += line.length();
    	
    	String line = null;
		try
		{
			line = reader.readLine();
			
			if (line == null)
			{
				return null;
			}
			
			line += "\n";
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        
        return line;
    }
    
    public String getOutput()
    {
    	return output.toString();
    }
}

///**
// * 
// * 
// * @author	Braden Steffaniak
// * @since	Mar 24, 2013 at 1:35:58 AM
// * @since	v0.1
// * @version Mar 24, 2013 at 1:35:58 AM
// * @version	v0.1
// */
//class LogStreamWriter implements Runnable
//{
//	private boolean			running;
//	private	boolean			error;
//
//	private String			line;
//	
//	private	StringBuilder	output;
//
//	private PrintWriter		writer;
//	
//	private	ArrayList<CommandListener>	listeners;
//	
//    public LogStreamWriter(OutputStream os, boolean error, ArrayList<CommandListener> listeners)
//    {
//        this.writer    = new PrintWriter(new OutputStreamWriter(os));
//        
//        this.listeners = listeners;
//        
//        this.error     = error;
//        
//        running        = true;
//        
//        output         = new StringBuilder();
//    }
//
//    public void run()
//    {
//    	writer.print("asdf");
//		writer.print(System.getProperty("line.separator"));
//		writer.flush();
//		
//    	writer.print("asdf2");
//		writer.print(System.getProperty("line.separator"));
//		writer.flush();
//		
//        writer.close();
//    }
//    
//    public void stop()
//    {
//    	running = false;
//    }
//    
//    public String getOutput()
//    {
//    	return output.toString();
//    }
//}