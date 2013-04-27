package net.foxycorndog.arrowide.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.Program;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.file.FileUtils;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class Command
{
	private String                      directory, line;
	
	private Program						program;
	
	private Display						display;
	
	private String						commands[];

	private ArrayList<CommandListener>	listeners;
	
	public Command(Display display, String command, String directory)
	{
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
		
		while (m.find())
		{
			String s = m.group(1);
			
			s = (s.startsWith("\"") || s.startsWith("'")) && (s.endsWith("\"") || s.endsWith("'")) ? s.substring(1, s.length() - 1) : s;
			
		    list.add(s);
		}
		
		init(display, list.toArray(new String[0]), directory);
	}
	
	public Command(Display display, String command[], String directory)
	{
//		this.command = command;
		init(display, command, directory);
	}
	
	private void init(Display display, String command[], String directory)
	{
		this.display   = display;
		
		this.directory = directory;
		
		this.commands  = command;
		
		listeners      = new ArrayList<CommandListener>();
	}
	
	public void execute() throws IOException
	{
		execute(null);
	}
	
	public void execute(String title) throws IOException
	{
//		System.out.println(Arrays.asList(commands) + ", " + directory);
		
		ProcessBuilder builder = new ProcessBuilder(commands);
		
		if (directory != null)
		{
			builder.directory(new File(directory));
		}
		
		final Process process = builder.start();
		
		program = new Program(process, title);
		
		new Thread()
		{
			public void run()
			{
				Display.getDefault().asyncExec(new Runnable()
				{
					public void run()
					{
						new Thread()
						{
							public void run()
							{
								int result = 0;
								
								try
								{
									LogStreamReader lsr = new LogStreamReader(display, process.getInputStream(), program, CONFIG_DATA.get("workspace.location") + "/");
									Thread thread = new Thread(lsr, "LogStreamReader");
									thread.start();
									
									BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
									
									try
									{
										while ((line = reader.readLine()) != null)
										{
											result = 1;
											
				    						if (program != null)
											{
				    							program.append(line + "\n");
											}
											
											if (!reader.ready())
											{
												break;
											}
										}
									}
									catch (IOException e)
									{
										result = 1;
										
										e.printStackTrace();
									}
									
									line = null;
									
									for (int i = listeners.size() - 1; i >= 0; i --)
									{
										listeners.get(i).resultReceived(result);
									}
									
									if (result == 0)
									{
										result = process.waitFor();
									
										thread.join();
										reader.close();
										lsr.stop();
									}
									
									process.waitFor();
									
									process.destroy();
									
									display.syncExec(new Runnable()
									{
										public void run()
										{
											for (int i = listeners.size() - 1; i >= 0; i --)
											{
												listeners.get(i).commandExecuted();
											}
										}
									});
								}
								catch (InterruptedException e)
								{
									e.printStackTrace();
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
							}
						}.start();
					}
				});
			}
		}.start();
	}
	
	public void addCommandListener(CommandListener lisetener)
	{
		listeners.add(lisetener);
	}
	
	/**
	 * Get the Program instance that this Command is using.
	 * 
	 * @return The Program instance that this Command is using.
	 */
	public Program getProgram()
	{
		return program;
	}
	
//	/**
//	 * Get the Process instance that this Command is using.
//	 * 
//	 * @return The Process instance that is Command is using.
//	 */
//	public Process getProcess()
//	{
//		return program.getProcess();
//	}
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

	private String			location, line;
	
	private Program			program;

	private BufferedReader	reader;
    
	private Display			display;
	
    public LogStreamReader(Display display, InputStream is, Program program, String location)
    {
    	this.location = location;
    	
        this.reader = new BufferedReader(new InputStreamReader(is));
        
        this.display = display;
        
        this.program = program;
        
        running = true;
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
            		display.syncExec(new Runnable()
    				{
    					public void run()
    					{
    						if (program != null)
							{
    							program.append(line + "\n");
							}
    					}
    				});
            	}
            	else
            	{
            		return;
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
}