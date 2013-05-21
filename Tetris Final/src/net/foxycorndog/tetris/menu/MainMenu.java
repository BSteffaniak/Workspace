package net.foxycorndog.tetris.menu;

import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.bundle.Bundle;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.jfoxyutil.Queue;
import net.foxycorndog.tetris.Tetris;

/**
 * Class that represents the first Menu that the user is faced with.
 * Also may be called the "Title menu." Has the basic functionality
 * needed to play the game, toggle options, view credits, or quit the
 * game.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 21, 2013 at 12:10:55 AM
 * @since	v0.1
 * @version	Apr 24, 2013 at 6:38:55 AM
 * @version	v0.1
 */
public class MainMenu extends Menu
{
	private	int				circleX, circleY;
	
	private Texture			titleScreenTexture, menuBoxTexture,
							circleTexture;
	
	private Button			playButton, optionsButton, creditsButton,
							quitButton;
	
	private Bundle			bundle;
	
	private OptionsMenu		optionsMenu;
	private	CreditsMenu		creditsMenu;
	
	private Tetris			tetris;
	
	private Queue<Integer>	queue;
	
	/**
	 * Create the MainMenu for the specified Tetris instance.
	 * 
	 * @param tetris The Tetris game to limk with this MainMenu.
	 */
	public MainMenu(final Tetris tetris)
	{
		this.tetris = tetris;
		
		bundle      = new Bundle(4 * 3, 2, true, false);
		
		queue       = new Queue<Integer>();
		
		Texture playButtonTexture    = null;
		Texture optionsButtonTexture = null;
		Texture creditsButtonTexture = null;
		Texture quitButtonTexture    = null;
		
		try
		{
			titleScreenTexture   = new Texture("res/images/titlescreen.png");
			menuBoxTexture       = new Texture("res/images/menu.png");
			circleTexture        = new Texture("res/images/circle.png");
			
			playButtonTexture    = new Texture("res/images/play.png");
			optionsButtonTexture = new Texture("res/images/options.png");
			creditsButtonTexture = new Texture("res/images/credits.png");
			quitButtonTexture    = new Texture("res/images/quit.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		bundle.beginEditingVertices();
		{
			bundle.addVertices(GL.genRectVerts(0, 0, titleScreenTexture.getWidth(), titleScreenTexture.getHeight()));
			bundle.addVertices(GL.genRectVerts(0, 0, menuBoxTexture.getWidth(), menuBoxTexture.getHeight()));
			bundle.addVertices(GL.genRectVerts(0, 0, circleTexture.getWidth(), circleTexture.getHeight()));
		}
		bundle.endEditingVertices();
		
		bundle.beginEditingTextures();
		{
			bundle.addTextures(GL.genRectTextures(titleScreenTexture.getImageOffsets()));
			bundle.addTextures(GL.genRectTextures(menuBoxTexture.getImageOffsets()));
			bundle.addTextures(GL.genRectTextures(circleTexture.getImageOffsets()));
		}
		bundle.endEditingTextures();
		
		// Make sure that the circleTexture is ready for rendering by
		// rendering it during the creation of the MainMenu.
		circleTexture.bind();
		
		playButton = new Button(null);
		playButton.setImage(playButtonTexture);
		playButton.setLocation(86, 230 - 38 * 0);
		
		optionsButton = new Button(null);
		optionsButton.setImage(optionsButtonTexture);
		optionsButton.setLocation(86, 230 - 38 * 1);
		
		creditsButton = new Button(null);
		creditsButton.setImage(creditsButtonTexture);
		creditsButton.setLocation(86, 230 - 38 * 2);
		
		quitButton = new Button(null);
		quitButton.setImage(quitButtonTexture);
		quitButton.setLocation(86, 232 - 38 * 3);
		
		ButtonListener listener = new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				if (!queue.isEmpty())
				{
					queue.dequeue();
				}
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				if (event.getSource() == playButton)
				{
					tetris.playGame();
				}
				else if (event.getSource() == optionsButton)
				{
					openOptionsMenu();
				}
				else if (event.getSource() == creditsButton)
				{
					openCreditsMenu();
				}
				else if (event.getSource() == quitButton)
				{
//					System.exit(0);
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				Button button  = event.getSource();
				
				circleX = button.getX();
				circleY = button.getY() - 5;
				
				queue.enqueue(circleY);
			}
		};
		
		playButton.addButtonListener(listener);
		optionsButton.addButtonListener(listener);
		creditsButton.addButtonListener(listener);
		quitButton.addButtonListener(listener);
	}
	
	/**
	 * Open the options menu into the main view.
	 */
	private void openOptionsMenu()
	{
		optionsMenu = new OptionsMenu(this);
		
		leaveMainMenuFocus();
	}
	
	/**
	 * Open the credits menu into the main view.
	 */
	private void openCreditsMenu()
	{
		creditsMenu = new CreditsMenu(this);
		
		leaveMainMenuFocus();
	}

	/**
	 * Close the options menu and return to the main menu.
	 */
	public void closeOptionsMenu()
	{
		optionsMenu.dispose();
		optionsMenu = null;
		
		returnToMainMenu();
	}

	/**
	 * Close the credits menu and return to the main menu.
	 */
	public void closeCreditsMenu()
	{
		creditsMenu.dispose();
		creditsMenu = null;
		
		returnToMainMenu();
	}

	/**
	 * Return to the main menu and restore its functionality.
	 */
	private void returnToMainMenu()
	{
		playButton.setEnabled(true);
		optionsButton.setEnabled(true);
		creditsButton.setEnabled(true);
		quitButton.setEnabled(true);
	}
	
	/**
	 * Take away the focus from the MainMenu.
	 */
	private void leaveMainMenuFocus()
	{
		playButton.setEnabled(false);
		optionsButton.setEnabled(false);
		creditsButton.setEnabled(false);
		quitButton.setEnabled(false);
	}
	
	/**
	 * Method called each frame to do logic.
	 */
	public void loop()
	{
		
	}
	
	/**
	 * Render the MainMenu and all of its components to the screen.
	 */
	public void render()
	{
		float width  = (float)titleScreenTexture.getWidth();
		float height = (float)titleScreenTexture.getHeight();
		
		float scaleX = Frame.getWidth()  / width;
		float scaleY = Frame.getHeight() / height;
		
		float min    = scaleX < scaleY ? scaleX : scaleY;
		
		float xOff   = (Frame.getWidth()  - (width  * min)) / 2f;
		float yOff   = (Frame.getHeight() - (height * min)) / 2f;
		
		GL.pushMatrix();
		{
			GL.pushAttrib(GL.TEXTURE_BIT);
			{
				GL.setTextureScaleMinMethod(GL.LINEAR);
				GL.setTextureScaleMagMethod(GL.LINEAR);
				
				GL.translate(xOff + getX(), yOff + getY(), 0);
				GL.scale(min, min, 1);
				
				GL.setColor(tetris.getRf(), tetris.getGf(), tetris.getBf(), 1);
				bundle.render(GL.QUADS, 0, 4, titleScreenTexture);
				
				if (optionsMenu != null)
				{
					optionsMenu.render();
				}
				else if (creditsMenu != null)
				{
					creditsMenu.render();
				}
				else
				{
					bundle.render(GL.QUADS, 4, 4, menuBoxTexture);
					
					renderButtons();
				}
				
				Texture.unbind();
			}
			GL.popAttrib();
		}
		GL.popMatrix();
	}
	
	/**
	 * Render the Buttons to their correct place.
	 */
	private void renderButtons()
	{
		GL.setColor((tetris.getR() + 100) / 255f, (tetris.getG() + 100) / 255f, (tetris.getB() + 100) / 255f, 1);
		
		playButton.render();
		optionsButton.render();
		creditsButton.render();
		
		if (circleY != quitButton.getY() - 5 || queue.size() <= 0)
		{
			quitButton.render();
		}
		else
		{
			GL.setColor(1, 0, 0, 1);
			Tetris.getFont().render("NO!!", quitButton.getX() + 50, quitButton.getY() - 8, 0, null);
		}
		
		if (queue.size() > 0)
		{
			GL.translate(circleX, circleY, 0);
			bundle.render(GL.QUADS, 4 * 2, 4, circleTexture);
		}
	}
	
	/**
	 * Dispose the Components used in the MainMenu.
	 */
	public void dispose()
	{
		playButton.dispose();
		optionsButton.dispose();
		creditsButton.dispose();
		quitButton.dispose();
	}
}
