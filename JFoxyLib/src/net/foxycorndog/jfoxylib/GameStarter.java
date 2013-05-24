package net.foxycorndog.jfoxylib;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.opengl.GL;

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
	private boolean	running;
	
	private Frame	mainFrame;
	
	/**
	 * Initializes all of the necessary OpenGL components.
	 */
	public GameStarter()
	{
		
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
		
		init();
		
		running  = true;
		
		int fps  = 0;
		int dfps = 0;
		
		int pred = 0;
		
		long startTime  = System.currentTimeMillis();
		long newOldTime = System.nanoTime();
		long oldTime    = newOldTime;
		
		while (!Display.isCloseRequested() && running)
		{
			long newTime = System.currentTimeMillis();
			
			if (fps == 0 && dfps > 0)
			{
				newOldTime = System.nanoTime();
				
				int change = (int)(newOldTime - oldTime);
				
				if (change != 0)
				{
					pred = 1000000000 / change;
					
					Frame.setFPS(pred);
				}
			
				oldTime = newOldTime;
			}
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			Frame.loop();
			loop();
			
			GL.resetMatrix();
			GL.initPerspective(Frame.getWidth(), Frame.getHeight(), 0.01f, 99999f);
			
			render3D();
			
			GL.resetMatrix();
			GL.initOrtho(Frame.getWidth(), Frame.getHeight());
//			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
//			{
//				GL11.glDisable(GL11.GL_LIGHTING);
				render2D();
//			}
//			GL11.glPopAttrib();
			
			dfps++;
			
			if (startTime + 1000 <= newTime)
			{
				fps  = dfps;
				dfps = 0;
				
				startTime = newTime;
				
				Frame.setFPS(fps);
			}
			
			Mouse.update();
			Keyboard.update();
			
			Display.sync(Frame.getTargetFPS());
			Display.update();
		}
		
		Keyboard.destroy();
		Display.destroy();
//		System.exit(0);
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
	public abstract void loop();
}
