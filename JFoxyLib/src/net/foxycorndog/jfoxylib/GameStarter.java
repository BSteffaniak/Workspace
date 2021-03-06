package net.foxycorndog.jfoxylib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.opengl.GL;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * This class serves as the entry point for all of the
 * OpenGL functionality. Once the start() method is
 * called, the loop will run until the Frame is closed
 * or the stop() method is called.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:44:47 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 10:36:47 PM
 * @version	v0.2
 */
public abstract class GameStarter
{
	private boolean			running;
	
	private	long			startTime;
	
	private	String			date;
	
	private	DateFormat		dateFormat;
	private	Calendar		calendar;
	
	private	ArrayList<File>	debugFiles;
	
	/**
	 * Initializes all of the necessary OpenGL components.
	 */
	public GameStarter()
	{
		debugFiles = new ArrayList<File>();
		
		dateFormat = new SimpleDateFormat("M/d/yyyy h:mm:ss");
		calendar   = Calendar.getInstance();
		
		date       = dateFormat.format(calendar.getTime()) + " " + (calendar.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
		
		startTime  = System.currentTimeMillis();
	}
	
	/**
	 * Tell JFoxyLib to output benchmark and debugging data to the file
	 * at the specified location.
	 * 
	 * @param location The location of the File to write the debugging
	 * 		data to.
	 */
	public void outputDebugging(String location)
	{
		debugFiles.add(new File(location));
	}
	
	/**
	 * Output the debugging data to the files that have been specified
	 * in the debugFiles ArrayList. These files were added when the
	 * outputDebugging(location) method was called.
	 */
	private void outputDebugging()
	{
		long now = System.currentTimeMillis();
		
		if (debugFiles.size() > 0)
		{
			for (int i = 0; i < debugFiles.size(); i++)
			{
				File file = debugFiles.get(i);
				
				try
				{
					BufferedReader reader = new BufferedReader(new FileReader(file));
					
					StringBuilder previousText = new StringBuilder();

					String line = reader.readLine();
					
					while (line != null)
					{
						previousText.append(line + "\r\n");
						
						line = reader.readLine();
					}
					
					reader.close();
					
					PrintWriter writer = new PrintWriter(new FileWriter(file));
					
					if (previousText.length() > 0)
					{
						writer.print(previousText);
					}
					
					writer.println("--Debugging run on " + date + "--");
					writer.println("Maximum FPS: " + Frame.getMaxFPS());
					writer.println("Minimum FPS: " + Frame.getMinFPS());
					writer.println("Average FPS: " + ((float)Frame.getTotalFPS() / Frame.getSecondsAlive()));
					writer.println("Time Elapsed: " + (now - startTime) / 1000.0 + " seconds");
					writer.println();
					
					writer.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Method that runs a loop that goes until the Frame
	 * is closed. The loop and render methods are called
	 * during each iteration of the loop.
	 */
	public void start()
	{
		if (!Frame.wasCreated())
		{
			throw new RuntimeException("The Frame must be created before you start.");
		}
		
		GL.setTextureScaleMinMethod(GL.LINEAR);
		GL.setTextureScaleMagMethod(GL.LINEAR);
		
		GL.setTextureWrapSMethod(GL.REPEAT);
		GL.setTextureWrapTMethod(GL.REPEAT);
		
		Frame.init();
		init();
		
		running  = true;
		
		try
		{
			while (!Display.isCloseRequested() && running)
			{
				
				
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	
				Frame.update();
				update();
				
				if (GL.is3DEnabled())
				{
					GL.resetMatrix();
					GL.viewPerspective(Frame.getWidth(), Frame.getHeight(), 0.01f, 99999f);
				
					render3D();
				
				}
				
				GL.resetMatrix();
				GL.viewOrtho(Frame.getWidth(), Frame.getHeight());
				
	//			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
	//			{
	//				GL11.glDisable(GL11.GL_LIGHTING);
					render2D();
	//			}
	//			GL11.glPopAttrib();
				
				Mouse.update();
				Keyboard.update();
				Font.update();
				
				Display.sync(Frame.getTargetFPS());
				Display.update();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Keyboard.destroy();
		Display.destroy();
		
		outputDebugging();
		
//		if (System.getProperty("os.name").toLowerCase().contains("mac"))
//		{
			System.exit(0);
//		}
	}
	
	/**
	 * Method that stops the loop from running.
	 */
	public void stop()
	{
		running = false;
	}
	
	/**
	 * Method in which you initialize all of your OpenGL data.
	 */
	public abstract void init();
	
	/**
	 * Method that is used to render through the orthographic
	 * mode. All two deminsional drawing should be done in here.
	 */
	public abstract void render2D();
	
	/**
	 * Method that is used to render through the perspective
	 * mode. All three deminsional drawing should be done in here.
	 */
	public abstract void render3D();
	
	/**
	 * Method that is right before the render methods are called.
	 * All calculations should be done in here.
	 */
	public abstract void update();
}
