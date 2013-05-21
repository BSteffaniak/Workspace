package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;

/**
 * Menu that is used for editing game options.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 5:20:30 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 5:20:30 PM
 * @version	v0.1
 */
public class OptionsMenu extends Menu
{
	private	boolean	soundsAreOn;
	
	private	Image	soundsOn, soundsOff, backImage;
	
	private	Button	toggleSoundButton, backButton;
	
	/**
	 * Create an OptionsMenu instance for the specified MainMenu.
	 * 
	 * @param menu The MainMenu that this instance belongs to.
	 */
	public OptionsMenu(final MainMenu menu)
	{
		soundsOn  = new Image(null);
		soundsOff = new Image(null);
		backImage = new Image(null);
		
		try
		{
			soundsOn.setImage("res/images/soundson.png");
			soundsOff.setImage("res/images/soundsoff.png");
			backImage.setImage("res/images/back.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		soundsAreOn = true;
		
		backButton = new Button(null);
		backButton.setAlignment(Button.CENTER, Button.CENTER);
		backButton.setImage(backImage);
		backButton.setLocation(0, -40);
		
		toggleSoundButton = new Button(null);
		toggleSoundButton.setAlignment(Button.CENTER, Button.CENTER);
		toggleSoundButton.setImage(soundsOn);
		toggleSoundButton.setLocation(0, 40);
		
		soundsOn.getTexture().bind();
		soundsOff.getTexture().bind();
		
		ButtonListener buttonListener = new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				Button source = event.getSource();
				
				if (source == toggleSoundButton)
				{
					if (soundsAreOn)
					{
						toggleSoundButton.setImage(soundsOff);
						soundsAreOn = false;
						
						Tetris.SOUND_LIBRARY.mute();
					}
					else
					{
						toggleSoundButton.setImage(soundsOn);
						soundsAreOn = true;
						
						Tetris.SOUND_LIBRARY.unmute();
					}
				}
				else if (source == backButton)
				{
					menu.closeOptionsMenu();
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		};
		
		toggleSoundButton.addButtonListener(buttonListener);
		backButton.addButtonListener(buttonListener);
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
			GL.scale(0.75f, 0.75f, 1);
			
			toggleSoundButton.render();
			backButton.render();
		}
		GL.popMatrix();
	}

	/**
	 * @see net.foxycorndog.tetris.menu.Menu#dispose()
	 */
	public void dispose()
	{
//		toggleSoundButton.removeButtonListener(buttonListener);
//		quitButton.removeButtonListener(buttonListener);
		
		soundsOff.dispose();
		soundsOn.dispose();
		toggleSoundButton.dispose();
		backButton.dispose();
	}
}