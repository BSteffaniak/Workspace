package net.foxycorndog.tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.GameStarter;
import net.foxycorndog.jfoxylib.font.Font;
import net.foxycorndog.jfoxylib.input.Mouse;
import net.foxycorndog.jfoxylib.openal.Sound;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.tetris.board.AbstractBoard;
import net.foxycorndog.tetris.board.Board;
import net.foxycorndog.tetris.board.Color;
import net.foxycorndog.tetris.menu.MainMenu;
import net.foxycorndog.tetris.sidebar.Sidebar;
import net.foxycorndog.tetris.sound.SoundLibrary;

/**
 * Main class and entry point for the Tetris game.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 20, 2013 at 11:17:19 PM
 * @since	v0.1
 * @version	Apr 22, 2013 at 11:17:19 PM
 * @version	v0.1
 */
public class Tetris extends GameStarter
{
	private boolean 					rUp, gUp, bUp;
	
	private int							r, g, b;
	
	private float						counter;
	
	private Board						board;
	private Sidebar						sidebar;
	
	private MainMenu					mainMenu;
	
	private static Font					font;
	
	public static final SoundLibrary	SOUND_LIBRARY = createSoundLibrary();
	
	/**
	 * Main method that creates a Frame and starts up the Tetris
	 * game.
	 * 
	 * @param args The unused command line arguments.
	 */
	public static void main(String args[])
	{
		Frame.create((int)(390 * 1.4), (int)(510 * 1.4));
		Frame.setResizable(false);
		Frame.setTitle("Tetris");
		Frame.setTargetFPS(60);
		
		Tetris game = new Tetris();
		
		game.start();
	}
	
	/**
	 * Tell the game to start.
	 */
	public void playGame()
	{
		mainMenu.dispose();
		mainMenu = null;
		
		board    = new Board(10, 20, 10, this);
		sidebar  = new Sidebar();
		
		board.addListener(sidebar.getScoreboard());
		board.addListener(sidebar.getLinesCompleted());
		
		board.newGame();
		board.move(50, 50);
	}
	
	/**
	 * The method to do the game's OpenGL initialization.
	 */
	public void init()
	{
		font = new Font("res/images/font.gif", 15, 6, new char[]
		{
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'A', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'a', 'a', '&', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '(', '$', ':', '.', ',', '!', '?', ')',
			' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ',
		});
		
		try
		{
			SOUND_LIBRARY.addSound("pop.wav", new Sound("res/sounds/pop.wav"));
			SOUND_LIBRARY.addSound("lineremoved.wav", new Sound("res/sounds/lineremoved.wav"));
			SOUND_LIBRARY.addSound("lose.wav", new Sound("res/sounds/lose.wav"));
			SOUND_LIBRARY.addSound("music.wav", new Sound("res/sounds/music.wav"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		r = 235;
		g = 235;
		b = 235;
		
		openMainMenu();
	}
	
	public void openMainMenu()
	{
		if (board != null)
		{
			board.dispose();
		}
		
		board   = null;
		sidebar = null;
		
		mainMenu = new MainMenu(this);
	}
	
	/**
	 * Get the Sidebar of the Tetris game.
	 * 
	 * @return The Sidebar of the Tetris game.
	 */
	public Sidebar getSidebar()
	{
		return sidebar;
	}

	/**
	 * Method used to render the 2D shapes to the screen each frame.
	 */
	public void render2D()
	{
		GL.setClearColor(getRf() / 2, getGf() / 2, getBf() / 2, 1);
		
		if (mainMenu != null)
		{
			mainMenu.render();
		}
		else
		{
			board.render();
			sidebar.render();
		}
	}

	/**
	 * Method to render the 3D shapes to the screen each frame.
	 */
	public void render3D()
	{
		
	}

	/**
	 * Method to calculate logic each frame.
	 */
	public void loop()
	{
		float delta = 60f / Frame.getFPS();
		
		if (!Float.isInfinite(delta) && !Float.isNaN(delta))
		{
			counter += delta;
		}
		
		if (counter >= 1)
		{
			updateColor();
			
			counter %= 1;
		}
		
		if (mainMenu != null)
		{
			mainMenu.loop();
		}
		else
		{
			board.loop();
		}
		
		int x = Mouse.getDisplayX() - Frame.getX();
		int y = Mouse.getDisplayY() - Frame.getY();
		
		int closeX = Frame.getWidth() - 50;
		int closeY = -20;
		
		int closeWidth  = 82;
		int closeHeight = 50;
		
		int dx = Mouse.getDX();
		int dy = Mouse.getDY();
		
		if (x > closeX && x < closeX + closeWidth && y > closeY && y < closeY + closeHeight)
		{
//			Frame.setLocation(Frame.getX() + dx, Frame.getY() + dy);
		}
	}
	
	/**
	 * Set the color that the Menu will be rendered in.
	 */
	private void updateColor()
	{
		r = rUp ? r + 1 : r - 1;
		r = r >= 256 ? 255 : r;
		r = r <= 100 ? 100 : r;
		
		g = gUp ? g + 1 : g - 1;
		g = g >= 256 ? 255 : g;
		g = g <= 100 ? 100 : g;
		
		b = bUp ? b + 1 : b - 1;
		b = b >= 256 ? 255 : b;
		b = b <= 100 ? 100 : b;
		
		if ((int)(Math.random() * 100) == 0)
		{
			rUp = !rUp;
		}
		if ((int)(Math.random() * 100) == 0)
		{
			gUp = !gUp;
		}
		if ((int)(Math.random() * 100) == 0)
		{
			bUp = !bUp;
		}
	}
	
	public int getR()
	{
		return r;
	}
	
	public int getG()
	{
		return g;
	}
	
	public int getB()
	{
		return b;
	}
	
	public float getRf()
	{
		return r / 255f;
	}
	
	public float getGf()
	{
		return g / 255f;
	}
	
	public float getBf()
	{
		return b / 255f;
	}
	
	/**
	 * Get the Tetris game default Font instance.
	 * 
	 * @return The Tetris game default Font instance.
	 */
	public static Font getFont()
	{
		return font;
	}
	
	/**
	 * Create the final SoundLibrary instance.
	 * 
	 * @return The final SoundLibrary instance.
	 */
	private static final SoundLibrary createSoundLibrary()
	{
		SoundLibrary library = new SoundLibrary();
		
		return library;
	}
}
