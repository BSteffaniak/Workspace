package net.foxycorndog.tetris.menu;

import java.io.IOException;

import sun.swing.BakedArrayList;

import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Menu used to display the credits.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 5:19:54 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 5:19:54 PM
 * @version	v0.1
 */
public class CreditsMenu extends Menu
{
	private Image	creditsImage, backImage;
	
	private	Button	backButton;
	
	/**
	 * Create a CreditsMenu instance.
	 */
	public CreditsMenu(final MainMenu menu)
	{
		creditsImage = new Image(null);
		backImage    = new Image(null);
		
		try
		{
			creditsImage.setImage("res/images/peoplecredits.png");
			backImage.setImage("res/images/back.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		creditsImage.setAlignment(Image.CENTER, Image.CENTER);
		creditsImage.setLocation(0, 30);
		
		backButton = new Button(null);
		backButton.setAlignment(Button.CENTER, Button.CENTER);
		backButton.setImage(backImage);
		backButton.setLocation(0, -350);
		
		backButton.addButtonListener(new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				menu.closeCreditsMenu();
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		});
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#loop()
	 */
	public void loop()
	{
		
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#render()
	 */
	public void render()
	{
		GL.pushMatrix();
		{
			GL.scale(0.4f, 0.4f, 1);
			creditsImage.render();
			
			backButton.render();
		}
		GL.popMatrix();
	}
	
	/**
	 * @see net.foxycorndog.tetris.menu.Menu#dispose()
	 */
	public void dispose()
	{
		creditsImage.dispose();
		backButton.dispose();
		backImage.dispose();
	}
}